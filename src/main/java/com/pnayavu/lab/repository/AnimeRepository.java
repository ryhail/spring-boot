package com.pnayavu.lab.repository;

import com.pnayavu.lab.entity.Anime;
import jakarta.transaction.Transactional;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface AnimeRepository extends JpaRepository<Anime, Long> {
  Anime findAnimeById(Long id);

  @Query(value = "SELECT a FROM Anime a WHERE a.name LIKE :animeName OR a.russian LIKE :animeName")
  List<Anime> searchAnimeByName(@Param(value = "animeName") String animeName);

  @Transactional
  void deleteAnimeById(Long id);
}
