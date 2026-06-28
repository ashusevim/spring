package com.springarena.service;

import com.springarena.model.Book;
import com.springarena.repository.BookRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class BookServiceImpl implements BookService {

    private final BookRepository bookRepository;

    public BookServiceImpl(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    @Override
    public List<Book> getAllBooks() {
        // TODO: Get all books from repository
        return null;
    }

    @Override
    public Optional<Book> getBookById(Long id) {
        // TODO: Get book by id
        return Optional.empty();
    }

    @Override
    public Book createBook(Book book) {
        // TODO: Create and save book
        return null;
    }

    @Override
    public Optional<Book> updateBook(Long id, Book book) {
        // TODO: Update book if exists
        return Optional.empty();
    }

    @Override
    public boolean deleteBook(Long id) {
        // TODO: Delete book and return true if existed, false otherwise
        return false;
    }
}
