package com.jemmy.gw;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.security.oauth2.client.EnableOAuth2Sso;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.cloud.client.SpringCloudApplication;
import org.springframework.cloud.netflix.zuul.EnableZuulProxy;
import org.springframework.context.annotation.ComponentScan;

@SpringCloudApplication
@EnableZuulProxy
@EnableOAuth2Sso
@ServletComponentScan
@ComponentScan({"com.jemmy.common.config"})
public class GwApplication {

    public static void main(String[] args) {
        SpringApplication.run(GwApplication.class, args);
    }
}
