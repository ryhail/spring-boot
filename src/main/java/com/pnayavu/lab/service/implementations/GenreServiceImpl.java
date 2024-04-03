package com.pnayavu.lab.service.implementations;

import com.pnayavu.lab.cache.InMemoryMap;
import com.pnayavu.lab.entity.Genre;
import com.pnayavu.lab.logging.Logged;
import com.pnayavu.lab.repository.GenreRepository;
import com.pnayavu.lab.service.GenreService;
import java.util.List;
import org.springframework.stereotype.Service;

@Service
public class GenreServiceImpl implements GenreService {
  private static final String GENRE_ID_KEY = "GENRE ID ";
  private final GenreRepository genreRepository;
  private final InMemoryMap inMemoryMap;

  public GenreServiceImpl(GenreRepository genreRepository, InMemoryMap inMemoryMap) {
    this.genreRepository = genreRepository;
    this.inMemoryMap = inMemoryMap;
  }

  @Override
  public List<Genre> findAllGenres() {
    return genreRepository.findAll();
  }

  @Logged
  @Override
  public Genre saveGenre(Genre genre) {
    return genreRepository.save(genre);
  }

  @Logged
  @Override
  public Genre findGenre(Long id) {
    String key = GENRE_ID_KEY + id;
    Genre cachedResult = (Genre) inMemoryMap.get(key);
    if (cachedResult != null) {
      return cachedResult;
    }
    Genre result = genreRepository.findGenreById(id);
    inMemoryMap.put(key, result);
    return result;
  }

  @Logged
  @Override
  public Genre updateGenre(Genre genre) {
    String key = GENRE_ID_KEY + genre.getId();
    if (inMemoryMap.containsKey(key)) {
      inMemoryMap.remove(key);
      inMemoryMap.put(key, genre);
    }
    return genreRepository.save(genre);
  }

  @Logged
  @Override
  public List<Genre> saveAllGenres(List<Genre> genres) {
    return genreRepository.saveAll(genres);
  }

  @Logged
  @Override
  public void deleteGenre(Long id) {
    String key = GENRE_ID_KEY + id;
    if (inMemoryMap.containsKey(key)) {
      inMemoryMap.remove(key);
    }
    genreRepository.deleteById(id);
  }
}
