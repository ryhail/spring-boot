package com.pnayavu.lab.service.implementations;

import com.pnayavu.lab.entity.Anime;
import com.pnayavu.lab.repository.AnimeRepository;
import com.pnayavu.lab.service.AnimeService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
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
    public Page<Anime> findAllWithPagination(int size, int num) {
        PageRequest pageRequest = PageRequest.of(num, size);
        return animeRepository.findAllWithPagination(pageRequest);
    }

    @Override
    public List<Anime> searchAnime(String name) {
        name = '%' + name + '%';
        return animeRepository.searchAnimeByName(name);
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
