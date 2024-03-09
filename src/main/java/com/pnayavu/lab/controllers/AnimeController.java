package com.pnayavu.lab.controllers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.pnayavu.lab.entity.Anime;
import com.pnayavu.lab.service.AnimeService;
import com.pnayavu.lab.service.implementations.ShikimoriAnimeService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping("/anime")
public class AnimeController {
    private final AnimeService animeService;
    private final ShikimoriAnimeService shikimoriAnimeService;
    private final ObjectMapper objectMapper;
    AnimeController(AnimeService animeService,
                    ShikimoriAnimeService shikimoriAnimeService,
                    ObjectMapper objectMapper) {
        this.animeService = animeService;
        this.shikimoriAnimeService = shikimoriAnimeService;
        this.objectMapper = objectMapper;
    }
    @GetMapping(value = "",produces = "application/json")
    public List<Anime> getAnime(@RequestParam(required = false) String search) {
        List<Anime> listAnime;
        if(search == null) {
            listAnime = animeService.findAllAnime();
        } else {
            listAnime = animeService.findAllAnimeByName(search);
            if(listAnime.isEmpty())
                listAnime = animeService.findAllAnimeByRussian(search);
            if(listAnime.isEmpty()) {
                JsonNode anime = shikimoriAnimeService.getAnimeInfo(
                        shikimoriAnimeService.searchAnime(search)
                );
                try {
                    listAnime.add(objectMapper.readValue(anime.toPrettyString(), Anime.class));
                } catch (JsonProcessingException e) {
                    throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR,"error processing JSON");
                }
            }
        }
        if (listAnime.isEmpty())
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "no anime found");
        return listAnime;
    }
    @GetMapping(value = "/shikimori")
    public String addAnimeFromShikimori(@RequestParam String animeName) {
        int animeId = shikimoriAnimeService.searchAnime(animeName);
        JsonNode animeNode = shikimoriAnimeService.getAnimeInfo(animeId);
        if(animeNode == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "anime not found");
        } else {
            try {
                Anime anime = objectMapper.readValue(animeNode.toPrettyString(), Anime.class);
                anime.setPoster("https://shikimori.one" + animeNode.findValue("original")
                        .toString().replace("\"",""));
                animeService.saveAnime(anime);
            } catch (JsonProcessingException e) {
                return "error processing anime";
            }
            return "added successfully anime " + animeNode.findValue("name");
        }
    }

    @GetMapping(value = "/{animeId}",produces = "application/json")
    public Anime getAnime(@PathVariable Long animeId) {
        Anime anime = animeService.findAnime(animeId);
        if(anime == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,"no anime with such id");
        }
        return anime;
    }

    @PostMapping(value = "/add")
    @ResponseStatus(HttpStatus.CREATED)
    public String addAnime(@RequestBody Anime anime) {
        if(animeService.saveAnime(anime) == null)
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR,"anime not saved");
        return "saved successfully";
    }
    @PatchMapping(value = "/update")
    public Anime updateAnime(@RequestBody Anime anime) {
        Anime newAnime = animeService.updateAnime(anime);
        if(newAnime == null)
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR,"cannot update");
        return newAnime;
    }

    @DeleteMapping(value = "/{animeId}")
    public String deleteAnime(@PathVariable Long animeId){
        if(animeService.findAnime(animeId) == null)
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        animeService.deleteAnime(animeId);
        return "successfully deleted";
    }
}
