package com.rptools.spring;

import org.springframework.context.annotation.*;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import com.google.appengine.api.users.User;
import com.rptools.util.Provider;

@Configuration
@ComponentScan(value="com.rptools", scopedProxy = ScopedProxyMode.INTERFACES)
@EnableWebMvc
public class SpringConfiguration {
    @Bean
    public Provider<User> userProvider() {
        return new Provider<>();
    }
}
