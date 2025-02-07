package com.fpt.capstone.tourism.repository;

import com.fpt.capstone.tourism.model.Tag;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TagRepository extends JpaRepository<Tag, Long> {
}
