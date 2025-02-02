package com.fpt.capstone.tourism.repository;

import com.fpt.capstone.tourism.model.ServiceCategory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ServiceCategoryRepository extends JpaRepository<ServiceCategory, Long> {
    Optional<ServiceCategory> findByCategoryName(String categoryName);

    Page<ServiceCategory> findByDeletedFalse(Pageable pageable);

    Page<ServiceCategory> findByDeletedTrue(Pageable pageable);
}
