package com.fpt.capstone.tourism.repository;


import com.fpt.capstone.tourism.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {
    Optional<User> findUserById(int id);
    User findUserByEmailContainsIgnoreCase(String email);
    User findUserByEmailAndPassword(String username,String password);


    Optional<User> findByUsername(String username);

    Boolean existsByUsername(String username);

}
