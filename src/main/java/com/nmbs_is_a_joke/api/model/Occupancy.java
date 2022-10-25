package com.nmbs_is_a_joke.api.model;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Occupancy {

    @JsonProperty("@id")
    private String url;
    @JsonProperty("name")
    private String name;
}
