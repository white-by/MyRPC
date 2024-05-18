package provider;

import com.myrpc.registry.LocalRegistry;
import com.myrpc.server.HttpServer;
import com.myrpc.server.VertxHttpServer;
import service.UserService;

public class SimpleProviderExample {
    public static void main(String[] args) {
        //注册
        LocalRegistry.register(UserService.class.getName(), UserServiceImpl.class);

        HttpServer httpServer = new VertxHttpServer();
        httpServer.doStart(8080);
    }
}
