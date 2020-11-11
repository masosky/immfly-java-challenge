package com.immfly;

import org.springframework.boot.*;
import org.springframework.boot.autoconfigure.*;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = "com.immfly")
public class Main {

    public static void main(String[] args) {
        SpringApplication.run(Main.class, args);
    }

}