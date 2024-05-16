package com.example.loginpage.module.books.typeofbooks;

import com.example.loginpage.module.books.Book;

/**
 * Represents a book classified under the allegorical genre that employs allegory as a primary narrative device.
 * This non-sealed class can be further extended to accommodate more specific types of allegorical works.
 * This class extends {@link Book}, It adds a property to store the type of allegory used, enhancing its narrative and thematic depth.
 */
public non-sealed class AllegoricalBook extends Book {
    /**
     * The type of allegory employed in the book, describing the symbolic narrative technique used.
     */
    private String allegoryType;

    /**
     * Constructs a new AllegoricalBook with detailed book information and the type of allegory employed.
     * Initializes all fields with the provided values, highlighting the allegorical narrative technique.
     *
     * @param id The unique identifier for the book.
     * @param title The title of the book.
     * @param author The author of the book.
     * @param year The publication year of the book.
     * @param genre The genre of the book, typically set to "Allegorical".
     * @param description A brief description of the book, outlining its allegorical elements.
     * @param imageSrc A URL or path to an image of the book's cover.
     * @param votes The number of votes or ratings the book has received.
     * @param allegoryType A description of the specific type of allegory used in the book.
     */
    public AllegoricalBook(int id, String title, String author, int year, String genre, String description, String imageSrc, int votes, String allegoryType) {
        super(id, title, author, year, genre, description, imageSrc, votes);
        this.allegoryType = allegoryType;
    }

    /**
     * Default constructor for creating an AllegoricalBook with uninitialized fields.
     * This constructor sets up a new AllegoricalBook instance without initializing its properties, including the allegory type.
     */
    public AllegoricalBook() {
        super();
    }

    /**
     * Retrieves the type of allegory used in the book.
     *
     * @return The type of allegory employed.
     */
    public String getAllegoryType() {
        return allegoryType;
    }

    /**
     * Sets the type of allegory used in the book.
     *
     * @param allegoryType The new type of allegory to be used in the book.
     */
    public void setAllegoryType(String allegoryType) {
        this.allegoryType = allegoryType;
    }

    /**
     * Prints detailed information about the AllegoricalBook, including its general information and the type of allegory employed.
     * This method overrides the {@code printInfo} method from the {@code Book} superclass to add specific information about the allegorical genre.
     */
    @Override
    public void printInfo() {
        super.printInfo(); // Call superclass method to print common info
        System.out.println("Type: Allegorical");
        System.out.println("Allegory Type: " + allegoryType);
    }

    /**
     * Provides a string representation of the AllegoricalBook, including the type of allegory used.
     * This method appends the allegory type information to the string representation provided by the {@code Book} superclass.
     *
     * @return A string representation of the AllegoricalBook, including details about the type of allegory employed.
     */
    @Override
    public String toString() {
        return super.toString() + " AllegoryType: " + allegoryType;
    }
}

