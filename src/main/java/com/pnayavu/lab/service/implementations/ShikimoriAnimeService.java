package com.pnayavu.lab.service.implementations;

import static java.lang.Thread.sleep;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.IntNode;
import com.pnayavu.lab.cache.InMemoryMap;
import com.pnayavu.lab.logging.Logged;
import com.pnayavu.lab.model.Anime;
import com.pnayavu.lab.model.Genre;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;
import org.springframework.web.server.ResponseStatusException;

@Service
public class ShikimoriAnimeService {
  private static final String SEARCH = "search";
  private final WebClient webClient;
  private final InMemoryMap cache;

  public ShikimoriAnimeService(InMemoryMap inMemoryMap) {
    this.cache = inMemoryMap;
    webClient = WebClient.builder().baseUrl("https://shikimori.one/api/animes").build();
  }

  @Logged
  public int searchAnime(String animeName) {
    String key = "SHIKIMORI ANIME NAME " + animeName;
    IntNode cachedResult = (IntNode) cache.get(key);
    if (cachedResult != null) {
      return cachedResult.asInt();
    }
    JsonNode response = webClient.get().uri(
            uriBuilder -> uriBuilder.queryParam(SEARCH, animeName).queryParam("limit", 1)
                .queryParam("order", "popularity").build()).retrieve().bodyToMono(JsonNode.class)
        .block();
    if (response != null && !response.isEmpty()) {
      cache.put(key, response.findValue("id"));
      return response.findValue("id").asInt();
    }
    return -1;
  }

  @Logged
  public Anime getAnimeInfo(int animeId) {
    if (animeId == -1) {
      throw new ResponseStatusException(HttpStatus.NOT_FOUND);
    }
    String key = "ANIME ID" + animeId;
    Anime cachedResult = (Anime) cache.get(key);
    if (cachedResult != null) {
      return cachedResult;
    }
    try {
      Anime anime = webClient.get().uri(uri -> uri.path("/" + animeId).build()).retrieve()
          .bodyToMono(Anime.class).block();
      cache.put(key, Optional.of(anime));
      return anime;
    } catch (WebClientResponseException.NotFound e) {
      throw new ResponseStatusException(HttpStatus.NOT_FOUND, "No anime with such id");
    }
  }

  @Logged
  public List<Integer> getAnimeWithParameters(Anime parameters, Integer page) {
    Long studioId = null;
    String genreId = null;
    if(parameters.getStudio() != null) {
      studioId = parameters.getStudio().getId();
    }
    if(parameters.getGenres() != null) {
      genreId = parameters.getGenres().stream().map(Genre::getId).map(Object::toString).collect(Collectors.joining(", "));
    }
    Long finalStudioId = studioId;
    String finalGenreId = genreId;
    try {
      JsonNode animes = webClient.get().uri(
              uriBuilder -> uriBuilder
                  .queryParam("limit", 20)
                  .queryParam("order", "popularity")
                  .queryParamIfPresent("page", Optional.ofNullable(page))
                  .queryParamIfPresent("kind", Optional.ofNullable(parameters.getKind()))
                  .queryParamIfPresent("status", Optional.ofNullable(parameters.getStatus()))
                  .queryParamIfPresent(SEARCH, Optional.ofNullable(parameters.getRussian()))
                  .queryParamIfPresent(SEARCH, Optional.ofNullable(parameters.getName()))
                  .queryParamIfPresent("score", Optional.ofNullable(parameters.getScore()))
                  .queryParamIfPresent("studio", Optional.ofNullable(finalStudioId))
                  .queryParamIfPresent("genre", Optional.ofNullable(finalGenreId))
                  .build()).retrieve()
          .bodyToMono(JsonNode.class)
          .block();
      return Stream.of(animes).flatMap(p -> p.findValues("id").stream().map(JsonNode::asInt)).toList();
    } catch(WebClientResponseException.UnprocessableEntity e) {
      throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Incorrect parameters");
    }
  }

  @Logged
  public List<Anime> getAnimeFullInfo(List<Integer> animeId) {
    List<Anime> animeList = new ArrayList<>();
    for (Integer id:
         animeId) {
      try {
        if(cache.containsKey("ANIME ID" + id))
          animeList.add((Anime)cache.get("ANIME ID "+id));
        else
          animeList.add(getAnimeInfo(id));
      } catch (WebClientResponseException.TooManyRequests e) {
        try {
          sleep(1000);
          animeList.add(getAnimeInfo(id));
        } catch (InterruptedException intExc) {
          throw new RuntimeException(intExc);
        }
      }
    }
    return animeList;
  }
}
