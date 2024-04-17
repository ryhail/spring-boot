package com.pnayavu.lab.service.implementations;

import com.fasterxml.jackson.databind.node.IntNode;
import com.pnayavu.lab.cache.InMemoryMap;
import com.pnayavu.lab.model.Anime;
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
class ShikimoriAnimeServiceTest {
  @Mock
  private InMemoryMap cache;
  @InjectMocks
  private ShikimoriAnimeService shikimoriAnimeService;

  @Test
  void testSearchAnime() {
    Assertions.assertEquals(21, shikimoriAnimeService.searchAnime("One Piece"));
  }
  @Test
  void testSearchAnime_inCache() {
    Mockito.when(cache.get("SHIKIMORI ANIME NAME One Piece")).thenReturn(IntNode.valueOf(20));
    Assertions.assertEquals(20, shikimoriAnimeService.searchAnime("One Piece"));
  }
  @Test
  void testSearchAnime_NotFound() {
    Assertions.assertEquals(-1, shikimoriAnimeService.searchAnime("bebebesbababa"));
  }
  @Test
  void testGetAnimeInfo() {
    Anime anime = shikimoriAnimeService.getAnimeInfo(21);
    Assertions.assertEquals(21L, anime.getId());
    Assertions.assertEquals("One Piece", anime.getName());
  }
  @Test
  void testGetAnimeInfo_notFound() {
    Assertions.assertThrows(ResponseStatusException.class,() -> shikimoriAnimeService.getAnimeInfo(25453));
  }
  @Test
  void testGetAnimeWithParameters() {
    Anime parameters = new Anime();
    parameters.setStatus("ongoing");
    List<Integer> animeIdList;
    animeIdList = shikimoriAnimeService.getAnimeWithParameters(parameters, 1);
    Assertions.assertEquals(20, animeIdList.size());
  }
  @Test
  void testGetAnimeWithParameters_notFound() {
    Anime parameters = new Anime();
    parameters.setScore(10.);
    Assertions.assertThrows(ResponseStatusException.class, ()-> shikimoriAnimeService.getAnimeWithParameters(parameters,1));
  }
  @Test
  void testGetAnimeFullInfo_shikimori() {
    Anime parameters = new Anime();
    parameters.setStatus("ongoing");
    List<Integer> animeIdList = shikimoriAnimeService.getAnimeWithParameters(parameters,1);
    List<Anime> animeList = shikimoriAnimeService.getAnimeFullInfo(animeIdList);
    Assertions.assertEquals(20, animeList.size());
  }
  @Test
  void testGetAnimeFullInfo_notFound() {
    Anime parameters = new Anime();
    parameters.setStatus("ongoing");
    List<Integer> animeIdList = shikimoriAnimeService.getAnimeWithParameters(parameters,1);
    animeIdList.remove(1);
    animeIdList.add(0);
    Assertions.assertThrows(ResponseStatusException.class, () -> shikimoriAnimeService.getAnimeFullInfo(animeIdList));
  }
}
