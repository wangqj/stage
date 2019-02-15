package com.cloudnative.common;

import com.cloudnative.dto.Product;
import com.github.rholder.retry.*;
import com.google.common.base.Predicate;
import com.google.common.base.Predicates;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpMethod;
import org.springframework.web.client.RestTemplate;

import javax.annotation.Nullable;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

public class GuavaRetryService {
    private static org.slf4j.Logger log= LoggerFactory.getLogger(GuavaRetryService.class.getName());

    //    private static Callable<Boolean> updateReimAgentsCall = new Callable<Boolean>() {
//        @Override
//        public Boolean call() throws Exception {
//            String url = "";
//            String result = HttpMethod.post(url, new ArrayList<BasicNameValuePair>());
//            if(StringUtils.isEmpty(result)){
//                throw new RemoteException("获取OA可报销代理人接口异常");
//            }
//            List<OAReimAgents> oaReimAgents = JSON.parseArray(result, OAReimAgents.class);
//            if(CollectionUtils.isNotEmpty(oaReimAgents)){
//                CacheUtil.put(Constants.REIM_AGENT_KEY,oaReimAgents);
//                return true;
//            }
//            return false;
//        }
//    };


    public void call() {
        Retryer<Boolean> retryer = RetryerBuilder.<Boolean>newBuilder()
                .retryIfException() //抛出runtime异常、checked异常时都会重试，但是抛出error不会重试。
                .retryIfResult(Predicates.equalTo(false))//返回false需要重试
                .withWaitStrategy(WaitStrategies.incrementingWait(2,TimeUnit.SECONDS,4,TimeUnit.SECONDS))//重调策略,初试2秒，以后增加4秒
                .withStopStrategy(StopStrategies.stopAfterAttempt(3))//重试次数
                .build();

        try {
            //重试入口采用call方法，用的是java.util.concurrent.Callable<V>的call方法,所以执行是线程安全的
            boolean result = retryer.call(new Callable<Boolean>() {
                @Override
                public Boolean call() throws Exception {
                    try {
                        log.info("----call-----");
                        String url = "http://localhost:8080/producwts/1";
                        RestTemplate restTemplate=new RestTemplate();
                        Product product = restTemplate.getForEntity(url, Product.class).getBody();
                        log.info("---------"+product.toString());
                        if (product!=null){
                            return true;
                        }
                        //特别注意：返回false说明无需重试，返回true说明需要继续重试
                        return false;
                    } catch (Exception e) {
                        log.error(e.getMessage());
                        throw new Exception(e);
                    }
                }
            });

        } catch (ExecutionException e) {
            log.error(e.getMessage());
        } catch (RetryException ex) {
            log.error(ex.getMessage());
        }
    }

    public static void main(String[] args) {
        GuavaRetryService guavaRetryService = new GuavaRetryService();
        guavaRetryService.call();
    }
}
