package provider.impl;

import service.DivService;

public class DivServiceImpl implements DivService {
    @Override
    public int div(int a, int b) {
        if (b == 0) {
            throw new IllegalArgumentException("被除数不能为0欸");
        }
        System.out.println("调用了div方法");
        return a / b;
    }
}
