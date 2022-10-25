package com.nmbs_is_a_joke.api.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class Stations {

    @JsonProperty("version")
    private String version;
    @JsonProperty("timestamp")
    private String timestamp;
    @JsonProperty("station")
    private List<Station> station;

}
