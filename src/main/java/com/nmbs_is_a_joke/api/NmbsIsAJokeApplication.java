package com.nmbs_is_a_joke.api;

import com.nmbs_is_a_joke.api.helper.TwitterHelper;
import com.nmbs_is_a_joke.api.service.IRailService;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import twitter4j.TwitterException;

import java.io.IOException;
import java.util.Calendar;

@SpringBootApplication
public class NmbsIsAJokeApplication {

    public static void main(String[] args) throws IOException, TwitterException {
        SpringApplication.run(NmbsIsAJokeApplication.class, args);

        IRailService iRailService = new IRailService();
        TwitterHelper twitterHelper = new TwitterHelper();

        twitterHelper.postTweet("Hello, world!");

//        System.out.printf("START: %s%n", System.currentTimeMillis());
//        Calendar today = Calendar.getInstance();
//        String totalDelay = iRailService.getTotalDelayForGivenDay(today);
//        System.out.println(totalDelay);
//        System.out.printf("STOP: %s%n", System.currentTimeMillis());
    }

}
