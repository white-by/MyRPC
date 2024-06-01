package provider.impl;

import service.ModuloService;

public class ModuloServiceImpl implements ModuloService {
    @Override
    public int modulo(int a, int b) {
        System.out.println("调用了Modulo方法");
        return a % b;
    }
}
