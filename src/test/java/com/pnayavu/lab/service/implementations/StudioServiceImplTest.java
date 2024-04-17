package com.pnayavu.lab.service.implementations;

import com.pnayavu.lab.cache.InMemoryMap;
import com.pnayavu.lab.model.Studio;
import com.pnayavu.lab.repository.StudioRepository;
import java.util.Optional;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.server.ResponseStatusException;

@ExtendWith(MockitoExtension.class)
class StudioServiceImplTest {
  @Mock
  private StudioRepository studioRepository;
  @Mock
  private InMemoryMap cache;
  @InjectMocks
  private StudioServiceImpl studioService;

  @Test
  void testGetStudioById_exists() {
    Studio studio = new Studio();
    studio.setId(1L);
    Mockito.when(studioRepository.findById(1L)).thenReturn(Optional.of(studio));
    Assertions.assertEquals(studio, studioService.getStudioById(1L));
  }
  @Test
  void testGetStudioById_existsInCache() {
    Studio studio = new Studio();
    studio.setId(1L);
    Mockito.when(cache.get("STUDIO ID 1")).thenReturn(studio);
    Assertions.assertEquals(studio, studioService.getStudioById(1L));
  }
  @Test
  void testGetStudioById_doesntExist() {
    Mockito.when(studioRepository.findById(1L)).thenReturn(Optional.empty());
    Assertions.assertThrows(ResponseStatusException.class, () -> studioService.getStudioById(1L));
  }
  @Test
  void testUpdateStudio() {
    Studio studio = new Studio();
    studio.setId(1L);
    Mockito.when(studioRepository.save(studio)).thenReturn(studio);
    Assertions.assertEquals(studio, studioService.updateStudio(studio));
  }
  @Test
  void testUpdateStudio_inCache() {
    Studio studio = new Studio();
    studio.setId(1L);
    Mockito.when(cache.containsKey("STUDIO ID 1")).thenReturn(true);
    studioService.updateStudio(studio);
    Mockito.verify(cache, Mockito.times(1)).remove("STUDIO ID 1");
  }
  @Test
  void testDeleteStudio_exists() {
    Mockito.when(studioRepository.existsById(1L)).thenReturn(true);
    studioService.deleteStudioById(1L);
    Mockito.verify(studioRepository, Mockito.times(1)).deleteById(1L);
  }
  @Test
  void testDeleteStudio_existsInCache() {
    Mockito.when(studioRepository.existsById(1L)).thenReturn(true);
    Mockito.when(cache.containsKey("STUDIO ID 1")).thenReturn(true);
    studioService.deleteStudioById(1L);
    Mockito.verify(cache, Mockito.times(1)).remove("STUDIO ID 1");
  }
  @Test
  void testDeleteStudio_doesntExist() {
    Mockito.when(studioRepository.existsById(1L)).thenReturn(false);
    Assertions.assertThrows(ResponseStatusException.class, () -> studioService.deleteStudioById(1L));
  }
}
