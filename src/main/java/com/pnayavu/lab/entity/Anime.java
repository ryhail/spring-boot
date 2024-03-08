package com.pnayavu.lab.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "animes")
public class Anime {
    @JsonProperty("id")
    @Id
    @GeneratedValue
    private Long id;
    @JsonProperty("name")
    private String name;
    @JsonProperty("russian")
    private String russian;
    @JsonProperty("kind")
    private String kind;
    @JsonProperty("score")
    private Double score;
    @JsonProperty("status")
    private String status;
    @JsonProperty("episodes")
    private int episodes;
    @JsonProperty("aired_on")
    @Column(name = "aired_on")
    private LocalDate airedOn;
    @JsonProperty("description")
    private String description;


    public Anime(){

    }
    public Anime(Long id, String name, String russian, String kind, String description, int episodes, LocalDate airedOn, String status) {
        this.id = id;
        this.name = name;
        this.russian = russian;
        this.kind = kind;
        this.description = description;
        this.episodes = episodes;
        this.airedOn = airedOn;
        this.status = status;
    }

}
