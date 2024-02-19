package com.pnayavu.lab1.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import java.time.LocalDate;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

@JsonPropertyOrder({
        "name",
        "kind",
        "status",
        "aired"
})
public class Anime {
    @JsonProperty("name")
    private String name;

    @JsonProperty("kind")
    private String kind;

    @JsonProperty("status")
    private String status;

    @JsonProperty("aired")
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

    @JsonProperty("aired")
    public void setAired(LocalDate aired) {
        this.aired = aired;
    }
}
