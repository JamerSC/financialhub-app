package com.jamersc.springboot.financialhub.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
public class PasswordConfig {
<<<<<<< HEAD
=======
    // Configuration for Bcrypt of Password for new User Account.
>>>>>>> 8723a7db6bbf6f331c4b2199efc44cd7c212925d
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}

































































