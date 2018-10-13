package com.jemmy.api.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan({"com.jemmy.common.web.exception"})
public class WebConfiguration {
}
