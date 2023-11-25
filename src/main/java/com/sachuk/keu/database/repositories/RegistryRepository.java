package com.sachuk.keu.database.repositories;

import com.sachuk.keu.entities.Registry;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RegistryRepository extends JpaRepository<Registry, Long> {
}
