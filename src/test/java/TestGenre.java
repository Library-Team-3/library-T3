import org.LT3.model.Author;
import org.LT3.model.Genre;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class TestGenre {
    private Genre genre1;
    private Genre genre2;
    private Genre genre3;
    private Genre genre4;

    @BeforeEach
    void setUp(){
        genre1 = new Genre(1L, "Fantasy");
        genre2 = new Genre(2L, "Adventure");
        genre3 = new Genre(3L, "Dystopian");
        genre4 = new Genre(4L, "Horror");
    }

    @Test
    void testGetters(){
        Long expectedId = 1L;
        String expectedName = "Adventure";

        assertEquals(expectedId, genre1.getId());
        assertEquals(expectedName, genre2.getName());
    }

    @Test
    void testSetters(){
        String expectedName = "Comedy";

        genre3.setName("Comedy");

        assertEquals(expectedName, genre3.getName());
    }

    @Test
    void testToString(){
        String expectedAuthor = "Genre{id=4, name='Horror'}";

        assertEquals(expectedAuthor, genre4.toString());
    }
}
