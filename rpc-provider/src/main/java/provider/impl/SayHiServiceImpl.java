package provider.impl;

import service.SayHiService;

public class SayHiServiceImpl implements SayHiService {
    @Override
    public String sayHi(String name) {
        System.out.println("调用了sayHi方法");
        return "Hi, " + name + "!";
    }
}
