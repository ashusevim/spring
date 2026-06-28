package com.springarena.repository;

import com.springarena.model.BlogPost;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BlogPostRepository extends JpaRepository<BlogPost, Long> {
    List<BlogPost> findByAuthor(String author);
    List<BlogPost> findByCategory(String category);
    List<BlogPost> findByPublished(boolean published);
}
