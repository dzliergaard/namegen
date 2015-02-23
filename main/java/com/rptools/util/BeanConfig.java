package com.rptools.util;

import com.google.appengine.api.users.User;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class BeanConfig {

    @Bean
    public Provider<User> getUser(){
        return new Provider<User>();
    }
}
