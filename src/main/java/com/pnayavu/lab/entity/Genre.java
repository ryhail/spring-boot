package com.pnayavu.lab.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;

import java.util.HashSet;
import java.util.Set;
@Entity
@Table(name = "genres")
@JsonIgnoreProperties(ignoreUnknown = true)
public class Genre {
    @Id
    @JsonProperty("id")
    private Long id;
    @JsonProperty("name")
    private String name;
    @JsonProperty("russian")
    private String russian;
    @ManyToMany(mappedBy = "genres")
    private Set<Anime> animes;
    @JsonProperty("entry_type")
    private String entryType;
}
