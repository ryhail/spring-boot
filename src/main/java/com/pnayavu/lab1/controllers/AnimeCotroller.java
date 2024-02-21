package com.pnayavu.lab1.controllers;
import com.pnayavu.lab1.model.Anime;
import com.pnayavu.lab1.service.MyAnimeList;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/anime")
public class AnimeCotroller {
    @GetMapping(value="/search", produces = "application/json")
    public String searchAnime(@RequestParam String animeName) {
        MyAnimeList session = new MyAnimeList();
        int animeId = session.getAnimeId(animeName);
        if(animeId == -1) {
            return "failed retrieving anime title by id";
        } else {
            return session.getAnimeInfo(animeId).toPrettyString();
        }
    }
}
