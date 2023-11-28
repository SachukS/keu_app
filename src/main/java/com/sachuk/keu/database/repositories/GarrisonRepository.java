package com.sachuk.keu.database.repositories;

import com.sachuk.keu.entities.Garrison;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GarrisonRepository extends JpaRepository<Garrison, Long> {
    Garrison findByRegion(String region);
}
