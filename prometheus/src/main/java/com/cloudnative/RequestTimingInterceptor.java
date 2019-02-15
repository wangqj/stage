package com.cloudnative;

import io.prometheus.client.Summary;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.Method;

public class RequestTimingInterceptor extends HandlerInterceptorAdapter {

    private static final String REQ_PARAM_TIMING = "timing";

    // @formatter:off
    // Note (1)
    private static final Summary responseTimeInMs = Summary
            .build()
            .name("http_response_time_milliseconds_spring")
            .labelNames("method", "handler", "status")
            .help("Request completed time in milliseconds")
            .register();
    // @formatter:on

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        System.out.println("request = [" + request + "], response = [" + response + "], handler = [" + handler + "]");
        request.setAttribute(REQ_PARAM_TIMING, System.currentTimeMillis());
        return true;
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex)throws Exception {
        System.out.println("request = [" + request + "], response = [" + response + "], handler = [" + handler + "], ex = [" + ex + "]");
        Long timingAttr = (Long) request.getAttribute(REQ_PARAM_TIMING);
        long completedTime = System.currentTimeMillis() - timingAttr;
        String handlerLabel = handler.toString();
        // get short form of handler method name
        if (handler instanceof HandlerMethod) {
            Method method = ((HandlerMethod) handler).getMethod();
            handlerLabel = method.getDeclaringClass().getSimpleName() + "." + method.getName();
        }
        // Note (3)
        responseTimeInMs.labels(request.getMethod(), handlerLabel,
                Integer.toString(response.getStatus())).observe(completedTime);
    }
}