package com.nmbs_is_a_joke.api.service;

import com.nmbs_is_a_joke.api.helper.IRailApiHelper;
import com.nmbs_is_a_joke.api.model.*;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.time.Instant;
import java.util.*;
import java.util.concurrent.TimeUnit;

@Service
public class IRailService {

    IRailApiHelper iRailApiHelper;

    public IRailService() {
        iRailApiHelper = new IRailApiHelper();
    }

    public String getTotalDelayForGivenDay(Calendar calendar) throws IOException {
        List<List<String>> vehiclesPerStationList = new ArrayList<>();
        List<VehicleRetrieval> vehicleDetailsForDate = new ArrayList<>();
        int totalDelayInSeconds = 0;
        int totalCanceledTrains = 0;

        Stations stations = iRailApiHelper.retrieveAllStations();

        // START - For testing purpose
//        assert stations != null;
//        List<Station> stationList = new ArrayList<>();
//        Optional<Station> dilbeek =
//                stations.getStationList()
//                        .stream()
//                        .filter(station -> station.getName().equalsIgnoreCase("Dilbeek"))
//                        .findFirst();
//        assert dilbeek.isPresent();
//        stationList.add(dilbeek.get());
//        stations.setStationList(stationList);
        // STOP - For testing purpose

        for(Station station : stations.getStationList()){
            vehiclesPerStationList.add(getVehiclesForStation(station, calendar));
        }
        for(List<String> vehiclesPerStation : vehiclesPerStationList) {
            vehicleDetailsForDate = getVehicleDetailsForDate(vehiclesPerStation, calendar);
        }
        for(VehicleRetrieval vehicleDetails : vehicleDetailsForDate) {
            totalDelayInSeconds += totalDelayForGivenVehicle(vehicleDetails);
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
        List<String> vehicles = new ArrayList<>();

        while (!allTrainsRetrieved) {
            Liveboard liveboard = iRailApiHelper.retrieveLiveboard(station.getId(), date, time);

            if (Objects.nonNull(liveboard)) {
                int index = 0;
                for (Departure departure : liveboard.getDepartures().getDepartureList()) {
                    index++;
                    Calendar departureDateTime = epochToCalendar(departure.getTime());

                    if (departureDateTime.get(Calendar.DAY_OF_MONTH) == date.get(Calendar.DAY_OF_MONTH)) {
                        if (!vehicles.contains(departure.getVehicle())) {
                            vehicles.add(departure.getVehicle());
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
        return vehicles;
    }

    private List<VehicleRetrieval> getVehicleDetailsForDate(List<String> vehicles, Calendar calendar) throws IOException {
        List<VehicleRetrieval> vehicleDetailsList = new ArrayList<>();
        for(String vehicleId : vehicles) {
            VehicleRetrieval vehicleDetails = iRailApiHelper.retrieveVehicle(vehicleId, calendar);
            vehicleDetailsList.add(vehicleDetails);
        }
        return vehicleDetailsList;
    }

    private int totalDelayForGivenVehicle(VehicleRetrieval vehicleDetail) {
        int lastStop = vehicleDetail.getStops().getNumber();
        return vehicleDetail.getStops().getStopList().get(lastStop - 1).getDelay();
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
