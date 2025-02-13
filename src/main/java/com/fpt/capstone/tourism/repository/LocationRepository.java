package com.fpt.capstone.tourism.repository;

import com.fpt.capstone.tourism.model.Location;
import com.fpt.capstone.tourism.model.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LocationRepository extends JpaRepository<Location, Long> {
}
