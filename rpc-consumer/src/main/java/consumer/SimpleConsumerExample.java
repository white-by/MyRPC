package consumer;

import model.User;
import proxy.ServiceProxyFactory;
import service.UserService;

public class SimpleConsumerExample {
    public static void main(String[] args) {
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
