package com.nmbs_is_a_joke.api;

import com.nmbs_is_a_joke.api.service.NmbsIsAJokeService;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import twitter4j.TwitterException;

import java.io.IOException;

@SpringBootApplication
public class NmbsIsAJokeApplication {

    public static void main(String[] args) throws IOException, TwitterException {
        SpringApplication.run(NmbsIsAJokeApplication.class, args).close();
        NmbsIsAJokeService.runNmbsIsAJokeApplication();
    }

}
