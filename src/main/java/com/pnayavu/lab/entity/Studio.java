package com.pnayavu.lab.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;

import java.util.Set;

@Entity
@Table(name = "studios")
public class Studio {
    @Id
    @JsonProperty("id")
    private Long id;
    @JsonProperty("name")
    private String name;
    @OneToMany(mappedBy="studio")
    private Set<Anime> animes;
}
