package com.nmbs_is_a_joke.api.service;

import org.springframework.stereotype.Service;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.concurrent.TimeUnit;

@Service
public class TwitterService {
    public String getTweetBody(Calendar calendar, int totalJourneys, int totalDelayedTrains, int totalCanceledJourneys, int totalDelayInSeconds) {
        String date = handleDate(calendar);
        String accumulatedDelay = handleAccumulatedDelay(totalDelayInSeconds);

        final double delayPercentage = ((double) totalDelayedTrains / (double) totalJourneys) * 100;
        final double cancellationPercentage = ((double) totalCanceledJourneys / (double) totalJourneys) * 100;
        NumberFormat nf = new DecimalFormat("##.#");

        return String.format(
                "%s:\n " +
                        "🚆 Total journeys: %s \n " +
                        "⏰ Trains delayed: %s (%s%%)\n " +
                        "🗑️ Trains cancelled: %s (%s%%)\n " +
                        "⌚️ Accumulated delay: %s \n " +
                        "#NMBS #SNCB #BelgianTrain",
                date,
                totalJourneys,
                totalDelayedTrains,
                nf.format(delayPercentage),
                totalCanceledJourneys,
                nf.format(cancellationPercentage),
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
        // Always zero as minimum train delay is 1min
//        long seconds = TimeUnit.SECONDS.toSeconds(pSeconds) - (TimeUnit.SECONDS.toMinutes(pSeconds) * 60);

        String dayString = day == 1 ? "day" : "days";
        String hourString = hours == 1 ? "hour" : "hours";
        String minutesString = minutes == 1 ? "minute" : "minutes";
//        String secondsString = seconds == 1 ? "second" : "seconds";

        return day == 0 ? String.format("%sh %smin", hours, minutes) :
                String.format("%s %s, %s %s, %s %s", day, dayString, hours, hourString, minutes, minutesString);
    }

}
