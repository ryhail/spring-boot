package com.pnayavu.lab.service;

import com.fasterxml.jackson.databind.JsonNode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.security.oauth2.client.OAuth2ClientProperties.*;
import org.springframework.core.env.Environment;
import org.springframework.web.reactive.function.client.WebClient;



public class MyAnimeList {
    final WebClient webClient;
    @Value("${client-id}")
    private String CLIENT_ID;
    public MyAnimeList() {
        this.webClient = WebClient
                .builder()
                .baseUrl("https://api.myanimelist.net/v2/")
                .build();
    }

    public int getAnimeId(String animeName) {
        JsonNode response =  webClient.get()
                .uri(uriBuilder -> uriBuilder
                        .path("anime")
                        .queryParam("q", animeName)
                        .queryParam("limit", 1)
                        .build()
                )
                .header("X-MAL-CLIENT-ID", CLIENT_ID)
                .retrieve()
                .bodyToMono(JsonNode.class)
                .block();
        if (response != null) {
            if(response.findValue("id") != null) {
                return response.findValue("id").asInt();
            } else {
                return -1;
            }
        } else {
            return -1;
        }
    }
    public JsonNode getAnimeInfo(Integer animeId) {
        String fields = "id,title,main_picture,alternative_titles" +
                ",start_date,end_date,synopsis,mean,rank,popularity," +
                "num_list_users,num_scoring_users,nsfw,created_at,updated_at," +
                "media_type,status,genres,my_list_status,num_episodes,start_season," +
                "broadcast,source,average_episode_duration,rating,pictures,background," +
                "related_anime,related_manga,recommendations,studios,statistics";

        return webClient.get()
                .uri(uriBuilder -> uriBuilder
                        .path("anime/"+animeId.toString())
                        .queryParam("fields",fields)
                        .build()
                )
                .header("X-MAL-CLIENT-ID", CLIENT_ID)
                .retrieve()
                .bodyToMono(JsonNode.class)
                .block();
    }
}
