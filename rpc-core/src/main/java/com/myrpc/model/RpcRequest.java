package com.myrpc.model;

import com.myrpc.constant.RpcConstant;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class RpcRequest implements Serializable {
    //服务名称
    private String serviceName;

    //方法名称
    private String methodName;

    //服务版本
    private String serviceVersion = RpcConstant.DEFAULT_SERVICE_VERSION;

    //参数类型列表
    private Class<?>[] parameterTypes;

    //参数列表
    private Object[] args;

}
