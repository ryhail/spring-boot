package com.pnayavu.lab.service.implementations;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClient;
import org.springframework.web.reactive.function.client.WebClient;

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
