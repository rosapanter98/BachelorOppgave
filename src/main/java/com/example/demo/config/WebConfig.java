package com.example.demo.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.lang.NonNull;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.view.InternalResourceViewResolver;

/**
 * Configuration class for web-related configurations.
 */
@Configuration
@EnableWebMvc
public class WebConfig implements WebMvcConfigurer {

    /**
     * Adds resource handlers for CSS, JavaScript, image, and HTML files.
     *
     * @param registry the resource handler registry
     */
    @Override
    public void addResourceHandlers(@NonNull ResourceHandlerRegistry registry) {
        // CSS files
        registry.addResourceHandler("/css/**")
                .addResourceLocations("file:src/main/webapp/css/");

        // JavaScript files
        registry.addResourceHandler("/js/**")
                .addResourceLocations("file:src/main/webapp/js/");

        // Image files
        registry.addResourceHandler("/images/**")
                .addResourceLocations("file:src/main/webapp/images/");
    }

    /**
     * Configures the view resolver for JSP files.
     *
     * @return the internal resource view resolver
     */
    @Bean
    public InternalResourceViewResolver viewResolver() {
        InternalResourceViewResolver resolver = new InternalResourceViewResolver();
        resolver.setPrefix("/WEB-INF/jsp/");
        resolver.setSuffix(".jsp");
        return resolver;
    }
}