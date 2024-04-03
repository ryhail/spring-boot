package com.pnayavu.lab.service.implementations;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.IntNode;
import com.pnayavu.lab.cache.InMemoryMap;
import com.pnayavu.lab.entity.Anime;
import com.pnayavu.lab.logging.Logged;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.server.ResponseStatusException;

@Service
public class ShikimoriAnimeService {
  private final WebClient webClient;
  private final InMemoryMap inMemoryMap;

  public ShikimoriAnimeService(InMemoryMap inMemoryMap) {
    this.inMemoryMap = inMemoryMap;
    webClient = WebClient.builder().baseUrl("https://shikimori.one/api/animes").build();
  }

  @Logged
  public int searchAnime(String animeName) {
    String key = "SHIKIMORI NAME " + animeName;
    IntNode cachedResult = (IntNode) inMemoryMap.get(key);
    if (cachedResult != null) {
      return cachedResult.asInt();
    }
    JsonNode response = webClient.get().uri(
            uriBuilder -> uriBuilder.queryParam("search", animeName).queryParam("limit", 1)
                .queryParam("order", "popularity").build()).retrieve().bodyToMono(JsonNode.class)
        .block();
    if (response != null && !response.isEmpty()) {
      inMemoryMap.put(key, response.findValue("id"));
      return response.findValue("id").asInt();
    }
    return -1;
  }

  @Logged
  public Anime getAnimeInfo(int animeId) {
    if (animeId == -1) {
      throw new ResponseStatusException(HttpStatus.NOT_FOUND);
    }
    String key = "SHIKIMORI ANIME " + animeId;
    Anime cachedResult = (Anime) inMemoryMap.get(key);
    if (cachedResult != null) {
      return cachedResult;
    }
    Anime anime = webClient.get().uri(uri -> uri.path("/" + animeId).build()).retrieve()
        .bodyToMono(Anime.class).block();
    if (anime == null) {
      throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Anime from Shikimori not found");
    }
    inMemoryMap.put(key, anime);
    return anime;
  }
}
