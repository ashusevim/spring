package com.springarena.controller;

import com.springarena.model.BlogPost;
import com.springarena.service.BlogPostService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/posts")
public class BlogPostController {

    private final BlogPostService blogPostService;

    public BlogPostController(BlogPostService blogPostService) {
        this.blogPostService = blogPostService;
    }

    @GetMapping
    public List<BlogPost> getAllPosts() {
        // TODO: Get all posts via blogPostService
        return blogPostService.getAllPosts();
    }

    @GetMapping("/{id}")
    public ResponseEntity<BlogPost> getPostById(@PathVariable Long id) {
        // TODO: Get post by id via blogPostService, return 200 or 404
        return blogPostService.getPostById(id)
                .map(post -> ResponseEntity.ok(post))
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping("/author/{author}")
    public List<BlogPost> getPostsByAuthor(@PathVariable String author) {
        // TODO: Get posts by author via blogPostService
        return blogPostService.getPostsByAuthor(author);
    }

    @GetMapping("/category/{category}")
    public List<BlogPost> getPostsByCategory(@PathVariable String category) {
        // TODO: Get posts by category via blogPostService
        return blogPostService.getPostsByCategory(category);
    }

    @GetMapping("/published")
    public List<BlogPost> getPublishedPosts() {
        // TODO: Get published posts via blogPostService
        return blogPostService.getPublishedPosts();
    }

    @PostMapping
    public ResponseEntity<BlogPost> createPost(@RequestBody BlogPost post) {
        // TODO: Create a post via blogPostService, return 201
        BlogPost created = blogPostService.createPost(post);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    @PutMapping("/{id}")
    public ResponseEntity<BlogPost> updatePost(@PathVariable Long id, @RequestBody BlogPost postDetails) {
        // TODO: Update a post via blogPostService, return 200 or 404
        return blogPostService.updatePost(id, postDetails)
                .map(updated -> ResponseEntity.ok(updated))
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePost(@PathVariable Long id) {
        // TODO: Delete post via blogPostService and return 204 or 404
        if (blogPostService.deletePost(id)) {
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
