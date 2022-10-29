package com.nmbs_is_a_joke.api;

import com.nmbs_is_a_joke.api.service.IRailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.io.IOException;
import java.util.Calendar;

@SpringBootApplication
public class NmbsIsAJokeApplication {

    public static void main(String[] args) throws IOException {
        SpringApplication.run(NmbsIsAJokeApplication.class, args);

        IRailService iRailService = new IRailService();
        Calendar today = Calendar.getInstance();

        String totalDelay = iRailService.getTotalDelayForGivenDay(today);
        System.out.println(totalDelay);
    }

}
