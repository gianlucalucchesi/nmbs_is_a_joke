package com.nmbs_is_a_joke.api.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class Stops {

    @JsonProperty("number")
    private int number;
    @JsonProperty("stop")
    private List<Stop> stopList;

}
