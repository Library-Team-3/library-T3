package org.LT3.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;


public class BookTest {

    private Book book1;
    private Book book2;
    private Book book3;
    private Book book4;

    private Author author1;
    private Author author2;
    private Author author3;
    private Author author4;

    private Genre genre1;
    private Genre genre2;
    private Genre genre3;
    private Genre genre4;

    @BeforeEach
    void setUp() {
        author1 = new Author(1L, "J.K. Rowling");
        author2 = new Author(2L, "J.R.R. Tolkien");
        author3 = new Author(3L, "George Orwell");
        author4 = new Author(4L, "Mary Shelley");

        genre1 = new Genre(1L, "Fantasy");
        genre2 = new Genre(2L, "Adventure");
        genre3 = new Genre(3L, "Dystopian");
        genre4 = new Genre(4L, "Horror");

        book1 = new Book(
                1L,
                "Harry Potter",
                "Magic and spells",
                "1234-4321",
                new ArrayList<>(List.of(author1)),
                new ArrayList<>(List.of(genre1))
        );

        book2 = new Book(
                2L,
                "The Hobbit",
                "An unexpected journey",
                "5678-8765",
                new ArrayList<>(List.of(author2)),
                new ArrayList<>(List.of(genre1, genre2))
        );

        book3 = new Book(
                3L,
                "1984",
                "Big Brother is watching you",
                "9101-1019",
                new ArrayList<>(List.of(author3)),
                new ArrayList<>(List.of(genre3))
        );

        book4 = new Book(
                4L,
                "Frankenstein",
                "A scientist creates life",
                "1121-1211",
                new ArrayList<>(List.of(author4)),
                new ArrayList<>(List.of(genre4, genre1))
        );
    }


    @Test
    void testGetters(){
        Long expectedId = 1L;
        String expectedTitle = "The Hobbit";
        String expectedDescription = "Big Brother is watching you";
        String expectedISBN = "1121-1211";
        ArrayList expectedAuthors = new ArrayList<>(List.of(author1));
        ArrayList expectedGenres = new ArrayList<>(List.of(genre4, genre1));

        assertEquals(expectedId,book1.getId());
        assertEquals(expectedTitle, book2.getTitle());
        assertEquals(expectedDescription, book3.getDescription());
        assertEquals(expectedISBN, book4.getIsbn());
        assertEquals(expectedAuthors, book1.getAuthors());
        assertEquals(expectedGenres, book4.getGenres());
    }

    @Test
    void testSetters(){
        String expectedTitle = "Harry Potter and the Philosopher's Stone";
        String expectedDescription = "An unexpected trip";
        String expectedISBN = "1019-9101";
        ArrayList expectedAuthors = new ArrayList<>(List.of(author1));
        ArrayList expectedGenres = new ArrayList<>(List.of(genre1, genre2));

        book1.setTitle("Harry Potter and the Philosopher's Stone");
        book2.setDescription("An unexpected trip");
        book3.setIsbn("1019-9101");
        book4.setAuthors(List.of(author1));
        book1.setGenres(List.of(genre1, genre2));

        assertEquals(expectedTitle, book1.getTitle());
        assertEquals(expectedDescription, book2.getDescription());
        assertEquals(expectedISBN, book3.getIsbn());
        assertEquals(expectedAuthors, book4.getAuthors());
        assertEquals(expectedGenres, book1.getGenres());
    }

    @Test
    void testAddAuthor(){
        ArrayList expectedAuthors = new ArrayList<>(List.of(author4, author1));

        book4.addAuthor(author1);

        assertEquals(expectedAuthors, book4.getAuthors());
    }

    @Test
    void testAddGenre(){
        ArrayList expectedGenres = new ArrayList<>(List.of(genre4, genre1, genre3));
        book4.addGenre(genre3);
        assertEquals(expectedGenres, book4.getGenres());
    }

    @Test
    void testToString(){
        String expectedToString = "ID: 1 | Title: Harry Potter | ISBN: 1234-4321 | Authors: J.K. Rowling | Genres: Fantasy";

        book1.toString();

        assertEquals(expectedToString, book1.toString());
    }

    @Test
    void testToStringWithDescription(){
        String expectedToStringWithDescription = "ID: 1 | Title: Harry Potter | Description: Magic and spells | ISBN: 1234-4321 | Authors: J.K. Rowling | Genres: Fantasy";

        book1.toString();

        assertEquals(expectedToStringWithDescription, book1.toStringWithDescription());
    }
}
