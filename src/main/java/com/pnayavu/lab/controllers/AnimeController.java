package com.pnayavu.lab.controllers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.pnayavu.lab.entity.Anime;
import com.pnayavu.lab.service.AnimeService;
import com.pnayavu.lab.service.implementations.ShikimoriAnimeService;
import com.pnayavu.lab.service.implementations.ShikimoriUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.servlet.NoHandlerFoundException;

import java.util.List;

@RestController
@RequestMapping("/anime")
public class AnimeController {
    @Autowired
    private AnimeService animeService;
    @Autowired
    private ShikimoriAnimeService shikimoriAnimeService;
    @Autowired
    ObjectMapper objectMapper;
    @GetMapping(value = "",produces = "application/json")
    public List<Anime> getAnime(@RequestParam(required = false) Integer limit) {
        List<Anime> listAnime = animeService.findAllAnime();
        if(listAnime == null)
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,"no anime found");
        return animeService.findAllAnime();
    }
//    @PostMapping(value = "/add")
//    @ResponseStatus(HttpStatus.CREATED)
//    public String addAnime(@RequestParam String animeName) {
//        Integer animeId = shikimoriAnimeService.searchAnime(animeName);
//        JsonNode animeNode = shikimoriAnimeService.getAnimeInfo(animeId);
//        try {
//            Anime anime = objectMapper.readValue(animeNode.toPrettyString(), Anime.class);
//            animeService.saveAnime(anime);
//        } catch (JsonProcessingException e) {
//            return "error processing anime";
//        }
//        return "posted succesfully anime " + animeNode.findValue("name");
//    }
    @ResponseBody
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
    @DeleteMapping(value = "/{animeid}")
    public String deleteAnime(@PathVariable Long animeid){
        animeService.deleteAnime(animeid);
        return "successfully deleted";
    }
}
