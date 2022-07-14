package com.sachuk.keu.database.repositories;

import com.sachuk.keu.entities.Quota;
import com.sachuk.keu.entities.Work;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface WorkRepository extends JpaRepository<Work,Long> {


}
