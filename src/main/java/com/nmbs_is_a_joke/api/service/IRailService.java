package com.nmbs_is_a_joke.api.service;

import com.nmbs_is_a_joke.api.helper.IRailApiHelper;
import com.nmbs_is_a_joke.api.model.*;
import org.apache.commons.lang3.NotImplementedException;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.time.Instant;
import java.util.*;
import java.util.concurrent.TimeUnit;

@Service
public class IRailService {

    public String getTotalDelayForGivenDay(Date date) throws IOException {
        List<VehicleRetrieval> vehicles;
        int totalDelayInSeconds;
        int totalCanceledTrains;

        Stations stations = IRailApiHelper.retrieveAllStations();

        // For testing purpose
        assert stations != null;
        List<Station> stationList = new ArrayList<>();
        Optional<Station> dilbeek =
                stations.getStationList()
                        .stream()
                        .filter(station -> station.getName().equalsIgnoreCase("Dilbeek"))
                        .findFirst();
        assert dilbeek.isPresent();
        stationList.add(dilbeek.get());
        stations.setStationList(stationList);

        for(Station station : stations.getStationList()){
            getTrainsForStation(station, date);
        }


        throw new NotImplementedException();
//        return secondsToReadableDate(totalDelayInSeconds);
    }

    /**
     * Retrieve liveboard for giver station.
     * Liveboard is limited to 50 results per request so iterated until full day is received
     * @param station
     * @param date
     * @return
     * @throws IOException
     */
    private static List<Liveboard> getTrainsForStation(Station station, Date date) throws IOException {
        String time = "0000";
        List<String> trains = new ArrayList<>();

        Liveboard liveboard = IRailApiHelper.retrieveLiveboard(station.getId(), date, "0000");
        if (Objects.nonNull(liveboard)) {
            for(Departure departure : liveboard.getDepartures().getDepartureList()) {
                if(!trains.contains(departure.getStation())) {
                    trains.add(departure.getStation());
                }
            }
        }
    }

    public static void handleDepartures(Liveboard liveboard) {
        throw new NotImplementedException();
    }

    private static int handleDelay(VehicleRetrieval vehicleRetrieval) {
        throw new NotImplementedException();
    }

    private static String secondsToReadableDate(int pSeconds) {
        int day = (int) TimeUnit.SECONDS.toDays(pSeconds);
        long hours = TimeUnit.SECONDS.toHours(pSeconds) - (day * 24L);
        long minutes = TimeUnit.SECONDS.toMinutes(pSeconds) - (TimeUnit.SECONDS.toHours(pSeconds) * 60);
//		long seconds = TimeUnit.SECONDS.toSeconds(pSeconds) - (TimeUnit.SECONDS.toMinutes(pSeconds) * 60); // Always zero
        return String.format("%s days %s hours %s minutes", day, hours, minutes);
    }

    public static Calendar epochToCalendar(String timeInSeconds) {
        Instant instant = Instant.ofEpochSecond(Long.parseLong(timeInSeconds));
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(Date.from(instant));
        return calendar;
    }

    public static String getTime(Calendar calendar) {
        String time;
        int hour = calendar.get(Calendar.HOUR_OF_DAY);
        int minutes = calendar.get(Calendar.MINUTE);

        if (hour < 10) {
            time = String.format("0%s", hour);
        } else {
            time = String.valueOf(hour);
        }

        if (minutes < 10) {
            time += String.format("0%s", minutes);
        } else {
            time += String.valueOf(minutes);
        }
        return time;
    }

}
