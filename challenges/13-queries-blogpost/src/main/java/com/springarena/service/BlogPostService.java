package com.springarena.service;

import com.springarena.model.BlogPost;
import java.util.List;
import java.util.Optional;

public interface BlogPostService {
    List<BlogPost> getAllPosts();
    Optional<BlogPost> getPostById(Long id);
    List<BlogPost> getPostsByAuthor(String author);
    List<BlogPost> getPostsByCategory(String category);
    List<BlogPost> getPublishedPosts();
    BlogPost createPost(BlogPost post);
    Optional<BlogPost> updatePost(Long id, BlogPost post);
    boolean deletePost(Long id);
}
