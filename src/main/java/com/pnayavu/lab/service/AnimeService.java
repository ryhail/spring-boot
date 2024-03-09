package com.pnayavu.lab.service;

import com.pnayavu.lab.entity.Anime;

import java.util.List;

public interface AnimeService {
    List<Anime> findAllAnime();
    List<Anime> findAllAnimeByName(String name);
    List<Anime> findAllAnimeByRussian(String russian);
    Anime saveAnime(Anime anime);
    Anime findAnime(Long id);
    Anime updateAnime(Anime anime);
    void deleteAnime(Long id);
}
