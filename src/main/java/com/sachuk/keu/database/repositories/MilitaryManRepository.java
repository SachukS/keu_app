package com.sachuk.keu.database.repositories;

import com.sachuk.keu.entities.MilitaryMan;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface MilitaryManRepository extends JpaRepository<MilitaryMan, Long>, JpaSpecificationExecutor<MilitaryMan> {


    @Query(value = "SELECT * FROM customer WHERE customer.work_id IN (SELECT id FROM work WHERE work.garrison = :query) ", nativeQuery = true)
    List<MilitaryMan> findAllByGarrison(@Param("query") String query);

    @Query(value = "SELECT * FROM customer WHERE (surname like %:query%) OR (phone_number like %:query%) ", nativeQuery = true, name = "query")
    Page<MilitaryMan> freeSearch(@Param("query") String query, Pageable pageable);

    List<MilitaryMan> findFirst20ByOrderByAccountingDate();

    List<MilitaryMan> findAllByQuotaType(String quotaType);

    long countByUpdateDateAfter(LocalDateTime afterSearchDate);

    Page<MilitaryMan> findAllByUpdateDateAfter(LocalDateTime afterSearchDate, Pageable pageable);


}
