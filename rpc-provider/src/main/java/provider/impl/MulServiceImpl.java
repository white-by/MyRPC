package provider.impl;

import service.MulService;

public class MulServiceImpl implements MulService {
    @Override
    public int mul(int a, int b) {
        System.out.println("调用了mul方法");
        return a * b;
    }
}
