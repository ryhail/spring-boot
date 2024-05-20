package com.pnayavu.lab.controllers;

import com.pnayavu.lab.error.MyNotFoundException;
import com.pnayavu.lab.logging.Logged;
import com.pnayavu.lab.model.Anime;
import com.pnayavu.lab.service.AnimeService;
import com.pnayavu.lab.service.implementations.ShikimoriAnimeService;
import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

@RestController
@RequestMapping("/anime")
@CrossOrigin
public class AnimeController {
  private final AnimeService animeService;
  private final ShikimoriAnimeService shikimoriAnimeService;

  AnimeController(AnimeService animeService, ShikimoriAnimeService shikimoriAnimeService) {
    this.animeService = animeService;
    this.shikimoriAnimeService = shikimoriAnimeService;
  }

  @Logged
  @GetMapping(value = "", produces = "application/json")
  public List<Anime> getAnime(@RequestParam(required = false) String search) {
    List<Anime> listAnime;
    if (search == null) {
      listAnime = animeService.findAllAnime();
    } else {
      listAnime = animeService.searchAnime(search);
      if (listAnime.isEmpty()) {
        Anime anime = shikimoriAnimeService.getAnimeInfo(shikimoriAnimeService.searchAnime(search));
        listAnime.add(anime);
      }
    }
    if (listAnime.isEmpty()) {
      throw new MyNotFoundException(HttpStatus.NOT_FOUND, "No animes found");
    }
    return listAnime;
  }

  @Logged
  @GetMapping(value = "/{animeId}", produces = "application/json")
  public Anime getAnime(@PathVariable Long animeId) {
    Anime anime = animeService.findAnime(animeId);
    if (anime == null) {
      throw new MyNotFoundException(HttpStatus.NOT_FOUND, "Anime not found");
    }
    return anime;
  }

  @Logged
  @GetMapping(value = "/shikimori", produces = "application/json")
  public String addAnimeFromShikimori(@RequestParam String animeName) {
    Anime anime = shikimoriAnimeService.getAnimeInfo(shikimoriAnimeService.searchAnime(animeName));
    anime = animeService.saveAnime(anime);
    if (anime == null) {
      throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Anime not saved");
    }
    return "Added successfully anime " + anime.getName();

  }

  @Logged
  @PostMapping(value = "")
  @ResponseStatus(HttpStatus.CREATED)
  public Anime addAnime(@RequestBody Anime anime) {
    if (anime.getId() == null) {
      throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
          "Anime not correct or id not specified");
    }
    Anime savedAnime = animeService.saveAnime(anime);
    if (savedAnime == null) {
      throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Anime not saved");
    }
    return savedAnime;
  }

  @Logged
  @PutMapping(value = "")
  public Anime updateAnime(@RequestBody Anime anime) {
    if (anime.getId() == null) {
      throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Id wasn't specified");
    }
    Anime newAnime = animeService.updateAnime(anime);
    if (newAnime == null) {
      throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Cannot update");
    }
    return newAnime;
  }

  @Logged
  @DeleteMapping(value = "/{animeId}", produces = "application/json")
  public String deleteAnime(@PathVariable Long animeId) {
    animeService.deleteAnime(animeId);
    return "successfully deleted";
  }

  @Logged
  @PostMapping(value = "/addAnimes", produces = MediaType.APPLICATION_JSON_VALUE)
  public String addAnimesFromShikimoriWithParameters(@RequestBody Anime bulkParameters,
                                                     @RequestParam(required = false) Integer page) {
    List<Anime> animeList = shikimoriAnimeService.getAnimeFullInfo(
        shikimoriAnimeService.getAnimeWithParameters(bulkParameters, page));
    animeService.bulkInsert(animeList);
    return "Saved new " + animeList.size() + " entities";
  }
}
