package com.pnayavu.lab.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.pnayavu.lab.entity.Genre;
import com.pnayavu.lab.repository.GenreRepository;
import com.pnayavu.lab.service.GenreService;
import com.pnayavu.lab.service.implementations.ShikimoriGenreService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/genre")
public class GenreController {
    private final GenreService genreService;
    private final ShikimoriGenreService shikimoriGenreService;
    public GenreController(GenreService genreService,
                           ShikimoriGenreService shikimoriGenreService) {
        this.genreService = genreService;
        this.shikimoriGenreService = shikimoriGenreService;
    }
    @GetMapping(value="")
    public List<Genre> getAllGenres() {
        return genreService.findAllGenres();
    }
    @GetMapping(value = "/update")
    public List<Genre> updateGenres(ObjectMapper objectMapper) {
        List<Genre> genres = shikimoriGenreService.getAllGenres(objectMapper);
        return genreService.saveAllGenres(genres);
    }
}
