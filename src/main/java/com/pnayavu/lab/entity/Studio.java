package com.pnayavu.lab.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonSetter;
import com.fasterxml.jackson.databind.node.TextNode;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.Set;

@Entity
@Table(name = "studios")
@JsonIgnoreProperties
@Getter
@Setter
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
    @JsonIgnore
    @OneToMany(mappedBy="studio")
    private Set<Anime> animes;
    @JsonSetter("image")
    void setStudioImageJson(TextNode image) {
        this.image = "https://shikimori.one"+image.asText();
    }
}
