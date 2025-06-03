package org.LT3.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class AuthorTest {

    private Author author1;
    private Author author2;
    private Author author3;
    private Author author4;

    @BeforeEach
    void setUp(){
        author1 = new Author(1L, "J.K. Rowling");
        author2 = new Author(2L, "J.R.R. Tolkien");
        author3 = new Author(3L, "George Orwell");
        author4 = new Author(4L, "Mary Shelley");
    }

    @Test
    void testGetters(){
        Long expectedId = 1L;
        String expectedName = "J.R.R. Tolkien";

        assertEquals(expectedId, author1.getId());
        assertEquals(expectedName, author2.getName());
    }

    @Test
    void testSetters(){
        String expectedName = "Laura Gallego";

        author3.setName("Laura Gallego");

        assertEquals(expectedName, author3.getName());
    }

    @Test
    void testToString(){
        String expectedAuthor = "Author{id=4, name='Mary Shelley'}";

        assertEquals(expectedAuthor, author4.toString());
    }
}
