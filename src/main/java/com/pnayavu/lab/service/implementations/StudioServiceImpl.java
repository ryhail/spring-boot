package com.pnayavu.lab.service.implementations;

import com.pnayavu.lab.cache.InMemoryMap;
import com.pnayavu.lab.logging.Logged;
import com.pnayavu.lab.model.Studio;
import com.pnayavu.lab.repository.StudioRepository;
import com.pnayavu.lab.service.StudioService;
import java.util.List;
import java.util.Optional;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
public class StudioServiceImpl implements StudioService {

  private static final String STUDIO_ID_KEY = "STUDIO ID ";
  private final StudioRepository studioRepository;
  private final InMemoryMap inMemoryMap;

  public StudioServiceImpl(StudioRepository studioRepository, InMemoryMap inMemoryMap) {
    this.studioRepository = studioRepository;
    this.inMemoryMap = inMemoryMap;
  }

  @Override
  public List<Studio> getAllStudios() {
    return studioRepository.findAll();
  }

  @Logged
  @Override
  public Studio getStudioById(Long id) {
    String key = STUDIO_ID_KEY + id;
    Studio cachedResult = (Studio) inMemoryMap.get(key);
    if (cachedResult != null) {
      return cachedResult;
    }
    Optional<Studio> result = studioRepository.findById(id);
    if (result.isPresent()) {
      inMemoryMap.put(key, result);
      return result.get();
    } else {
      throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Studio with such id not found");
    }
  }

  @Logged
  @Override
  public Studio createStudio(Studio studio) {
    return studioRepository.save(studio);
  }

  @Logged
  @Override
  public Studio updateStudio(Studio studio) {
    String key = STUDIO_ID_KEY + studio.getId();
    if (inMemoryMap.containsKey(key)) {
      inMemoryMap.remove(key);
      inMemoryMap.put(key, Optional.of(studio));
    }
    return studioRepository.save(studio);
  }

  @Logged
  @Override
  public void deleteStudioById(Long id) {
    String key = STUDIO_ID_KEY + id;
    if (studioRepository.existsById(id)) {
      if (inMemoryMap.containsKey(key)) {
        inMemoryMap.remove(key);
      }
      studioRepository.deleteById(id);
    } else {
      throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Studio with such id not found");
    }
  }
}
