package provider.impl;

import service.FactorialService;

public class FactorialServiceImpl implements FactorialService {
    @Override
    public int factorial(int n) {
        int result = 1;
        for (int i = 1; i <= n; i++) {
            result *= i;
        }
        System.out.println("调用了Factorial方法");
        return result;
    }
}
