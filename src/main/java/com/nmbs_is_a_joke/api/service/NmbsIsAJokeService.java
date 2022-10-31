package com.nmbs_is_a_joke.api.service;

import com.nmbs_is_a_joke.api.helper.TwitterHelper;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import twitter4j.TwitterException;

import java.io.IOException;
import java.util.Calendar;

@Service
public class NmbsIsAJokeService {

    @Scheduled(fixedDelay = 86400000)
    public static void runNmbsIsAJokeApplication() throws IOException, TwitterException {
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
