package com.pnayavu.lab.repository;

import com.pnayavu.lab.entity.Anime;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AnimeRepository extends JpaRepository<Anime, Long> {
    Anime findAnimeById(Long id);
    @Transactional
    void deleteAnimeById(Long id);
}
