package com.springarena.service;

import com.springarena.model.BlogPost;
import com.springarena.repository.BlogPostRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class BlogPostServiceImpl implements BlogPostService {

    private final BlogPostRepository blogPostRepository;

    public BlogPostServiceImpl(BlogPostRepository blogPostRepository) {
        this.blogPostRepository = blogPostRepository;
    }

    @Override
    public List<BlogPost> getAllPosts() {
        // TODO: Get all posts
        return null;
    }

    @Override
    public Optional<BlogPost> getPostById(Long id) {
        // TODO: Get post by id
        return Optional.empty();
    }

    @Override
    public List<BlogPost> getPostsByAuthor(String author) {
        // TODO: Find posts by author (custom repository query)
        return null;
    }

    @Override
    public List<BlogPost> getPostsByCategory(String category) {
        // TODO: Find posts by category (custom repository query)
        return null;
    }

    @Override
    public List<BlogPost> getPublishedPosts() {
        // TODO: Find all published posts (custom repository query)
        return null;
    }

    @Override
    public BlogPost createPost(BlogPost post) {
        // TODO: Create a post
        return null;
    }

    @Override
    public Optional<BlogPost> updatePost(Long id, BlogPost post) {
        // TODO: Update a post
        return Optional.empty();
    }

    @Override
    public boolean deletePost(Long id) {
        // TODO: Delete post and return true if existed, false otherwise
        return false;
    }
}
