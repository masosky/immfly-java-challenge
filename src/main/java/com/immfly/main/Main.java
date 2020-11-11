package com.immfly.main;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.*;
import org.springframework.boot.autoconfigure.*;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.core.env.Environment;

@SpringBootApplication
@ComponentScan(basePackages = "com.immfly")
public class Main {

    @Autowired
    private Environment env;

    /*@RequestMapping("/")
    String home() {
        System.out.println(env.getProperty("REDIS_HOST"));
        return "Hello World!";
    }

    @RequestMapping("/env")
    String env() {
        return env.getProperty("REDIS_HOST");
    }

    @RequestMapping("/test")
    String caca() {
        Jedis jedis = new Jedis(new HostAndPort(env.getProperty("REDIS_HOST"), 6379));
        jedis.auth("xkav7KNwwpXXM2v6");
        jedis.set("a", "aeswfgwergrgerg");
        return jedis.get("a");
    }*/

    public static void main(String[] args) {
        SpringApplication.run(Main.class, args);
    }

}