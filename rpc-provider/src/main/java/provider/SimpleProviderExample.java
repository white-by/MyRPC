package provider;

import registry.LocalRegistry;
import server.HttpServer;
import server.VertxHttpServer;
import service.UserService;

public class SimpleProviderExample {
    public static void main(String[] args) {
        //注册
        LocalRegistry.register(UserService.class.getName(), UserServiceImpl.class);

        HttpServer httpServer = new VertxHttpServer();
        httpServer.doStart(8080);
    }
}
