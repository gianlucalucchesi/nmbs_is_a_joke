package com.nmbs_is_a_joke.api.service;

import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.concurrent.TimeUnit;

@Service
public class TwitterService {

    public String getTweetBody(Calendar calendar, int totalJourneys, int totalDelayedTrains, int totalCanceledJourneys, int totalDelayInSeconds) {
        String date = handleDate(calendar);
        String accumulatedDelay = handleAccumulatedDelay(totalDelayInSeconds);

        final double delayPercentage = (double)(totalDelayedTrains / totalJourneys) * 100;
        final double cancellationPercentage = (double)(totalCanceledJourneys / totalJourneys) * 100;

        return String.format(
                "%s:\n " +
                "🚆 Total journeys: %s \n " +
                "⏰ Trains delayed: %s (%s%%)\n " +
                "🗑️ Trains cancelled: %s (%s%%)\n " +
                "🕓 Accumulated delay: %s",
                date,
                totalJourneys,
                totalDelayedTrains,
                delayPercentage,
                totalCanceledJourneys,
                cancellationPercentage,
                accumulatedDelay
        );
    }

    private String handleDate(Calendar calendar) {
        SimpleDateFormat sdf = new SimpleDateFormat("EEEE dd MMMM yyyy");
        return sdf.format(calendar.getTime());
    }

    private String handleAccumulatedDelay(int pSeconds) {
        int day = (int) TimeUnit.SECONDS.toDays(pSeconds);
        long hours = TimeUnit.SECONDS.toHours(pSeconds) - (day * 24L);
        long minutes = TimeUnit.SECONDS.toMinutes(pSeconds) - (TimeUnit.SECONDS.toHours(pSeconds) * 60);
//		long seconds = TimeUnit.SECONDS.toSeconds(pSeconds) - (TimeUnit.SECONDS.toMinutes(pSeconds) * 60); // Always zero
        return String.format("%s days %s hours %s minutes", day, hours, minutes);
    }

}
