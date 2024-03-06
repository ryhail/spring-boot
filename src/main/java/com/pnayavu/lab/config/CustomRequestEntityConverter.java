package com.pnayavu.lab.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.security.oauth2.client.OAuth2ClientProperties;
import org.springframework.context.EnvironmentAware;
import org.springframework.context.annotation.Bean;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.core.convert.converter.Converter;
import org.springframework.core.env.Environment;
import org.springframework.http.RequestEntity;
import org.springframework.security.oauth2.client.endpoint.OAuth2AuthorizationCodeGrantRequest;
import org.springframework.security.oauth2.client.endpoint.OAuth2AuthorizationCodeGrantRequestEntityConverter;
import org.springframework.security.oauth2.client.registration.ClientRegistrationRepository;
import org.springframework.security.oauth2.core.endpoint.PkceParameterNames;
import org.springframework.util.MultiValueMap;

import org.springframework.security.oauth2.core.endpoint.OAuth2ParameterNames;

public class CustomRequestEntityConverter implements
        Converter<OAuth2AuthorizationCodeGrantRequest, RequestEntity<?>> {

    @Value("${client-id}")
    private String MAL_CLIENT_ID;
    @Value("${client-secret}")
    private String MAL_CLIENT_SECRET;
    private OAuth2AuthorizationCodeGrantRequestEntityConverter defaultConverter;

    public CustomRequestEntityConverter() {
        defaultConverter = new OAuth2AuthorizationCodeGrantRequestEntityConverter();
    }

    @Override
    public RequestEntity<?> convert(OAuth2AuthorizationCodeGrantRequest req) {
        RequestEntity<?> entity = defaultConverter.convert(req);
        SecurityConfig securityConfig = new SecurityConfig();
        MultiValueMap<String, String> params = null;
        if (entity != null) {
            params = (MultiValueMap<String,String>) entity.getBody();
        }
        if (params != null) {
            params.set(PkceParameterNames.CODE_VERIFIER, SecurityConfig.getCodeVerifier());
        }
//        if(params.getFirst(OAuth2ParameterNames.CLIENT_ID) == MAL_CLIENT_ID) {
//            params.add(OAuth2ParameterNames.CLIENT_SECRET,MAL_CLIENT_SECRET);
//        }
        return new RequestEntity<>(params, entity.getHeaders(),
                entity.getMethod(), entity.getUrl());
    }

}