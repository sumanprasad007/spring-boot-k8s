package com.example.repository;

import com.example.model.BlogPost;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BlogPostRepository extends JpaRepository<BlogPost, Long> {
    
    List<BlogPost> findByPublishedTrueOrderByCreatedAtDesc();
    
    List<BlogPost> findByPublishedTrueAndCategoryOrderByCreatedAtDesc(String category);
}