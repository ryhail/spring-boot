package com.pnayavu.lab.service.implementations;

import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
@Service
public class ShikimoriUserService {
    private final WebClient webClient;
    public ShikimoriUserService(){
        webClient = WebClient
                .builder()
                .baseUrl("https://shikimori.one/api/")
                .build();
    }
    public String getCurrentUser(String accessToken) {
        return webClient.get()
                .uri("users/whoami")
                .header("bearer", accessToken)
                .retrieve()
                .bodyToMono(String.class)
                .block();
    }
}
