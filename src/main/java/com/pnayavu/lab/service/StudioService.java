package com.pnayavu.lab.service;


import com.pnayavu.lab.entity.Studio;
import jakarta.transaction.Transactional;


import java.beans.Transient;
import java.util.List;

public interface StudioService {
    List<Studio> getAllStudios();
    Studio getStudioById(Long id);
    Studio createStudio(Studio studio);
    Studio updateStudio(Studio studio);

    void deleteStudioById(Long id);
}
