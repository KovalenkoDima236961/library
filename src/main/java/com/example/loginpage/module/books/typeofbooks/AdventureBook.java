package com.example.loginpage.module.books.typeofbooks;

import com.example.loginpage.module.books.Book;

/**
 * Represents a book classified under the adventure genre, primarily focused on the exploits and experiences of a central character.
 * This non-sealed class can be further extended to accommodate more specific types of adventure books.
 * This class extends {@link Book},It adds a property to store the name of the main character, highlighting the adventure narrative centered around this figure.
 */
public non-sealed class AdventureBook extends Book {
    /**
     * The name of the main character in the adventure book, around whom the plot typically revolves.
     */
    private String mainCharacter;

    /**
     * Constructs a new AdventureBook with detailed book information, including the main character's name.
     * Initializes all fields with the provided values, enhancing the thematic focus on adventure and exploration.
     *
     * @param id The unique identifier for the book.
     * @param title The title of the book.
     * @param author The author of the book.
     * @param year The publication year of the book.
     * @param genre The genre of the book, typically set to "Adventure".
     * @param description A brief description of the book, emphasizing the adventurous elements.
     * @param imageSrc A URL or path to an image of the book's cover.
     * @param votes The number of votes or ratings the book has received.
     * @param mainCharacter The name of the main character central to the adventure.
     */
    public AdventureBook(int id, String title, String author, int year, String genre, String description, String imageSrc, int votes, String mainCharacter) {
        super(id, title, author, year, genre, description, imageSrc, votes);
        this.mainCharacter = mainCharacter;
    }

    /**
     * Default constructor for creating an AdventureBook with uninitialized fields.
     * This constructor sets up a new AdventureBook instance without initializing its properties, including the main character.
     */
    public AdventureBook() {
        super();
    }

    /**
     * Retrieves the name of the main character in the adventure book.
     *
     * @return The name of the main character.
     */
    public String getMainCharacter() {
        return mainCharacter;
    }

    /**
     * Sets the name of the main character in the adventure book.
     *
     * @param mainCharacter The new name for the main character.
     */
    public void setMainCharacter(String mainCharacter) {
        this.mainCharacter = mainCharacter;
    }

    /**
     * Prints detailed information about the AdventureBook, including its general information and the main character's name.
     * This method overrides the {@code printInfo} method from the {@code Book} superclass to add specific information about the adventure genre.
     */
    @Override
    public void printInfo() {
        super.printInfo(); // Call superclass method to print common info
        System.out.println("Type: Adventure");
        System.out.println("Main Character: " + mainCharacter);
    }

    /**
     * Provides a string representation of the AdventureBook, including the main character's name.
     * This method appends the main character information to the string representation provided by the {@code Book} superclass.
     *
     * @return A string representation of the AdventureBook, including details about the main character.
     */
    @Override
    public String toString() {
        return super.toString() + " MainCharacter: " + mainCharacter;
    }
}
