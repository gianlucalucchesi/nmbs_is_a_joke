package com.nmbs_is_a_joke.api.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Stop {

    @JsonProperty("id")
    private String id;
    @JsonProperty("station")
    private String station;
    @JsonProperty("stationinfo")
    private Station stationInfo;
    @JsonProperty("time")
    private String time;
    @JsonProperty("delay")
    private int delay;
    @JsonProperty("platform")
    private String platform;
    @JsonProperty("platforminfo")
    private Platform platformInfo;
    @JsonProperty("canceled")
    private String canceled;
    @JsonProperty("left")
    private String left;
    @JsonProperty("arrived")
    private String arrived;
    @JsonProperty("departureDelay")
    private int departureDelay;
    @JsonProperty("departureCanceled")
    private int departureCanceled;
    @JsonProperty("scheduledDepartureTime")
    private long scheduledDepartureTime;
    @JsonProperty("scheduledArrivalTime")
    private long scheduledArrivalTime;
    @JsonProperty("arrivalDelay")
    private int arrivalDelay;
    @JsonProperty("arrivalCanceled")
    private int arrivalCanceled;
    @JsonProperty("isExtraStop")
    private int isExtraStop;
    @JsonProperty("departureConnection")
    private String departureConnection;
    @JsonProperty("occupancy")
    private Occupancy occupancy;

}
