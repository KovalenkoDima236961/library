package com.example.loginpage.module.users.typeofuser;

import com.example.loginpage.module.books.Book;
import com.example.loginpage.module.users.User;
/**
 * Represents a client user in the system. Client users usually do not have administrative privileges.
 * This class extends {@link User},providing all available functionality.
 */
public class Client extends User {
    /**
     * Default constructor that initializes a new client with default settings.
     */
    public Client(){}
    /**
     * Constructs a new client with detailed user information and admin status.
     * @param name Client's full name.
     * @param email Client's email address.
     * @param phone Client's phone number.
     * @param password Client's password for authentication.
     * @param isAdmin Indicates whether the client should have administrative privileges (typically false for clients).
     */
    public Client(String name, String email, String phone, String password, boolean isAdmin) {
        super(name, email, phone, password, isAdmin);
    }

    /**
     * Purchases a book and prints out a confirmation.
     * @param book The book to be purchased by the client.
     */
    public void purchaseBook(Book book) {
        System.out.println("Book purchased: " + book.getTitle());
    }
    /**
     * Provides a string representation of the client, primarily showing the client's name.
     * @return The name of the client.
     */
    @Override
    public String toString() {
        return getName();
    }
}

