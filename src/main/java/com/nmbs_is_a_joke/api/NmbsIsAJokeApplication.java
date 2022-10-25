package com.nmbs_is_a_joke.api;

import com.nmbs_is_a_joke.api.helper.IRailApiHelper;
import com.nmbs_is_a_joke.api.model.Liveboard;
import com.nmbs_is_a_joke.api.model.Station;
import com.nmbs_is_a_joke.api.model.Stations;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.io.IOException;
import java.util.*;

@SpringBootApplication
public class NmbsIsAJokeApplication {

	public static void main(String[] args) throws IOException {

		SpringApplication.run(NmbsIsAJokeApplication.class, args);

		List<Liveboard> liveboardList = handleLiveboards();

		System.out.println("===================================");
		System.out.println(liveboardList);
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

}
