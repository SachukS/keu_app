package com.sachuk.keu.database.repositories;

import com.sachuk.keu.entities.FinanceSource;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FinanceSourceRepository extends JpaRepository<FinanceSource, Long> {
}
