package provider.impl;

import service.AccumulateService;

public class AccumulateServiceImpl implements AccumulateService {
    @Override
    public int accumulate(int n) {
        int sum = 0;
        for (int i = 1; i <= n; i++) {
            sum += i;
        }
        System.out.println("调用了Accumulate方法，从1累加到" + n);
        return sum;
    }
}
