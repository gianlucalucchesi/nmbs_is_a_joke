package com.nmbs_is_a_joke.api.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Station {

    @JsonProperty("id")
    String id;
    @JsonProperty("@id")
    String url;
    @JsonProperty("name")
    String name;
    @JsonProperty("standardname")
    String standardName;
    @JsonProperty("locationX")
    double longitude;
    @JsonProperty("locationY")
    double latitude;

}
