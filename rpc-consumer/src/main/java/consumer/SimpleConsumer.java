//package consumer;
//
//import com.simplerpc.config.Config;
//import model.User;
//import proxy.ServiceProxyFactory;
//import service.*;
//
//import java.util.Scanner;
//
//public class SimpleConsumerExample {
//    public static void main(String[] args) {
//        Config config = new Config();
//        int serverPort = readPortFromCommandLine();
//        config.setPort(serverPort);
//
//        // 获取服务的代理对象
//        UserService userService = ServiceProxyFactory.getProxy(UserService.class);
//        AddService addService = ServiceProxyFactory.getProxy(AddService.class);
//        SubService subtractService = ServiceProxyFactory.getProxy(SubService.class);
//        MulService multiplyService = ServiceProxyFactory.getProxy(MulService.class);
//        DivService divideService = ServiceProxyFactory.getProxy(DivService.class);
//        ModuloService moduloService = ServiceProxyFactory.getProxy(ModuloService.class);
//        AccumulateService accumulateService = ServiceProxyFactory.getProxy(AccumulateService.class);
//        FactorialService factorialService = ServiceProxyFactory.getProxy(FactorialService.class);
//        SayHiService sayHiService = ServiceProxyFactory.getProxy(SayHiService.class);
//        SayWelcomeService sayWelcomeService = ServiceProxyFactory.getProxy(SayWelcomeService.class);
//
//        // 创建 User 对象
//        User user = new User();
//        user.setName("whiteby");
//
//        // 调用远程服务方法
//        User newUser = userService.getUser(user);
//        int sum = addService.add(5, 3);  // 调用 AddService 的 add 方法
//        int difference = subtractService.sub(10, 3);  // 调用 SubtractService 的 subtract 方法
//        int product = multiplyService.mul(5, 4);  // 调用 MultiplyService 的 multiply 方法
//        int quotient = divideService.div(15, 3);  // 调用 DivideService 的 divide 方法
//        int remainder = moduloService.modulo(10, 3);  // 调用 ModuloService 的 modulo 方法
//        int accumulatedSum = accumulateService.accumulate(5);  // 调用 AccumulateService 的 accumulate 方法
//        int factorial = factorialService.factorial(5);  // 调用 FactorialService 的 factorial 方法
//        String greeting = sayHiService.sayHi("John");  // 调用 SayHiService 的 sayHi 方法
//        String welcomeMessage = sayWelcomeService.sayWelcome("Alice");  // 调用 SayWelcomeService 的 sayWelcome 方法
//
//        // 输出结果
//        if (newUser != null) {
//            System.out.println("Hello! " + newUser.getName());
//        } else {
//            System.out.println("User is null :(");
//        }
//
//        System.out.println("Sum: " + sum);  // 输出加法结果
//        System.out.println("Difference: " + difference);  // 输出减法结果
//        System.out.println("Product: " + product);  // 输出乘法结果
//        System.out.println("Quotient: " + quotient);  // 输出除法结果
//        System.out.println("Remainder: " + remainder);  // 输出取余结果
//        System.out.println("Accumulated Sum: " + accumulatedSum);  // 输出累加结果
//        System.out.println("Factorial: " + factorial);  // 输出阶乘结果
//        System.out.println("Greeting: " + greeting);  // 输出问候语
//        System.out.println("Welcome Message: " + welcomeMessage);  // 输出欢迎消息
//    }
//
//    // 从命令行读取服务器端口号
//    private static int readPortFromCommandLine() {
//        Scanner scanner = new Scanner(System.in);
//        System.out.print("Enter server port number: ");
//        return scanner.nextInt();
//    }
//}
//
//

package consumer;

import com.simplerpc.config.Config;
import model.User;
import proxy.ServiceProxyFactory;
import service.*;

public class SimpleConsumer {
    public static void main(String[] args) {
        // 解析命令行参数
        String serverIp = null;
        int serverPort = -1;

        for (int i = 0; i < args.length; i++) {
            if ("-i".equals(args[i]) && i + 1 < args.length) {
                serverIp = args[i + 1];
            } else if ("-p".equals(args[i]) && i + 1 < args.length) {
                serverPort = Integer.parseInt(args[i + 1]);
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

        // 初始化服务代理工厂的配置
        Config config = new Config();
        config.setIp(serverIp);
        config.setPort(serverPort);

        // 获取服务的代理对象
        UserService userService = ServiceProxyFactory.getProxy(UserService.class);
        AddService addService = ServiceProxyFactory.getProxy(AddService.class);
        SubService subtractService = ServiceProxyFactory.getProxy(SubService.class);
        MulService multiplyService = ServiceProxyFactory.getProxy(MulService.class);
        DivService divideService = ServiceProxyFactory.getProxy(DivService.class);
        ModuloService moduloService = ServiceProxyFactory.getProxy(ModuloService.class);
        AccumulateService accumulateService = ServiceProxyFactory.getProxy(AccumulateService.class);
        FactorialService factorialService = ServiceProxyFactory.getProxy(FactorialService.class);
        SayHiService sayHiService = ServiceProxyFactory.getProxy(SayHiService.class);
        SayWelcomeService sayWelcomeService = ServiceProxyFactory.getProxy(SayWelcomeService.class);

        // 创建 User 对象
        User user = new User();
        user.setName("whiteby");

        // 调用远程服务方法
        User newUser = userService.getUser(user);
        int sum = addService.add(5, 3);  // 调用 AddService 的 add 方法
        int difference = subtractService.sub(10, 3);  // 调用 SubtractService 的 subtract 方法
        int product = multiplyService.mul(5, 4);  // 调用 MultiplyService 的 multiply 方法
        int quotient = divideService.div(15, 3);  // 调用 DivideService 的 divide 方法
        int remainder = moduloService.modulo(10, 3);  // 调用 ModuloService 的 modulo 方法
        int accumulatedSum = accumulateService.accumulate(5);  // 调用 AccumulateService 的 accumulate 方法
        int factorial = factorialService.factorial(5);  // 调用 FactorialService 的 factorial 方法
        String greeting = sayHiService.sayHi("John");  // 调用 SayHiService 的 sayHi 方法
        String welcomeMessage = sayWelcomeService.sayWelcome("Alice");  // 调用 SayWelcomeService 的 sayWelcome 方法

        // 输出结果
        if (newUser != null) {
            System.out.println("Hello! " + newUser.getName());
        } else {
            System.out.println("User is null :(");
        }

        System.out.println("Sum: " + sum);  // 输出加法结果
        System.out.println("Difference: " + difference);  // 输出减法结果
        System.out.println("Product: " + product);  // 输出乘法结果
        System.out.println("Quotient: " + quotient);  // 输出除法结果
        System.out.println("Remainder: " + remainder);  // 输出取余结果
        System.out.println("Accumulated Sum: " + accumulatedSum);  // 输出累加结果
        System.out.println("Factorial: " + factorial);  // 输出阶乘结果
        System.out.println("Greeting: " + greeting);  // 输出问候语
        System.out.println("Welcome Message: " + welcomeMessage);  // 输出欢迎消息
    }

    private static void printHelp() {
        System.out.println("Usage: ./consumer -i <server-ip> -p <server-port>");
        System.out.println("Options:");
        System.out.println("  -h           Show this help message");
        System.out.println("  -i <ip>      Specify the server IP address (IPv4 or IPv6)");
        System.out.println("  -p <port>    Specify the server port number");
    }
}