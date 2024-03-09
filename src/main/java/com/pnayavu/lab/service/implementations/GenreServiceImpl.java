package com.pnayavu.lab.service.implementations;

import com.pnayavu.lab.entity.Genre;
import com.pnayavu.lab.repository.GenreRepository;
import com.pnayavu.lab.service.GenreService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GenreServiceImpl implements GenreService {
    private final GenreRepository genreRepository;
    public GenreServiceImpl (GenreRepository genreRepository) {
        this.genreRepository = genreRepository;
    }
    @Override
    public List<Genre> findAllGenres() {
        return genreRepository.findAll();
    }

    @Override
    public Genre saveGenre(Genre genre) {
        return genreRepository.save(genre);
    }

    @Override
    public Genre findGenre(Long id) {
        return genreRepository.findGenreById(id);
    }

    @Override
    public Genre updateGenre(Genre genre) {
        return genreRepository.save(genre);
    }
    @Override
    public List<Genre> saveAllGenres(List<Genre> genres) {
        return genreRepository.saveAll(genres);
    }
    @Override
    public void deleteGenre(Long id) {
        genreRepository.deleteById(id);
    }
}
