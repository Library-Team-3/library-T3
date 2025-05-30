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
        bookController.saveBook(book);

        System.out.println("Book added successfully!");
    }

    private void updateBook() {
        System.out.println("\n=== Update Book ===");
        showAllBooks();

        System.out.print("Enter book ID to update: ");
        Long id = scanner.nextLong();
        scanner.nextLine();

        System.out.print("Enter new title: ");
        String title = scanner.nextLine();

        System.out.print("Enter new description: ");
        String description = scanner.nextLine();

        System.out.print("Enter new ISBN: ");
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

        Book book = new Book(id, title, description, isbn, authors, genres);
        bookController.updateBook(book);

        System.out.println("Book updated successfully!");
    }

    private void deleteBook() {
        System.out.println("\n=== Delete Book ===");
        showAllBooks();

        System.out.print("Enter book ID to delete: ");
        Long id = scanner.nextLong();
        scanner.nextLine();

        System.out.print("Are you sure you want to delete this book? (y/n): ");
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
}
