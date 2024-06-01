package provider.impl;

import service.AddService;

public class AddServiceImpl implements AddService {
    public int add(int a, int b) {
        int sum = a + b;
        System.out.println("调用了add方法：" + a + " + " + b + " = " + sum);
        return sum;
    }
}
