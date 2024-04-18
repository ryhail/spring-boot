package com.pnayavu.lab.controllers;

import com.pnayavu.lab.model.Genre;
import com.pnayavu.lab.service.implementations.GenreServiceImpl;
import com.pnayavu.lab.service.implementations.ShikimoriGenreService;
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
class GenreControllerTest {
  @Mock
  private GenreServiceImpl genreService;
  @Mock
  private ShikimoriGenreService shikimoriGenreService;
  @InjectMocks
  private GenreController genreController;
  @Test
  void testGetAllGenres() {
    List<Genre> genreList = new ArrayList<>();
    Genre genre = new Genre();
    genreList.add(genre);
    Mockito.when(genreService.findAllGenres()).thenReturn(genreList);
    Assertions.assertEquals(genreList, genreController.getAllGenres());
  }
  @Test
  void testGetAllGenres_empty() {
    Mockito.when(genreService.findAllGenres()).thenReturn(new ArrayList<>());
    Assertions.assertThrows(ResponseStatusException.class,()-> genreController.getAllGenres());
  }
  @Test
  void testUpdateAllGenres() {
    List<Genre> genreList = new ArrayList<>();
    Genre genre = new Genre();
    genreList.add(genre);
    Mockito.when(shikimoriGenreService.getAllGenres()).thenReturn(genreList);
    Mockito.when(genreService.saveAllGenres(genreList)).thenReturn(genreList);
    Assertions.assertEquals(genreList, genreController.updateGenres());
  }
  @Test
  void testUpdateAllGenres_empty() {
    Mockito.when(shikimoriGenreService.getAllGenres()).thenReturn(new ArrayList<>());
    Assertions.assertThrows(ResponseStatusException.class, ()->genreController.updateGenres());
  }
  @Test
  void testGetGenreById() {
    Genre genre = new Genre();
    genre.setId(1L);
    genre.setName("Test");
    Mockito.when(genreService.findGenre(1L)).thenReturn(genre);
    Assertions.assertEquals(genre, genreController.getGenreById(1L));
  }
  @Test
  void testAddGenre_saved() {
    Genre genre = new Genre();
    genre.setName("test");
    genre.setId(1L);
    Mockito.when(genreService.saveGenre(genre)).thenReturn(genre);
    Assertions.assertEquals(genre, genreController.createGenre(genre));
  }
  @Test
  void testAddGenre_genreIdNull() {
    Genre genre = new Genre();
    genre.setName("test");
    Assertions.assertThrows(ResponseStatusException.class, () -> genreController.createGenre(genre));
  }
  @Test
  void testAddGenre_genreNull() {
    Genre genre = new Genre();
    Assertions.assertThrows(ResponseStatusException.class, () -> genreController.createGenre(genre));
  }
  @Test
  void testAddGenre_genreNotSaved() {
    Genre genre = new Genre();
    genre.setId(1L);
    Mockito.when(genreService.saveGenre(genre)).thenReturn(null);
    Assertions.assertThrows(ResponseStatusException.class, () -> genreController.createGenre(genre));
  }
  @Test
  void testUpdateGenre_saved() {
    Genre genre = new Genre();
    genre.setName("test");
    genre.setId(1L);
    Mockito.when(genreService.updateGenre(genre)).thenReturn(genre);
    Assertions.assertEquals(genre, genreController.updateGenre(genre));
  }
  @Test
  void testUpdateGenre_genreIdNull() {
    Genre genre = new Genre();
    genre.setName("test");
    Assertions.assertThrows(ResponseStatusException.class, () -> genreController.updateGenre(genre));
  }
  @Test
  void testUpdateGenre_genreNull() {
    Genre genre = new Genre();
    Assertions.assertThrows(ResponseStatusException.class, () -> genreController.updateGenre(genre));
  }
  @Test
  void testUpdateGenre_genreNotSaved() {
    Genre genre = new Genre();
    genre.setId(1L);
    Mockito.when(genreService.updateGenre(genre)).thenReturn(null);
    Assertions.assertThrows(ResponseStatusException.class, () -> genreController.updateGenre(genre));
  }
  @Test
  void testDeleteGenre() {
    Mockito.verify(genreService, Mockito.atMostOnce()).deleteGenre(1L);
    Assertions.assertEquals("Deleted genre 1", genreController.deleteGenre(1L));
  }
}
