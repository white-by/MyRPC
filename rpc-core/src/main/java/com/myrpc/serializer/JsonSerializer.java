package com.myrpc.serializer;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.myrpc.model.RpcRequest;
import com.myrpc.model.RpcResponse;

import java.io.IOException;

public class JsonSerializer implements Serializer {
    private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();

    @Override
    public <T> byte[] mySerialize(T obj) throws IOException {
        return OBJECT_MAPPER.writeValueAsBytes(obj);
    }

    @Override
    public <T> T myDeserialize(byte[] bytes, Class<T> targetClass) throws IOException {
        T obj = OBJECT_MAPPER.readValue(bytes, targetClass);
        if (obj instanceof RpcRequest) {
            return handleRequest((RpcRequest) obj, targetClass);
        }
        if (obj instanceof RpcResponse) {
            return handleResponse((RpcResponse) obj, targetClass);
        }
        return obj;
    }

    private <T> T handleRequest(RpcRequest rpcRequest, Class<T> targetClass) throws IOException {
        Class<?>[] parameterTypes = rpcRequest.getParameterTypes();
        Object[] args = rpcRequest.getArgs();

        for (int i = 0; i < parameterTypes.length; i++) {
            Class<?> clazz = parameterTypes[i];
            if (args[i] != null && !clazz.isAssignableFrom(args[i].getClass())) {
                byte[] argBytes = OBJECT_MAPPER.writeValueAsBytes(args[i]);
                args[i] = OBJECT_MAPPER.readValue(argBytes, clazz);
            }
        }
        return targetClass.cast(rpcRequest);
    }

    private <T> T handleResponse(RpcResponse rpcResponse, Class<T> targetClass) throws IOException {
        if (rpcResponse.getData() != null) {
            byte[] dataBytes = OBJECT_MAPPER.writeValueAsBytes(rpcResponse.getData());
            rpcResponse.setData(OBJECT_MAPPER.readValue(dataBytes, rpcResponse.getDataType()));
        }
        return targetClass.cast(rpcResponse);
    }
}
