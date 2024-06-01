package proxy;

import cn.hutool.http.HttpRequest;
import cn.hutool.http.HttpResponse;
import model.RpcRequest;
import model.RpcResponse;
import com.simplerpc.serializer.JdkSerializer;
import com.simplerpc.serializer.Serializer;

import java.io.IOException;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

public class ServiceProxy implements InvocationHandler {
    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        // 指定序列化器
        Serializer serializer = new JdkSerializer();

        // 构造请求
        RpcRequest rpcRequest = RpcRequest.builder()
                .serviceName(method.getDeclaringClass().getName())
                .methodName(method.getName())
                .parameterTypes(method.getParameterTypes())
                .args(args)
                .build();
        try {
            // 序列化
            byte[] bodyBytes = serializer.mySerialize(rpcRequest);
            // 发送请求
            try (HttpResponse httpResponse = HttpRequest.post("http://localhost:8080")
                    .body(bodyBytes)
                    .execute()) {
                byte[] result = httpResponse.bodyBytes();
                // 反序列化
                RpcResponse rpcResponse = serializer.myDeserialize(result, RpcResponse.class);
                return rpcResponse.getData();
            }
        }
        catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }
}

