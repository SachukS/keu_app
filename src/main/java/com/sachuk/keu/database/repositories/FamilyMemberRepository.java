package com.sachuk.keu.database.repositories;

import com.sachuk.keu.entities.FamilyMember;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FamilyMemberRepository extends JpaRepository<FamilyMember, Long> {

}
