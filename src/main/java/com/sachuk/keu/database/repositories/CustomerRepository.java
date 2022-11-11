package com.sachuk.keu.database.repositories;

import com.sachuk.keu.entities.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, Long>, JpaSpecificationExecutor<Customer> {


    @Query(value = "SELECT * FROM customer WHERE customer.work_id IN (SELECT id FROM work WHERE work.garrison = :query) ", nativeQuery = true)
    List<Customer> findAllByGarrison(@Param("query") String query);

    @Query(value = "SELECT * FROM customer WHERE (surname like %:query%) OR (phone_number like %:query%) ", nativeQuery = true, name = "query")
    List<Customer> freeSearch(@Param("query") String query);

    List<Customer> findFirst20ByOrderByAccountingDate();

    List<Customer> findAllByQuotaType(String quotaType);

    long countByUpdateDateAfter(LocalDateTime afterSearchDate);

    List<Customer> findAllByUpdateDateAfter(LocalDateTime afterSearchDate);


}
