package com.pnayavu.lab.service.implementations;

import com.fasterxml.jackson.databind.JsonNode;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.server.ResponseStatusException;

@Service
public class ShikimoriAnimeService {
    private final WebClient webClient;
    public ShikimoriAnimeService(){
        webClient = WebClient
                .builder()
                .baseUrl("https://shikimori.one/api/animes")
                .build();
    }

    public int searchAnime(String animeName) {
        JsonNode response = webClient.get()
                .uri(uriBuilder -> uriBuilder
                        .queryParam("search", animeName)
                        .queryParam("limit", 1)
                        .queryParam("order","popularity")
                        .build()
                )
                .retrieve()
                .bodyToMono(JsonNode.class)
                .block();
        if (response != null) {
            if (!response.isEmpty()) {
                return response.findValue("id").asInt();
            }
        }
        return -1;
    }
    public JsonNode getAnimeInfo(int animeId) {
        if(animeId == -1)
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        return webClient.get()
                .uri(uri -> uri.path("/"+animeId).build())
                .retrieve()
                .bodyToMono(JsonNode.class)
                .block();
    }
}
