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

    public static final String RESET = "\033[0m";
    public static final String BOLD = "\033[1m";
    public static final String RED = "\033[31m";
    public static final String LIGHT_BLUE = "\033[36m";
    public static final String GOLDEN = "\033[33m";
    public static final String GREEN = "\033[32m";

    public BookView(BookController bookController, Scanner scanner) {
        this.bookController = bookController;
        this.scanner = scanner;
    }

    public void showMenu() {
        while (true) {
            System.out.println(BOLD + LIGHT_BLUE);
            System.out.println("╔═════════════════════╗");
            System.out.println("║   === Library ===   ║");
            System.out.println("╚═════════════════════╝");
            System.out.println(RESET);
            System.out.println("1. Show all books");
            System.out.println("2. Add new book");
            System.out.println("3. Update book");
            System.out.println("4. Delete book");
            System.out.println("5. Search by title");
            System.out.println("6. Search by author");
            System.out.println("7. Search by genre");
            System.out.println(RED + "0. Exit" + RESET);
            System.out.print(GOLDEN + "Choose an option: " + RESET);

            int choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 0:
                    System.out.println("\n" + GREEN + "Goodbye!" + RESET);
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
                    System.out.println(RED + "Invalid option" + RESET);
            }
        }
    }

    private void showAllBooks() {
        List<Book> books = bookController.getAllBooks();
        if (books.isEmpty()) {
            System.out.println(GOLDEN + "No books found." + RESET);
            return;
        }
        System.out.println(BOLD + LIGHT_BLUE + "\n=== All Books ===" + RESET);
        for (Book book : books) {
            System.out.println(book);
        }
    }

    private void addNewBook() {
        System.out.println(BOLD + GREEN + "\n=== Add New Book ===" + RESET);

        System.out.print(LIGHT_BLUE + "Enter title: " + RESET);
        String title = scanner.nextLine();

        System.out.print(LIGHT_BLUE + "Enter description: " + RESET);
        String description = scanner.nextLine();

        System.out.print(LIGHT_BLUE + "Enter ISBN: " + RESET);
        String isbn = scanner.nextLine();

        List<Author> authors = new ArrayList<>();
        System.out.print(LIGHT_BLUE + "Enter number of authors: " + RESET);
        int authorCount = scanner.nextInt();
        scanner.nextLine();

        for (int i = 0; i < authorCount; i++) {
            System.out.print(LIGHT_BLUE + "Enter author " + (i + 1) + " name: " + RESET);
            String authorName = scanner.nextLine();
            authors.add(new Author(authorName));
        }

        List<Genre> genres = new ArrayList<>();
        System.out.print(LIGHT_BLUE + "Enter number of genres: " + RESET);
        int genreCount = scanner.nextInt();
        scanner.nextLine();

        for (int i = 0; i < genreCount; i++) {
            System.out.print(LIGHT_BLUE + "Enter genre " + (i + 1) + " name: " + RESET);
            String genreName = scanner.nextLine();
            genres.add(new Genre(genreName));
        }

        Book book = new Book(title, description, isbn, authors, genres);

        System.out.println(BOLD + GOLDEN + "\n=== Book information to be saved ===" + RESET);
        System.out.println(GOLDEN + "Title: " + book.getTitle());
        System.out.println(GOLDEN + "Description: " + book.getDescription() + RESET);
        System.out.println(GOLDEN + "ISBN: " + book.getIsbn() + RESET);
        System.out.println(GOLDEN + "Authors: " + getAuthorsString(book.getAuthors()) + RESET);
        System.out.println(GOLDEN + "Genres: " + getGenresString(book.getGenres()) + RESET);

        System.out.print(GREEN + "\nAre you sure you want to save this book? (yes/no): " + RESET);
        String confirmation = scanner.nextLine();

        if (confirmation.equalsIgnoreCase("yes") || confirmation.equalsIgnoreCase("y")) {
            bookController.saveBook(book);
            System.out.println(GREEN + "Book added successfully!" + RESET);
        } else {
            System.out.println(GOLDEN + "Book creation cancelled." + RESET);
        }
    }

    private void updateBook() {
        System.out.println(BOLD + LIGHT_BLUE + "\n=== Update Book ===" + RESET);
        showAllBooks();
        System.out.println();
        System.out.print(GOLDEN + "Enter book ID to update: " + RESET);
        Long id = scanner.nextLong();
        scanner.nextLine();

        Book existingBook = bookController.findByID(id);
        if (existingBook == null) {
            System.out.println(RED + "Book with ID " + id + " not found." + RESET);
            return;
        }

        System.out.println(BOLD + LIGHT_BLUE + "\nOriginal book information:" + RESET);
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
                System.out.println(BOLD + LIGHT_BLUE + "\n=== Current book information ===" + RESET);
                System.out.println(updatedBook);
            }

            System.out.println(BOLD + LIGHT_BLUE + "\n=== What would you like to update? ===" + RESET);
            System.out.println(LIGHT_BLUE + "1. Title" + RESET);
            System.out.println(LIGHT_BLUE + "2. Description" + RESET);
            System.out.println(LIGHT_BLUE + "3. ISBN" + RESET);
            System.out.println(LIGHT_BLUE + "4. Authors" + RESET);
            System.out.println(LIGHT_BLUE + "5. Genres" + RESET);
            System.out.println(GREEN + "0. Finish updating" + RESET);
            System.out.print(GOLDEN + "Choose field to update: " + RESET);

            int choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 0:
                    continueUpdating = false;
                    break;
                case 1:
                    System.out
                            .print(LIGHT_BLUE + "Enter new title (current: " + updatedBook.getTitle() + "): " + RESET);
                    String newTitle = scanner.nextLine();
                    if (!newTitle.trim().isEmpty()) {
                        updatedBook.setTitle(newTitle);
                        hasChanges = true;
                        System.out.println(GREEN + "Title updated!" + RESET);
                    }
                    break;
                case 2:
                    System.out.print(
                            LIGHT_BLUE + "Enter new description (current: " + updatedBook.getDescription() + "): "
                                    + RESET);
                    String newDescription = scanner.nextLine();
                    if (!newDescription.trim().isEmpty()) {
                        updatedBook.setDescription(newDescription);
                        hasChanges = true;
                        System.out.println(GREEN + "Description updated!" + RESET);
                    }
                    break;
                case 3:
                    System.out.print(LIGHT_BLUE + "Enter new ISBN (current: " + updatedBook.getIsbn() + "): " + RESET);
                    String newIsbn = scanner.nextLine();
                    if (!newIsbn.trim().isEmpty()) {
                        updatedBook.setIsbn(newIsbn);
                        hasChanges = true;
                        System.out.println(GREEN + "ISBN updated!" + RESET);
                    }
                    break;
                case 4:
                    System.out.println(LIGHT_BLUE + "Current authors: "
                            + getAuthorsString(updatedBook.getAuthors()) + RESET);
                    System.out.print(LIGHT_BLUE + "Do you want to replace all authors? (yes/no): " + RESET);
                    String replaceAuthors = scanner.nextLine();
                    if (replaceAuthors.equalsIgnoreCase("y") || replaceAuthors.equalsIgnoreCase("yes")) {
                        List<Author> authors = new ArrayList<>();
                        System.out.print(LIGHT_BLUE + "Enter number of authors: " + RESET);
                        int authorCount = scanner.nextInt();
                        scanner.nextLine();

                        for (int i = 0; i < authorCount; i++) {
                            System.out.print(LIGHT_BLUE + "Enter author " + (i + 1) + " name: " + RESET);
                            String authorName = scanner.nextLine();
                            authors.add(new Author(authorName));
                        }
                        updatedBook.setAuthors(authors);
                        hasChanges = true;
                        System.out.println(GREEN + "Authors updated!" + RESET);
                    } else {
                        System.out.print(LIGHT_BLUE + "How many authors do you want to add?: " + RESET);
                        int addAuthorCount = scanner.nextInt();
                        scanner.nextLine();

                        if (addAuthorCount > 0) {
                            for (int i = 0; i < addAuthorCount; i++) {
                                System.out.print(LIGHT_BLUE + "Enter new author " + (i + 1) + " name: " + RESET);
                                String authorName = scanner.nextLine();
                                updatedBook.addAuthor(new Author(authorName));
                            }
                            hasChanges = true;
                            System.out.println(GREEN + "Authors added!" + RESET);
                        }
                    }
                    break;
                case 5:
                    System.out.println(
                            LIGHT_BLUE + "Current genres: " + getGenresString(updatedBook.getGenres()) + RESET);
                    System.out.print(LIGHT_BLUE + "Do you want to replace all genres? (yes/no): " + RESET);
                    String replaceGenres = scanner.nextLine();
                    if (replaceGenres.equalsIgnoreCase("y") || replaceGenres.equalsIgnoreCase("yes")) {
                        List<Genre> genres = new ArrayList<>();
                        System.out.print(LIGHT_BLUE + "Enter number of genres: " + RESET);
                        int genreCount = scanner.nextInt();
                        scanner.nextLine();

                        for (int i = 0; i < genreCount; i++) {
                            System.out.print(LIGHT_BLUE + "Enter genre " + (i + 1) + " name: " + RESET);
                            String genreName = scanner.nextLine();
                            genres.add(new Genre(genreName));
                        }
                        updatedBook.setGenres(genres);
                        hasChanges = true;
                        System.out.println(GREEN + "Genres updated!" + RESET);
                    } else {
                        System.out.print(LIGHT_BLUE + "How many genres do you want to add?: " + RESET);
                        int addGenreCount = scanner.nextInt();
                        scanner.nextLine();

                        if (addGenreCount > 0) {
                            for (int i = 0; i < addGenreCount; i++) {
                                System.out.print(LIGHT_BLUE + "Enter new genre " + (i + 1) + " name: " + RESET);
                                String genreName = scanner.nextLine();
                                updatedBook.addGenre(new Genre(genreName));
                            }
                            hasChanges = true;
                            System.out.println(GREEN + "Genres added!" + RESET);
                        }
                    }
                    break;
                default:
                    System.out.println(RED + "Invalid option" + RESET);
            }
        }

        System.out.println(BOLD + LIGHT_BLUE + "\n=== Final book data ===" + RESET);
        System.out.println(updatedBook);
        System.out.print(GOLDEN + "\nAre you sure you want to save these changes? (yes/no): " + RESET);
        String confirmation = scanner.nextLine();

        if (confirmation.equalsIgnoreCase("yes") || confirmation.equalsIgnoreCase("y")) {
            bookController.updateBook(updatedBook);
            System.out.println(GREEN + "Book updated successfully!" + RESET);
        } else {
            System.out.println(GOLDEN + "Changes cancelled." + RESET);
        }
    }

    private void deleteBook() {
        System.out.println(BOLD + LIGHT_BLUE + "\n=== Delete Book ===" + RESET);
        showAllBooks();
        System.out.println();
        System.out.print(GOLDEN + "Enter book ID to delete: " + RESET);
        Long id = scanner.nextLong();
        scanner.nextLine();

        System.out.print(RED + "Are you sure you want to delete this book? (yes/no): " + RESET);
        String confirmation = scanner.nextLine();

        if (confirmation.equalsIgnoreCase("y") || confirmation.equalsIgnoreCase("yes")) {
            bookController.deleteBook(id);
            System.out.println(GREEN + "Book deleted successfully!" + RESET);
        } else {
            System.out.println(GOLDEN + "Deletion cancelled." + RESET);
        }
    }

    private void searchByTitle() {
        System.out.println(BOLD + LIGHT_BLUE + "\n=== Search by Title ===" + RESET);
        System.out.print(LIGHT_BLUE + "Enter title to search: " + RESET);
        String title = scanner.nextLine();

        List<Book> books = bookController.findByTitle(title);

        if (books.isEmpty()) {
            System.out.println(GOLDEN + "No books found with title containing: " + title + RESET);
        } else {
            System.out.println(GREEN + "Found " + books.size() + " book(s):" + RESET);
            for (Book book : books) {
                System.out.println(book);
            }
        }
    }

    private void searchByAuthor() {
        System.out.println(BOLD + LIGHT_BLUE + "\n=== Search by Author ===" + RESET);
        System.out.print(LIGHT_BLUE + "Enter author name to search: " + RESET);
        String author = scanner.nextLine();

        List<Book> books = bookController.findByAuthor(author);

        if (books.isEmpty()) {
            System.out.println(GOLDEN + "No books found by author: " + author + RESET);
        } else {
            System.out.println(GREEN + "Found " + books.size() + " book(s) by " + author + ":" + RESET);
            for (Book book : books) {
                System.out.println(book);
            }
        }
    }

    private void searchByGenre() {
        System.out.println(BOLD + LIGHT_BLUE + "\n=== Search by Genre ===" + RESET);
        System.out.print(LIGHT_BLUE + "Enter genre to search: " + RESET);
        String genre = scanner.nextLine();

        List<Book> books = bookController.findByGenre(genre);

        if (books.isEmpty()) {
            System.out.println(GOLDEN + "No books found in genre: " + genre + RESET);
        } else {
            System.out.println(GREEN + "Found " + books.size() + " book(s) in genre " + genre + ":" + RESET);
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
