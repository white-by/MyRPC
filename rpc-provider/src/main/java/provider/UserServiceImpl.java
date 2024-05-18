package provider;

import model.User;
import service.UserService;

public class UserServiceImpl implements UserService {

    public User getUser(User user) {
        System.out.println("user's name is " + user.getName());
        return user;
    }
}
