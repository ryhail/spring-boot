package com.pnayavu.lab.controllers;

import com.pnayavu.lab.entity.Anime;
import com.pnayavu.lab.error.MyNotFoundException;
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

    AnimeController(AnimeService animeService,
                    ShikimoriAnimeService shikimoriAnimeService) {
        this.animeService = animeService;
        this.shikimoriAnimeService = shikimoriAnimeService;
    }
    @GetMapping(value = "",produces = "application/json")
    public List<Anime> getAnime(@RequestParam(required = false) String search) {
        List<Anime> listAnime;
        if(search == null) {
            listAnime = animeService.findAllAnime();
        } else {
            listAnime = animeService.searchAnime(search);
            if(listAnime.isEmpty()) {
                Anime anime = shikimoriAnimeService.getAnimeInfo(
                        shikimoriAnimeService.searchAnime(search)
                );
                listAnime.add(anime);
            }
        }
        if (listAnime.isEmpty())
            throw new MyNotFoundException(HttpStatus.NOT_FOUND, "Anime not found");
        return listAnime;
    }
    @GetMapping(value = "/shikimori", produces = "application/json")
    public String addAnimeFromShikimori(@RequestParam String animeName) {
        Anime anime = shikimoriAnimeService.getAnimeInfo(
                shikimoriAnimeService.searchAnime(animeName)
        );
        if(anime == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "anime not found");
        } else {
            anime = animeService.saveAnime(anime);
            if(anime == null)
                throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "anime not saved");
            return "added successfully anime " + anime.getName();
        }
    }

    @GetMapping(value = "/{animeId}",produces = "application/json")
    public Anime getAnime(@PathVariable Long animeId) {
        Anime anime = animeService.findAnime(animeId);
        if(anime == null) {
            throw new MyNotFoundException(HttpStatus.NOT_FOUND, "Anime not found");
        }
        return anime;
    }

    @PostMapping(value = "")
    @ResponseStatus(HttpStatus.CREATED)
    public Anime addAnime(@RequestBody Anime anime) {
        Anime savedAnime = animeService.saveAnime(anime);
        if(savedAnime == null)
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR,"anime not saved");
        return savedAnime;
    }
    @PutMapping(value = "")
    public Anime updateAnime(@RequestBody Anime anime) {
        Anime newAnime = animeService.updateAnime(anime);
        if(newAnime == null)
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR,"cannot update");
        return newAnime;
    }

    @DeleteMapping(value = "/{animeId}", produces = "application/json")
    public String deleteAnime(@PathVariable Long animeId){
        if(animeService.findAnime(animeId) == null)
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        animeService.deleteAnime(animeId);
        return "successfully deleted";
    }
}
