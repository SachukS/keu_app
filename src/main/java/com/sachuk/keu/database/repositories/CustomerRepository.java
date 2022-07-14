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
public interface CustomerRepository extends JpaRepository<Customer,Long>, JpaSpecificationExecutor<Customer> {

//    Customer findEnrolleeByCertificateId(Long id);
//    Customer findEnrolleeByPersonalFile(String personalFile);
    @Query(value = "SELECT * FROM customer WHERE (name like %:query%) OR (surname like %:query%) ", nativeQuery = true, name = "query")
    List<Customer> freeSearch(@Param("query") String query);
    List<Customer> findFirst20ByOrderByAccountingDate();
//    long countBySocialStatusAndCreateDateTimeAfter(SocialStatus socialStatus, LocalDateTime afterSearchDate);
//
//    long countByCreateDateTimeAfter(LocalDateTime afterSearchDate);
//    long countByCreateDateTimeAfterAndCreateDateTimeBefore(LocalDateTime start, LocalDateTime end);
//    List<Customer> findEnrolleesByCreateDateTimeAfterAndCreateDateTimeBefore(LocalDateTime start, LocalDateTime end);
//    long countByCreateDateTimeAfterAndCreateDateTimeBeforeAndSocialStatus(LocalDateTime start, LocalDateTime end, SocialStatus socialStatus);
//
//    long countByGender(Gender gender);
//    long countByChecked(Checked checked);
//
//    @Query(value = "SELECT * FROM enrollee WHERE enrollee.id IN (SELECT DISTINCT (enrollee_id) FROM enrollee_specialty WHERE enrollee_specialty.specialty_id = :spec)", nativeQuery = true)
//    List<Customer> findEnrolleesBySpecialties( @Param("spec") Specialty specialty);
//
//    @Deprecated
//    @Query(value = "select :query", nativeQuery = true)
//    List<Customer> findAllByCustomQuery(@Param("query") String query);

}
