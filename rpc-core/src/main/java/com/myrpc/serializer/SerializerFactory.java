package com.myrpc.serializer;

import com.myrpc.spi.SpiLoader;

public class SerializerFactory {
    static {
        SpiLoader.load(Serializer.class);
    }

    //获取实例
    public static Serializer getInstance(String key) {
        return SpiLoader.getInstance(Serializer.class, key);
    }

}