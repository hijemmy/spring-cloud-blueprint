package com.jemmy.user.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan({"com.jemmy.common.web.exception", "com.jemmy.common.security.oauth2"})
public class WebConfiguration {
}
