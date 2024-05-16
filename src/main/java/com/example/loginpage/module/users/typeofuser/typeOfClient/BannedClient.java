package com.example.loginpage.module.users.typeofuser.typeOfClient;

import com.example.loginpage.module.books.Book;
import com.example.loginpage.module.users.typeofuser.Client;

/**
 * Represents a customer who has been banned from making purchases.
 * This class extends {@link Client} to add functionality specific to dealing with banned customers,
 * including the ability to track the reason for the ban and whether the ban is permanent.
 */
public class BannedClient extends Client {
    /**
     * Indicates whether the ban on this client is permanent.
     */
    private boolean isPermanentlyBanned;

    /**
     * The reason why the client was banned.
     */
    private String reasonForBan;

    /**
     * Constructs a new BannedClient with detailed user information, the reason for the ban, and its permanency status.
     * Initializes the client as non-admin by default.
     *
     * @param name The full name of the client.
     * @param email The email address of the client.
     * @param phone The phone number of the client.
     * @param password The password for the client's account.
     * @param reasonForBan The reason why the client is banned.
     * @param isPermanentlyBanned A boolean flag indicating whether the ban is permanent.
     */
    public BannedClient(String name, String email, String phone, String password, String reasonForBan, boolean isPermanentlyBanned) {
        super(name, email, phone, password, false);  // Passes false for isAdmin, assuming banned clients cannot have admin privileges
        this.reasonForBan = reasonForBan;
        this.isPermanentlyBanned = isPermanentlyBanned;
    }

    /**
     * Overrides the purchaseBook method to deny purchase attempts, providing feedback that the client is banned.
     * @param book The book that the client attempts to purchase.
     */
    @Override
    public void purchaseBook(Book book) {
        System.out.println("Purchase denied. This account is banned. Reason: " + reasonForBan);
    }

    /**
     * Provides a string representation of the BannedClient, appending "(Banned)" to the standard client representation.
     * @return A string representation of the BannedClient including the banned status.
     */
    @Override
    public String toString() {
        return super.toString() + " (Banned)";
    }

    /**
     * Checks if the ban on the client is permanent.
     * @return true if the ban is permanent, otherwise false.
     */
    public boolean isPermanentlyBanned() {
        return isPermanentlyBanned;
    }

    /**
     * Sets the permanency of the client's ban.
     * @param permanentlyBanned A boolean indicating the new permanency status of the ban.
     */
    public void setPermanentlyBanned(boolean permanentlyBanned) {
        isPermanentlyBanned = permanentlyBanned;
    }

    /**
     * Retrieves the reason for the client's ban.
     * @return The reason for the ban.
     */
    public String getReasonForBan() {
        return reasonForBan;
    }

    /**
     * Sets the reason for the client's ban.
     * @param reasonForBan The new reason for the ban.
     */
    public void setReasonForBan(String reasonForBan) {
        this.reasonForBan = reasonForBan;
    }
}
