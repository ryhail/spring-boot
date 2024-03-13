package com.pnayavu.lab.controllers;

import com.pnayavu.lab.service.implementations.ShikimoriUserService;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClient;
import org.springframework.security.oauth2.client.annotation.RegisteredOAuth2AuthorizedClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user")
public class UserController {
    ShikimoriUserService shikimoriUserService;
    public UserController(ShikimoriUserService service) {
        this.shikimoriUserService = service;
    }
    @GetMapping(value="", produces = "application/json")
    public String userHome(@RegisteredOAuth2AuthorizedClient("shikimori") OAuth2AuthorizedClient authorizedClient) {
        shikimoriUserService = new ShikimoriUserService();
        return shikimoriUserService.getCurrentUser(authorizedClient.getAccessToken().getTokenValue());
    }
}
