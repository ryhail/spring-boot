package com.pnayavu.lab.service;

import com.pnayavu.lab.model.Anime;
import java.util.List;

public interface AnimeService {
  List<Anime> findAllAnime();

  List<Anime> searchAnime(String name);

  Anime saveAnime(Anime anime);

  Anime findAnime(Long id);

  Anime updateAnime(Anime anime);

  void deleteAnime(Long id);

  List<Anime> bulkInsert(List<Anime> animeList);
}
