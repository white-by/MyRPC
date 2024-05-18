package com.myrpc.config;

import lombok.Data;

@Data
public class RpcConfig {
    private String name = "MyRPC";
    private String version = "1.0";
    private String serverHost = "localhost";
    private Integer serverPort = 8080;
}
