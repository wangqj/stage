package com.cloudnative.common;

import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.remoting.RemoteAccessException;
import org.springframework.retry.annotation.Backoff;
import org.springframework.retry.annotation.Recover;
import org.springframework.retry.annotation.Retryable;
import org.springframework.stereotype.Service;

@Service
public class SpringRetryService {
    private static org.slf4j.Logger log= LoggerFactory.getLogger(SpringRetryService.class.getName());
    @Retryable(value= {RemoteAccessException.class},maxAttempts = 3,backoff = @Backoff(delay = 1000,multiplier = 2))
    public void call() throws Exception {
        log.info("do something........");
        throw new RemoteAccessException("RPC调用异常----RemoteAccessException");
    }
    @Retryable(value= {RuntimeException.class},maxAttempts = 3,backoff = @Backoff(delay = 1000,multiplier = 2))
    public void call2() throws Exception {
        log.info("do something........");
        throw new RuntimeException("RPC调用异常----RuntimeException");
    }
    @Retryable(exceptionExpression="#{message.contains('customException')}",maxAttempts = 3,backoff = @Backoff(delay = 1000,multiplier = 2))
    public void call3() throws Exception {
        log.info("do something........");
        throw new Exception("RPC调用异常----customException");
    }
    @Recover
    public void recoverRemoteAccessException(RemoteAccessException e) {
        log.info(e.getMessage());
    }
    @Recover
    public void recoverRuntimeException(RuntimeException e) {
        log.info(e.getMessage());
    }
    @Recover
    public void recoverCustomException(Exception e) {
        log.info(e.getMessage());
    }
}
