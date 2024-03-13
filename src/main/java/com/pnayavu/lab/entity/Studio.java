package com.pnayavu.lab.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

import java.util.Set;

@Entity
@Table(name = "studios")
@JsonIgnoreProperties
public class Studio {
    @Id
    @JsonProperty("id")
    private Long id;
    @JsonProperty("name")
    private String name;
    @JsonProperty("real")
    private Boolean real;
    @JsonProperty("image")
    private String image;
    @OneToMany(mappedBy="studio")
    private Set<Anime> animes;

}
