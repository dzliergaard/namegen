package com.rptools.spring;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import com.google.appengine.api.users.User;
import com.rptools.util.Provider;

@Configuration
@ComponentScan("com.rptools")
@EnableWebMvc
@EnableAspectJAutoProxy
public class SpringConfiguration {
    @Bean
    public Provider<User> userProvider() {
        return new Provider<>();
    }
}
