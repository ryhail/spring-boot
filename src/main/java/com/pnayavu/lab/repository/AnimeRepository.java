package com.pnayavu.lab.repository;

import com.pnayavu.lab.entity.Anime;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AnimeRepository extends JpaRepository<Anime, Long> {
    Anime findAnimeById(Long id);
    List<Anime> searchAllByNameContaining(String name);
    List<Anime> searchAllByRussianContaining(String russian);
    @Transactional
    void deleteAnimeById(Long id);
}
