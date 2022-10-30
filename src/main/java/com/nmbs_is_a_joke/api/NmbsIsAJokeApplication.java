package com.nmbs_is_a_joke.api;

import com.nmbs_is_a_joke.api.service.IRailService;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.io.IOException;
import java.util.Calendar;

@SpringBootApplication
public class NmbsIsAJokeApplication {

    public static void main(String[] args) throws IOException {
        SpringApplication.run(NmbsIsAJokeApplication.class, args);

        System.out.printf("START: %s%n", System.currentTimeMillis());
        IRailService iRailService = new IRailService();
        Calendar today = Calendar.getInstance();
        today.add(Calendar.DAY_OF_MONTH, -10);
        String totalDelay = iRailService.getTotalDelayForGivenDay(today);
        System.out.println(totalDelay);
        System.out.printf("STOP: %s%n", System.currentTimeMillis());
    }

}
