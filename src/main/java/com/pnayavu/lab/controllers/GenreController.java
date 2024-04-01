package com.pnayavu.lab.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.pnayavu.lab.entity.Genre;
import com.pnayavu.lab.logging.Logged;
import com.pnayavu.lab.service.GenreService;
import com.pnayavu.lab.service.implementations.ShikimoriGenreService;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.stream.Stream;

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
        List<Genre> genres = genreService.findAllGenres();
        if(genres.isEmpty())
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Genres are empty");
        return genres;
    }
    @GetMapping(value = "/shikimori")
    @Logged
    public List<Genre> updateGenres(ObjectMapper objectMapper) {
        List<Genre> genres = shikimoriGenreService.getAllGenres(objectMapper);
        if(genres.isEmpty()){
            throw new ResponseStatusException(HttpStatus.FAILED_DEPENDENCY, "Shikimori API could not be accessed");
        }
        return genreService.saveAllGenres(genres);
    }
    @Logged
    @GetMapping(value = "/{genreId}")
    public Genre getGenreById(@PathVariable Long genreId){
        Genre genre = genreService.findGenre(genreId);
        if(genre == null)
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,"Genre with such id not found");
        return genre;
    }
    @Logged
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping(value = "")
    public Genre createGenre(@RequestBody Genre genre) {
        if(Stream.of(genre).allMatch(null))
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"No genre provided");
        Genre newGenre = genreService.saveGenre(genre);
        if(newGenre == null)
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Genre not saved");
        return newGenre;
    }
    @Logged
    @PutMapping(value = "")
    public Genre updateGenre(@RequestBody Genre genre) {
        if(Stream.of(genre).allMatch( null))
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"No genre provided");
        Genre newGenre = genreService.updateGenre(genre);
        if(newGenre == null)
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Genre not updated");
        return newGenre;
    }
    @Logged
    @DeleteMapping(value = "/{genreId}", produces = "application/json")
    public String deleteGenre(@PathVariable Long genreId) {
        Genre genre = genreService.findGenre(genreId);
        if(genre == null)
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Genre not found");
        try {
            genreService.deleteGenre(genreId);
            return "deleted genre " + genreId.toString();
        } catch(DataIntegrityViolationException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Has reference");
        }
    }
}
