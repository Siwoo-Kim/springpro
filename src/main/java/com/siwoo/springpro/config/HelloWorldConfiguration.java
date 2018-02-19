package com.siwoo.springpro.config;

import com.siwoo.springpro.StartFirstApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class HelloWorldConfiguration {

    @Bean
    public StartFirstApplication.MessageProvider provider(){
        return new StartFirstApplication.HelloMessageProvider();
    }

    @Bean
    public StartFirstApplication.MessageRenderer renderer(){
        StartFirstApplication.MessageRenderer renderer = new StartFirstApplication.StandardOutMessageRenderer();
        renderer.setMessageProvider(provider());
        return renderer;
    }
}
