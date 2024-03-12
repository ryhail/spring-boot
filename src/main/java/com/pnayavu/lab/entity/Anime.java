package com.pnayavu.lab.entity;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;

import java.time.LocalDate;
import java.util.Set;

@Entity
@Table(name = "animes")
@JsonIgnoreProperties(ignoreUnknown = true)
public class Anime {
    @JsonProperty("id")
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
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
    @JsonProperty("poster")
    private String poster;
    @ManyToMany (cascade = {
            CascadeType.PERSIST,
            CascadeType.MERGE,
            CascadeType.REFRESH
    })
    @JsonProperty("genres")
    @JoinTable(name = "anime_genre",
            joinColumns = @JoinColumn(name = "anime_id"),
            inverseJoinColumns = @JoinColumn(name = "genre_id")
    )
    private Set<Genre> genres;
    @JsonProperty("studio")
    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinColumn(name="studio_id", nullable=false)
    private Studio studio;
    public void setPoster(String poster) {
        this.poster = poster;
    }
}
