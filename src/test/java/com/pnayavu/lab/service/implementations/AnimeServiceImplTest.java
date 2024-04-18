package com.pnayavu.lab.service.implementations;

import com.pnayavu.lab.cache.InMemoryMap;
import com.pnayavu.lab.model.Anime;
import com.pnayavu.lab.repository.AnimeRepository;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.server.ResponseStatusException;

@ExtendWith(MockitoExtension.class)
class AnimeServiceImplTest {
  @InjectMocks
  private AnimeServiceImpl animeService;
  @Mock
  private AnimeRepository animeRepository;
  @Mock
  private InMemoryMap cache;

  @Test
  void testSave() {
    Anime anime = new Anime();
    anime.setName("Test name");
    anime.setStatus("test");
    anime.setScore(10.);
    anime.setId(1L);
    anime.setAiredOn(LocalDate.MAX);
    Mockito.when(animeRepository.save(anime)).thenReturn(anime);
    Assertions.assertEquals(anime, animeService.saveAnime(anime));
  }
  @Test
  void testFindAnime_exists() {
    Anime anime = new Anime();
    anime.setId(1L);
    anime.setName("anime name test");
    Mockito.when(animeRepository.findById(1L)).thenReturn(Optional.of(anime));
    Assertions.assertEquals(anime, animeService.findAnime(1L));
  }

  @Test
  void testFindAnime_existsInCache() {
    Anime anime = new Anime();
    anime.setId(1L);
    anime.setName("anime name test");
    Mockito.when(cache.get("ANIME ID 1")).thenReturn(anime);
    Assertions.assertEquals(anime, animeService.findAnime(1L));
  }

  @Test
  void testFindAnime_doesntExists() {
    Mockito.when(animeRepository.findById(1L)).thenReturn(Optional.empty());
    Assertions.assertThrows(ResponseStatusException.class, () -> animeService.findAnime(1L),
        "No anime with such id");
  }

  @Test
  void testSearchAnime_exists() {
    Anime anime = new Anime();
    anime.setName("anime name test");
    List<Anime> animeList = new ArrayList<>();
    animeList.add(anime);
    Mockito.when(animeRepository.searchAnimeByName("%anime name test%"))
        .thenReturn(Optional.of(animeList));
    Assertions.assertEquals(animeList, animeService.searchAnime("anime name test"));
  }

  @Test
  void testSearchAnime_existsInCache() {
    Anime anime = new Anime();
    anime.setName("anime name test");
    List<Anime> animeList = new ArrayList<>();
    animeList.add(anime);
    Mockito.when(cache.get("ANIME NAME " + anime.getName())).thenReturn(animeList);
    Assertions.assertEquals(animeList, animeService.searchAnime("anime name test"));
  }

  @Test
  void testSearchAnime_doesntExist() {
    Mockito.when(animeRepository.searchAnimeByName("%test name%")).thenReturn(Optional.empty());
    Assertions.assertTrue(animeService.searchAnime("test name").isEmpty());
  }

  @Test
  void testFindAll() {
    List<Anime> animeList = new ArrayList<>();
    Mockito.when(animeRepository.findAll()).thenReturn(animeList);
    Assertions.assertEquals(animeList, animeService.findAllAnime());
  }

  @Test
  void testDeleteAnimeId_Exists() {
    Mockito.when(animeRepository.existsById(1L)).thenReturn(true);
    animeService.deleteAnime(1L);
    Mockito.verify(animeRepository, Mockito.times(1)).deleteById(1L);
  }

  @Test
  void testDeleteAnimeId_ExistsInCache() {
    Mockito.when(animeRepository.existsById(1L)).thenReturn(true);
    Mockito.when(cache.containsKey("ANIME ID 1")).thenReturn(true);
    animeService.deleteAnime(1L);
    Mockito.verify(cache, Mockito.times(1)).remove("ANIME ID 1");
  }

  @Test
  void testDeleteAnimeId_doesntExist() {
    Mockito.when(animeRepository.existsById(1L)).thenReturn(false);
    Assertions.assertThrows(ResponseStatusException.class, () -> animeService.deleteAnime(1L),
        "Anime with such id not found");
  }

  @Test
  void testBulkInsert_success() {
    List<Anime> animeList = new ArrayList<>();
    animeList.add(new Anime());
    Mockito.when(animeRepository.saveAll(animeList)).thenReturn(animeList);
    Assertions.assertEquals(animeList, animeService.bulkInsert(animeList));
  }

  @Test
  void testBulkInsert_fail() {
    List<Anime> animeList = new ArrayList<>();
    Mockito.when(animeRepository.saveAll(animeList)).thenReturn(animeList);
    Assertions.assertThrows(ResponseStatusException.class, () -> animeService.bulkInsert(animeList),
        "Anime list was not saved");
  }
  @Test
  void testUpdateAnime() {
    Anime anime = new Anime();
    anime.setName("Test name");
    anime.setStatus("test");
    anime.setScore(10.);
    anime.setId(1L);
    Mockito.when(animeRepository.save(anime)).thenReturn(anime);
    Assertions.assertEquals(anime, animeService.updateAnime(anime));
  }
  @Test
  void testUpdateAnime_inCache() {
    Anime anime = new Anime();
    anime.setName("Test name");
    anime.setStatus("test");
    anime.setScore(10.);
    anime.setId(1L);
    String key = "ANIME ID "+anime.getId();
    Mockito.when(cache.containsKey(key)).thenReturn(true);
    animeService.updateAnime(anime);
    Mockito.verify(cache, Mockito.times(1)).remove(key);
    Mockito.verify(cache, Mockito.times(1)).put(key, anime);
  }
}
