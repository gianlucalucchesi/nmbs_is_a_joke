package com.nmbs_is_a_joke.api.service;

import ch.qos.logback.classic.Level;
import ch.qos.logback.classic.Logger;
import com.nmbs_is_a_joke.api.helper.IRailApiHelper;
import com.nmbs_is_a_joke.api.model.*;
import lombok.Getter;
import lombok.Setter;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.time.Instant;
import java.util.*;
import java.util.stream.Collectors;

@Getter
@Setter
@Service
public class IRailService {
    List<String> vehicleList = new ArrayList<>();
    List<VehicleRetrieval> vehicleDetailsForDateList = new ArrayList<>();
    int totalDelayedTrains = 0;
    int totalTrainsCancelled = 0;
    IRailApiHelper iRailApiHelper;
    Logger log = (Logger) LoggerFactory.getLogger("com.nmbs_is_a_joke");

    public IRailService() {
        iRailApiHelper = new IRailApiHelper();
        log.setLevel(Level.INFO);
    }

    public int getTotalDelayInSecondsForGivenDay(Calendar calendar) throws IOException {
        int i = 0;
        int totalDelayInSeconds = 0;

        Stations stations = this.iRailApiHelper.retrieveAllStations();
        assert stations != null;
        log.info("All stations retrieved\r");

        // START - For testing purpose
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

        for (Station station : stations.getStationList()) {
            i++;
            log.info("Retrieving liveboard for {} ({}/{})\r", station.getName(), i, stations.getStationList().size());
            this.vehicleList.addAll(getVehiclesForStation(station, calendar));
        }

        this.vehicleList = removeDuplicates(this.vehicleList);

        i = 0;
        for (String vehicleName : this.vehicleList) {
            i++;
            System.out.printf("Retrieving vehicle details of %s (%s/%s)\r", vehicleName, i, this.vehicleList.size());
            log.info("Retrieving vehicle details of {} ({}/{})\r", vehicleName, i, this.vehicleList.size());
            this.vehicleDetailsForDateList.add(getVehicleDetailsForDate(vehicleName, calendar));
        }

        for (VehicleRetrieval vehicleDetails : this.vehicleDetailsForDateList) {
            totalDelayInSeconds += getDelayForVehicle(vehicleDetails);
            handleCancellation(vehicleDetails);
        }
        log.info("Total cancelled trains: {}", this.totalTrainsCancelled);
        log.info("Total delay in seconds: {}", totalDelayInSeconds);
        return totalDelayInSeconds;
    }

    /**
     * Retrieve liveboard for giver station.
     * Liveboard is limited to 50 results per request so iterated until full day is received
     *
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
            if (Objects.nonNull(liveboard) && !liveboard.getDepartures().getDepartureList().isEmpty()) {
                int index = 0;
                for (Departure departure : liveboard.getDepartures().getDepartureList()) {
                    index++;
                    Calendar departureDateTime = epochToCalendar(departure.getTime());

                    if (departureDateTime.get(Calendar.DAY_OF_MONTH) == date.get(Calendar.DAY_OF_MONTH)) {
                        if (!vehicles.contains(departure.getVehicle()) && !departure.getVehicle().contains("BUS")) {
                            vehicles.add(departure.getVehicle());
                        }
                        if (index == liveboard.getDepartures().getDepartureList().size()) {
                            // If there is only 1 or less we will keep retrieving the same one
                            if (index < 2) {
                                allTrainsRetrieved = true;
                                break;
                            } else {
                                time = getTime(departureDateTime);
                            }
                        }
                    } else {
                        allTrainsRetrieved = true;
                        break;
                    }
                }
            } else {
                allTrainsRetrieved = true;
            }
        }
        return vehicles;
    }

    private VehicleRetrieval getVehicleDetailsForDate(String vehicleId, Calendar calendar) throws IOException {
        return iRailApiHelper.retrieveVehicle(vehicleId, calendar);
    }

    private List<String> removeDuplicates(List<String> vehicleList) {
        return vehicleList.stream().distinct().collect(Collectors.toList());
    }

    private int getDelayForVehicle(VehicleRetrieval vehicleDetail) {
        if (Objects.nonNull(vehicleDetail)) {
            int lastStop = vehicleDetail.getStops().getNumber();
            int delay = vehicleDetail.getStops().getStopList().get(lastStop - 1).getDelay();
            if (delay > 0) {
                this.totalDelayedTrains++;
            }
            return delay;
        }
        return 0;
    }

    private void handleCancellation(VehicleRetrieval vehicleDetail) {
        if (Objects.nonNull(vehicleDetail)) {
            for (Stop stop : vehicleDetail.getStops().getStopList()) {
                if (!stop.getCanceled().isEmpty() && !stop.getCanceled().equals("0")) {
                    totalTrainsCancelled++;
                    break;
                }
            }
        }
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
