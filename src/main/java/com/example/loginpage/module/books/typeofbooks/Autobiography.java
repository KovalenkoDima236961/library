package com.example.loginpage.module.books.typeofbooks;

import com.example.loginpage.module.books.Book;

/**
 * Represents a book classified under the autobiography genre that focuses on the life of a particular subject.
 * This non-sealed class can be further extended to accommodate more specific types of autobiographical works.
 * This class extends {@link Book},It adds a property to store the name of the subject of the autobiography, enhancing its narrative focus.
 */
public non-sealed class Autobiography extends Book {
    /**
     * The name of the subject whose life story is detailed in the autobiography.
     */
    private String subject;

    /**
     * Constructs a new Autobiography with detailed book information and the subject's name.
     * Initializes all fields with the provided values, highlighting the focus on the subject's life story.
     *
     * @param id The unique identifier for the book.
     * @param title The title of the book.
     * @param author The author of the book, typically the subject or a close associate.
     * @param year The publication year of the book.
     * @param genre The genre of the book, typically set to "Autobiography".
     * @param description A brief description of the book, often outlining key events in the subject’s life.
     * @param imageSrc A URL or path to an image of the book's cover.
     * @param votes The number of votes or ratings the book has received.
     * @param subject The name of the subject of the autobiography.
     */
    public Autobiography(int id, String title, String author, int year, String genre, String description, String imageSrc, int votes, String subject) {
        super(id, title, author, year, genre, description, imageSrc, votes);
        this.subject = subject;
    }

    /**
     * Default constructor for creating an Autobiography with uninitialized fields.
     * This constructor sets up a new Autobiography instance without initializing its properties, including the subject.
     */
    public Autobiography() {
        super();
    }

    /**
     * Retrieves the name of the subject of the autobiography.
     *
     * @return The name of the subject.
     */
    public String getSubject() {
        return subject;
    }

    /**
     * Sets the name of the subject of the autobiography.
     *
     * @param subject The new name for the subject of the autobiography.
     */
    public void setSubject(String subject) {
        this.subject = subject;
    }

    /**
     * Prints detailed information about the Autobiography, including its general information and the subject's name.
     * This method overrides the {@code printInfo} method from the {@code Book} superclass to add specific information about the autobiography genre.
     */
    @Override
    public void printInfo() {
        super.printInfo(); // Call superclass method to print common info
        System.out.println("Type: Autobiography");
        System.out.println("Subject: " + subject);
    }

    /**
     * Provides a string representation of the Autobiography, including the subject's name.
     * This method appends the subject information to the string representation provided by the {@code Book} superclass.
     *
     * @return A string representation of the Autobiography, including the name of the subject.
     */
    @Override
    public String toString() {
        return super.toString() + " Subject: " + subject;
    }
}

