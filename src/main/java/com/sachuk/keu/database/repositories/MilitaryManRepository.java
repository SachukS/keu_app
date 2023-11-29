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


    @Query(value = "SELECT * FROM military_man WHERE military_man.id NOT IN (SELECT deleted_militery_men.military_man_id FROM deleted_militery_men ) " +
            "AND military_man.registrated = 1 AND military_man.provided != 'POST' AND military_man.provided != 'COMP' " +
            "AND military_man.work_id IN (SELECT id FROM works WHERE works.garrison_id IN (SELECT id FROM garrisons WHERE garrisons.name = :garrison)) ", nativeQuery = true)
    List<MilitaryMan> findAllByGarrison(@Param("garrison") String garrison);
    @Query(value = "SELECT * FROM military_man WHERE military_man.id NOT IN (SELECT deleted_militery_men.military_man_id FROM deleted_militery_men ) " +
            "AND military_man.registrated = 1 AND military_man.provided != 'POST' AND military_man.provided != 'COMP' " +
            "AND military_man.work_id IN (SELECT id FROM works WHERE works.garrison_id IN (SELECT id FROM garrisons WHERE garrisons.name = :garrison)) " +
            "AND military_man.quota_id IN (SELECT id FROM quotas WHERE quotas.type = :type) ", nativeQuery = true)
    List<MilitaryMan> findQueueTypeByGarrison(@Param("garrison") String garrison, @Param("type") String type);
    @Query(value = "SELECT * FROM military_man WHERE (surname like %:query%) OR (ipn like %:query%) ", nativeQuery = true, name = "query")
    Page<MilitaryMan> freeSearch(@Param("query") String query, Pageable pageable);

    List<MilitaryMan> findFirst20ByOrderByAccountingDate();

    List<MilitaryMan> findAllByQuotaType(String quotaType);

    long countByCreateDateAfter(LocalDateTime afterSearchDate);

    Page<MilitaryMan> findAllByCreateDateAfter(LocalDateTime afterSearchDate, Pageable pageable);

    List<MilitaryMan> findAllByIpn(String ipn);
}
