package org.LT3.controller;
import org.LT3.model.Book;
import org.LT3.repository.BookRepository;
import java.util.List;
public class BookController {
    private final BookRepository bookRepository;
    public BookController(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }
    public List<Book> getAllBooks() {
        return bookRepository.findAll();
    }
    public void saveBook(Book book) {
        bookRepository.save(book);
    }
    public void updateBook(Book book) {
        bookRepository.update(book);
    }
    public void deleteBook(Long id) {
        bookRepository.delete(id);
    }
    public List<Book> findByTitle(String title) {
        return bookRepository.findByTitle(title);
    }
    public List<Book> findByAuthor(String author) {
        return bookRepository.findByAuthor(author);
    }
    public List<Book> findByGenre(String genre) {
        return bookRepository.findByGenre(genre);
    }
}
