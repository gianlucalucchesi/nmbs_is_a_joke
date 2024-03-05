package com.nmbs_is_a_joke.api.service;

import ch.qos.logback.classic.Level;
import com.nmbs_is_a_joke.api.helper.TwitterHelper;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import twitter4j.TwitterException;

import java.io.IOException;
import java.util.Calendar;

@Service
public class NmbsIsAJokeService {

    static ch.qos.logback.classic.Logger log = (ch.qos.logback.classic.Logger) LoggerFactory.getLogger("com.nmbs_is_a_joke");

    public NmbsIsAJokeService() {
        log.setLevel(Level.INFO);
    }

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

        // If there are not at least 50 trains, there is an issue with the API
        if (totalJourneys > 50) {
            String tweetBody = twitterService.getTweetBody(today, totalJourneys, totalDelayedTrains, totalCancelledJourneys, totalDelayInSeconds);
            twitterHelper.postTweet(tweetBody);
        } else {
            log.info("API ERROR: totalJourneys={}", totalJourneys);
        }
    }
}
