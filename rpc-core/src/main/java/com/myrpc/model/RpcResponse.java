package com.myrpc.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class RpcResponse implements Serializable {
    //响应数据
    private Object data;

    //响应数据类型
    private Class<?> dataType;

    //响应消息
    private String message;

    //异常消息
    private Exception exception;

}

