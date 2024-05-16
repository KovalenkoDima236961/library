package com.example.loginpage.module.books.typeofbooks;

import com.example.loginpage.module.books.Book;

/**
 * Represents a book classified under the drama genre that focuses on a particular main character.
 * This non-sealed class can be further extended to accommodate more specific types of drama books.
 * This class extends {@link Book},It adds a property to store the name of the main character of the story, distinguishing it in the context of dramatic narratives
 */
public non-sealed class DramaBook extends Book {
    /**
     * The name of the main character in the drama book.
     */
    private String mainCharacter;

    /**
     * Constructs a new DramaBook with detailed book information and the main character's name.
     * Initializes all fields with the provided values.
     *
     * @param id The unique identifier for the book.
     * @param title The title of the book.
     * @param author The author of the book.
     * @param year The publication year of the book.
     * @param genre The genre of the book, typically set to "Drama".
     * @param description A brief description of the book.
     * @param imageSrc A URL or path to an image of the book's cover.
     * @param votes The number of votes or ratings the book has received.
     * @param mainCharacter The name of the main character central to the drama narrative.
     */
    public DramaBook(int id, String title, String author, int year, String genre, String description, String imageSrc, int votes, String mainCharacter) {
        super(id, title, author, year, genre, description, imageSrc, votes);
        this.mainCharacter = mainCharacter;
    }

    /**
     * Default constructor for creating a DramaBook with uninitialized fields.
     * This constructor sets up a new DramaBook instance without initializing its properties, including the main character.
     */
    public DramaBook() {
        super();
    }

    /**
     * Retrieves the name of the main character in the drama book.
     *
     * @return The name of the main character.
     */
    public String getMainCharacter() {
        return mainCharacter;
    }

    /**
     * Sets the name of the main character in the drama book.
     *
     * @param mainCharacter The new name for the main character.
     */
    public void setMainCharacter(String mainCharacter) {
        this.mainCharacter = mainCharacter;
    }

    /**
     * Prints detailed information about the DramaBook, including its general information and the main character's name.
     * This method overrides the {@code printInfo} method from the {@code Book} superclass to add specific information about the drama genre.
     */
    @Override
    public void printInfo() {
        super.printInfo(); // Call superclass method to print common info
        System.out.println("Type: Drama");
        System.out.println("Main Character: " + mainCharacter);
    }

    /**
     * Provides a string representation of the DramaBook, including the main character's name.
     * This method appends the main character information to the string representation provided by the {@code Book} superclass.
     *
     * @return A string representation of the DramaBook, including the name of the main character.
     */
    @Override
    public String toString() {
        return super.toString() + " MainCharacter: " + mainCharacter;
    }
}