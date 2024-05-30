package com.myrpc.fault.retry;

import com.myrpc.model.RpcResponse;

import java.util.concurrent.Callable;

public interface RetryStrategy {
    RpcResponse doRetry(Callable<RpcResponse> callable) throws Exception;

}
