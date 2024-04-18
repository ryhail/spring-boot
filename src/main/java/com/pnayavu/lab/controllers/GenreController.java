package com.pnayavu.lab.controllers;

import com.pnayavu.lab.logging.Logged;
import com.pnayavu.lab.model.Genre;
import com.pnayavu.lab.service.GenreService;
import com.pnayavu.lab.service.implementations.ShikimoriGenreService;
import java.util.List;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

@RestController
@RequestMapping("/genre")
public class GenreController {
  private final GenreService genreService;
  private final ShikimoriGenreService shikimoriGenreService;

  public GenreController(GenreService genreService, ShikimoriGenreService shikimoriGenreService) {
    this.genreService = genreService;
    this.shikimoriGenreService = shikimoriGenreService;
  }

  @GetMapping(value = "")
  public List<Genre> getAllGenres() {
    List<Genre> genres = genreService.findAllGenres();
    if (genres.isEmpty()) {
      throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Genres are empty");
    }
    return genres;
  }

  @GetMapping(value = "/shikimori")
  @Logged
  public List<Genre> updateGenres() {
    List<Genre> genres = shikimoriGenreService.getAllGenres();
    if (genres.isEmpty()) {
      throw new ResponseStatusException(HttpStatus.FAILED_DEPENDENCY,
          "Shikimori API could not be accessed");
    }
    return genreService.saveAllGenres(genres);
  }

  @Logged
  @GetMapping(value = "/{genreId}")
  public Genre getGenreById(@PathVariable Long genreId) {
    return genreService.findGenre(genreId);
  }

  @Logged
  @ResponseStatus(HttpStatus.CREATED)
  @PostMapping(value = "")
  public Genre createGenre(@RequestBody Genre genre) {
    if (genre.getId() == null) {
      throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Set genre id");
    }
    Genre newGenre = genreService.saveGenre(genre);
    if (newGenre == null) {
      throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Genre not saved");
    }
    return newGenre;
  }

  @Logged
  @PutMapping(value = "")
  public Genre updateGenre(@RequestBody Genre genre) {
    if (genre.getId() == null) {
      throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Not full information");
    }
    Genre newGenre = genreService.updateGenre(genre);
    if (newGenre == null) {
      throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Genre not updated");
    }
    return newGenre;
  }

  @Logged
  @DeleteMapping(value = "/{genreId}", produces = "application/json")
  public String deleteGenre(@PathVariable Long genreId) {
    try {
      genreService.deleteGenre(genreId);
      return "Deleted genre " + genreId.toString();
    } catch (DataIntegrityViolationException e) {
      throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Has reference");
    }
  }
}
