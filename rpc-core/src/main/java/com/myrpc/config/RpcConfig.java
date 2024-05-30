package com.myrpc.config;

import com.myrpc.fault.retry.RetryStrategyKeys;
import com.myrpc.fault.tolerant.TolerantStrategyKeys;
import com.myrpc.loadbalancer.LoadBalancerKeys;
import com.myrpc.serializer.SerializerKeys;
import lombok.Data;

@Data
public class RpcConfig {
    private String name = "MyRPC";
    private String version = "1.0";
    private String serverHost = "localhost";
    private Integer serverPort = 8088;

    private String loadBalancer = LoadBalancerKeys.CONSISTENT_HASH;

    private String retryStrategy = RetryStrategyKeys.NO;

    private String tolerantStrategy = TolerantStrategyKeys.FAIL_FAST;

    //序列化器
    private String serializer = SerializerKeys.JDK;
    //注册中心配置
    private RegistryConfig registryConfig = new RegistryConfig();


}
