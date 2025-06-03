import org.LT3.controller.BookController;
import org.LT3.model.Author;
import org.LT3.model.Book;
import org.LT3.model.Genre;
import org.LT3.repository.BookRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

public class BookControllerTest {
    BookRepository bookRepository;
    BookController bookController;
    private Author author1;
    private Author author2;
    private Genre genre1;
    private Genre genre2;
    private Book book1;
    private Book book2;

    @BeforeEach
    void setUp() {
        bookRepository = mock(BookRepository.class);
        bookController = new BookController(bookRepository);

        author1 = new Author(1L, "J.K. Rowling");
        author2 = new Author(2L, "Tolkien");

        genre1 = new Genre(1L, "Fantasy");
        genre2 = new Genre(2L, "Adventure");

        book1 = new Book(1L, "Harry Potter", "Magic and spells", "1234", List.of(author1), List.of(genre1));
        book2 = new Book(2L, "Hobbit", "An unexpected trip", "5678", List.of(author2), List.of(genre1, genre2));
    }

        @Test
    void getAllBooks_shouldReturnBookListWithAuthorsAndGenres() {

        List<Book> mockBooks = Arrays.asList(book1, book2);

        when(bookRepository.findAll()).thenReturn(mockBooks);

        List<Book> result = bookController.getAllBooks();

        assertEquals(2, result.size());
        assertEquals("Harry Potter", result.getFirst().getTitle());
        assertEquals("Tolkien", result.get(1).getAuthors().getFirst().getName());
        assertEquals("Adventure", result.get(1).getGenres().get(1).getName());
    }

    @Test
    void findById_shouldReturnCorrectBook() {
        when(bookRepository.findById(1L)).thenReturn(book1);

        Book result = bookController.findByID(1L);
        assertEquals("Harry Potter", result.getTitle());
    }

    @Test
    void saveBook_shouldCallRepositorySave() {
        bookController.saveBook(book1);

        verify(bookRepository, times(1)).save(book1);
    }

    @Test
    void deleteBook_shouldCallRepositoryDelete() {
        bookController.deleteBook(5L);
        verify(bookRepository, times(1)).delete(5L);
    }

    @Test
    void updateBook_shouldCallRepositoryUpdate() {
        bookController.updateBook(book1);

        verify(bookRepository, times(1)).update(book1);
    }

    @Test
    void findByTitle_shouldReturnCorrectBook() {
        when(bookRepository.findByTitle("Harry Potter")).thenReturn(Collections.singletonList(book1));

        List<Book> result = bookController.findByTitle("Harry Potter");
        assertEquals("Harry Potter", result.getFirst().getTitle());
    }

    @Test
    void findByAuthor_shouldReturnCorrectBook() {
        when(bookRepository.findByAuthor("J.K. Rowling")).thenReturn(Collections.singletonList(book1));

        List<Book> result = bookController.findByAuthor("J.K. Rowling");
        assertEquals(List.of(author1), result.getFirst().getAuthors());
    }

    @Test
    void findByGenre_shouldReturnCorrectBook() {
        when(bookRepository.findByGenre("Fantasy")).thenReturn(Collections.singletonList(book2));

        List<Book> result = bookController.findByGenre("Fantasy");
        assertEquals(List.of(genre1, genre2), result.getFirst().getGenres());
    }
}
