package org.LT3.view;

import org.LT3.controller.BookController;
import org.LT3.model.Author;
import org.LT3.model.Book;
import org.LT3.model.Genre;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class BookViewTest {

    @Mock
    private BookController mockBookController;

    private Scanner scanner;
    private BookView bookView;
    private ByteArrayOutputStream outputStream;
    private PrintStream originalOut;
    private List<Book> testBooks;
    private Book testBook1;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        originalOut = System.out;
        outputStream = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outputStream));

        Author author1 = new Author(1L, "J.K. Rowling");
        Author author2 = new Author(2L, "George Orwell");
        Genre genre1 = new Genre(1L, "Fantasy");
        Genre genre2 = new Genre(2L, "Dystopian");

        testBook1 = new Book(1L, "Harry Potter", "A magical adventure", "978-0123456789",
                List.of(author1), List.of(genre1));
        Book testBook2 = new Book(2L, "1984", "Big Brother is watching", "978-0987654321",
                List.of(author2), List.of(genre2));

        testBooks = Arrays.asList(testBook1, testBook2);
    }

    @AfterEach
    void tearDown() {
        System.setOut(originalOut);
        if (scanner != null) {
            scanner.close();
        }
    }

    private void setupScanner(String input) {
        scanner = new Scanner(new ByteArrayInputStream(input.getBytes()));
        bookView = new BookView(mockBookController, scanner);
    }

    @Test
    void constructor_shouldInitializeCorrectly() {
        Scanner testScanner = new Scanner("test");
        BookView view = new BookView(mockBookController, testScanner);

        assertNotNull(view);
        testScanner.close();
    }

    @Test
    void showMenu_shouldExitWhenZeroIsSelected() {
        setupScanner("0\n");

        bookView.showMenu();

        String output = outputStream.toString();
        assertTrue(output.contains("Choose an option:"));
        assertTrue(output.contains("Goodbye! 👋"));
    }

    @Test
    void showMenu_shouldShowAllBooksWhenOneIsSelected() {
        setupScanner("1\n0\n");
        when(mockBookController.getAllBooks()).thenReturn(testBooks);

        bookView.showMenu();

        verify(mockBookController).getAllBooks();
        String output = outputStream.toString();
        assertTrue(output.contains("=== All Books ==="));
    }

    @Test
    void showMenu_shouldShowNoBooksMessageWhenListIsEmpty() {
        setupScanner("1\n0\n");
        when(mockBookController.getAllBooks()).thenReturn(Collections.emptyList());

        bookView.showMenu();

        verify(mockBookController).getAllBooks();
        String output = outputStream.toString();
        assertTrue(output.contains("No books found in the library"));
    }

    @Test
    void showMenu_shouldHandleInvalidInputGracefully() {
        setupScanner("invalid\n0\n");

        bookView.showMenu();

        String output = outputStream.toString();
        assertTrue(output.contains("Error: Please enter a valid number!"));
        assertTrue(output.contains("Goodbye! 👋"));
    }

    @Test
    void showMenu_shouldHandleInvalidOptionNumber() {
        setupScanner("99\n0\n");

        bookView.showMenu();

        String output = outputStream.toString();
        assertTrue(output.contains("Error: Invalid option! Please choose a number from 0-7."));
    }

    @Test
    void showMenu_shouldAddNewBookSuccessfully() {
        String input = """
                2
                Test Title
                Test Description
                123-456-789
                1
                Test Author
                1
                Test Genre
                yes
                0
                """;
        setupScanner(input);

        bookView.showMenu();

        verify(mockBookController).saveBook(any(Book.class));
        String output = outputStream.toString();
        assertTrue(output.contains("=== Add New Book ==="));
        assertTrue(output.contains("Book added successfully! ✅"));
    }

    @Test
    void showMenu_shouldCancelBookCreationWhenUserSaysNo() {
        String input = """
                2
                Test Title
                Test Description
                123-456-789
                1
                Test Author
                1
                Test Genre
                no
                0
                """;
        setupScanner(input);

        bookView.showMenu();

        verify(mockBookController, never()).saveBook(any(Book.class));
        String output = outputStream.toString();
        assertTrue(output.contains("Book creation cancelled"));
    }

    @Test
    void showMenu_shouldHandleSaveBookException() {
        String input = """
                2
                Test Title
                Test Description
                123-456-789
                1
                Test Author
                1
                Test Genre
                yes
                0
                """;
        setupScanner(input);

        doThrow(new RuntimeException("Database error")).when(mockBookController).saveBook(any(Book.class));

        bookView.showMenu();

        String output = outputStream.toString();
        assertTrue(output.contains("Error: Failed to save book. Database error"));
    }

    @Test
    void showMenu_shouldUpdateBookSuccessfully() {
        String input = """
                3
                1
                1
                New Title
                0
                yes
                0
                """;
        setupScanner(input);

        when(mockBookController.getAllBooks()).thenReturn(testBooks);
        when(mockBookController.findByID(1L)).thenReturn(testBook1);

        bookView.showMenu();

        verify(mockBookController).findByID(1L);
        verify(mockBookController).updateBook(any(Book.class));
        String output = outputStream.toString();
        assertTrue(output.contains("=== Update Book ==="));
        assertTrue(output.contains("Book updated successfully! ✅"));
    }

    @Test
    void showMenu_shouldHandleBookNotFoundForUpdate() {
        String input = """
                3
                999
                0
                """;
        setupScanner(input);

        when(mockBookController.getAllBooks()).thenReturn(testBooks);
        when(mockBookController.findByID(999L)).thenReturn(null);

        bookView.showMenu();

        verify(mockBookController).findByID(999L);
        verify(mockBookController, never()).updateBook(any(Book.class));
        String output = outputStream.toString();
        assertTrue(output.contains("Error: Book with ID 999 not found"));
    }

    @Test
    void showMenu_shouldDeleteBookSuccessfully() {
        String input = """
                4
                1
                yes
                0
                """;
        setupScanner(input);

        when(mockBookController.getAllBooks()).thenReturn(testBooks);
        when(mockBookController.findByID(1L)).thenReturn(testBook1);

        bookView.showMenu();

        verify(mockBookController).findByID(1L);
        verify(mockBookController).deleteBook(1L);
        String output = outputStream.toString();
        assertTrue(output.contains("=== Delete Book ==="));
        assertTrue(output.contains("Book deleted successfully! ✅"));
    }

    @Test
    void showMenu_shouldCancelDeletionWhenUserSaysNo() {
        String input = """
                4
                1
                no
                0
                """;
        setupScanner(input);

        when(mockBookController.getAllBooks()).thenReturn(testBooks);
        when(mockBookController.findByID(1L)).thenReturn(testBook1);

        bookView.showMenu();

        verify(mockBookController).findByID(1L);
        verify(mockBookController, never()).deleteBook(1L);
        String output = outputStream.toString();
        assertTrue(output.contains("Deletion cancelled"));
    }

    @Test
    void showMenu_shouldSearchByTitleSuccessfully() {
        String input = """
                5
                Harry
                0
                """;
        setupScanner(input);

        when(mockBookController.findByTitle("Harry")).thenReturn(List.of(testBook1));

        bookView.showMenu();

        verify(mockBookController).findByTitle("Harry");
        String output = outputStream.toString();
        assertTrue(output.contains("=== Search by Title 🔍 ==="));
        assertTrue(output.contains("Found 1 book(s):"));
    }

    @Test
    void showMenu_shouldShowNoResultsForTitleSearch() {
        String input = """
                5
                NonExistent
                0
                """;
        setupScanner(input);

        when(mockBookController.findByTitle("NonExistent")).thenReturn(Collections.emptyList());

        bookView.showMenu();

        verify(mockBookController).findByTitle("NonExistent");
        String output = outputStream.toString();
        assertTrue(output.contains("No books found with title containing: \"NonExistent\""));
    }

    @Test
    void showMenu_shouldSearchByAuthorSuccessfully() {
        String input = """
                6
                Rowling
                0
                """;
        setupScanner(input);

        when(mockBookController.findByAuthor("Rowling")).thenReturn(List.of(testBook1));

        bookView.showMenu();

        verify(mockBookController).findByAuthor("Rowling");
        String output = outputStream.toString();
        assertTrue(output.contains("=== Search by Author 👤 ==="));
        assertTrue(output.contains("Found 1 book(s) by \"Rowling\":"));
    }

    @Test
    void showMenu_shouldSearchByGenreSuccessfully() {
        String input = """
                7
                Fantasy
                0
                """;
        setupScanner(input);

        when(mockBookController.findByGenre("Fantasy")).thenReturn(List.of(testBook1));

        bookView.showMenu();

        verify(mockBookController).findByGenre("Fantasy");
        String output = outputStream.toString();
        assertTrue(output.contains("=== Search by Genre 🏷️ ==="));
        assertTrue(output.contains("Found 1 book(s) in genre \"Fantasy\":"));
    }

    @Test
    void showMenu_shouldHandleSearchExceptions() {
        String input = """
                5
                Test
                0
                """;
        setupScanner(input);

        when(mockBookController.findByTitle("Test")).thenThrow(new RuntimeException("Search error"));

        bookView.showMenu();

        String output = outputStream.toString();
        assertTrue(output.contains("Error: Failed to search books. Search error"));
    }

    @Test
    void showMenu_shouldHandleMultipleAuthorsAndGenres() {
        String input = """
                2
                Multi Author Book
                A book with multiple authors
                123-456-789
                2
                Author One
                Author Two
                2
                Genre One
                Genre Two
                yes
                0
                """;
        setupScanner(input);

        bookView.showMenu();

        verify(mockBookController).saveBook(argThat(book -> book.getAuthors().size() == 2 &&
                book.getGenres().size() == 2 &&
                book.getTitle().equals("Multi Author Book")));
    }

    @Test
    void showMenu_shouldValidatePositiveIntegerInput() {
        String input = """
                2
                Test Title
                Test Description
                123-456-789
                0
                1
                Test Author
                1
                Test Genre
                no
                0
                """;
        setupScanner(input);

        bookView.showMenu();

        String output = outputStream.toString();
        assertTrue(output.contains("Error: Number must be at least 1!"));
    }

    @Test
    void showMenu_shouldValidateNonEmptyStringInput() {
        String input = """
                2
                
                Valid Title
                Valid Description
                123-456-789
                1
                Test Author
                1
                Test Genre
                no
                0
                """;
        setupScanner(input);

        bookView.showMenu();

        String output = outputStream.toString();
        assertTrue(output.contains("Error: Book title cannot be empty!"));
    }
}