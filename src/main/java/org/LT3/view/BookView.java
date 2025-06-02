package org.LT3.view;

import org.LT3.controller.BookController;
import org.LT3.model.Author;
import org.LT3.model.Book;
import org.LT3.model.Genre;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class BookView {

    private final BookController bookController;
    private final Scanner scanner;

    public BookView(BookController bookController, Scanner scanner) {
        this.bookController = bookController;
        this.scanner = scanner;
    }

    public void showMenu() {
        while (true) {
            System.out.println("\n=== Library ===");
            System.out.println("1. Show all books");
            System.out.println("2. Add new book");
            System.out.println("3. Update book");
            System.out.println("4. Delete book");
            System.out.println("5. Search by title");
            System.out.println("6. Search by author");
            System.out.println("7. Search by genre");
            System.out.println("0. Exit");
            System.out.println("Choose an option:");

            int choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 0:
                    return;
                case 1:
                    showAllBooks();
                    break;
                case 2:
                    addNewBook();
                    break;
                case 3:
                    updateBook();
                    break;
                case 4:
                    deleteBook();
                    break;
                case 5:
                    searchByTitle();
                    break;
                case 6:
                    searchByAuthor();
                    break;
                case 7:
                    searchByGenre();
                    break;
                default:
                    System.out.println("Invalid option");
            }
        }
    }

    private void showAllBooks() {
        List<Book> books = bookController.getAllBooks();
        if (books.isEmpty()) {
            System.out.println("No books found.");
            return;
        }
        System.out.println("\n=== All Books ===");
        for (Book book : books) {
            System.out.println(book);
        }
    }

    private void addNewBook() {
        System.out.println("\n=== Add New Book ===");

        System.out.print("Enter title: ");
        String title = scanner.nextLine();

        System.out.print("Enter description: ");
        String description = scanner.nextLine();

        System.out.print("Enter ISBN: ");
        String isbn = scanner.nextLine();

        List<Author> authors = new ArrayList<>();
        System.out.print("Enter number of authors: ");
        int authorCount = scanner.nextInt();
        scanner.nextLine();

        for (int i = 0; i < authorCount; i++) {
            System.out.print("Enter author " + (i + 1) + " name: ");
            String authorName = scanner.nextLine();
            authors.add(new Author(authorName));
        }

        List<Genre> genres = new ArrayList<>();
        System.out.print("Enter number of genres: ");
        int genreCount = scanner.nextInt();
        scanner.nextLine();

        for (int i = 0; i < genreCount; i++) {
            System.out.print("Enter genre " + (i + 1) + " name: ");
            String genreName = scanner.nextLine();
            genres.add(new Genre(genreName));
        }

        Book book = new Book(title, description, isbn, authors, genres);

        System.out.println("\n=== Book information to be saved ===");
        System.out.println("Title: " + book.getTitle());
        System.out.println("Description: " + book.getDescription());
        System.out.println("ISBN: " + book.getIsbn());
        System.out.println("Authors: " + getAuthorsString(book.getAuthors()));
        System.out.println("Genres: " + getGenresString(book.getGenres()));

        System.out.print("\nAre you sure you want to save this book? (yes/no): ");
        String confirmation = scanner.nextLine();

        if (confirmation.equalsIgnoreCase("yes") || confirmation.equalsIgnoreCase("y")) {
            bookController.saveBook(book);
            System.out.println("Book added successfully!");
        } else {
            System.out.println("Book creation cancelled.");
        }
    }

    private void updateBook() {
        System.out.println("\n=== Update Book ===");
        showAllBooks();

        System.out.print("Enter book ID to update: ");
        Long id = scanner.nextLong();
        scanner.nextLine();

        Book existingBook = bookController.findByID(id);
        if (existingBook == null) {
            System.out.println("Book with ID " + id + " not found.");
            return;
        }

        System.out.println("\nOriginal book information:");
        System.out.println(existingBook);

        Book updatedBook = new Book(
                existingBook.getId(),
                existingBook.getTitle(),
                existingBook.getDescription(),
                existingBook.getIsbn(),
                new ArrayList<>(existingBook.getAuthors()),
                new ArrayList<>(existingBook.getGenres()));

        boolean continueUpdating = true;
        boolean hasChanges = false;
        while (continueUpdating) {
            if (hasChanges) {
                System.out.println("\n=== Current book information ===");
                System.out.println(updatedBook);
            }

            System.out.println("\n=== What would you like to update? ===");
            System.out.println("1. Title");
            System.out.println("2. Description");
            System.out.println("3. ISBN");
            System.out.println("4. Authors");
            System.out.println("5. Genres");
            System.out.println("0. Finish updating");
            System.out.print("Choose field to update: ");

            int choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 0:
                    continueUpdating = false;
                    break;
                case 1:
                    System.out.print("Enter new title (current: " + updatedBook.getTitle() + "): ");
                    String newTitle = scanner.nextLine();
                    if (!newTitle.trim().isEmpty()) {
                        updatedBook.setTitle(newTitle);
                        hasChanges = true;
                        System.out.println("Title updated!");
                    }
                    break;
                case 2:
                    System.out.print("Enter new description (current: " + updatedBook.getDescription() + "): ");
                    String newDescription = scanner.nextLine();
                    if (!newDescription.trim().isEmpty()) {
                        updatedBook.setDescription(newDescription);
                        hasChanges = true;
                        System.out.println("Description updated!");
                    }
                    break;
                case 3:
                    System.out.print("Enter new ISBN (current: " + updatedBook.getIsbn() + "): ");
                    String newIsbn = scanner.nextLine();
                    if (!newIsbn.trim().isEmpty()) {
                        updatedBook.setIsbn(newIsbn);
                        hasChanges = true;
                        System.out.println("ISBN updated!");
                    }
                    break;
                case 4:
                    System.out.println("Current authors: " + getAuthorsString(updatedBook.getAuthors()));
                    System.out.print("Do you want to replace all authors? (yes/no): ");
                    String replaceAuthors = scanner.nextLine();
                    if (replaceAuthors.equalsIgnoreCase("y") || replaceAuthors.equalsIgnoreCase("yes")) {
                        List<Author> authors = new ArrayList<>();
                        System.out.print("Enter number of authors: ");
                        int authorCount = scanner.nextInt();
                        scanner.nextLine();

                        for (int i = 0; i < authorCount; i++) {
                            System.out.print("Enter author " + (i + 1) + " name: ");
                            String authorName = scanner.nextLine();
                            authors.add(new Author(authorName));
                        }
                        updatedBook.setAuthors(authors);
                        hasChanges = true;
                        System.out.println("Authors updated!");
                    } else {
                        System.out.print("How many authors do you want to add?: ");
                        int addAuthorCount = scanner.nextInt();
                        scanner.nextLine();

                        if (addAuthorCount > 0) {
                            for (int i = 0; i < addAuthorCount; i++) {
                                System.out.print("Enter new author " + (i + 1) + " name: ");
                                String authorName = scanner.nextLine();
                                updatedBook.addAuthor(new Author(authorName));
                            }
                            hasChanges = true;
                            System.out.println("Authors added!");
                        }
                    }
                    break;
                case 5:
                    System.out.println("Current genres: " + getGenresString(updatedBook.getGenres()));
                    System.out.print("Do you want to replace all genres? (yes/no): ");
                    String replaceGenres = scanner.nextLine();
                    if (replaceGenres.equalsIgnoreCase("y") || replaceGenres.equalsIgnoreCase("yes")) {
                        List<Genre> genres = new ArrayList<>();
                        System.out.print("Enter number of genres: ");
                        int genreCount = scanner.nextInt();
                        scanner.nextLine();

                        for (int i = 0; i < genreCount; i++) {
                            System.out.print("Enter genre " + (i + 1) + " name: ");
                            String genreName = scanner.nextLine();
                            genres.add(new Genre(genreName));
                        }
                        updatedBook.setGenres(genres);
                        hasChanges = true;
                        System.out.println("Genres updated!");
                    } else {
                        System.out.print("How many genres do you want to add?: ");
                        int addGenreCount = scanner.nextInt();
                        scanner.nextLine();

                        if (addGenreCount > 0) {
                            for (int i = 0; i < addGenreCount; i++) {
                                System.out.print("Enter new genre " + (i + 1) + " name: ");
                                String genreName = scanner.nextLine();
                                updatedBook.addGenre(new Genre(genreName));
                            }
                            hasChanges = true;
                            System.out.println("Genres added!");
                        }
                    }
                    break;
                default:
                    System.out.println("Invalid option");
            }
        }

        System.out.println("\n=== Final book data ===");
        System.out.println(updatedBook);
        System.out.print("\nAre you sure you want to save these changes? (yes/no): ");
        String confirmation = scanner.nextLine();

        if (confirmation.equalsIgnoreCase("yes") || confirmation.equalsIgnoreCase("y")) {
            bookController.updateBook(updatedBook);
            System.out.println("Book updated successfully!");
        } else {
            System.out.println("Changes cancelled.");
        }
    }

    private void deleteBook() {
        System.out.println("\n=== Delete Book ===");
        showAllBooks();

        System.out.print("Enter book ID to delete: ");
        Long id = scanner.nextLong();
        scanner.nextLine();

        System.out.print("Are you sure you want to delete this book? (yes/no): ");
        String confirmation = scanner.nextLine();

        if (confirmation.equalsIgnoreCase("y") || confirmation.equalsIgnoreCase("yes")) {
            bookController.deleteBook(id);
            System.out.println("Book deleted successfully!");
        } else {
            System.out.println("Deletion cancelled.");
        }
    }

    private void searchByTitle() {
        System.out.println("\n=== Search by Title ===");
        System.out.print("Enter title to search: ");
        String title = scanner.nextLine();

        List<Book> books = bookController.findByTitle(title);

        if (books.isEmpty()) {
            System.out.println("No books found with title containing: " + title);
        } else {
            System.out.println("Found " + books.size() + " book(s):");
            for (Book book : books) {
                System.out.println(book);
            }
        }
    }

    private void searchByAuthor() {
        System.out.println("\n=== Search by Author ===");
        System.out.print("Enter author name to search: ");
        String author = scanner.nextLine();

        List<Book> books = bookController.findByAuthor(author);

        if (books.isEmpty()) {
            System.out.println("No books found by author: " + author);
        } else {
            System.out.println("Found " + books.size() + " book(s) by " + author + ":");
            for (Book book : books) {
                System.out.println(book);
            }
        }
    }

    private void searchByGenre() {
        System.out.println("\n=== Search by Genre ===");
        System.out.print("Enter genre to search: ");
        String genre = scanner.nextLine();

        List<Book> books = bookController.findByGenre(genre);

        if (books.isEmpty()) {
            System.out.println("No books found in genre: " + genre);
        } else {
            System.out.println("Found " + books.size() + " book(s) in genre " + genre + ":");
            for (Book book : books) {
                System.out.println(book);
            }
        }
    }

    private String getAuthorsString(List<Author> authors) {
        if (authors == null || authors.isEmpty()) {
            return "No authors";
        }
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < authors.size(); i++) {
            if (i > 0)
                sb.append(", ");
            sb.append(authors.get(i).getName());
        }
        return sb.toString();
    }

    private String getGenresString(List<Genre> genres) {
        if (genres == null || genres.isEmpty()) {
            return "No genres";
        }
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < genres.size(); i++) {
            if (i > 0)
                sb.append(", ");
            sb.append(genres.get(i).getName());
        }
        return sb.toString();
    }
}
