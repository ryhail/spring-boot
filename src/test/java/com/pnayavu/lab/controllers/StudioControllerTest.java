package com.pnayavu.lab.controllers;

import com.pnayavu.lab.model.Studio;
import com.pnayavu.lab.service.implementations.StudioServiceImpl;
import java.util.NoSuchElementException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.server.ResponseStatusException;

@ExtendWith(MockitoExtension.class)
public class StudioControllerTest {
  @InjectMocks
  private StudioController studioController;
  @Mock
  private StudioServiceImpl studioService;
  @Test
  void testGetStudioById_exists() {
    Studio studio = new Studio();
    studio.setName("test");
    Mockito.when(studioService.getStudioById(1L)).thenReturn(studio);
    Assertions.assertEquals(studio, studioController.getStudioById(1L));
  }
  @Test
  void testGetStudioById_doesntExists() {
    Mockito.when(studioService.getStudioById(1L)).thenThrow(NoSuchElementException.class);
    Assertions.assertThrows(ResponseStatusException.class,()-> studioController.getStudioById(1L));
  }
  @Test
  void testPostStudio_saved() {
    Studio studio = new Studio();
    studio.setId(1L);
    Mockito.when(studioService.createStudio(studio)).thenReturn(studio);
    Assertions.assertEquals(studio, studioController.postStudio(studio));
  }
  @Test
  void testPostStudio_invalidArg() {
    Studio studio = new Studio();
    Assertions.assertThrows(ResponseStatusException.class, ()->studioController.postStudio(studio));
  }
  @Test
  void testPostStudio_notSaved() {
    Studio studio = new Studio();
    studio.setId(1L);
    Mockito.when(studioService.createStudio(studio)).thenReturn(null);
    Assertions.assertThrows(ResponseStatusException.class, ()->studioController.postStudio(studio));
  }
  @Test
  void testUpdateStudio_updated() {
    Studio studio = new Studio();
    studio.setId(1L);
    Mockito.when(studioService.updateStudio(studio)).thenReturn(studio);
    Assertions.assertEquals(studio, studioController.updateStudio(studio));
  }
  @Test
  void testUpdateStudio_invalidArg() {
    Studio studio = new Studio();
    Assertions.assertThrows(ResponseStatusException.class, ()->studioController.updateStudio(studio));
  }
  @Test
  void testUpdateStudio_notSaved() {
    Studio studio = new Studio();
    studio.setId(1L);
    Mockito.when(studioService.updateStudio(studio)).thenReturn(null);
    Assertions.assertThrows(ResponseStatusException.class, ()->studioController.updateStudio(studio));
  }
  @Test
  void testDeleteStudio() {
    Mockito.verify(studioService, Mockito.atMostOnce()).deleteStudioById(1L);
    Assertions.assertEquals("Deleted studio with id 1", studioController.deleteStudio(1L));
  }
}
