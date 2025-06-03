import org.LT3.controller.BookController;
import org.LT3.model.Author;
import org.LT3.model.Book;
import org.LT3.model.Genre;
import org.LT3.repository.BookRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.Arrays;
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

        genre1 = new Genre(1L, "Fantasía");
        genre2 = new Genre(2L, "Aventura");

        book1 = new Book(1L, "Harry Potter", "Magia y hechizos", "1234", List.of(author1), List.of(genre1));
        book2 = new Book(2L, "El Hobbit", "Un viaje inesperado", "5678", List.of(author2), List.of(genre1, genre2));
    }

        @Test
    void getAllBooks_shouldReturnBookListWithAuthorsAndGenres() {

        List<Book> mockBooks = Arrays.asList(book1, book2);

        when(bookRepository.findAll()).thenReturn(mockBooks);

        List<Book> result = bookController.getAllBooks();

        assertEquals(2, result.size());
        assertEquals("Harry Potter", result.get(0).getTitle());
        assertEquals("Tolkien", result.get(1).getAuthors().get(0).getName());
        assertEquals("Aventura", result.get(1).getGenres().get(1).getName());
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
}
