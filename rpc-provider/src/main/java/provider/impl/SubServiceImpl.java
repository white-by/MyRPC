package provider.impl;

import service.SubService;

public class SubServiceImpl implements SubService {
    @Override
    public int sub(int a, int b) {
        System.out.println("调用了sub方法");
        return a - b;
    }
}
