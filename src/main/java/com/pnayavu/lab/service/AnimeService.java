package com.pnayavu.lab.service;

import com.pnayavu.lab.entity.Anime;
import org.springframework.data.domain.Page;

import java.util.List;

public interface AnimeService {
    List<Anime> findAllAnime();
    Page<Anime> findAllWithPagination(int size, int num);
    List<Anime> searchAnime(String name);
    Anime saveAnime(Anime anime);
    Anime findAnime(Long id);
    Anime updateAnime(Anime anime);
    void deleteAnime(Long id);
}
