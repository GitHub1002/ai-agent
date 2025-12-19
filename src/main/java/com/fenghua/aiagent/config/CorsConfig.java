package com.fenghua.aiagent.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * @ClassName CorsConfig
 * @Description
 * @Author Feng
 * @Date 2025/12/19
 **/
@Configuration
public class CorsConfig implements WebMvcConfigurer {

    /**
     * @Author Feng
     * @Description  跨域配置
     * @Date 2025/12/19
     * @Param [registry]
     * @return void
     **/
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedOrigins("*")
                .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS")
                .allowedHeaders("*")
                .maxAge(3600);
    }
}
