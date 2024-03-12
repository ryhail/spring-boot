package com.pnayavu.lab.service;


import com.pnayavu.lab.entity.Studio;


import java.util.List;

public interface StudioService {
    List<Studio> getAllStudios();
    Studio getStudioById(Long id);
    Studio createStudio(Studio studio);
    Studio updateStudio(Studio studio);
    void deleteStudioById(Long id);
}
