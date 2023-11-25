package com.sachuk.keu.database.repositories;

import com.sachuk.keu.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

//    User getByLogin(String email);
//
//    List<User> getAllByLastName(String surname);


}
