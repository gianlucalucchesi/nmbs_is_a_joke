package com.nmbs_is_a_joke.api.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Departure {

    @JsonProperty("id")
    private int id;
    @JsonProperty("delay")
    private int delay;
    @JsonProperty("station")
    private String station;
    @JsonProperty("stationinfo")
    private Station stationInfo;
    @JsonProperty("time")
    private int time;
    @JsonProperty("vehicle")
    private String vehicle;
    @JsonProperty("vehicleinfo")
    private Vehicle vehicleInfo;
    @JsonProperty("platform")
    private String platform;
    @JsonProperty("platforminfo")
    private Platform platformInfo;
    @JsonProperty("canceled")
    private int canceled;
    @JsonProperty("left")
    private int left;
    @JsonProperty("isExtra")
    private String isExtra;
    @JsonProperty("departureConnection")
    private String departureConnection;
    @JsonProperty("occupancy")
    private Occupancy occupancy;

}
