package consumer;

import com.myrpc.config.RpcConfig;
import com.myrpc.proxy.ServiceProxyFactory;
import com.myrpc.utils.ConfigUtils;
import model.User;
import service.UserService;

public class ConsumerExample {
    public static void main(String[] args) {
        RpcConfig rpcConfig = ConfigUtils.loadConfig(RpcConfig.class, "rpc");
        System.out.println(rpcConfig);

        // 动态代理
        UserService userService = ServiceProxyFactory.getProxy(UserService.class);

        User user = new User();
        user.setName("whiteby");
        // 调用
        User newUser = userService.getUser(user);
        if (newUser != null) {
            System.out.println("newUser's name is " + newUser.getName());
        }
        else {
            System.out.println("user == null");
        }
    }
}
