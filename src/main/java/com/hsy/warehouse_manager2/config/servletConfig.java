package com.hsy.warehouse_manager2.config;

import com.hsy.warehouse_manager2.filter.LoginCheckFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.core.StringRedisTemplate;

@Configuration
public class servletConfig {

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    //配置FilterRegistrationBean的对象，注册过滤器
    @Bean
    public FilterRegistrationBean filterRegistrationBean(){
        //创建FilterRegistrationBean
        FilterRegistrationBean filterRegistrationBean = new FilterRegistrationBean();
        //创建自定义配置器
        LoginCheckFilter loginCheckFilter = new LoginCheckFilter();
        //手动注入redis模板
        loginCheckFilter.setStringRedisTemplate(stringRedisTemplate);
        //将自定义过滤器注册到创建FilterRegistrationBean中
        filterRegistrationBean.setFilter(loginCheckFilter);

        //给过滤器生成拦截的请求,(拦截所有请求)
        filterRegistrationBean.addUrlPatterns("/*");
        return filterRegistrationBean;
    }
}
