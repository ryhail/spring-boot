package com.pnayavu.lab.service.implementations;

import com.pnayavu.lab.cache.InMemoryMap;
import com.pnayavu.lab.entity.Studio;
import com.pnayavu.lab.repository.StudioRepository;
import com.pnayavu.lab.service.StudioService;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

@Service
public class StudioServiceImpl implements StudioService {

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

    @Override
    public Studio getStudioById(Long id) {
        String key = "STUDIO ID " + id;
        Studio cachedResult = (Studio) inMemoryMap.get(key);
        if(cachedResult != null)
            return cachedResult;
        Studio result = studioRepository.findById(id).orElse(null);
        inMemoryMap.put(key, result);
        return result;
    }

    @Override
    public Studio createStudio(Studio studio) {
        return studioRepository.save(studio);
    }
    @Override
    public Studio updateStudio(Studio studio) {
        String key = "STUDIO ID " + studio.getId();
        if(inMemoryMap.containsKey(key)) {
            inMemoryMap.remove(key);
            inMemoryMap.put(key, studio);
        }
        return studioRepository.save(studio);
    }

    @Override
    public void deleteStudioById(Long id) {
        String key = "STUDIO ID " + id;
        if(inMemoryMap.containsKey(key)) {
            inMemoryMap.remove(key);
        }
        studioRepository.deleteById(id);
    }
}
