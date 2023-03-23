package com.example.nicocommunity.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * @author yang
 */
@Configuration
public class WebConfig implements WebMvcConfigurer {
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        //此处路径和上面的图片上传位置保持一致
//        registry.addResourceHandler("/images/**").addResourceLocations("file:D:/NicoCommunity/src/main/resources/static/images/");
        registry.addResourceHandler("/images/**").addResourceLocations("file:"+"/uploadedImg/");
    }
}
