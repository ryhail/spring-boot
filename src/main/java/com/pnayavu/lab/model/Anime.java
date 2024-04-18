package com.pnayavu.lab.model;


import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonSetter;
import com.fasterxml.jackson.databind.node.ObjectNode;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import java.time.LocalDate;
import java.util.Set;
import lombok.Getter;
import lombok.Setter;


@Entity
@Table(name = "animes")
@JsonIgnoreProperties(ignoreUnknown = true)
@Getter
@Setter
public class Anime {
  @JsonProperty("id")
  @Id
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
  @JsonProperty("image")
  private String image;
  @ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
  @JsonProperty("genres")
  @JoinTable(name = "anime_genre", joinColumns = @JoinColumn(name = "anime_id"),
      inverseJoinColumns = @JoinColumn(name = "genre_id"))
  private Set<Genre> genres;
  @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
  @JoinColumn(name = "studio_id", nullable = false)
  private Studio studio;


  @JsonSetter("studios")
  public void setStudioFromArray(Studio[] studios) {
    if (studios.length == 0) {
      this.studio = new Studio();
      this.studio.setId(0L);
      return;
    }
    this.studio = studios[0];
  }

  @JsonGetter("studio")
  public Studio getStudio() {
    return this.studio;
  }

  @JsonSetter("image")
  public void setImage(ObjectNode image) {
    this.image = "https://shikimori.one" + image.get("original").asText();
  }
}
