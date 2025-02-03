package com.fpt.capstone.tourism.repository;

import com.fpt.capstone.tourism.model.ServiceContact;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ServiceContactRepository extends JpaRepository<ServiceContact, Long> {
    boolean existsByPhoneNumber(String phoneNumber);
}
