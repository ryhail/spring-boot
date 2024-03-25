package com.pnayavu.lab.repository;

import com.pnayavu.lab.entity.Anime;
import jakarta.transaction.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;


import java.util.List;

@Repository
public interface AnimeRepository extends JpaRepository<Anime, Long> {
    Anime findAnimeById(Long id);
    Page<Anime> findAllWithPagination(Pageable pageable);
    @Query(value = "SELECT a FROM Anime a WHERE a.name LIKE :animeName OR a.russian LIKE :animeName")
    List<Anime> searchAnimeByName(@Param(value = "animeName") String animeName);


    @Transactional
    void deleteAnimeById(Long id);
}
