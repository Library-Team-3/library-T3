package org.LT3;

import org.LT3.config.DatabaseInitializer;
import org.LT3.controller.BookController;
import org.LT3.repository.MySqlBookRepository;
import org.LT3.view.BookView;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        try {
            DatabaseInitializer.initialize();

            MySqlBookRepository bookRepository = new MySqlBookRepository();
            BookController bookController = new BookController(bookRepository);
            Scanner scanner = new Scanner(System.in);
            BookView bookView = new BookView(bookController, scanner);

            System.out.println("Welcome to the library!");
            bookView.showMenu();

            scanner.close();
            System.out.println("Goodbye!");

        } catch (Exception e) {
            System.err.println("Error while starting the application: " + e.getMessage());
        }
    }
}
