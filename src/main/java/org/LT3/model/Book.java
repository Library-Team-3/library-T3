package org.LT3.model;

import java.util.ArrayList;
import java.util.List;

public class Book {
    Long id;
    String title;
    String description;
    String isbn;
    private List<Author> authors;
    private List<Genre> genres;

    public Book(Long id, String title, String description, String isbn, List<Author> authors, List<Genre> genres) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.isbn = isbn;
        this.authors = authors;
        this.genres = genres;
    }

    public Book(String title, String description, String isbn, List<Author> authors, List<Genre> genres) {
        this.title = title;
        this.description = description;
        this.isbn = isbn;
        this.authors = authors;
        this.genres = genres;
    }

    public Long getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getIsbn() {
        return isbn;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    public List<Author> getAuthors() {
        return authors;
    }

    public void setAuthors(List<Author> authors) {
        this.authors = authors;
    }

    public List<Genre> getGenres() {
        return genres;
    }

    public void setGenres(List<Genre> genres) {
        this.genres = genres;
    }

    public void addAuthor(Author author){
        if (this.authors == null) {
            this.authors = new ArrayList<>();
        }
        this.authors.add(author);
    }

    public void addGenre(Genre genre){
        if (this.genres == null) {
            this.genres = new ArrayList<>();
        }
        this.genres.add(genre);
    }

    @Override
    public String toString() {
        StringBuilder authorsStr = new StringBuilder();
        if (authors != null && !authors.isEmpty()) {
            for (int i = 0; i < authors.size(); i++) {
                if (i > 0)
                    authorsStr.append(", ");
                authorsStr.append(authors.get(i).getName());
            }
        }
        StringBuilder genresStr = new StringBuilder();
        if (genres != null && !genres.isEmpty()) {
            for (int i = 0; i < genres.size(); i++) {
                if (i > 0)
                    genresStr.append(", ");
                genresStr.append(genres.get(i).getName());
            }
        }
        return String.format("ID: %d | %s | ISBN: %s | Authors: %s | Genres: %s",
                id != null ? id : 0,
                title != null ? title : "Without title",
                isbn != null ? isbn : "isbn doesn't exist",
                !authorsStr.isEmpty() ? authorsStr.toString() : "Author doesn't exist",
                !genresStr.isEmpty() ? genresStr.toString() : "Genre doesn't exist");
    }

}
