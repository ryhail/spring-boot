package com.pnayavu.lab.service.implementations;

import com.pnayavu.lab.entity.Studio;
import com.pnayavu.lab.repository.StudioRepository;
import com.pnayavu.lab.service.StudioService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class StudioServiceImpl implements StudioService {

    StudioRepository studioRepository;
    public StudioServiceImpl(StudioRepository studioRepository) {
        this.studioRepository = studioRepository;
    }
    @Override
    public List<Studio> getAllStudios() {
        return studioRepository.findAll();
    }

    @Override
    public Studio getStudioById(Long id) {
        Optional<Studio> studio = studioRepository.findById(id);
        return studio.orElse(null);
    }

    @Override
    public Studio createStudio(Studio studio) {
        return studioRepository.save(studio);
    }
    @Override
    public Studio updateStudio(Studio studio) {
        return studioRepository.save(studio);
    }

    @Override
    public void deleteStudioById(Long id) {
        studioRepository.deleteById(id);
    }
}
