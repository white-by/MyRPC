package com.simplerpc.registry;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class LocalRegistry {
    //存储
    public static final Map<String, Class<?>> map = new ConcurrentHashMap<>();

    //注册
    public static void register(String serviceName, Class<?> implClass) {
        map.put(serviceName, implClass);
    }

    //获取
    public static Class<?> get(String serviceName) {
        return map.get(serviceName);
    }

    //删除
    public static void remove(String serviceName) {
        map.remove(serviceName);
    }
}
