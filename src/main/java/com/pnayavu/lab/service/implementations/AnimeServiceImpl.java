package com.pnayavu.lab.service.implementations;

import com.pnayavu.lab.cache.InMemoryMap;
import com.pnayavu.lab.entity.Anime;
import com.pnayavu.lab.logging.Logged;
import com.pnayavu.lab.repository.AnimeRepository;
import com.pnayavu.lab.service.AnimeService;
import java.util.List;
import org.springframework.stereotype.Service;

@Service
public class AnimeServiceImpl implements AnimeService {
  private static final String ANIME_ID_KEY = "ANIME ID ";
  private final AnimeRepository animeRepository;
  private final InMemoryMap inMemoryMap;

  public AnimeServiceImpl(AnimeRepository animeRepository, InMemoryMap inMemoryMap) {
    this.animeRepository = animeRepository;
    this.inMemoryMap = inMemoryMap;
  }

  @Override
  public List<Anime> findAllAnime() {
    return animeRepository.findAll();
  }

  @Logged
  @Override
  public List<Anime> searchAnime(String name) {
    String key = "SEARCH ANIME " + name;
    List<Anime> cachedResult = (List<Anime>) inMemoryMap.get(key);
    if (cachedResult != null) {
      return cachedResult;
    }
    name = '%' + name + '%';
    List<Anime> result = animeRepository.searchAnimeByName(name);
    inMemoryMap.put(key, result);
    return result;
  }

  @Logged
  @Override
  public Anime saveAnime(Anime anime) {
    return animeRepository.save(anime);
  }

  @Logged
  @Override
  public Anime findAnime(Long id) {
    String key = ANIME_ID_KEY + id;
    Anime cachedResult = (Anime) inMemoryMap.get(key);
    if (cachedResult != null) {
      return cachedResult;
    }
    Anime result = animeRepository.findAnimeById(id);
    inMemoryMap.put(key, result);
    return result;
  }

  @Logged
  @Override
  public Anime updateAnime(Anime anime) {
    String key = ANIME_ID_KEY + anime.getId();
    if (inMemoryMap.containsKey(key)) {
      inMemoryMap.remove(key);
      inMemoryMap.put(key, anime);
    }
    return animeRepository.save(anime);
  }

  @Logged
  @Override
  public void deleteAnime(Long id) {
    String key = ANIME_ID_KEY + id;
    animeRepository.deleteAnimeById(id);
    if (inMemoryMap.containsKey(key)) {
      inMemoryMap.remove(key);
    }
  }
}
