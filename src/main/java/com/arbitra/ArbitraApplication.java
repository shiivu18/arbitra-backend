package com.arbitra;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.actuate.autoconfigure.security.servlet.ManagementWebSecurityAutoConfiguration;
import org.springframework.scheduling.annotation.EnableAsync;

@SpringBootApplication(exclude = { ManagementWebSecurityAutoConfiguration.class })
@EnableAsync
public class ArbitraApplication {

    public static void main(String[] args) {
        SpringApplication.run(ArbitraApplication.class, args);
    }
}