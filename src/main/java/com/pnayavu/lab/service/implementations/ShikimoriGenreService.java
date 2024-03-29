package com.pnayavu.lab.service.implementations;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.pnayavu.lab.entity.Genre;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;
@Service
public class ShikimoriGenreService {
    private final WebClient webClient;
    public ShikimoriGenreService(){
        webClient = WebClient
                .builder()
                .baseUrl("https://shikimori.one/api/genres")
                .build();
    }

    public List<Genre> getAllGenres(@Autowired ObjectMapper objectMapper) {
        JsonNode response  = webClient.get()
                    .retrieve()
                    .bodyToMono(JsonNode.class)
                    .block();
        List<Genre> genreList = new ArrayList<>();
        if (response != null) {
            for (JsonNode element : response) {
                Genre object;
                try {
                    object = objectMapper.treeToValue(element, Genre.class);
                } catch (JsonProcessingException e) {
                    throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR);
                }
                genreList.add(object);
            }
        }
        return genreList;
    }
}
