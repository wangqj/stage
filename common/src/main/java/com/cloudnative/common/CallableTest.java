package com.cloudnative.common;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;

import static java.util.concurrent.TimeUnit.SECONDS;

public class CallableTest {
    public static void main(String[] args) {
        List<Future<String>> resultList =  new ArrayList<Future<String>>();
        ThreadPoolExecutor executor = new ThreadPoolExecutor(6, 10, 50, SECONDS, new LinkedBlockingDeque<Runnable>());
        Callable<String> task = new Callable<String>() {
            public String call() {
                System.out.println("Sleep start.");
                try {
                    Thread.sleep(1000 * 10);
                } catch (InterruptedException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
                System.out.println("Sleep end.");
                return "time=" + System.currentTimeMillis();
            }
        };
        for (int i = 0; i <300 ; i++) {
            resultList.add(executor.submit(task));
            System.out.println("submit="+i);
        }
        for (Future<String> result:resultList) {
            try {
                System.out.println("result="+result.get(30, SECONDS));
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (ExecutionException e) {
                e.printStackTrace();
            } catch (TimeoutException e) {
                e.printStackTrace();
            }
        }
    }
}

