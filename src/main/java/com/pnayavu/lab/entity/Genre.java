package com.pnayavu.lab.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;
import java.util.Set;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "genres")
@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
public class Genre {
  @Id
  @JsonProperty("id")
  private Long id;
  @JsonProperty("name")
  private String name;
  @JsonProperty("russian")
  private String russian;
  @JsonIgnore
  @ManyToMany(mappedBy = "genres")
  private Set<Anime> animes;
  @JsonProperty("entry_type")
  private String entryType;
}
