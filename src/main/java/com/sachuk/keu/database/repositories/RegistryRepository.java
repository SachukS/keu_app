package com.sachuk.keu.database.repositories;

import com.sachuk.keu.entities.Registry;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface RegistryRepository extends JpaRepository<Registry, Long> {
    @Query(value = "SELECT * FROM registry WHERE registry.provided_flat_id IS NOT NULL ", nativeQuery = true)
    List<Registry> findByReceivedFlat(@Param("garrison") String garrison);

    @Query(value = "SELECT * FROM registry WHERE registry.received_money != 0.0 ", nativeQuery = true)
    List<Registry> findByReceivedMoney(@Param("garrison") String garrison);
}
