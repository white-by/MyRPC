package com.myrpc.proxy;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.IdUtil;
import cn.hutool.http.HttpRequest;
import cn.hutool.http.HttpResponse;
import com.myrpc.RpcApplication;
import com.myrpc.config.RpcConfig;
import com.myrpc.constant.RpcConstant;
import com.myrpc.fault.retry.RetryStrategy;
import com.myrpc.fault.retry.RetryStrategyFactory;
import com.myrpc.fault.tolerant.TolerantStrategy;
import com.myrpc.fault.tolerant.TolerantStrategyFactory;
import com.myrpc.loadbalancer.LoadBalancer;
import com.myrpc.loadbalancer.LoadBalancerFactory;
import com.myrpc.model.RpcRequest;
import com.myrpc.model.RpcResponse;
import com.myrpc.model.ServiceMetaInfo;
import com.myrpc.protocol.*;
import com.myrpc.registry.Registry;
import com.myrpc.registry.RegistryFactory;
import com.myrpc.serializer.Serializer;
import com.myrpc.serializer.SerializerFactory;
import com.myrpc.server.tcp.VertxTcpClient;
import io.vertx.core.Vertx;
import io.vertx.core.buffer.Buffer;
import io.vertx.core.net.NetClient;

import java.io.IOException;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

public class ServiceProxy implements InvocationHandler {
    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable{
        // 指定序列化器
        Serializer serializer = SerializerFactory.getInstance(RpcApplication.getRpcConfig().getSerializer());

        // 构造请求
        String serviceName = method.getDeclaringClass().getName();
        RpcRequest rpcRequest = RpcRequest.builder()
                .serviceName(method.getDeclaringClass().getName())
                .methodName(method.getName())
                .parameterTypes(method.getParameterTypes())
                .args(args)
                .build();
        try {
            // 序列化
            byte[] bodyBytes = serializer.mySerialize(rpcRequest);

            //从注册中心获取服务提供者请求地址
            RpcConfig rpcConfig = RpcApplication.getRpcConfig();
            Registry registry = RegistryFactory.getInstance(rpcConfig.getRegistryConfig().getRegistry());
            ServiceMetaInfo serviceMetaInfo = new ServiceMetaInfo();
            serviceMetaInfo.setServiceName(serviceName);
            serviceMetaInfo.setServiceVersion(RpcConstant.DEFAULT_SERVICE_VERSION);
            List<ServiceMetaInfo> serviceMetaInfoList = registry.serviceDiscovery(serviceMetaInfo.getServiceKey());
            if (CollUtil.isEmpty(serviceMetaInfoList)) {
                throw new RuntimeException("暂无服务地址 :(");
            }
            //暂时获取第一个
//            ServiceMetaInfo selectedServiceMetaInfo = serviceMetaInfoList.get(0);
            LoadBalancer loadBalancer = LoadBalancerFactory.getInstance(rpcConfig.getLoadBalancer());
            Map<String, Object> requestParams = new HashMap<>();
            requestParams.put("methodName", rpcRequest.getMethodName());
            ServiceMetaInfo selectedServiceMetaInfo = loadBalancer.select(requestParams, serviceMetaInfoList);

            //发送tcp请求
//            RpcResponse rpcResponse = new VertxTcpClient().doRequest(rpcRequest, selectedServiceMetaInfo);
//            return rpcResponse.getData();
            //使用重试机制发送 HTTP 请求
            RetryStrategy retryStrategy = RetryStrategyFactory.getInstance(rpcConfig.getRetryStrategy());
            RpcResponse rpcResponse = retryStrategy.doRetry(() -> {
                try (HttpResponse httpResponse = HttpRequest.post(selectedServiceMetaInfo.getServiceAddress())
                        .body(bodyBytes)
                        .execute()) {
                    byte[] result = httpResponse.bodyBytes();
                    return serializer.myDeserialize(result, RpcResponse.class);
                } catch (Exception e) {
                    throw new RuntimeException("HTTP request failed", e);
                }
            });

            return rpcResponse.getData();

        } catch (Exception e) {
            // 容错机制
            TolerantStrategy tolerantStrategy = TolerantStrategyFactory.getInstance(RpcApplication.getRpcConfig().getTolerantStrategy());
            RpcResponse rpcResponse = tolerantStrategy.doTolerant(null, e);
            return rpcResponse.getData();
        }
    }
}