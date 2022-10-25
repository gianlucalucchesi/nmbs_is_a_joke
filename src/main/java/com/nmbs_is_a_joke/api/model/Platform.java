package com.nmbs_is_a_joke.api.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Platform {

    @JsonProperty("name")
    private String name;
    @JsonProperty("normal")
    private String normal;

}
