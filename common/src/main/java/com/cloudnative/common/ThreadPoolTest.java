package com.cloudnative.common;

import java.util.concurrent.*;

public class ThreadPoolTest {
    public static void main(String[] args) {
        ThreadPoolExecutor executor = new ThreadPoolExecutor(6, 10, 50, TimeUnit.SECONDS, new LinkedBlockingDeque<Runnable>());

        Runnable myRunnable = new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(20000);
                    System.out.println(Thread.currentThread().getName() + " run");
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

            }
        };
        for (int i = 0; i <30 ; i++) {
            executor.execute(myRunnable);
            executor.execute(myRunnable);
            executor.execute(myRunnable);
            System.out.println("---开三个---");
            System.out.println("核心线程数" + executor.getCorePoolSize());
            System.out.println("线程池数" + executor.getPoolSize());
            System.out.println("队列任务数" + executor.getQueue().size());
        }
        System.out.println("args = [" + args + "]");


    }
}
