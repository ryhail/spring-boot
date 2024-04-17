package com.pnayavu.lab.repository;

import com.pnayavu.lab.model.Anime;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface AnimeRepository extends JpaRepository<Anime, Long> {
  @Query(value = "SELECT a FROM Anime a WHERE a.name LIKE :animeName OR a.russian LIKE :animeName")
  Optional<List<Anime>> searchAnimeByName(@Param(value = "animeName") String animeName);
}
