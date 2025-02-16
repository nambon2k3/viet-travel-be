package com.fpt.capstone.tourism.repository;

import com.fpt.capstone.tourism.model.Activity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ActivityRepository extends JpaRepository<Activity, Long> {
    @Query(value = "SELECT * FROM activity WHERE is_deleted = FALSE ORDER BY RANDOM() LIMIT :numberActivity", nativeQuery = true)
    List<Activity> findRandomActivities( int numberActivity);
}

