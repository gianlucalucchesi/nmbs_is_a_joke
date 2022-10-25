package com.nmbs_is_a_joke.api.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Liveboard {

    @JsonProperty("version")
    private String version;
    @JsonProperty("timestamp")
    private int timestamp;
    @JsonProperty("station")
    private String station;
    @JsonProperty("stationinfo")
    private Station stationInfo;
    @JsonProperty("departures")
    private Departures departures;

}
