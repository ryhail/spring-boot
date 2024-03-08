package com.pnayavu.lab.controllers;

import com.pnayavu.lab.entity.Anime;
import com.pnayavu.lab.service.AnimeService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping("/anime")
public class AnimeController {
    private final AnimeService animeService;
    //private final ShikimoriAnimeService shikimoriAnimeService;
    AnimeController(AnimeService animeService) {
        this.animeService = animeService;
        //this.shikimoriAnimeService = shikimoriAnimeService;
    }
    @GetMapping(value = "",produces = "application/json")
    public List<Anime> getAnime() {
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
        Anime newAnime = animeService.saveAnime(anime);
        if(newAnime == null)
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR,"cannot update");
        return newAnime;
    }


    @DeleteMapping(value = "/{animeid}")
    public String deleteAnime(@PathVariable Long animeid){
        animeService.deleteAnime(animeid);
        return "successfully deleted";
    }
}
