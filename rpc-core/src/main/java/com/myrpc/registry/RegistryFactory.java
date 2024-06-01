package com.myrpc.registry;

import com.myrpc.spi.SpiLoader;

public class RegistryFactory {
    // SPI 动态加载
    static {
        SpiLoader.load(Registry.class);
    }

    //获取实例
    public static Registry getInstance(String key) {
        return SpiLoader.getInstance(Registry.class, key);
    }

}
