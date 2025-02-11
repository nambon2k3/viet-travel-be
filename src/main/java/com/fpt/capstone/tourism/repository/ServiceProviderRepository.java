package com.fpt.capstone.tourism.repository;

import com.fpt.capstone.tourism.model.ServiceProvider;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ServiceProviderRepository extends JpaRepository<ServiceProvider, Long> {
    boolean existsByName(String serviceProviderName);

    Optional<ServiceProvider> findByName(String serviceProviderName);
}
