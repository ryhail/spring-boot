package com.pnayavu.lab1.controllers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.pnayavu.lab1.model.Anime;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;


@RestController
@RequestMapping("/anime")
public class AnimeCotroller {

    @GetMapping("/frieren")
    public String sendFrieren() {

        Anime frieren = new Anime();
        frieren.setName("Frieren: Beyond Journey's End");
        frieren.setKind("tv");
        frieren.setStatus("ongoing");
        frieren.setAired(LocalDate.of(2023,9,29));

        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());

        try {
            return objectMapper.writeValueAsString(frieren);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            return "JSON conversion failed";
        }
    }
}
