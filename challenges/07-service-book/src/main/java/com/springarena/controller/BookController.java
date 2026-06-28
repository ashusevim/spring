package com.springarena.controller;

import com.springarena.model.Book;
import com.springarena.service.BookService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/books")
public class BookController {

    private final BookService bookService;

    public BookController(BookService bookService) {
        this.bookService = bookService;
    }

    @GetMapping
    public List<Book> getAllBooks() {
        // TODO: Get all books via bookService
        return null;
    }

    @GetMapping("/{id}")
    public ResponseEntity<Book> getBookById(@PathVariable Long id) {
        // TODO: Get book by id via bookService
        return null;
    }

    @PostMapping
    public ResponseEntity<Book> createBook(@RequestBody Book book) {
        // TODO: Create book via bookService
        return null;
    }

    @PutMapping("/{id}")
    public ResponseEntity<Book> updateBook(@PathVariable Long id, @RequestBody Book book) {
        // TODO: Update book via bookService
        return null;
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteBook(@PathVariable Long id) {
        // TODO: Delete book via bookService
        return null;
    }
}
