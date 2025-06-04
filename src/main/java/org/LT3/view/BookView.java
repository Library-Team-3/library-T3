package org.LT3.view;

import org.LT3.controller.BookController;
import org.LT3.model.Author;
import org.LT3.model.Book;
import org.LT3.model.Genre;
import org.LT3.util.ColorConstants;

import java.util.ArrayList;
import java.util.InputMismatchException;
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
            System.out.println(ColorConstants.BOLD + ColorConstants.MENU_COLOR);
            System.out.println("╔═════════════════════════════╗");
            System.out.println("║       === Library ===       ║");
            System.out.println("╚═════════════════════════════╝");
            System.out.println(ColorConstants.RESET);
            System.out.println("1. Show all books          📚");
            System.out.println("2. Add new book            ➕");
            System.out.println("3. Update book             ✏️");
            System.out.println("4. Delete book             🗑️");
            System.out.println("5. Search by title         🔍");
            System.out.println("6. Search by author        🙎🏼‍♂️");
            System.out.println("7. Search by genre         🏷️");
            System.out.println();
            System.out.println(ColorConstants.ERROR_COLOR + "0. Exit                    🚪" + ColorConstants.RESET);
            System.out.println();
            System.out.print(ColorConstants.OPTION_COLOR + "Choose an option: " + ColorConstants.RESET);

            int choice;
            try {
                choice = scanner.nextInt();
                scanner.nextLine();
            } catch (InputMismatchException e) {
                System.out.println(
                        ColorConstants.ERROR_COLOR + "\nError: Please enter a valid number!" + ColorConstants.RESET);
                scanner.nextLine();
                continue;
            }

            switch (choice) {
                case 0:
                    System.out.println("\n" + ColorConstants.SUCCESS_COLOR + "Goodbye! 👋" + ColorConstants.RESET);
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
                    System.out.println(ColorConstants.ERROR_COLOR
                            + "\nError: Invalid option! Please choose a number from 0-7." + ColorConstants.RESET);
            }

            System.out.println();
        }
    }

    private void showAllBooks() {
        List<Book> books = bookController.getAllBooks();
        if (books.isEmpty()) {
            System.out
                    .println(ColorConstants.OPTION_COLOR + "\nNo books found in the library." + ColorConstants.RESET);
            return;
        }
        System.out.println(
                ColorConstants.BOLD + ColorConstants.MENU_COLOR + "\n=== All Books ===" + ColorConstants.RESET);
        for (Book book : books) {
            System.out.println(book);
        }
    }

    private void addNewBook() {
        System.out.println(
                ColorConstants.BOLD + ColorConstants.SUCCESS_COLOR + "\n=== Add New Book ===" + ColorConstants.RESET);

        String title = getValidStringInput("Enter title: ", "Book title cannot be empty!");
        String description = getValidStringInput("Enter description: ", "Book description cannot be empty!");
        String isbn = getValidStringInput("Enter ISBN: ", "ISBN cannot be empty!");

        List<Author> authors = new ArrayList<>();
        int authorCount = getValidPositiveIntegerInput("Enter number of authors: ",
                "Number of authors must be a positive integer!");

        for (int i = 0; i < authorCount; i++) {
            String authorName = getValidStringInput("Enter author " + (i + 1) + " name: ",
                    "Author name cannot be empty!");
            authors.add(new Author(authorName));
        }

        List<Genre> genres = new ArrayList<>();
        int genreCount = getValidPositiveIntegerInput("Enter number of genres: ",
                "Number of genres must be a positive integer!");

        for (int i = 0; i < genreCount; i++) {
            String genreName = getValidStringInput("Enter genre " + (i + 1) + " name: ", "Genre name cannot be empty!");
            genres.add(new Genre(genreName));
        }

        Book book = new Book(title, description, isbn, authors, genres);

        System.out.println(ColorConstants.BOLD + ColorConstants.OPTION_COLOR + "\n=== Book information to be saved ==="
                + ColorConstants.RESET);
        System.out.println(ColorConstants.OPTION_COLOR + "Title: " + book.getTitle());
        System.out
                .println(ColorConstants.OPTION_COLOR + "Description: " + book.getDescription() + ColorConstants.RESET);
        System.out.println(ColorConstants.OPTION_COLOR + "ISBN: " + book.getIsbn() + ColorConstants.RESET);
        System.out.println(ColorConstants.OPTION_COLOR + "Authors: " + getAuthorsString(book.getAuthors())
                + ColorConstants.RESET);
        System.out.println(
                ColorConstants.OPTION_COLOR + "Genres: " + getGenresString(book.getGenres()) + ColorConstants.RESET);

        System.out.print(ColorConstants.SUCCESS_COLOR + "\nAre you sure you want to save this book? (yes/no): "
                + ColorConstants.RESET);
        String confirmation = scanner.nextLine();

        if (confirmation.equalsIgnoreCase("yes") || confirmation.equalsIgnoreCase("y")) {
            try {
                bookController.saveBook(book);
                System.out.println(ColorConstants.SUCCESS_COLOR + "Book added successfully! ✅" + ColorConstants.RESET);
            } catch (Exception e) {
                System.out.println(ColorConstants.ERROR_COLOR + "Error: Failed to save book. " + e.getMessage()
                        + ColorConstants.RESET);
            }
        } else {
            System.out.println(ColorConstants.OPTION_COLOR + "Book creation cancelled." + ColorConstants.RESET);
        }
    }

    private void updateBook() {
        System.out.println(
                ColorConstants.BOLD + ColorConstants.MENU_COLOR + "\n=== Update Book ===" + ColorConstants.RESET);
        showAllBooks();
        System.out.println();

        Long id = getValidLongInput("Enter book ID to update: ");

        Book existingBook;
        try {
            existingBook = bookController.findByID(id);
            if (existingBook == null) {
                System.out.println(ColorConstants.ERROR_COLOR + "Error: Book with ID " + id + " not found."
                        + ColorConstants.RESET);
                return;
            }
        } catch (Exception e) {
            System.out.println(ColorConstants.ERROR_COLOR + "Error: Failed to find book. " + e.getMessage()
                    + ColorConstants.RESET);
            return;
        }

        System.out.println(ColorConstants.BOLD + ColorConstants.MENU_COLOR + "\nOriginal book information:"
                + ColorConstants.RESET);
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
                System.out.println(ColorConstants.BOLD + ColorConstants.MENU_COLOR
                        + "\n=== Current book information ===" + ColorConstants.RESET);
                System.out.println(updatedBook);
            }

            System.out.println(ColorConstants.BOLD + ColorConstants.MENU_COLOR
                    + "\n=== What would you like to update? ===" + ColorConstants.RESET);
            System.out.println(ColorConstants.MENU_COLOR + "1. Title              ✏️" + ColorConstants.RESET);
            System.out.println(ColorConstants.MENU_COLOR + "2. Description        📝" + ColorConstants.RESET);
            System.out.println(ColorConstants.MENU_COLOR + "3. ISBN               🔢" + ColorConstants.RESET);
            System.out.println(ColorConstants.MENU_COLOR + "4. Authors            👥" + ColorConstants.RESET);
            System.out.println(ColorConstants.MENU_COLOR + "5. Genres             🏷️" + ColorConstants.RESET);
            System.out.println();
            System.out.println(ColorConstants.SUCCESS_COLOR + "0. Finish updating    ✅" + ColorConstants.RESET);
            System.out.println();
            System.out.print(ColorConstants.OPTION_COLOR + "Choose field to update: " + ColorConstants.RESET);

            int choice;
            try {
                choice = scanner.nextInt();
                scanner.nextLine();
            } catch (InputMismatchException e) {
                System.out.println(
                        ColorConstants.ERROR_COLOR + "Error: Please enter a valid number!" + ColorConstants.RESET);
                scanner.nextLine();
                continue;
            }

            switch (choice) {
                case 0:
                    continueUpdating = false;
                    break;
                case 1:
                    System.out
                            .print(ColorConstants.MENU_COLOR + "Enter new title (current: " + updatedBook.getTitle()
                                    + "): " + ColorConstants.RESET);
                    String newTitle = scanner.nextLine();
                    if (!newTitle.trim().isEmpty()) {
                        updatedBook.setTitle(newTitle);
                        hasChanges = true;
                        System.out.println(ColorConstants.SUCCESS_COLOR + "Title updated!" + ColorConstants.RESET);
                    } else {
                        System.out.println(ColorConstants.ERROR_COLOR + "Title cannot be empty. Update cancelled."
                                + ColorConstants.RESET);
                    }
                    break;
                case 2:
                    System.out.print(
                            ColorConstants.MENU_COLOR + "Enter new description (current: "
                                    + updatedBook.getDescription() + "): "
                                    + ColorConstants.RESET);
                    String newDescription = scanner.nextLine();
                    if (!newDescription.trim().isEmpty()) {
                        updatedBook.setDescription(newDescription);
                        hasChanges = true;
                        System.out
                                .println(ColorConstants.SUCCESS_COLOR + "Description updated!" + ColorConstants.RESET);
                    } else {
                        System.out.println(ColorConstants.ERROR_COLOR
                                + "Description cannot be empty. Update cancelled." + ColorConstants.RESET);
                    }
                    break;
                case 3:
                    System.out.print(ColorConstants.MENU_COLOR + "Enter new ISBN (current: " + updatedBook.getIsbn()
                            + "): " + ColorConstants.RESET);
                    String newIsbn = scanner.nextLine();
                    if (!newIsbn.trim().isEmpty()) {
                        updatedBook.setIsbn(newIsbn);
                        hasChanges = true;
                        System.out.println(ColorConstants.SUCCESS_COLOR + "ISBN updated!" + ColorConstants.RESET);
                    } else {
                        System.out.println(ColorConstants.ERROR_COLOR + "ISBN cannot be empty. Update cancelled."
                                + ColorConstants.RESET);
                    }
                    break;
                case 4:
                    System.out.println(ColorConstants.MENU_COLOR + "Current authors: "
                            + getAuthorsString(updatedBook.getAuthors()) + ColorConstants.RESET);
                    System.out.print(ColorConstants.MENU_COLOR + "Do you want to replace all authors? (yes/no): "
                            + ColorConstants.RESET);
                    String replaceAuthors = scanner.nextLine();
                    if (replaceAuthors.equalsIgnoreCase("y") || replaceAuthors.equalsIgnoreCase("yes")) {
                        List<Author> authors = new ArrayList<>();
                        int authorCount = getValidPositiveIntegerInput("Enter number of authors: ",
                                "Number of authors must be a positive integer!");

                        for (int i = 0; i < authorCount; i++) {
                            String authorName = getValidStringInput("Enter author " + (i + 1) + " name: ",
                                    "Author name cannot be empty!");
                            authors.add(new Author(authorName));
                        }
                        updatedBook.setAuthors(authors);
                        hasChanges = true;
                        System.out.println(ColorConstants.SUCCESS_COLOR + "Authors updated!" + ColorConstants.RESET);
                    } else {
                        int addAuthorCount = getValidPositiveIntegerInput("How many authors do you want to add?: ",
                                "Number must be a positive integer!");

                        if (addAuthorCount > 0) {
                            for (int i = 0; i < addAuthorCount; i++) {
                                String authorName = getValidStringInput("Enter new author " + (i + 1) + " name: ",
                                        "Author name cannot be empty!");
                                updatedBook.addAuthor(new Author(authorName));
                            }
                            hasChanges = true;
                            System.out.println(ColorConstants.SUCCESS_COLOR + "Authors added!" + ColorConstants.RESET);
                        }
                    }
                    break;
                case 5:
                    System.out.println(
                            ColorConstants.MENU_COLOR + "Current genres: " + getGenresString(updatedBook.getGenres())
                                    + ColorConstants.RESET);
                    System.out.print(ColorConstants.MENU_COLOR + "Do you want to replace all genres? (yes/no): "
                            + ColorConstants.RESET);
                    String replaceGenres = scanner.nextLine();
                    if (replaceGenres.equalsIgnoreCase("y") || replaceGenres.equalsIgnoreCase("yes")) {
                        List<Genre> genres = new ArrayList<>();
                        int genreCount = getValidPositiveIntegerInput("Enter number of genres: ",
                                "Number of genres must be a positive integer!");

                        for (int i = 0; i < genreCount; i++) {
                            String genreName = getValidStringInput("Enter genre " + (i + 1) + " name: ",
                                    "Genre name cannot be empty!");
                            genres.add(new Genre(genreName));
                        }
                        updatedBook.setGenres(genres);
                        hasChanges = true;
                        System.out.println(ColorConstants.SUCCESS_COLOR + "Genres updated!" + ColorConstants.RESET);
                    } else {
                        int addGenreCount = getValidPositiveIntegerInput("How many genres do you want to add?: ",
                                "Number must be a positive integer!");

                        if (addGenreCount > 0) {
                            for (int i = 0; i < addGenreCount; i++) {
                                String genreName = getValidStringInput("Enter new genre " + (i + 1) + " name: ",
                                        "Genre name cannot be empty!");
                                updatedBook.addGenre(new Genre(genreName));
                            }
                            hasChanges = true;
                            System.out.println(ColorConstants.SUCCESS_COLOR + "Genres added!" + ColorConstants.RESET);
                        }
                    }
                    break;
                default:
                    System.out.println(ColorConstants.ERROR_COLOR
                            + "Error: Invalid option! Please choose a number from 0-5." + ColorConstants.RESET);
            }
        }

        if (!hasChanges) {
            System.out.println(ColorConstants.OPTION_COLOR + "No changes made to the book." + ColorConstants.RESET);
            return;
        }

        System.out.println(
                ColorConstants.BOLD + ColorConstants.MENU_COLOR + "\n=== Final book data ===" + ColorConstants.RESET);
        System.out.println(updatedBook);
        System.out.print(ColorConstants.OPTION_COLOR + "\nAre you sure you want to save these changes? (yes/no): "
                + ColorConstants.RESET);
        String confirmation = scanner.nextLine();

        if (confirmation.equalsIgnoreCase("yes") || confirmation.equalsIgnoreCase("y")) {
            try {
                bookController.updateBook(updatedBook);
                System.out
                        .println(ColorConstants.SUCCESS_COLOR + "Book updated successfully! ✅" + ColorConstants.RESET);
            } catch (Exception e) {
                System.out.println(ColorConstants.ERROR_COLOR + "Error: Failed to update book. " + e.getMessage()
                        + ColorConstants.RESET);
            }
        } else {
            System.out.println(ColorConstants.OPTION_COLOR + "Changes cancelled." + ColorConstants.RESET);
        }
    }

    private void deleteBook() {
        System.out.println(
                ColorConstants.BOLD + ColorConstants.MENU_COLOR + "\n=== Delete Book ===" + ColorConstants.RESET);
        showAllBooks();
        System.out.println();

        Long id = getValidLongInput("Enter book ID to delete: ");

        Book bookToDelete;
        try {
            bookToDelete = bookController.findByID(id);
            if (bookToDelete == null) {
                System.out.println(ColorConstants.ERROR_COLOR + "Error: Book with ID " + id + " not found."
                        + ColorConstants.RESET);
                return;
            }
        } catch (Exception e) {
            System.out.println(ColorConstants.ERROR_COLOR + "Error: Failed to find book. " + e.getMessage()
                    + ColorConstants.RESET);
            return;
        }

        System.out.println(ColorConstants.OPTION_COLOR + "\nBook to be deleted:" + ColorConstants.RESET);
        System.out.println(bookToDelete);

        System.out.print(ColorConstants.ERROR_COLOR + "\nAre you sure you want to delete this book? (yes/no): "
                + ColorConstants.RESET);
        String confirmation = scanner.nextLine();

        if (confirmation.equalsIgnoreCase("y") || confirmation.equalsIgnoreCase("yes")) {
            try {
                bookController.deleteBook(id);
                System.out
                        .println(ColorConstants.SUCCESS_COLOR + "Book deleted successfully! ✅" + ColorConstants.RESET);
            } catch (Exception e) {
                System.out.println(ColorConstants.ERROR_COLOR + "Error: Failed to delete book. " + e.getMessage()
                        + ColorConstants.RESET);
            }
        } else {
            System.out.println(ColorConstants.OPTION_COLOR + "Deletion cancelled." + ColorConstants.RESET);
        }
    }

    private void searchByTitle() {
        System.out.println(ColorConstants.BOLD + ColorConstants.MENU_COLOR + "\n=== Search by Title 🔍 ==="
                + ColorConstants.RESET);
        String title = getValidStringInput("Enter title to search: ", "Search title cannot be empty!");

        try {
            List<Book> books = bookController.findByTitle(title);

            if (books.isEmpty()) {
                System.out.println(ColorConstants.OPTION_COLOR + "No books found with title containing: \"" + title
                        + "\"" + ColorConstants.RESET);
            } else {
                System.out.println(
                        ColorConstants.SUCCESS_COLOR + "Found " + books.size() + " book(s):" + ColorConstants.RESET);
                for (Book book : books) {
                    System.out.println(book.toStringWithDescription());
                }
            }
        } catch (Exception e) {
            System.out.println(ColorConstants.ERROR_COLOR + "Error: Failed to search books. " + e.getMessage()
                    + ColorConstants.RESET);
        }
    }

    private void searchByAuthor() {
        System.out.println(ColorConstants.BOLD + ColorConstants.MENU_COLOR + "\n=== Search by Author 👤 ==="
                + ColorConstants.RESET);
        String author = getValidStringInput("Enter author name to search: ", "Author name cannot be empty!");

        try {
            List<Book> books = bookController.findByAuthor(author);

            if (books.isEmpty()) {
                System.out.println(ColorConstants.OPTION_COLOR + "No books found by author: \"" + author + "\""
                        + ColorConstants.RESET);
            } else {
                System.out.println(ColorConstants.SUCCESS_COLOR + "Found " + books.size() + " book(s) by \"" + author
                        + "\":" + ColorConstants.RESET);
                for (Book book : books) {
                    System.out.println(book.toStringWithDescription());
                }
            }
        } catch (Exception e) {
            System.out.println(ColorConstants.ERROR_COLOR + "Error: Failed to search books. " + e.getMessage()
                    + ColorConstants.RESET);
        }
    }

    private void searchByGenre() {
        System.out.println(ColorConstants.BOLD + ColorConstants.MENU_COLOR + "\n=== Search by Genre 🏷️ ==="
                + ColorConstants.RESET);
        String genre = getValidStringInput("Enter genre to search: ", "Genre name cannot be empty!");

        try {
            List<Book> books = bookController.findByGenre(genre);

            if (books.isEmpty()) {
                System.out.println(ColorConstants.OPTION_COLOR + "No books found in genre: \"" + genre + "\""
                        + ColorConstants.RESET);
            } else {
                System.out.println(ColorConstants.SUCCESS_COLOR + "Found " + books.size() + " book(s) in genre \""
                        + genre + "\":" + ColorConstants.RESET);
                for (Book book : books) {
                    System.out.println(book);
                }
            }
        } catch (Exception e) {
            System.out.println(ColorConstants.ERROR_COLOR + "Error: Failed to search books. " + e.getMessage()
                    + ColorConstants.RESET);
        }
    }

    private String getValidStringInput(String prompt, String errorMessage) {
        while (true) {
            System.out.print(ColorConstants.MENU_COLOR + prompt + ColorConstants.RESET);
            String input = scanner.nextLine().trim();
            if (!input.isEmpty()) {
                return input;
            }
            System.out.println(ColorConstants.ERROR_COLOR + "Error: " + errorMessage + ColorConstants.RESET);
        }
    }

    private int getValidPositiveIntegerInput(String prompt, String errorMessage) {
        while (true) {
            System.out.print(ColorConstants.MENU_COLOR + prompt + ColorConstants.RESET);
            try {
                int value = scanner.nextInt();
                scanner.nextLine();
                if (value > 0) {
                    return value;
                } else {
                    System.out.println(
                            ColorConstants.ERROR_COLOR + "Error: Number must be at least 1!" + ColorConstants.RESET);
                }
            } catch (InputMismatchException e) {
                System.out.println(ColorConstants.ERROR_COLOR + "Error: " + errorMessage + ColorConstants.RESET);
                scanner.nextLine();
            }
        }
    }

    private Long getValidLongInput(String prompt) {
        while (true) {
            System.out.print(ColorConstants.MENU_COLOR + prompt + ColorConstants.RESET);
            try {
                Long value = scanner.nextLong();
                scanner.nextLine();
                return value;
            } catch (InputMismatchException e) {
                System.out.println(ColorConstants.ERROR_COLOR + "Error: " + "Please enter a valid book ID!"
                        + ColorConstants.RESET);
                scanner.nextLine();
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
