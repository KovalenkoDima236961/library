package com.example.loginpage.module.books;

import com.example.loginpage.module.books.typeofbooks.*;

import java.io.Serializable;

/**
 * An abstract base class for a book that provides a common structure and functionality for all book types.
 * This class includes basic book metadata and support for serialisation.
 */
abstract sealed public class Book implements Serializable permits FictionBook, FamilyBook, DramaBook, Autobiography, AllegoricalBook,AdventureBook {
    private static final long serialVersionUID = 1L; // Recommended to include
    /** The title of the book. */
    private String title;
    /** The author of the book. */
    private String author;
    /** The publication year of the book. */
    private int year;
    /** The genre of the book. */
    private String genre;
    /** A brief description of the book. */
    private String description;
    /** The URL of the image representing the book cover. */
    private String imageSrc;
    /** The number of votes or ratings the book has received. */
    private int votes;
    /** The unique identifier for the book. */
    private int id;

    // Static variable to track the count of books created.
    private static int COUNT_OF_BOOKS = 1;
    {
        id = COUNT_OF_BOOKS + 1;
    }
    /**
     * Default constructor for creating a book object without initializing fields.
     */
    public Book(){}
    /**
     * Constructs a new book with specified details.
     * @param id Unique identifier for the book.
     * @param title Title of the book.
     * @param author Author of the book.
     * @param year Publication year of the book.
     * @param genre Genre of the book.
     * @param description A brief description of the book.
     * @param imageSrc Image source URL for the book cover.
     * @param votes Number of votes or ratings the book has received.
     */
    public Book(int id,String title, String author, int year, String genre, String description,String imageSrc,int votes) {
        this.id = id;
        this.title = title;
        this.author = author;
        this.year = year;
        this.genre = genre;
        this.description = description;
        this.imageSrc = imageSrc;
        this.votes  = votes;
        COUNT_OF_BOOKS++;
    }
    /**
     * Returns the total count of books created.
     * @return Total number of books.
     */
    public static int getCountOfBooks() {
        return COUNT_OF_BOOKS;
    }
    /**
     * Sets the ID of the book.
     * @param id New ID for the book.
     */
    public void setId(int id) {
        this.id = id;
    }
    /**
     * Returns the ID of the book.
     * @return Book's ID.
     */
    public int getId() {
        return id;
    }
    /**
     * Returns the number of votes for the book.
     * @return Number of votes.
     */
    public int getVotes() {
        return votes;
    }
    /**
     * Sets the number of votes for the book.
     * @param votes New vote count.
     */
    public void setVotes(int votes) {
        this.votes = votes;
    }
    /**
     * Sets the image source URL for the book cover.
     * @param imageSrc New image source URL.
     */
    public void setImageSrc(String imageSrc) {
        this.imageSrc = imageSrc;
    }
    /**
     * Returns the image source URL for the book cover.
     * @return Image source URL.
     */
    public String getImageSrc() {
        return imageSrc;
    }
    /**
     * Returns the title of the book.
     * @return Book's title.
     */
    public String getTitle() {
        return title;
    }
    /**
     * Sets the title of the book.
     * @param title New title for the book.
     */
    public void setTitle(String title) {
        this.title = title;
    }
    /**
     * Returns the author of the book.
     * @return Book's author.
     */
    public String getAuthor() {
        return author;
    }
    /**
     * Sets the author of the book.
     * @param author New author for the book.
     */
    public void setAuthor(String author) {
        this.author = author;
    }
    /**
     * Returns the publication year of the book.
     * @return Publication year.
     */
    public int getYear() {
        return year;
    }
    /**
     * Sets the publication year of the book.
     * @param year New publication year.
     */
    public void setYear(int year) {
        this.year = year;
    }
    /**
     * Returns the genre of the book.
     * @return Book's genre.
     */
    public String getGenre() {
        return genre;
    }
    /**
     * Sets the genre of the book.
     * @param genre New genre for the book.
     */
    public void setGenre(String genre) {
        this.genre = genre;
    }
    /**
     * Returns a description of the book.
     * @return Book's description.
     */
    public String getDescription() {
        return description;
    }
    /**
     * Sets a description for the book.
     * @param description New description of the book.
     */

    public void setDescription(String description) {
        this.description = description;
    }
    /**
     * Prints detailed information about the book.
     */
    public void printInfo() {
        System.out.println("Title: " + title);
        System.out.println("Author: " + author);
        System.out.println("Year: " + year);
        System.out.println("Genre: " + genre);
        System.out.println("Description: " + description);
    }
    /**
     * Returns a string representation of the book including title, author, year, genre, and description.
     * @return String representation of the book.
     */
    @Override
    public String toString() {
        return "Book{" +
                "title='" + title + '\'' +
                ", author='" + author + '\'' +
                ", year=" + year +
                ", genre='" + genre + '\'' +
                ", description='" + description + '\'' +
                '}';
    }
}