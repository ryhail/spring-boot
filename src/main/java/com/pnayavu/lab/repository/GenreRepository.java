package com.pnayavu.lab.repository;

import com.pnayavu.lab.entity.Genre;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface GenreRepository extends JpaRepository<Genre,Long> {
    Genre findGenreById(Long id);
}
