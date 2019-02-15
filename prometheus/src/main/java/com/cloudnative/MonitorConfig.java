package com.cloudnative;

import io.prometheus.client.exporter.MetricsServlet;
import io.prometheus.client.hotspot.DefaultExports;
import io.prometheus.client.spring.boot.SpringBootMetricsCollector;
import org.springframework.boot.actuate.endpoint.PublicMetrics;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import java.util.Collection;

/**
 * 配置类
 */
@Configuration
public class MonitorConfig extends WebMvcConfigurerAdapter {
    @Bean
    public SpringBootMetricsCollector springBootMetricsCollector(Collection<PublicMetrics> publicMetrics) {
        SpringBootMetricsCollector springBootMetricsCollector = new SpringBootMetricsCollector(publicMetrics);
        springBootMetricsCollector.register();
        return springBootMetricsCollector;
    }

    @Bean
    public ServletRegistrationBean servletRegistrationBean() {
        DefaultExports.initialize();
        //设置路径，在Prometheus。yml中需要对应
        return new ServletRegistrationBean(new MetricsServlet(), "/prometheus");
    }
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        // addPathPatterns 添加连接器并拦截所有请求
        registry.addInterceptor(new RequestCounterInterceptor()).addPathPatterns("/**");
        super.addInterceptors(registry);
    }
}
