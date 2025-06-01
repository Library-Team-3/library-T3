package org.LT3.controller;

import org.LT3.model.Book;
import org.LT3.repository.BookRepository;

import java.util.List;

public class BookController {

    private final BookRepository bookRepository;

    public BookController(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    public List<Book> getAllBooks(){
        return bookRepository.findAll();
    }
}
