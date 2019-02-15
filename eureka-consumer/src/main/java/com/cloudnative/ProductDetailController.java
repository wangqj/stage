package com.cloudnative;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController
public class ProductDetailController {
    @Autowired
    RestTemplate restTemplate;

    @RequestMapping(value = "/detail/{id}",method = RequestMethod.GET)
    @HystrixCommand(fallbackMethod = "getFallback",
            commandProperties = {
                    @HystrixProperty(name = "execution.isolation.thread.timeoutInMilliseconds", value = "500"),//指定超时时间，单位毫秒。超时进fallback
                    @HystrixProperty(name = "circuitBreaker.requestVolumeThreshold", value = "2"),//判断熔断的最少请求数，默认是10；只有在一个统计窗口内处理的请求数量达到这个阈值，才会进行熔断与否的判断
            }
            )
    public String getProductDetail(@PathVariable long id){
        System.out.println("getProductDetail:id = [" + id + "]");
        return restTemplate.getForEntity("http://PROVIDER/price/"+id,String.class).getBody();
    }

    protected String getFallback(long id,Throwable throwable) {
        System.out.println("getFallback:id = [" + id + "], throwable = [" + throwable + "]");
        return "error" +id;
    }
}
