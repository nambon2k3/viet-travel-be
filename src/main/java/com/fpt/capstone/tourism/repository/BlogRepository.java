package com.fpt.capstone.tourism.repository;

import com.fpt.capstone.tourism.model.Blog;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BlogRepository extends JpaRepository<Blog, Long> {
}
