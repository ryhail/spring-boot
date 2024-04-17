package com.pnayavu.lab.service;

import com.pnayavu.lab.model.Genre;
import java.util.List;

public interface GenreService {
  List<Genre> findAllGenres();

  List<Genre> saveAllGenres(List<Genre> genres);

  Genre saveGenre(Genre genre);

  Genre findGenre(Long id);

  Genre updateGenre(Genre genre);

  void deleteGenre(Long id);
}
