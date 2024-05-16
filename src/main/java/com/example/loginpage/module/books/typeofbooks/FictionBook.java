package com.example.loginpage.module.books.typeofbooks;

import com.example.loginpage.module.books.Book;
import com.example.loginpage.module.users.User;

/**
 * Represents a specific type of book, specifically fiction, extending the generic Book class.
 * This class extends {@link Book},includes additional attributes specific to fiction books.
 */
public non-sealed class FictionBook extends Book {
    /** The specific type of fiction of the book. */
    private String fictionType;
    /**
     * Constructs a new FictionBook with specified details.
     * This constructor initializes all fields including the specific type of fiction.
     * @param id Unique identifier for the book.
     * @param title Title of the book.
     * @param author Author of the book.
     * @param year Publication year of the book.
     * @param genre Genre of the book, typically a subgenre of Fiction.
     * @param description A brief description of the book.
     * @param imageSrc Image source URL for the book cover.
     * @param votes Number of votes or ratings the book has received.
     * @param fictionType Specific type of fiction.
     */
    public FictionBook(int id,String title, String author, int year, String genre, String description, String imageSrc, int votes, String fictionType) {
        super(id,title, author, year, genre, description, imageSrc, votes);
        this.fictionType = fictionType;
    }
    /**
     * Default constructor for FictionBook.
     * Initializes a new instance without setting properties, used when properties will be set separately.
     */
    public FictionBook(){}
    /**
     * Returns the specific type of fiction of the book.
     * @return The fiction type.
     */
    public String getFictionType() {
        return fictionType;
    }
    /**
     * Sets the specific type of fiction for the book.
     * @param fictionType New type of fiction.
     */
    public void setFictionType(String fictionType) {
        this.fictionType = fictionType;
    }
    /**
     * Prints detailed information about the fiction book, including common book info and specific fiction type.
     */
    @Override
    public void printInfo() {
        super.printInfo(); // Call superclass method to print common info
        System.out.println("Type: Fiction");
        System.out.println("Fiction Type: " + fictionType);
    }
    /**
     * Returns a string representation of the fiction book including all common book details and the fiction type.
     * @return String representation of the fiction book.
     */
    @Override
    public String toString() {
        return super.toString() + " FictionType: " + fictionType;
    }
}
