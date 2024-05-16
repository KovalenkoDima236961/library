package com.example.loginpage.controller.factoryPattern;

import com.example.loginpage.module.books.Book;
import com.example.loginpage.module.books.typeofbooks.*;

/**
 * The BookFactory class provides a factory method for creating instances of Book subclasses based on a given genre.
 */
public class BookFactory {
    /**
     * Default constructor for the BookFactory class.
     */
    public BookFactory(){}

    /**
     * Creates a new Book instance based on the provided parameters.
     *
     * @param id          The unique identifier of the book.
     * @param genre       The genre of the book. Supported genres include "Fiction", "Drama", "Adventure", "Allegorical",
     *                    "Autobiography", and "Family".
     * @param title       The title of the book.
     * @param author      The author of the book.
     * @param year        The year of publication of the book.
     * @param description A brief description of the book.
     * @param imageSrc    The URL or file path of the book's cover image.
     * @param votes       The number of votes or ratings received by the book.
     * @param some        Any additional information specific to the book.
     * @return A new instance of a subclass of Book based on the specified genre.
     * @throws IllegalArgumentException If the provided genre is not supported.
     */
    public static Book createBook(int id,String genre, String title, String author, int year, String description, String imageSrc, int votes,String some) {
        return switch (genre) {
            case "Fiction" -> new FictionBook(id, title, author, year, genre, description, imageSrc, votes, some);
            case "Drama" -> new DramaBook(id, title, author, year, genre, description, imageSrc, votes, some);
            case "Adventure" -> new AdventureBook(id, title, author, year, genre, description, imageSrc, votes, some);
            case "Allegorical" ->
                    new AllegoricalBook(id, title, author, year, genre, description, imageSrc, votes, some);
            case "Autobiography" ->
                    new Autobiography(id, title, author, year, genre, description, imageSrc, votes, some);
            case "Family" -> new FamilyBook(id, title, author, year, genre, description, imageSrc, votes, true);
            default -> throw new IllegalArgumentException("Genre not supported");
        };
    }
}
