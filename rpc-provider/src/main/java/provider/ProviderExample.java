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
import service.UserService;

public class ProviderExample {
    public static void main(String[] args) {
        RpcApplication.init();

//        System.out.println("whiteby1");
        //注册
        String serviceName = UserService.class.getName();
        LocalRegistry.register(serviceName, UserServiceImpl.class);
//        System.out.println("whiteby2");

        RpcConfig rpcConfig = RpcApplication.getRpcConfig();
        RegistryConfig registryConfig = rpcConfig.getRegistryConfig();
        Registry registry = RegistryFactory.getInstance(registryConfig.getRegistry());
        ServiceMetaInfo serviceMetaInfo = new ServiceMetaInfo();
        serviceMetaInfo.setServiceName(serviceName);
        serviceMetaInfo.setServiceHost(rpcConfig.getServerHost());
        serviceMetaInfo.setServicePort(rpcConfig.getServerPort());
        System.out.println(serviceName+rpcConfig.getServerHost()+rpcConfig.getServerPort());
//        System.out.println("whiteby3");
        try {
//            System.out.println("w1");
            registry.register(serviceMetaInfo);
//            System.out.println("w2");
        }
        catch (Exception e) {
            throw new RuntimeException(e);
        }
//        System.out.println("whiteby4");

        HttpServer httpServer = new VertxHttpServer();
        httpServer.doStart(RpcApplication.getRpcConfig().getServerPort());
    }
}
