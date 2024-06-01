package consumer;

import com.myrpc.config.RpcConfig;
import com.myrpc.proxy.ServiceProxyFactory;
import com.myrpc.utils.ConfigUtils;
import model.User;
import service.*;

import java.util.Scanner;

public class ConsumerExample {
    public static void main(String[] args) {
        int serverPort = readPortFromCommandLine();

        // 加载 RpcConfig 配置
        RpcConfig rpcConfig = ConfigUtils.loadConfig(RpcConfig.class, "rpc");

        // 设置服务器端口号
        rpcConfig.setServerPort(serverPort);

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

    // 从命令行读取服务器端口号
    private static int readPortFromCommandLine() {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter server port number: ");
        return scanner.nextInt();
    }
}
