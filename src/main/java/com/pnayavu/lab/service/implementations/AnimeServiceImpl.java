package com.pnayavu.lab.service.implementations;

import com.pnayavu.lab.cache.InMemoryMap;
import com.pnayavu.lab.logging.Logged;
import com.pnayavu.lab.model.Anime;
import com.pnayavu.lab.repository.AnimeRepository;
import com.pnayavu.lab.service.AnimeService;
import jakarta.transaction.Transactional;
import java.util.List;
import java.util.Optional;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

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
    String key = "ANIME NAME " + name;
    List<Anime> cachedResult = (List<Anime>) inMemoryMap.get(key);
    if (cachedResult != null) {
      return cachedResult;
    }
    name = '%' + name + '%';
    Optional<List<Anime>> result = animeRepository.searchAnimeByName(name);
    if(result.isPresent()) {
      inMemoryMap.put(key, result);
      return result.get();
    } else
      return null;
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
    Optional<Anime> result = animeRepository.findById(id);
    if(result.isPresent()) {
      inMemoryMap.put(key, result.get());
      return result.get();
    } else {
      throw new ResponseStatusException(HttpStatus.NOT_FOUND, "No anime with such id");
    }
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
    if(animeRepository.existsById(id)) {
      String key = ANIME_ID_KEY + id;
      animeRepository.deleteById(id);
      if (inMemoryMap.containsKey(key)) {
        inMemoryMap.remove(key);
      }
    } else {
      throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Anime with such id not found");
    }
  }

  @Logged
  @Transactional
  public List<Anime> bulkInsert(List<Anime> animeList) {
    animeList = animeRepository.saveAll(animeList);
    if(animeList.isEmpty())
      throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Anime list was not saved");
    else
      return animeList;
  }
}
