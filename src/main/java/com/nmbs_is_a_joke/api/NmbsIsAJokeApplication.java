package com.nmbs_is_a_joke.api;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;

/**
 * War file necessary to deploy on server
 * https://javapointers.com/spring/spring-boot/create-war-file-in-spring-boot/
 * run 'mvn package' to build
 */
@SpringBootApplication
public class NmbsIsAJokeApplication extends SpringBootServletInitializer {

    public static void main(String[] args) {
        SpringApplication.run(NmbsIsAJokeApplication.class, args);
    }

    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder builder) {
        return builder.sources(NmbsIsAJokeApplication.class);
    }

}
