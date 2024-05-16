package com.example.loginpage.module.users.typeofuser.typeOfClient;

import com.example.loginpage.module.books.Book;
import com.example.loginpage.module.users.typeofuser.Client;

/**
 * Represents a client who is not restricted from making something or any other client-specific activities.
 * This class extends {@link Client} to encapsulate functionalities for clients in good standing.
 */
public class NotBannedClient extends Client {

    /**
     * Default constructor for creating a NotBannedClient with default settings inherited from the {@link Client} superclass.
     * This constructor initializes a client with no predefined information, relying on the superclass for default initialization.
     */
    public NotBannedClient() {
        super();
    }

    /**
     * Constructs a new NotBannedClient with detailed user information, without administrative privileges.
     * This calls the constructor of the superclass {@link Client} to set up the client's basic profile.
     *
     * @param name Client's full name.
     * @param email Client's email address.
     * @param phone Client's phone number.
     * @param password Client's password for authentication.
     */
    public NotBannedClient(String name, String email, String phone, String password) {
        super(name, email, phone, password, false); // All NotBannedClients are initialized without admin rights
    }

    /**
     * Allows a not-banned client to purchase a book. This method enhances the base class purchase method by thanking the client,
     * suggesting an implementation where purchase tracking or rewards could be handled.
     *
     * @param book The book to be purchased by the client.
     */
    @Override
    public void purchaseBook(Book book) {
        super.purchaseBook(book);  // Calls the base class method to handle the purchase
        System.out.println("Thank you for your purchase, " + getName() + "!");
    }

    /**
     * Provides a string representation of the NotBannedClient, appending the "Not Banned" status to the output of the superclass's toString method.
     * This helps in quickly identifying the client's status in logs or user interfaces.
     *
     * @return A string representation of the NotBannedClient, indicating they are not subject to any bans.
     */
    @Override
    public String toString() {
        return super.toString() + " - Not Banned";
    }
}
