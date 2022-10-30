package com.nmbs_is_a_joke.api;

import com.nmbs_is_a_joke.api.helper.TwitterHelper;
import com.nmbs_is_a_joke.api.service.IRailService;
import com.nmbs_is_a_joke.api.service.TwitterService;
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
        TwitterService twitterService = new TwitterService();
        TwitterHelper twitterHelper = new TwitterHelper();

        System.out.printf("START: %s%n", System.currentTimeMillis());
        Calendar today = Calendar.getInstance();

        int totalDelayInSeconds = iRailService.getTotalDelayInSecondsForGivenDay(today);
        int totalDelayedTrains = iRailService.getTotalDelayedTrains();
        int totalCancelledJourneys = iRailService.getTotalTrainsCancelled();
        int totalJourneys = iRailService.getVehicleList().size();

        String tweetBody = twitterService.getTweetBody(today, totalJourneys, totalDelayedTrains, totalCancelledJourneys, totalDelayInSeconds);
        twitterHelper.postTweet(tweetBody);

        System.out.printf("STOP: %s%n", System.currentTimeMillis());
    }

}
