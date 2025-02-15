package com.fpt.capstone.tourism.repository;

import com.fpt.capstone.tourism.model.Location;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface LocationRepository extends JpaRepository<Location, Long>, JpaSpecificationExecutor<Location> {
    Location findByName(String name);

}
