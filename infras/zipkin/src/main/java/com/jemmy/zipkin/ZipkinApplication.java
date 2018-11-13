package com.jemmy.zipkin;

import org.springframework.boot.SpringApplication;
import org.springframework.cloud.client.SpringCloudApplication;
import zipkin.server.internal.EnableZipkinServer;

@EnableZipkinServer
@SpringCloudApplication
public class ZipkinApplication {
    public static void main(String[] args) {
        SpringApplication.run(ZipkinApplication.class, args);
    }
}
