package com.codingapi.tm;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.SpringCloudApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.ComponentScan;


/**
 * @author Jemmy
 */
@SpringCloudApplication
@EnableDiscoveryClient
@ComponentScan({"com.juneng.common.core", "com.codingapi.tm"})
public class TxManagerApplication {



    public static void main(String[] args) {
        SpringApplication.run(TxManagerApplication.class, args);
    }

}
