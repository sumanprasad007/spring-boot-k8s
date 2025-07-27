package com.example.service;

import com.example.model.BlogPost;
import com.example.repository.BlogPostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class BlogService {

    private final BlogPostRepository blogPostRepository;

    @Autowired
    public BlogService(BlogPostRepository blogPostRepository) {
        this.blogPostRepository = blogPostRepository;
    }

    public List<BlogPost> getAllPublishedPosts() {
        return blogPostRepository.findByPublishedTrueOrderByCreatedAtDesc();
    }

    public List<BlogPost> getPublishedPostsByCategory(String category) {
        return blogPostRepository.findByPublishedTrueAndCategoryOrderByCreatedAtDesc(category);
    }

    public Optional<BlogPost> getPostById(Long id) {
        return blogPostRepository.findById(id);
    }

    @Transactional
    public BlogPost createPost(BlogPost blogPost) {
        return blogPostRepository.save(blogPost);
    }

    @Transactional
    public Optional<BlogPost> updatePost(Long id, BlogPost blogPostDetails) {
        return blogPostRepository.findById(id)
                .map(existingPost -> {
                    existingPost.setTitle(blogPostDetails.getTitle());
                    existingPost.setSummary(blogPostDetails.getSummary());
                    existingPost.setContent(blogPostDetails.getContent());
                    existingPost.setCategory(blogPostDetails.getCategory());
                    existingPost.setImageUrl(blogPostDetails.getImageUrl());
                    existingPost.setPublished(blogPostDetails.isPublished());
                    return blogPostRepository.save(existingPost);
                });
    }

    @Transactional
    public boolean deletePost(Long id) {
        return blogPostRepository.findById(id)
                .map(post -> {
                    blogPostRepository.delete(post);
                    return true;
                })
                .orElse(false);
    }

    public List<BlogPost> getLatestPosts(int limit) {
        return blogPostRepository.findByPublishedTrueOrderByCreatedAtDesc()
                .stream()
                .limit(limit)
                .toList();
    }
}