package com.example.loginpage.module.books.typeofbooks;

import com.example.loginpage.module.books.Book;

/**
 * Represents a book classified under the family genre that may include additional suitability criteria for children.
 * This non-sealed class can be further extended to accommodate more specific types of family books.
 * This class extends {@link Book},It adds a property to determine whether the book is appropriate for younger readers.
 */
public non-sealed class FamilyBook extends Book {
    /**
     * Indicates whether the book is considered suitable for children.
     */
    private boolean suitableForChildren;

    /**
     * Constructs a new FamilyBook with detailed book information and suitability for children.
     * Initializes all fields with the provided values, including the suitability flag.
     *
     * @param id The unique identifier for the book.
     * @param title The title of the book.
     * @param author The author of the book.
     * @param year The publication year of the book.
     * @param genre The genre of the book.
     * @param description A brief description of the book.
     * @param imageSrc A URL or path to an image of the book's cover.
     * @param votes The number of votes or ratings the book has received.
     * @param suitableForChildren A boolean indicating whether the book is suitable for children.
     */
    public FamilyBook(int id, String title, String author, int year, String genre, String description, String imageSrc, int votes, boolean suitableForChildren) {
        super(id, title, author, year, genre, description, imageSrc, votes);
        this.suitableForChildren = suitableForChildren;
    }

    /**
     * Default constructor for creating a FamilyBook with uninitialized fields.
     * This constructor sets up a new FamilyBook instance without initializing its properties.
     */
    public FamilyBook() {
        super();
    }

    /**
     * Returns whether the book is suitable for children.
     *
     * @return true if the book is suitable for children, otherwise false.
     */
    public boolean isSuitableForChildren() {
        return suitableForChildren;
    }

    /**
     * Sets the suitability of the book for children.
     *
     * @param suitableForChildren A boolean indicating whether the book should be suitable for children.
     */
    public void setSuitableForChildren(boolean suitableForChildren) {
        this.suitableForChildren = suitableForChildren;
    }

    /**
     * Prints detailed information about the FamilyBook, including its general information and its suitability for children.
     * This method overrides the {@code printInfo} method from the {@code Book} superclass to add additional information
     * about the book's suitability for children.
     */
    @Override
    public void printInfo() {
        super.printInfo(); // Call superclass method to print common info
        System.out.println("Type: Family");
        System.out.println("Suitable for Children: " + (suitableForChildren ? "Yes" : "No"));
    }

    /**
     * Provides a string representation of the FamilyBook, including its suitability for children.
     * This method appends the suitability information to the string representation provided by the {@code Book} superclass.
     *
     * @return A string representation of the FamilyBook, including details about its suitability for children.
     */
    @Override
    public String toString() {
        return super.toString() + " SuitableForChildren: " + suitableForChildren;
    }
}
