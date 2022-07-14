package com.sachuk.keu.database.repositories;

import com.sachuk.keu.entities.Quota;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface QuotaRepository extends JpaRepository<Quota,Long> {


}