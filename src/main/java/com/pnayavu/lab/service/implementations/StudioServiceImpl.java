package com.pnayavu.lab.service.implementations;

import com.pnayavu.lab.cache.InMemoryMap;
import com.pnayavu.lab.entity.Studio;
import com.pnayavu.lab.logging.Logged;
import com.pnayavu.lab.repository.StudioRepository;
import com.pnayavu.lab.service.StudioService;
import java.util.List;
import org.springframework.stereotype.Service;

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
    Studio result = studioRepository.findById(id).orElse(null);
    inMemoryMap.put(key, result);
    return result;
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
      inMemoryMap.put(key, studio);
    }
    return studioRepository.save(studio);
  }

  @Logged
  @Override
  public void deleteStudioById(Long id) {
    String key = STUDIO_ID_KEY + id;
    if (inMemoryMap.containsKey(key)) {
      inMemoryMap.remove(key);
    }
    studioRepository.deleteById(id);
  }
}
