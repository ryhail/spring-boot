package com.pnayavu.lab.service.implementations;

import com.fasterxml.jackson.databind.JsonNode;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
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
            return response.findValue("id").asInt();
        } else {
            return -1;
        }
    }
    public JsonNode getAnimeInfo(int animeId) {
        return webClient.get()
                .uri(uri -> uri.path("/"+animeId).build())
                .retrieve()
                .bodyToMono(JsonNode.class)
                .block();
    }
}
