package com.myrpc.model;

import cn.hutool.core.util.StrUtil;
import com.myrpc.constant.RpcConstant;
import lombok.Data;

@Data
public class ServiceMetaInfo {

    //服务名称
    private String serviceName;

    //服务版本号
    private String serviceVersion = RpcConstant.DEFAULT_SERVICE_VERSION;

    //服务域名
    private String serviceHost;

    //服务端口号
    private Integer servicePort;

    //服务分组（暂未实现）
    private String serviceGroup = "default";


    //获取服务键名
    public String getServiceKey() {
        return String.format("%s:%s", serviceName, serviceVersion);
    }


    //获取服务注册节点键名
    public String getServiceNodeKey() {
        return String.format("%s/%s:%s", getServiceKey(), serviceHost, servicePort);
    }

    //获取完整服务地址
    public String getServiceAddress() {
        if (!StrUtil.contains(serviceHost, "http")) {
            return String.format("http://%s:%s", serviceHost, servicePort);
        }
        return String.format("%s:%s", serviceHost, servicePort);
    }
}

