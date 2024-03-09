package com.pnayavu.lab.service.implementations;

import com.pnayavu.lab.entity.Anime;
import com.pnayavu.lab.repository.AnimeRepository;
import com.pnayavu.lab.service.AnimeService;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class AnimeServiceImpl implements AnimeService {

    private final AnimeRepository animeRepository;
    public AnimeServiceImpl(AnimeRepository animeRepository) {
        this.animeRepository = animeRepository;
    }
    @Override
    public List<Anime> findAllAnime() {
        return animeRepository.findAll();
    }
    @Override
    public List<Anime> findAllAnimeByName(String name) {
        return animeRepository.searchAllByNameContaining(name);
    }

    @Override
    public List<Anime> findAllAnimeByRussian(String russian) {
        return animeRepository.searchAllByRussianContaining(russian);
    }

    @Override
    public Anime saveAnime(Anime anime) {
        return animeRepository.save(anime);
    }

    @Override
    public Anime findAnime(Long id) {
        return animeRepository.findAnimeById(id);
    }

    @Override
    public Anime updateAnime(Anime anime) {
        return animeRepository.save(anime);
    }

    @Override
    public void deleteAnime(Long id) {
        animeRepository.deleteAnimeById(id);
    }
}
