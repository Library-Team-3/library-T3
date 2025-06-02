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
        String sql = "SELECT id, title, description, isbn FROM book ORDER BY id";
        try (Connection conn = DatabaseConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                Book book = new Book(
                        rs.getLong("id"),
                        rs.getString("title"),
                        rs.getString("description"),
                        rs.getString("isbn"),
                        new ArrayList<>(),
                        new ArrayList<>());
                loadAuthors(conn, book);
                loadGenres(conn, book);
                books.add(book);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return books;
    }

    @Override
    public Book findById(Long id) {
        String sql = "SELECT id, title, description, isbn FROM book WHERE id = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setLong(1, id);
            try(ResultSet rs = stmt.executeQuery()){
                if(rs.next()){
                    Book book = new Book(rs.getLong("id"), rs.getString("title"), rs.getString("description"), rs.getString("isbn"), new ArrayList<>(), new ArrayList<>() );
                    loadAuthors(conn, book);
                    loadGenres(conn, book);

                    return book;
                }
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public void save(Book book) {
        String sql = "INSERT INTO book (title, description, isbn) VALUES (?, ?, ?)";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            stmt.setString(1, book.getTitle());
            stmt.setString(2, book.getDescription());
            stmt.setString(3, book.getIsbn());
            stmt.executeUpdate();
            try (ResultSet keys = stmt.getGeneratedKeys()) {
                if (keys.next()) {
                    long bookId = keys.getLong(1);
                    saveAuthors(conn, bookId, book.getAuthors());
                    saveGenres(conn, bookId, book.getGenres());
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    @Override
    public void update(Book book) {
        String sql = "UPDATE book SET title = ?, description = ?, isbn = ? WHERE id = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, book.getTitle());
            stmt.setString(2, book.getDescription());
            stmt.setString(3, book.getIsbn());
            stmt.setLong(4, book.getId());
            stmt.executeUpdate();
            deleteRelations(conn, "book_author", book.getId());
            deleteRelations(conn, "book_genre", book.getId());
            saveAuthors(conn, book.getId(), book.getAuthors());
            saveGenres(conn, book.getId(), book.getGenres());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    @Override
    public void delete(Long id) {
        try (Connection conn = DatabaseConnection.getConnection()) {
            deleteRelations(conn, "book_author", id);
            deleteRelations(conn, "book_genre", id);
            try (PreparedStatement stmt = conn.prepareStatement("DELETE FROM book WHERE id = ?")) {
                stmt.setLong(1, id);
                stmt.executeUpdate();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    @Override
    public List<Book> findByTitle(String title) {
        String sql = "SELECT id, title, description, isbn FROM book WHERE title LIKE ? ORDER BY id";
        return findBy(sql, title);
    }
    @Override
    public List<Book> findByAuthor(String author) {
        String sql = "SELECT DISTINCT b.id, b.title, b.description, b.isbn FROM book b " +
                "JOIN book_author ba ON b.id = ba.id_book " +
                "JOIN author a ON ba.id_author = a.id WHERE a.name LIKE ? ORDER BY b.id";
        return findBy(sql, author);
    }
    @Override
    public List<Book> findByGenre(String genre) {
        String sql = "SELECT DISTINCT b.id, b.title, b.description, b.isbn FROM book b " +
                "JOIN book_genre bg ON b.id = bg.id_book " +
                "JOIN genre g ON bg.id_genre = g.id WHERE g.name LIKE ? ORDER BY b.id";
        return findBy(sql, genre);
    }
    private List<Book> findBy(String sql, String keyword) {
        List<Book> books = new ArrayList<>();
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(
                     sql.contains("LIKE") ? sql : "SELECT id, title, description, isbn FROM book WHERE " + sql)) {
            stmt.setString(1, "%" + keyword + "%");
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    Book book = new Book(
                            rs.getLong("id"),
                            rs.getString("title"),
                            rs.getString("description"),
                            rs.getString("isbn"),
                            new ArrayList<>(),
                            new ArrayList<>());
                    loadAuthors(conn, book);
                    loadGenres(conn, book);
                    books.add(book);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return books;
    }
    private void loadAuthors(Connection conn, Book book) throws SQLException {
        String sql = "SELECT a.id, a.name FROM author a JOIN book_author ba ON a.id = ba.id_author WHERE ba.id_book = ?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setLong(1, book.getId());
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    book.addAuthor(new Author(rs.getLong("id"), rs.getString("name")));
                }
            }
        }
    }
    private void loadGenres(Connection conn, Book book) throws SQLException {
        String sql = "SELECT g.id, g.name FROM genre g JOIN book_genre bg ON g.id = bg.id_genre WHERE bg.id_book = ?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setLong(1, book.getId());
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    book.addGenre(new Genre(rs.getLong("id"), rs.getString("name")));
                }
            }
        }
    }
    private void saveAuthors(Connection conn, long bookId, List<Author> authors) throws SQLException {
        for (Author author : authors) {
            long authorId = getOrCreate(conn, "author", author.getName());
            try (PreparedStatement stmt = conn
                    .prepareStatement("INSERT INTO book_author (id_book, id_author) VALUES (?, ?)")) {
                stmt.setLong(1, bookId);
                stmt.setLong(2, authorId);
                stmt.executeUpdate();
            }
        }
    }
    private void saveGenres(Connection conn, long bookId, List<Genre> genres) throws SQLException {
        for (Genre genre : genres) {
            long genreId = getOrCreate(conn, "genre", genre.getName());
            try (PreparedStatement stmt = conn
                    .prepareStatement("INSERT INTO book_genre (id_book, id_genre) VALUES (?, ?)")) {
                stmt.setLong(1, bookId);
                stmt.setLong(2, genreId);
                stmt.executeUpdate();
            }
        }
    }
    private long getOrCreate(Connection conn, String table, String name) throws SQLException {
        String selectSql = "SELECT id FROM " + table + " WHERE name = ?";
        try (PreparedStatement stmt = conn.prepareStatement(selectSql)) {
            stmt.setString(1, name);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next())
                    return rs.getLong("id");
            }
        }
        String insertSql = "INSERT INTO " + table + " (name) VALUES (?)";
        try (PreparedStatement stmt = conn.prepareStatement(insertSql, Statement.RETURN_GENERATED_KEYS)) {
            stmt.setString(1, name);
            stmt.executeUpdate();
            try (ResultSet keys = stmt.getGeneratedKeys()) {
                if (keys.next())
                    return keys.getLong(1);
            }
        }
        return 0;
    }
    private void deleteRelations(Connection conn, String table, long bookId) throws SQLException {
        String sql = "DELETE FROM " + table + " WHERE id_book = ?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setLong(1, bookId);
            stmt.executeUpdate();
        }
    }
}