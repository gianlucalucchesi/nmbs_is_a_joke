package com.nmbs_is_a_joke.api;

import com.nmbs_is_a_joke.api.helper.IRailApiHelper;
import com.nmbs_is_a_joke.api.model.*;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.io.IOException;
import java.util.*;
import java.util.concurrent.TimeUnit;

@SpringBootApplication
public class NmbsIsAJokeApplication {

	public static void main(String[] args) throws IOException {

		SpringApplication.run(NmbsIsAJokeApplication.class, args);

		List<Liveboard> liveboardList = handleLiveboards();
		System.out.println("===================================");
		int totalDelayInSeconds = handleDelay(liveboardList);
		System.out.printf("%s seconds%n", totalDelayInSeconds);
		String readableDelay = secondsToReadableDate(totalDelayInSeconds);
		System.out.println(readableDelay);
	}

	private static List<Liveboard> handleLiveboards() throws IOException {
		Date today = Calendar.getInstance().getTime();

		Stations stations = IRailApiHelper.retrieveAllStations();
		assert stations != null;

		List<Liveboard> liveboardList = new ArrayList<>();
		for (Station station : stations.getStationList()) {
			Liveboard liveboard = IRailApiHelper.retrieveLiveboardForGivenStation(station.getId(), today);
			if(Objects.nonNull(liveboard)) {
				System.out.println(Objects.nonNull(liveboard.getStation()) ? liveboard.getStation() : "");
			}
			liveboardList.add(liveboard);
		}
		return liveboardList;
	}

	private static int handleDelay(List<Liveboard> liveboardList) {
		int totalDelayInSeconds = 0;
		for(Liveboard liveboard : liveboardList) {
			if(Objects.nonNull(liveboard)) {
				for (Departure departure : liveboard.getDepartures().getDepartureList()) {
					totalDelayInSeconds += departure.getDelay();
				}
			}
		}
		return totalDelayInSeconds;
	}

	private static String secondsToReadableDate(int pSeconds) {
		int day = (int) TimeUnit.SECONDS.toDays(pSeconds);
		long hours = TimeUnit.SECONDS.toHours(pSeconds) - (day * 24L);
		long minutes = TimeUnit.SECONDS.toMinutes(pSeconds) - (TimeUnit.SECONDS.toHours(pSeconds) * 60);
//		long seconds = TimeUnit.SECONDS.toSeconds(pSeconds) - (TimeUnit.SECONDS.toMinutes(pSeconds) * 60);
		return String.format("%s days %s hours %s minutes", day, hours, minutes);
	}

}
