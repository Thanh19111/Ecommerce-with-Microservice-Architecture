package com.thanhpham.Product.config;


import feign.form.FormEncoder;
import feign.form.spring.SpringFormEncoder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class FeignClientConfig {
    @Bean
    public FormEncoder feignFormEncoder() {
        return new SpringFormEncoder();
    }
}
