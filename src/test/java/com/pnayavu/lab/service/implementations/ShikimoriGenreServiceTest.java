package com.pnayavu.lab.service.implementations;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.pnayavu.lab.model.Genre;
import java.util.List;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.reactive.function.client.WebClient;

@ExtendWith(MockitoExtension.class)
public class ShikimoriGenreServiceTest {
  @Mock
  private WebClient webClient;
  @Mock
  private ObjectMapper objectMapper;
  @InjectMocks
  private ShikimoriGenreService genreService;
  @Test
  void testGetAllGenres() {
    List<Genre> genreList = genreService.getAllGenres();
    Assertions.assertFalse(genreList.isEmpty());
  }
}
