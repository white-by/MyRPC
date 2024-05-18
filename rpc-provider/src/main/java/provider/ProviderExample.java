package provider;

import com.myrpc.RpcApplication;
import com.myrpc.registry.LocalRegistry;
import com.myrpc.server.HttpServer;
import com.myrpc.server.VertxHttpServer;
import service.UserService;

public class ProviderExample {
    public static void main(String[] args) {
        RpcApplication.init();
        LocalRegistry.register(UserService.class.getName(), UserServiceImpl.class);

        HttpServer httpServer = new VertxHttpServer();
        httpServer.doStart(RpcApplication.getRpcConfig().getServerPort());
    }
}
