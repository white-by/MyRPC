package provider;

import com.myrpc.RpcApplication;
import com.myrpc.config.RegistryConfig;
import com.myrpc.config.RpcConfig;
import com.myrpc.model.ServiceMetaInfo;
import com.myrpc.registry.LocalRegistry;
import com.myrpc.registry.Registry;
import com.myrpc.registry.RegistryFactory;
import com.myrpc.server.HttpServer;
import com.myrpc.server.VertxHttpServer;
import provider.impl.*;
import service.*;

import java.util.Scanner;

public class Provider {
    public static void main(String[] args) {
//        int serverPort = readPortFromCommandLine();
//
//        // 初始化RpcConfig，动态设置端口号
        RpcConfig rpcConfig = new RpcConfig();
//        rpcConfig.setServerPort(serverPort);
        RpcApplication.init(rpcConfig);

        // 注册服务
        registerService(UserService.class, UserServiceImpl.class, rpcConfig);
        registerService(AddService.class, AddServiceImpl.class, rpcConfig);
        registerService(SubService.class, SubServiceImpl.class, rpcConfig);
        registerService(MulService.class, MulServiceImpl.class, rpcConfig);
        registerService(DivService.class, DivServiceImpl.class, rpcConfig);
        registerService(ModuloService.class, ModuloServiceImpl.class, rpcConfig);
        registerService(AccumulateService.class, AccumulateServiceImpl.class, rpcConfig);
        registerService(FactorialService.class, FactorialServiceImpl.class, rpcConfig);
        registerService(SayHiService.class, SayHiServiceImpl.class, rpcConfig);
        registerService(SayWelcomeService.class, SayWelcomeServiceImpl.class, rpcConfig);

        // 启动HttpServer
        HttpServer httpServer = new VertxHttpServer();
        httpServer.doStart(rpcConfig.getServerPort());
    }

    // 从命令行读取端口号
    private static int readPortFromCommandLine() {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter server port number: ");
        return scanner.nextInt();
    }

    // 注册服务的方法
    private static <T> void registerService(Class<T> serviceClass, Class<? extends T> implClass, RpcConfig rpcConfig) {
        // 注册服务到本地注册中心
        String serviceName = serviceClass.getName();
        LocalRegistry.register(serviceName, implClass);

        // 获取注册中心配置
        RegistryConfig registryConfig = rpcConfig.getRegistryConfig();
        Registry registry = RegistryFactory.getInstance(registryConfig.getRegistry());

        // 注册服务到注册中心
        ServiceMetaInfo serviceMetaInfo = new ServiceMetaInfo();
        serviceMetaInfo.setServiceName(serviceName);
        serviceMetaInfo.setServiceHost(rpcConfig.getServerHost());
        serviceMetaInfo.setServicePort(rpcConfig.getServerPort());

        try {
            registry.register(serviceMetaInfo);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
