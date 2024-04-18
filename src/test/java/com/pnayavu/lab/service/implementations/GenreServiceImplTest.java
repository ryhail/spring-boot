package com.pnayavu.lab.service.implementations;

import com.pnayavu.lab.cache.InMemoryMap;
import com.pnayavu.lab.model.Genre;
import com.pnayavu.lab.repository.GenreRepository;
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
class GenreServiceImplTest {
  @Mock
  private GenreRepository genreRepository;
  @Mock
  private InMemoryMap cache;
  @InjectMocks
  private GenreServiceImpl genreService;

  @Test
  void testGetAll() {
    List<Genre> genres = new ArrayList<>();
    Mockito.when(genreRepository.findAll()).thenReturn(genres);
    Assertions.assertEquals(genres, genreService.findAllGenres());
  }
  @Test
  void testSaveGenre() {
    Genre genre = new Genre();
    genre.setId(1L);
    genre.setName("Test name");
    Mockito.when(genreRepository.save(genre)).thenReturn(genre);
    Assertions.assertEquals(genre, genreService.saveGenre(genre));
  }

  @Test
  void testFindGenre_exists() {
    Genre genre = new Genre();
    genre.setId(1L);
    Mockito.when(genreRepository.findById(1L)).thenReturn(Optional.of(genre));
    Assertions.assertEquals(genre, genreService.findGenre(1L));
  }

  @Test
  void testFindGenre_existsInCache() {
    Genre genre = new Genre();
    genre.setId(1L);
    Mockito.when(cache.get("GENRE ID 1")).thenReturn(genre);
    Assertions.assertEquals(genre, genreService.findGenre(1L));
  }

  @Test
  void testFindGenre_doesntExist() {
    Mockito.when(genreRepository.findById(1L)).thenReturn(Optional.empty());
    Assertions.assertThrows(ResponseStatusException.class, () -> genreService.findGenre(1L),
        "Genre with such id not found");
  }
  @Test
  void testUpdateGenre() {
    Genre genre = new Genre();
    genre.setId(1L);
    genreService.updateGenre(genre);
    Mockito.verify(genreRepository, Mockito.times(1)).save(genre);
  }
  @Test
  void testUpdateGenre_inCache() {
    Genre genre = new Genre();
    genre.setId(1L);
    Mockito.when(cache.containsKey("GENRE ID 1")).thenReturn(true);
    genreService.updateGenre(genre);
    Mockito.verify(cache, Mockito.times(1)).remove("GENRE ID 1");
    Mockito.verify(cache, Mockito.times(1)).put("GENRE ID 1", genre);
  }

  @Test
  void testDeleteGenre_exists() {
    Mockito.when(genreRepository.existsById(1L)).thenReturn(true);
    genreService.deleteGenre(1L);
    Mockito.verify(genreRepository, Mockito.times(1)).deleteById(1L);
  }
  @Test
  void testDeleteGenre_existsInCache() {
    Mockito.when(cache.containsKey("GENRE ID 1")).thenReturn(true);
    Mockito.when(genreRepository.existsById(1L)).thenReturn(true);
    genreService.deleteGenre(1L);
    Mockito.verify(cache, Mockito.times(1)).remove("GENRE ID 1");
  }
  @Test
  void testDeleteGenre_doesntExist() {
    Mockito.when(genreRepository.existsById(1L)).thenReturn(false);
    Assertions.assertThrows(ResponseStatusException.class,() -> genreService.deleteGenre(1L), "Genre with such id not found");
  }
}
