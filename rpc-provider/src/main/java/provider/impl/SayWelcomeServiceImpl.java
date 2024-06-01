package provider.impl;

import service.SayWelcomeService;

public class SayWelcomeServiceImpl implements SayWelcomeService {
    @Override
    public String sayWelcome(String name) {
        System.out.println("调用了sayWelcome方法");
        return "Welcome, " + name + "!";
    }
}
