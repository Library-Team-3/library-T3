package org.LT3.repository;

import org.LT3.model.Author;
import org.LT3.model.Book;
import org.LT3.model.Genre;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class MySqlBookRepositoryTest {
    private static MySqlBookRepository mySqlBookRepository;

    @BeforeAll
    static void setUp() {
        mySqlBookRepository = new MySqlBookRepository();
    }

    @AfterAll
    static void cleanUp() {
        String[] testBookTitles = {
                "Test Book",
                "Delete Me",
                "Update Me",
                "Updated Now",
                "New Book With Relations"
        };

        for (String title : testBookTitles) {
            try {
                List<Book> books = mySqlBookRepository.findByTitle(title);
                for (Book book : books) {
                    mySqlBookRepository.delete(book.getId());
                }
            } catch (Exception e) {
                System.out.println("Warning: Could not delete test book: " + title);
            }
        }

        System.out.println("Test data cleanup completed");
    }

    @Test
    void testSaveBook() {
        Book newBook = new Book(
                "Test Book",
                "This is a test book",
                "12345-54321",
                List.of(new Author("Author Test")),
                List.of(new Genre("Genre Test")));

        mySqlBookRepository.save(newBook);
        List<Book> books = mySqlBookRepository.findByTitle("Test Book");

        assertNotNull(books.getFirst().getId(), "The saved book should have an ID.");
        assertEquals("Test Book", books.getFirst().getTitle());
        assertEquals("This is a test book", books.getFirst().getDescription());
    }

    @Test
    void testFindAllBooks() {
        List<Book> books = mySqlBookRepository.findAll();

        assertNotNull(books);
        assertFalse(books.isEmpty(), "The book list should not be empty.");
        assertTrue(books.size() >= 20);
    }

    @Test
    void testFindBookById() {
        List<Book> books = mySqlBookRepository.findAll();
        Long expectedId = books.getFirst().getId();

        Book book = mySqlBookRepository.findById(expectedId);

        assertNotNull(book);
        assertEquals(expectedId, book.getId());
    }

    @Test
    void testFindBookByIsbn() {
        List<Book> books = mySqlBookRepository.findAll();
        String expectedIsbn = books.getFirst().getIsbn();

        Book book = mySqlBookRepository.findByIsbn(expectedIsbn);

        assertNotNull(book);
        assertEquals(expectedIsbn, book.getIsbn());
    }

    @Test
    void testDeleteBook() {
        Book book = new Book(
                "Delete Me",
                "To be deleted",
                "DELETE123",
                List.of(new Author("Author Test")),
                List.of(new Genre("Genre Test")));
        mySqlBookRepository.save(book);
        List<Book> books = mySqlBookRepository.findByTitle("Delete Me");
        mySqlBookRepository.delete(books.getFirst().getId());

        Book deleted = mySqlBookRepository.findById(books.getFirst().getId());
        assertNull(deleted);
    }

    @Test
    void testFindByTitle() {
        List<Book> books = mySqlBookRepository.findByTitle("Clean");

        String expectedTitle1 = "Clean Code";
        String expectedTitle2 = "Clean Architecture";
        String expectedTitle3 = "The Clean Coder";

        assertEquals(expectedTitle1, books.getFirst().getTitle());
        assertEquals(expectedTitle2, books.get(1).getTitle());
        assertEquals(expectedTitle3, books.getLast().getTitle());
    }

    @Test
    void testUpdateBook() {
        Book book = new Book(
                "Update Me",
                "To be updated",
                "123-321",
                List.of(new Author("Author Test")),
                List.of(new Genre("Genre Test")));

        String expectedTitle = "Updated Now";
        String expectedDescription = "This book is updated now";

        mySqlBookRepository.save(book);

        Long id = mySqlBookRepository.findByTitle("Update Me").getFirst().getId();

        Book newBook = new Book(
                id,
                expectedTitle,
                expectedDescription,
                book.getIsbn(),
                book.getAuthors(),
                book.getGenres());
        mySqlBookRepository.update(newBook);

        assertEquals(expectedTitle, mySqlBookRepository.findByTitle(expectedTitle).getFirst().getTitle());
    }

    @Test
    void testFindBookByAuthor() {
        List<Book> books = mySqlBookRepository.findByAuthor("Jeff Geerling");
        assertNotNull(books.getFirst());
        assertNotNull(books.getLast());
        assertFalse(books.getFirst().getAuthors().isEmpty());
        assertFalse(books.getLast().getAuthors().isEmpty());
    }

    @Test
    void testFindBookByGenre() {
        List<Book> books = mySqlBookRepository.findByGenre("Programming");
        assertNotNull(books.getFirst());
        assertNotNull(books.getLast());
        assertFalse(books.getFirst().getGenres().isEmpty());
        assertFalse(books.getLast().getGenres().isEmpty());
    }

    @Test
    void testSaveBookWithAuthorsAndGenres() {
        Author author = new Author(1L, "Robert C. Martin");
        Genre genre = new Genre(1L, "Programming");
        String expectedTitle = "New Book With Relations";

        Book book = new Book(
                "New Book With Relations",
                "To be deleted",
                "DELETE123",
                new ArrayList<>(List.of()),
                new ArrayList<>(List.of()));
        book.getAuthors().add(author);
        book.getGenres().add(genre);

        mySqlBookRepository.save(book);
        List<Book> books = mySqlBookRepository.findByTitle("New Book With Relations");

        assertNotNull(books.getFirst().getId());
        assertEquals(expectedTitle, books.getFirst().getTitle());
    }

}
