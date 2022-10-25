package com.nmbs_is_a_joke.api;

import com.nmbs_is_a_joke.api.helper.IRailApiHelper;
import com.nmbs_is_a_joke.api.model.Station;
import com.nmbs_is_a_joke.api.model.Stations;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.io.IOException;
import java.util.Calendar;
import java.util.Date;

@SpringBootApplication
public class NmbsIsAJokeApplication {

	public static void main(String[] args) throws IOException {
		Date today = Calendar.getInstance().getTime();

		SpringApplication.run(NmbsIsAJokeApplication.class, args);

		Stations stations = IRailApiHelper.retrieveAllStations();
		for (Station station : stations.getStation()) {
			IRailApiHelper.retrieveLiveboardForGivenStation(station.getName(), today);
		}
	}

}
