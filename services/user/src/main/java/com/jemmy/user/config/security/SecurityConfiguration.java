package com.jemmy.user.config.security;

import com.jemmy.common.security.Pbkdf2PasswordEncoder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.ReflectionSaltSource;
import org.springframework.security.authentication.dao.SaltSource;

/**
 * 安全配置
 */
@Configuration
public class SecurityConfiguration{

    @Bean
    public Pbkdf2PasswordEncoder passwordEncoder() {
        return new Pbkdf2PasswordEncoder(512,1000,16);
    }

    @Bean
    public SaltSource saltSource(){
        ReflectionSaltSource saltSource = new ReflectionSaltSource();
        saltSource.setUserPropertyToUse("salt");
        return saltSource;
    }
}
