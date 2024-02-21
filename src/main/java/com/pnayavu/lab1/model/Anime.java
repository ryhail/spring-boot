package com.pnayavu.lab1.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonSetter;

import java.time.LocalDate;

public class Anime {
    @JsonProperty("name")
    private String name;
    @JsonProperty("kind")
    private String kind;
    @JsonProperty("status")
    private String status;
    @JsonProperty("aired-on")
    private LocalDate aired;

    @JsonProperty("name")
    public void setName(String name) {
        this.name = name;
    }
    @JsonProperty("kind")
    public void setKind(String kind) {
        this.kind = kind;
    }
    @JsonProperty("status")
    public void setStatus(String status) {
        this.status = status;
    }
    @JsonSetter("aired-on")
    public void setAired(LocalDate aired) {
        this.aired = aired;
    }

}
