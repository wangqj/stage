package com.cloudnative.common;

import com.google.common.collect.Maps;
import org.slf4j.LoggerFactory;

import java.util.Map;

public class RetryCommon {
    private static org.slf4j.Logger log= LoggerFactory.getLogger(RetryCommon.class.getName());

    /**
     *  try-catch-redo简单重试模式
     */
    public void retrySimple() {
        Map<String, Object> paramMap = Maps.newHashMap();
        paramMap.put("x", "1");
        paramMap.put("y", "2");
        int result =0;
        try {
            result = invoke(paramMap);
            if (result!=200) {
                Thread.sleep(1000);
                invoke(paramMap);  //第一次重试
            }
        } catch (Exception e) {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e1) {
                e1.printStackTrace();
            }
            invoke(paramMap);//第二次重试
        }
    }

    /**
     *  try-catch-redo-retry strategy策略重试模式
     */
    public void retryStrategy() {
        Map<String, Object> paramMap = Maps.newHashMap();
        paramMap.put("x", "1");
        paramMap.put("y", "2");
        int result =0;
        try {
            result = invoke(paramMap);
            if (result!=200) {
                invokeRetry(paramMap,1000L,100L,10);//延迟多次重试
            }
        } catch (Exception e) {
            try {
                invokeRetry(paramMap,1000L,100L,10);//延迟多次重试
            } catch (InterruptedException e1) {
                e1.printStackTrace();
            }
        }
    }

    /**
     *  递归重试调用
     * @param paramMap
     * @param intevalTime
     * @param retryCount
     * @return
     * @throws InterruptedException
     */
    private int invokeRetry(Map<String, Object> paramMap, long intevalTime,long weakTime, int retryCount) throws InterruptedException {
        log.info("invokeRetry retryCount="+retryCount);
        Thread.sleep(intevalTime);
        int result = invoke(paramMap);
        if (result!=200&&retryCount>1){
            invokeRetry(paramMap,intevalTime+weakTime,weakTime,retryCount-1);//递归重试
        }else if (retryCount==1){
            log.info("retry completed");
        }else {
            log.info("success");
            return 200;
        }
        return result;
    }

    /**
     *  模拟业务调用方法
     * @param paramMap
     * @return
     */
    private int invoke(Map<String, Object> paramMap) {
        log.info("invoke-------");
        return 400;//模拟异常
    }

    public static void main(String[] args) {
        RetryCommon retryCommon=new RetryCommon();
        retryCommon.retrySimple();
        retryCommon.retryStrategy();
    }
}
