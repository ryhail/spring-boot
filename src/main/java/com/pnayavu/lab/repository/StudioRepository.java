package com.pnayavu.lab.repository;

import com.pnayavu.lab.entity.Studio;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StudioRepository extends JpaRepository<Studio,Long> {
}
