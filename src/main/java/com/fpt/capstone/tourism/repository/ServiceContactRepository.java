package com.fpt.capstone.tourism.repository;

import com.fpt.capstone.tourism.model.ServiceContact;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ServiceContactRepository extends JpaRepository<ServiceContact, Long> {
    boolean existsByPhoneNumber(String phoneNumber);
    boolean existsByEmail(String email);
    Optional<ServiceContact> findByPhoneNumber(String phoneNumber);
    Optional<ServiceContact> findByEmail(String email);

    Page<ServiceContact> findAll(Specification<ServiceContact> spec, Pageable pageable);
}
