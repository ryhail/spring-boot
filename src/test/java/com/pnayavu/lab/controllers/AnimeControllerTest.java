package com.pnayavu.lab.controllers;

import com.pnayavu.lab.error.MyNotFoundException;
import com.pnayavu.lab.model.Anime;
import com.pnayavu.lab.service.implementations.AnimeServiceImpl;
import com.pnayavu.lab.service.implementations.ShikimoriAnimeService;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.server.ResponseStatusException;

@ExtendWith(MockitoExtension.class)
class AnimeControllerTest {
  @Mock
  private AnimeServiceImpl animeService;
  @Mock
  private ShikimoriAnimeService shikimoriAnimeService;
  @InjectMocks
  private AnimeController animeController;

  @Test
  void testGetAnime_searchNull() {
    List<Anime> animeList = new ArrayList<>();
    animeList.add(new Anime());
    Mockito.when(animeService.findAllAnime()).thenReturn(animeList);
    Assertions.assertEquals(animeList, animeController.getAnime((String) null));
    Mockito.verify(animeService, Mockito.times(1)).findAllAnime();
  }

  @Test
  void testGetAnime_listEmpty() {
    Mockito.when(animeService.findAllAnime()).thenReturn(new ArrayList<>());
    Assertions.assertThrows(MyNotFoundException.class,
        () -> animeController.getAnime((String) null));
  }

  @Test
  void testGetAnime_searchNotNull() {
    String search = "test";
    List<Anime> animeList = new ArrayList<>();
    animeList.add(new Anime());
    Mockito.when(animeService.searchAnime(search)).thenReturn(animeList);
    Assertions.assertEquals(animeList, animeController.getAnime(search));
  }

  @Test
  void testGetAnime_fromShikimori() {
    String search = "test";
    Anime anime = new Anime();
    anime.setId(1L);
    anime.setName(search);
    Mockito.when(shikimoriAnimeService.searchAnime(search)).thenReturn(1);
    Mockito.when(shikimoriAnimeService.getAnimeInfo(1)).thenReturn(anime);
    List<Anime> animeList = animeController.getAnime(search);
    Assertions.assertEquals(1, animeList.size());
    Assertions.assertEquals(anime, animeList.get(0));
  }

  @Test
  void getAnimeById() {
    Anime anime = new Anime();
    anime.setId(1L);
    anime.setName("Test");
    Mockito.when(animeService.findAnime(1L)).thenReturn(anime);
    Assertions.assertEquals(anime, animeController.getAnime(1L));
  }

  @Test
  void testGetAnimeById_notFound() {
    Mockito.when(animeService.findAnime(1L)).thenReturn(null);
    Assertions.assertThrows(ResponseStatusException.class, () -> animeController.getAnime(1L));
  }

  @Test
  void testAddAnimeFromShikimori_found() {
    String animeName = "Test";
    Anime anime = new Anime();
    anime.setId(1L);
    anime.setName(animeName);
    Mockito.when(shikimoriAnimeService.searchAnime(animeName)).thenReturn(1);
    Mockito.when(shikimoriAnimeService.getAnimeInfo(1)).thenReturn(anime);
    Mockito.when(animeService.saveAnime(anime)).thenReturn(anime);
    Assertions.assertEquals("Added successfully anime " + anime.getName(),
        animeController.addAnimeFromShikimori(animeName));
  }

  @Test
  void testAddAnimeFromShikimori_notFound() {
    Mockito.when(shikimoriAnimeService.searchAnime("test")).thenReturn(-1);
    Assertions.assertThrows(ResponseStatusException.class,
        () -> animeController.addAnimeFromShikimori("test"));
  }

  @Test
  void testAddAnimeFromShikimori_notSaved() {
    Anime anime = new Anime();
    anime.setName("test");
    Mockito.when(animeService.saveAnime(anime)).thenReturn(null);
    Mockito.when(shikimoriAnimeService.searchAnime("test")).thenReturn(1);
    Mockito.when(shikimoriAnimeService.getAnimeInfo(1)).thenReturn(anime);
    Assertions.assertThrows(ResponseStatusException.class,
        () -> animeController.addAnimeFromShikimori("test"));
  }

  @Test
  void testAddAnime_saved() {
    Anime anime = new Anime();
    anime.setName("test");
    anime.setId(1L);
    Mockito.when(animeService.saveAnime(anime)).thenReturn(anime);
    Assertions.assertEquals(anime, animeController.addAnime(anime));
  }

  @Test
  void testAddAnime_animeIdNull() {
    Anime anime = new Anime();
    anime.setName("test");
    Assertions.assertThrows(ResponseStatusException.class, () -> animeController.addAnime(anime));
  }

  @Test
  void testAddAnime_animeNull() {
    Anime anime = new Anime();
    Assertions.assertThrows(ResponseStatusException.class, () -> animeController.addAnime(anime));
  }

  @Test
  void testAddAnime_animeNotSaved() {
    Anime anime = new Anime();
    anime.setId(1L);
    Mockito.when(animeService.saveAnime(anime)).thenReturn(null);
    Assertions.assertThrows(ResponseStatusException.class, () -> animeController.addAnime(anime));
  }

  @Test
  void testUpdateAnime_updated() {
    Anime anime = new Anime();
    anime.setId(1L);
    Mockito.when(animeService.updateAnime(anime)).thenReturn(anime);
    Assertions.assertEquals(anime, animeController.updateAnime(anime));
  }

  @Test
  void testUpdateAnime_animeIdNull() {
    Anime anime = new Anime();
    anime.setId(1L);
    Assertions.assertThrows(ResponseStatusException.class,
        () -> animeController.updateAnime(anime));
  }

  @Test
  void testUpdateAnime_animeNull() {
    Anime anime = new Anime();
    Assertions.assertThrows(ResponseStatusException.class,
        () -> animeController.updateAnime(anime));
  }

  @Test
  void testUpdateAnime_animeNotSaved() {
    Anime anime = new Anime();
    anime.setId(1L);
    Mockito.when(animeService.updateAnime(anime)).thenReturn(null);
    Assertions.assertThrows(ResponseStatusException.class,
        () -> animeController.updateAnime(anime));
  }

  @Test
  void testDeleteAnime() {
    animeController.deleteAnime(1L);
    Mockito.verify(animeService, Mockito.times(1)).deleteAnime(1L);
  }

  @Test
  void testBulkInsert() {
    Anime parameters = new Anime();
    List<Integer> animeId = new ArrayList<>();
    List<Anime> animeList = new ArrayList<>();
    animeList.add(new Anime());
    Mockito.when(shikimoriAnimeService.getAnimeWithParameters(parameters, 1)).thenReturn(animeId);
    Mockito.when(shikimoriAnimeService.getAnimeFullInfo(animeId)).thenReturn(animeList);
    Mockito.when(animeService.bulkInsert(animeList)).thenReturn(animeList);
    Assertions.assertEquals("Saved new " + animeList.size() + " entities",
        animeController.addAnimesFromShikimoriWithParameters(parameters, 1));
  }
}
