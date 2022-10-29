package com.nmbs_is_a_joke.api.service;

import com.nmbs_is_a_joke.api.helper.IRailApiHelper;
import com.nmbs_is_a_joke.api.model.*;
import org.apache.commons.lang3.NotImplementedException;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.time.Instant;
import java.util.*;
import java.util.concurrent.TimeUnit;

@Component
public class IRailService {

    public String getTotalDelayForGivenDay(Calendar date) throws IOException {
        List<List<String>> vehicles = new ArrayList<>();
        int totalDelayInSeconds = 0;
        int totalCanceledTrains = 0;

        Stations stations = IRailApiHelper.retrieveAllStations();

        // START - For testing purpose
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
        // STOP - For testing purpose

        for(Station station : stations.getStationList()){
            vehicles.add(getVehiclesForStation(station, date));
        }

        return secondsToReadableDate(totalDelayInSeconds);
    }

    /**
     * Retrieve liveboard for giver station.
     * Liveboard is limited to 50 results per request so iterated until full day is received
     * @param station
     * @param date
     * @return
     * @throws IOException
     */
    private List<String> getVehiclesForStation(Station station, Calendar date) throws IOException {
        boolean allTrainsRetrieved = false;
        String time = "0000";
        List<String> trains = new ArrayList<>();

        while (!allTrainsRetrieved) {
            Liveboard liveboard = IRailApiHelper.retrieveLiveboard(station.getId(), date, time);

            if (Objects.nonNull(liveboard)) {
                int index = 0;
                for (Departure departure : liveboard.getDepartures().getDepartureList()) {
                    index++;
                    Calendar departureDateTime = epochToCalendar(departure.getTime());

                    if (departureDateTime.get(Calendar.DAY_OF_MONTH) == date.get(Calendar.DAY_OF_MONTH)) {
                        if (!trains.contains(departure.getVehicle())) {
                            trains.add(departure.getVehicle());
                        }
                        if (index == liveboard.getDepartures().getDepartureList().size()) {
                            time = getTime(departureDateTime);
                        }
                    } else {
                        allTrainsRetrieved = true;
                        break;
                    }
                }
            }
        }
        return trains;
    }

    private void handleDepartures(Liveboard liveboard) {
        throw new NotImplementedException();
    }

    private int handleDelay(VehicleRetrieval vehicleRetrieval) {
        throw new NotImplementedException();
    }

    private static String secondsToReadableDate(int pSeconds) {
        int day = (int) TimeUnit.SECONDS.toDays(pSeconds);
        long hours = TimeUnit.SECONDS.toHours(pSeconds) - (day * 24L);
        long minutes = TimeUnit.SECONDS.toMinutes(pSeconds) - (TimeUnit.SECONDS.toHours(pSeconds) * 60);
//		long seconds = TimeUnit.SECONDS.toSeconds(pSeconds) - (TimeUnit.SECONDS.toMinutes(pSeconds) * 60); // Always zero
        return String.format("%s days %s hours %s minutes", day, hours, minutes);
    }

    private Calendar epochToCalendar(long timeInSeconds) {
        Instant instant = Instant.ofEpochSecond(timeInSeconds);
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(Date.from(instant));
        return calendar;
    }

    private String getTime(Calendar calendar) {
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
