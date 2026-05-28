package com.itheima.config;

import com.itheima.interceptors.LoginInterceptors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration //告诉 Spring：这是一个配置类 项目启动时，Spring 会自动加载这里面的配置
//WebMvcConfigurer是 SpringMVC 提供的一个配置接口，里面有很多方法，专门让你改 MVC 行为 比如：配置拦截器、配置跨域、配置静态资源、配置消息转换器（JSON）、配置参数解析等
public class WebConfig implements WebMvcConfigurer {

    @Autowired
    private LoginInterceptors loginInterceptors;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(loginInterceptors).excludePathPatterns("/admin/employee/register","/admin/employee/login");
    }
}
