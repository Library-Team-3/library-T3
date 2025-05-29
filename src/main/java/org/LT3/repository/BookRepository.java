package org.LT3.repository;

import org.LT3.model.Book;

import java.util.List;

public interface BookRepository {
    List<Book> findAll();
    void save(Book book);
    void update(Book book);
    void delete(Long id);
    List<Book> findByTitle(String title);
    List<Book> findByAuthor(String author);
    List<Book> findByGenre(String genre);

}
