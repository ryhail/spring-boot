package com.pnayavu.lab.controllers;

import org.springframework.security.oauth2.client.OAuth2AuthorizedClient;
import org.springframework.security.oauth2.client.annotation.RegisteredOAuth2AuthorizedClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.pnayavu.lab.service.implementations.ShikimoriUserService;

@RestController
@RequestMapping("/user")
public class UserController {

    @GetMapping(value="", produces = "application/json")
    public String userHome(@RegisteredOAuth2AuthorizedClient("shikimori") OAuth2AuthorizedClient authorizedClient) {
        ShikimoriUserService session = new ShikimoriUserService();
        return session.getCurrentUser(authorizedClient.getAccessToken().getTokenValue());
    }
}
