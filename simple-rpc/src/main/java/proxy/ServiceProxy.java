//package proxy;
//
//import cn.hutool.http.HttpRequest;
//import cn.hutool.http.HttpResponse;
//import com.simplerpc.config.Config;
//import model.RpcRequest;
//import model.RpcResponse;
//import com.simplerpc.serializer.JdkSerializer;
//import com.simplerpc.serializer.Serializer;
//
//import java.io.IOException;
//import java.lang.reflect.InvocationHandler;
//import java.lang.reflect.Method;
//
//public class ServiceProxy implements InvocationHandler {
//    @Override
//    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
//        // 指定序列化器
//        Serializer serializer = new JdkSerializer();
//
//        // 构造请求
//        RpcRequest rpcRequest = RpcRequest.builder()
//                .serviceName(method.getDeclaringClass().getName())
//                .methodName(method.getName())
//                .parameterTypes(method.getParameterTypes())
//                .args(args)
//                .build();
//        try {
//            // 序列化
//            byte[] bodyBytes = serializer.mySerialize(rpcRequest);
//            // 发送请求
//            Config config = new Config();
//            String ip = config.getIp();
//            String port = String.valueOf(config.getPort());
//            String url = "http://" + ip + ":" +port;
//            try (HttpResponse httpResponse = HttpRequest.post(url)//"http://localhost:8080"
//                    .body(bodyBytes)
//                    .execute()) {
//                byte[] result = httpResponse.bodyBytes();
//                // 反序列化
//                RpcResponse rpcResponse = serializer.myDeserialize(result, RpcResponse.class);
//                return rpcResponse.getData();
//            }
//        }
//        catch (IOException e) {
//            e.printStackTrace();
//        }
//
//        return null;
//    }
//}
//

package proxy;

import com.simplerpc.config.Config;
import model.RpcRequest;
import model.RpcResponse;
import com.simplerpc.serializer.JdkSerializer;
import com.simplerpc.serializer.Serializer;

import java.io.*;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.net.HttpURLConnection;
import java.net.URL;

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

            // 配置请求
            Config config = new Config();
            String ip = config.getIp();
            String port = String.valueOf(config.getPort());
            String urlString = "http://" + ip + ":" + port;

            // 创建 URL 和 HttpURLConnection
            URL url = new URL(urlString);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("POST");
            connection.setDoOutput(true);
            connection.setRequestProperty("Content-Type", "application/octet-stream");

            // 发送请求
            try (OutputStream outputStream = connection.getOutputStream()) {
                outputStream.write(bodyBytes);
                outputStream.flush();
            }

            // 获取响应
            int responseCode = connection.getResponseCode();
            if (responseCode == HttpURLConnection.HTTP_OK) {
                try (InputStream inputStream = connection.getInputStream();
                     ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream()) {
                    byte[] buffer = new byte[1024];
                    int length;
                    while ((length = inputStream.read(buffer)) != -1) {
                        byteArrayOutputStream.write(buffer, 0, length);
                    }
                    byte[] resultBytes = byteArrayOutputStream.toByteArray();
                    // 反序列化
                    RpcResponse rpcResponse = serializer.myDeserialize(resultBytes, RpcResponse.class);
                    return rpcResponse.getData();
                }
            } else {
                throw new IOException("HTTP request failed with code " + responseCode);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }
}
