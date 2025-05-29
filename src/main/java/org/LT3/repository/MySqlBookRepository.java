package org.LT3.repository;

import org.LT3.config.DatabaseConnection;
import org.LT3.model.Author;
import org.LT3.model.Book;
import org.LT3.model.Genre;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class MySqlBookRepository implements BookRepository {
    @Override
    public List<Book> findAll() {
        List<Book> books = new ArrayList<>();
        String query = "SELECT \n" +
                "    b.title,\n" +
                "    b.isbn,\n" +
                "    a.name as author,\n" +
                "    g.name as genre\n" +
                "FROM book b\n" +
                "JOIN author a ON b.author_id = a.id\n" +
                "JOIN genre g ON b.genre_id = g.id;";

        try (Connection conn = DatabaseConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {

            while (rs.next()) {
                Book book = new Book(
                        rs.getLong("id"),
                        rs.getString("title"),
                        rs.getString("description"),
                        rs.getString("isbn"),
                        new ArrayList<>(),
                        new ArrayList<>()
                );
                String authorsStr = rs.getString("authors");
                if (authorsStr != null) {
                    for (String authorName : authorsStr.split(",")) {
                        Author author = new Author(authorName);
                        book.addAuthor(author);
                    }
                }

                String genresStr = rs.getString("genres");
                if (genresStr != null) {
                    for (String genreName : genresStr.split(",")) {
                        Genre genre = new Genre(genreName);
                        book.addGenre(genre);
                    }
                }

                books.add(book);

            }

        } catch (SQLException e) {

            System.err.println("Error getting all books: " + e.getMessage());

        } catch (Exception e) {
            throw new RuntimeException("Error getting all books: " + e.getMessage());
        }

        return books;
        }

    @Override
    public void save(Book book) {

    }

    @Override
    public void update(Book book) {

    }

    @Override
    public void delete(Long id) {

    }

    @Override
    public List<Book> findByTitle(String title) {
        return List.of();
    }

    @Override
    public List<Book> findByAuthor(String author) {
        return List.of();
    }

    @Override
    public List<Book> findByGenre(String genre) {
        return List.of();
    }
}



