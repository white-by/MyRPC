package provider;

import com.simplerpc.registry.LocalRegistry;
import com.simplerpc.server.HttpServer;
import com.simplerpc.server.VertxHttpServer;
import provider.impl.*;
import service.*;

import java.util.Scanner;

public class SimpleProviderExample {
    public static void main(String[] args) {
//        int serverPort = readPortFromCommandLine();

        //注册
        LocalRegistry.register(UserService.class.getName(), UserServiceImpl.class);
        LocalRegistry.register(AddService.class.getName(), AddServiceImpl.class);
        LocalRegistry.register(SubService.class.getName(), SubServiceImpl.class);
        LocalRegistry.register(MulService.class.getName(), MulServiceImpl.class);
        LocalRegistry.register(DivService.class.getName(), DivServiceImpl.class);
        LocalRegistry.register(ModuloService.class.getName(), ModuloServiceImpl.class);
        LocalRegistry.register(AccumulateService.class.getName(), AccumulateServiceImpl.class);
        LocalRegistry.register(FactorialService.class.getName(), FactorialServiceImpl.class);
        LocalRegistry.register(SayHiService.class.getName(), SayHiServiceImpl.class);
        LocalRegistry.register(SayWelcomeService.class.getName(), SayWelcomeServiceImpl.class);

        HttpServer httpServer = new VertxHttpServer();
        httpServer.doStart(8081);
    }

    // 从命令行读取端口号
    private static int readPortFromCommandLine() {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter server port number: ");
        return scanner.nextInt();
    }
}
