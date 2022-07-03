package com.jdqc.aam;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

@SpringBootApplication
public class ActionAPIMonitor {

    public static void main(String[] args) {
        ConfigurableApplicationContext context = SpringApplication.run(ActionAPIMonitor.class, args);
        int exitCode = SpringApplication.exit(context, () -> 0);
        System.exit(exitCode);
    }
}
