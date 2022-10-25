package com.nmbs_is_a_joke.api.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Vehicle {

    @JsonProperty("name")
    private String name;
    @JsonProperty("shortname")
    private String shortName;
    @JsonProperty("number")
    private String number;
    @JsonProperty("type")
    private String type;
    @JsonProperty("@id")
    private String url;

}
