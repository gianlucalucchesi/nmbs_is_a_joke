package com.nmbs_is_a_joke.api.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class Departures {

    @JsonProperty("number")
    private int number;
    @JsonProperty("departure")
    private List<Departure> departureList;

}
