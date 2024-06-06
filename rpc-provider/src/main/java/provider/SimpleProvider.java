//package provider;
//
//import com.simplerpc.config.Config;
//import com.simplerpc.registry.LocalRegistry;
//import com.simplerpc.server.HttpServer;
//import com.simplerpc.server.VertxHttpServer;
//import provider.impl.*;
//import service.*;
//
//import java.util.Scanner;
//
//public class SimpleProviderExample {
//    public static void main(String[] args) {
//        Config config = new Config();
//        int serverPort = readPortFromCommandLine();
//        config.setPort(serverPort);
//
//        //注册
//        LocalRegistry.register(UserService.class.getName(), UserServiceImpl.class);
//        LocalRegistry.register(AddService.class.getName(), AddServiceImpl.class);
//        LocalRegistry.register(SubService.class.getName(), SubServiceImpl.class);
//        LocalRegistry.register(MulService.class.getName(), MulServiceImpl.class);
//        LocalRegistry.register(DivService.class.getName(), DivServiceImpl.class);
//        LocalRegistry.register(ModuloService.class.getName(), ModuloServiceImpl.class);
//        LocalRegistry.register(AccumulateService.class.getName(), AccumulateServiceImpl.class);
//        LocalRegistry.register(FactorialService.class.getName(), FactorialServiceImpl.class);
//        LocalRegistry.register(SayHiService.class.getName(), SayHiServiceImpl.class);
//        LocalRegistry.register(SayWelcomeService.class.getName(), SayWelcomeServiceImpl.class);
//
//        HttpServer httpServer = new VertxHttpServer();
//        httpServer.doStart(8080);
//    }
//
//    // 从命令行读取端口号
//    private static int readPortFromCommandLine() {
//        Scanner scanner = new Scanner(System.in);
//        System.out.print("Enter server port number: ");
//        return scanner.nextInt();
//    }
//}

package provider;

import com.simplerpc.config.Config;
import com.simplerpc.registry.LocalRegistry;
import com.simplerpc.server.HttpServer;
import com.simplerpc.server.VertxHttpServer;
import provider.impl.*;
import service.*;

public class SimpleProvider {
    public static void main(String[] args) {
        // 初始化配置
        Config config = new Config();

        // 解析命令行参数
        String serverIp = null;
        int serverPort = -1;

        for (int i = 0; i < args.length; i++) {
            if ("-i".equals(args[i]) && i + 1 < args.length) {
                serverIp = args[i + 1];
                i++;
            } else if ("-p".equals(args[i]) && i + 1 < args.length) {
                serverPort = Integer.parseInt(args[i + 1]);
                i++;
            } else if ("-h".equals(args[i])) {
                printHelp();
                return;
            }
        }

        if (serverIp == null || serverPort <= 0) {
            System.err.println("Server IP and port must be specified.");
            printHelp();
            return;
        }

        config.setIp(serverIp);
        config.setPort(serverPort);

        // 注册服务
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

        // 启动 HTTP 服务器
        HttpServer httpServer = new VertxHttpServer();
        httpServer.doStart(8080);
    }

    // 打印帮助信息
    private static void printHelp() {
        System.out.println("Usage: java -jar MyRPC.jar -i <server-ip> -p <server-port>");
        System.out.println("Options:");
        System.out.println("  -h           Show this help message");
        System.out.println("  -i <ip>      Specify the server IP address (IPv4 or IPv6)");
        System.out.println("  -p <port>    Specify the server port number");
    }
}
