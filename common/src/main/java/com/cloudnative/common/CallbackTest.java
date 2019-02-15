package com.cloudnative.common;

import com.google.common.util.concurrent.ListeningExecutorService;
import com.google.common.util.concurrent.MoreExecutors;
import com.google.common.util.concurrent.FutureCallback;
import com.google.common.util.concurrent.Futures;
import com.google.common.util.concurrent.ListenableFuture;
import com.google.common.util.concurrent.ListeningExecutorService;
import com.google.common.util.concurrent.MoreExecutors;

import java.util.concurrent.*;


public class CallbackTest {
    public static void main(String[] args) throws Exception {
// jdk自带Future模式,实现异步，交给线程池处理任务
        ExecutorService jdkExecutor = Executors.newSingleThreadExecutor();
        Future<String> jdkFuture = jdkExecutor
                .submit(new Callable<String>() {
                    @Override
                    public String call() throws Exception {
// 模拟业务消耗时间

                        TimeUnit.SECONDS.sleep(100);
                        return "This is native future call.not support async callback";
                    }
                });
// Future只实现了异步，而没有实现回调.所以此时主线程get结果时阻塞.或者可以轮训以便获取异步调用是否完成，提交到线程池到get结果之间是非阻塞的，可以处理其他任务。
        System.out.println(jdkFuture.get());
// 好的实现应该是提供回调,即异步调用完成后,可以直接回调.本例采用guava提供的异步回调接口,方便很多.
        ListeningExecutorService guavaExecutor = MoreExecutors
                .listeningDecorator(Executors.newSingleThreadExecutor());
        final ListenableFuture<String> listenableFuture = guavaExecutor
                .submit(new Callable<String>() {
                    @Override
                    public String call() throws Exception {
                        Thread.sleep(1000);
                        return "this is guava future call.support async callback";
                    }
                });
// 注册监听器,即异步调用完成时会在指定的线程池中执行注册的监听器
        listenableFuture.addListener(new Runnable() {
            @Override
            public void run() {
                try {
                    System.out.println("async complete.result:"+ listenableFuture.get());
                } catch (Exception e) {
                }
            }
        }, Executors.newSingleThreadExecutor());
// 主线程可以继续执行,异步完成后会执行注册的监听器任务.
        System.out.println("go on execute.asyn complete will callback");
// 除了ListenableFuture,guava还提供了FutureCallback接口,相对来说更加方便一些.
        ListeningExecutorService guavaExecutor2 = MoreExecutors
                .listeningDecorator(Executors.newSingleThreadExecutor());
        final ListenableFuture<String> listenableFuture2 = guavaExecutor2
                .submit(new Callable<String>() {
                    @Override
                    public String call() throws Exception {
                        Thread.sleep(1000);
                        System.out.println("asyncThreadName:"
                                + Thread.currentThread().getName());
                        return "this is guava future call.support async callback using FutureCallback";
                    }
                });
// 注意这里没用指定执行回调的线程池,从输出可以看出，默认是和执行异步操作的线程是同一个.当然也可以再起一个线程池，取决于处理的复杂度，和相互之间的影响。
        Futures.addCallback(listenableFuture2, new FutureCallback<String>() {
            @Override
            public void onSuccess(String result) {
                System.out
                        .println("async callback(using FutureCallback) result:"
                                + result);
                System.out.println("execute callback threadName:"
                        + Thread.currentThread().getName());
            }

            @Override
            public void onFailure(Throwable throwable) {

            }
        });
    }
}
