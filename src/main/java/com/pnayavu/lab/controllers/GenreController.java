package com.pnayavu.lab.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.pnayavu.lab.entity.Genre;
import com.pnayavu.lab.service.GenreService;
import com.pnayavu.lab.service.implementations.ShikimoriGenreService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

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
    @GetMapping(value = "/shikimori")
    public List<Genre> updateGenres(ObjectMapper objectMapper) {
        List<Genre> genres = shikimoriGenreService.getAllGenres(objectMapper);
        return genreService.saveAllGenres(genres);
    }
    @GetMapping(value = "/{genreId}")
    public Genre getGenreById(@PathVariable Long genreId){
        Genre genre = genreService.findGenre(genreId);
        if(genre == null)
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,"genre with such id not found");
        return genre;
    }
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping(value = "")
    public Genre createGenre(@RequestBody Genre genre) {
        if(genre == null)
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"No genre provided");
        Genre newGenre = genreService.saveGenre(genre);
        if(newGenre == null)
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Genre not saved");
        return newGenre;
    }

    @PatchMapping(value = "")
    public Genre updateGenre(@RequestBody Genre genre) {
        if(genre == null)
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"No genre provided");
        Genre newGenre = genreService.updateGenre(genre);
        if(newGenre == null)
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Genre not updated");
        return newGenre;
    }

    @DeleteMapping(value = "/{genreId}")
    public String deleteGenre(@PathVariable Long genreId) {
        Genre genre = genreService.findGenre(genreId);
        if(genre == null)
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Genre not found");
        genreService.deleteGenre(genreId);
        return "deleted genre " + genreId.toString();
    }
}
