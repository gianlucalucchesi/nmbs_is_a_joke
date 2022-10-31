package com.nmbs_is_a_joke.api.service;

import com.nmbs_is_a_joke.api.helper.TwitterHelper;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import twitter4j.TwitterException;

import java.io.IOException;
import java.util.Calendar;

@Service
public class NmbsIsAJokeService {

    /**
     * https://reflectoring.io/spring-scheduler/
     *
     * @throws IOException
     * @throws TwitterException
     */
    @Scheduled(cron = "0 30 22 * * *") // Every day at 22h30
    public static void runNmbsIsAJokeApplication() throws IOException, TwitterException {
        IRailService iRailService = new IRailService();
        TwitterService twitterService = new TwitterService();
        TwitterHelper twitterHelper = new TwitterHelper();

        Calendar today = Calendar.getInstance();

        int totalDelayInSeconds = iRailService.getTotalDelayInSecondsForGivenDay(today);
        int totalDelayedTrains = iRailService.getTotalDelayedTrains();
        int totalCancelledJourneys = iRailService.getTotalTrainsCancelled();
        int totalJourneys = iRailService.getVehicleList().size();

        String tweetBody = twitterService.getTweetBody(today, totalJourneys, totalDelayedTrains, totalCancelledJourneys, totalDelayInSeconds);
        twitterHelper.postTweet(tweetBody);
    }

}
