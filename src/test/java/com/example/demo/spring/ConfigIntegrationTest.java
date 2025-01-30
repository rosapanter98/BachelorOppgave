package com.example.demo.spring;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@ComponentScan("com.example.demo.config.spring")
public class ConfigIntegrationTest implements WebMvcConfigurer {

    public ConfigIntegrationTest() {
        super();
    }

    // API

}