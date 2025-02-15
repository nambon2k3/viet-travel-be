package com.fpt.capstone.tourism.repository;

import com.fpt.capstone.tourism.dto.common.BlogDTO;
import com.fpt.capstone.tourism.model.Blog;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BlogRepository extends JpaRepository<Blog, Long>, JpaSpecificationExecutor<Blog> {
    @Query("SELECT b FROM Blog b WHERE b.isDeleted = FALSE ORDER BY b.createdAt DESC")
    List<Blog> findTopBlogs(Pageable pageable);
}
