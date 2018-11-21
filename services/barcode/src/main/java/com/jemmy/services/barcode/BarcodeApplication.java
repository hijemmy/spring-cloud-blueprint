package com.jemmy.services.barcode;

import org.springframework.boot.SpringApplication;
import org.springframework.cloud.client.SpringCloudApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableOAuth2Client;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * Created by pc on 2018/11/10.
 */
@SpringCloudApplication
@EnableOAuth2Client
@EnableSwagger2
@ComponentScan({"com.jemmy.common.zk","com.jemmy.common.core","com.jemmy.services.barcode"})
public class BarcodeApplication {

    public static void main(String... args){
        SpringApplication.run(BarcodeApplication.class, args);
    }


}
