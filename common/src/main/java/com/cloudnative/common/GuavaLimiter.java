package com.cloudnative.common;

import com.google.common.util.concurrent.ListenableFuture;
import com.google.common.util.concurrent.RateLimiter;

import java.util.concurrent.TimeUnit;

public class GuavaLimiter {
    public static void main(String[] args) throws InterruptedException {
//        //创建桶容量为5且每秒新增5个令牌，即每隔200毫秒新增一个令牌
//        RateLimiter limiter = RateLimiter.create(5);
//        for (int i = 0; i < 5; i++) {
//            //消费令牌，如果当前桶中有足够令牌则成功（返回0），如果桶中没有令牌则阻塞，最终返回等待时间，假设发令牌间隔是200毫秒，则等待200毫秒后再去消费令牌
//            System.out.println("i = [" + i + "] time = ["+limiter.acquire()+"]");
//        }

//        //创建桶容量为5且每秒新增5个令牌，即每隔200毫秒新增一个令牌
//        RateLimiter limiter = RateLimiter.create(5);
//        //第一次把5个全部拿完
//        System.out.println("first time = ["+limiter.acquire(5)+"]");
//        System.out.println("second time = ["+limiter.acquire(1)+"]");
//        System.out.println("third time = ["+limiter.acquire(5)+"]");

        RateLimiter limiter = RateLimiter.create(5, 1, TimeUnit.SECONDS);
        for(int i = 0; i < 5;i++) {
            System.out.println("i = [" + i + "] time = ["+limiter.tryAcquire(1000,TimeUnit.MILLISECONDS)+"]");
        }
    }
}
