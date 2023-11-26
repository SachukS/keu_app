package com.sachuk.keu.database.repositories;

import com.sachuk.keu.entities.Registry;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface RegistryRepository extends JpaRepository<Registry, Long> {
    @Query(value = "SELECT * FROM registry WHERE registry.provided_flat_id IS NOT NULL AND military_man.work_id IN (SELECT id FROM works WHERE works.garrison = :garrison) ", nativeQuery = true)
    List<Registry> findByReceivedFlat(@Param("garrison") String garrison);

    @Query(value = "SELECT * FROM registry WHERE registry.received_money IS NOT NULL AND military_man.work_id IN (SELECT id FROM works WHERE works.garrison = :garrison) ", nativeQuery = true)
    List<Registry> findByReceivedMoney(@Param("garrison") String garrison);
}
