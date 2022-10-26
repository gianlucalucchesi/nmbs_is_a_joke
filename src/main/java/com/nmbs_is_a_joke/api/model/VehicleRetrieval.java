package com.nmbs_is_a_joke.api.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class VehicleRetrieval {

    @JsonProperty("version")
    private String version;
    @JsonProperty("timestamp")
    private String timestamp;
    @JsonProperty("vehicle")
    private String vehicle;
    @JsonProperty("vehicleinfo")
    private Vehicle vehicleInfo;
    @JsonProperty("stops")
    private Stops stops;

}
