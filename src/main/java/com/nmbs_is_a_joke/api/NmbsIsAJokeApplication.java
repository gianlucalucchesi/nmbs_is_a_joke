package com.nmbs_is_a_joke.api;

import com.nmbs_is_a_joke.api.service.IRailService;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.io.IOException;
import java.util.Calendar;
import java.util.Date;

@SpringBootApplication
public class NmbsIsAJokeApplication {
    static
    IRailService iRailService;

    public static void main(String[] args) throws IOException {
        SpringApplication.run(NmbsIsAJokeApplication.class, args);

        Date today = Calendar.getInstance().getTime();
        String totalDelay = iRailService.getTotalDelayForGivenDay(today);
        System.out.println(totalDelay);
    }

}
