package com.example.loginpage.module.users;

import com.example.loginpage.controller.dao.UserDAO;
import com.example.loginpage.controller.observe.FavouritesObserver;
import com.example.loginpage.controller.serialization.SerializationForCountOfPeople;
import com.example.loginpage.module.books.Book;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * An abstract base class that represents a user in the system. This class implements the serialisation functionality
 * Each user has a unique identifier, profile details, and a list of books they have recently interacted with
 * or marked as favourites. The application provides methods for managing user data and behaviour related to book management.
 */
public abstract class User implements Serializable, FavouritesObserver {
    /** List of the most recently interacted books by the user. */
    private List<Book> latestBook;
    /** Static counter to track the number of User instances. */
    private static int COUNT_OF_PEOPLE;
    /** Unique identifier for the user. */
    private int id;

    static {
        try {
            COUNT_OF_PEOPLE = SerializationForCountOfPeople.loadCount();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    // Initializer block to set the user's unique ID
    {
        COUNT_OF_PEOPLE++;
        id = COUNT_OF_PEOPLE;
    }
    /** Inner class instance containing user profile details. */
    private UserProfile profile;
    /** List of user's favorite books. */
    private List<Book> favouriteBooks;
    /** Flag to determine if the user has administrative privileges. */
    private boolean isAdmin;

    /**
     * Default constructor that initializes user profile and lists of books.
     */
    public User() {
        this.latestBook = new ArrayList<>();
        this.profile = new UserProfile();
    }
    /**
     * Custom serialization of User.
     * @param out the ObjectOutputStream to write the object.
     * @throws IOException if an I/O error occurs during writing.
     */
    private void writeObject(ObjectOutputStream out) throws IOException {
        out.defaultWriteObject();
        out.writeInt(COUNT_OF_PEOPLE);
    }
    /**
     * Custom deserialization of User.
     * @param in the ObjectInputStream to read the object from.
     * @throws IOException if an I/O error occurs during reading.
     * @throws ClassNotFoundException if the class of a serialized object could not be found.
     */
    private void readObject(ObjectInputStream in) throws IOException, ClassNotFoundException {
        in.defaultReadObject(); // Perform the default deserialization for all non-static fields
        COUNT_OF_PEOPLE = in.readInt(); // Manually deserialize and restore the static field
    }

    /**
     * Constructor with parameters for user creation including detailed profile and administrative status.
     * @param name User's full name
     * @param email User's email address
     * @param phone User's phone number
     * @param password User's account password
     * @param isAdmin Flag indicating whether the user has admin privileges
     */
    public User(String name, String email, String phone, String password, boolean isAdmin) {
        this();
        this.profile = new UserProfile(name, email, phone, password);
        this.isAdmin = isAdmin;
    }

    /**
     * Returns a list of the latest books interacted with by the user.
     * @return List of latest books
     */
    public List<Book> getLatestBook() {
        return latestBook;
    }

    /**
     * Adds a book to the list of latest books.
     * @param book Book to be added
     */
    public void addToLatestBook(Book book) {
        latestBook.removeIf(b -> b.getId() == book.getId());
        latestBook.addFirst(book);
        if (latestBook.size() > 10) {
            latestBook.remove(10);
        }
    }

    /**
     * Returns the current count of people.
     * @return Current count of people
     */
    public static int getCountOfPeople() {
        return COUNT_OF_PEOPLE;
    }

    /**
     * Sets the total count of people.
     * @param countOfPeople New count of people
     */
    public static void setCountOfPeople(int countOfPeople) {
        COUNT_OF_PEOPLE = countOfPeople;
    }

    /**
     * Gets the user's ID.
     * @return User's ID
     */
    public int getId() {
        return id;
    }

    /**
     * Sets the user's ID.
     * @param id New ID for the user
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * Gets the user's name from the profile.
     * @return User's name
     */
    public String getName() {
        return profile.name;
    }

    /**
     * Sets the user's name.
     * @param name New name for the user
     */
    public void setName(String name) {
        profile.setName(name);
    }

    /**
     * Gets the user's email.
     * @return User's email
     */
    public String getEmail() {
        return profile.email;
    }

    /**
     * Sets the user's email.
     * @param email New email for the user
     */
    public void setEmail(String email) {
        profile.setEmail(email);
    }

    /**
     * Gets the user's phone number.
     * @return User's phone number
     */
    public String getPhone() {
        return profile.phone;
    }

    /**
     * Sets the user's phone number.
     * @param phone New phone number for the user
     */
    public void setPhone(String phone) {
        profile.setPhone(phone);
    }

    /**
     * Gets the user's password.
     * @return User's password
     */
    public String getPassword() {
        return profile.password;
    }

    /**
     * Sets the user's password.
     * @param password New password for the user
     */
    public void setPassword(String password) {
        profile.setPassword(password);
    }

    /**
     * Returns a list of the user's favourite books.
     * @return List of favourite books
     */
    public List<Book> getFavouriteBooks() {
        if(favouriteBooks == null){
            return new ArrayList<>();
        }
        return favouriteBooks;
    }

    /**
     * Sets the list of the user's favourite books.
     * @param favouriteBooks New list of favourite books
     */
    public void setFavouriteBooks(List<Book> favouriteBooks) {
        this.favouriteBooks = favouriteBooks;
    }

    /**
     * Checks if the user is an admin.
     * @return true if the user is an admin, false otherwise
     */
    public boolean isAdmin() {
        return isAdmin;
    }

    /**
     * Sets the user's admin status.
     * @param admin New admin status
     */
    public void setAdmin(boolean admin) {
        isAdmin = admin;
    }

    /**
     * Abstract method to return a string representation of the user.
     * @return String representation of the user
     */
    abstract public String toString();

    /**
     * Responds to an event when a book is added to the favorites list.
     * @param book Book added to favorites
     */
    @Override
    public void onFavoriteBookAdded(Book book) {
        System.out.println("Book added to favorites: " + book.getTitle());
    }

    /**
     * Responds to an event when a book is removed from the favorites list.
     * @param book Book removed from favorites
     */
    @Override
    public void onFavoriteBookRemoved(Book book) {
        System.out.println("Book removed from favorites: " + book.getTitle());
    }

    /**
     * Inner class to handle user profile details
     */
    private class UserProfile {
        /** User's full name. */
        private String name;
        /** User's email address. */
        private String email;
        /** User's phone number. */
        private String phone;
        /** User's account password. */
        private String password;
        /**
         * Default constructor for user profile.
         */
        public UserProfile() {}
        /**
         * Constructor with parameters for user profile creation.
         * @param name User's full name
         * @param email User's email address
         * @param phone User's phone number
         * @param password User's account password
         */
        public UserProfile(String name, String email, String phone, String password) {
            setName(name);
            setEmail(email);
            setPhone(phone);
            setPassword(password);
        }
        /**
         * Sets the user's name.
         * @param name New name for the user
         */
        public void setName(String name) {
            this.name = name;
        }
        /**
         * Sets the user's email, ensuring it contains an "@" symbol.
         * @param email New email for the user
         * @throws IllegalArgumentException If the email does not contain an "@" symbol
         */
        public void setEmail(String email) {
            if (email == null || !email.contains("@")) {
                throw new IllegalArgumentException("Invalid email address");
            }
            this.email = email;
        }
        /**
         * Sets the user's phone number.
         * @param phone New phone number for the user
         */
        public void setPhone(String phone) {
            this.phone = phone;
        }
        /**
         * Sets the user's password.
         * @param password New password for the user
         */
        public void setPassword(String password) {
            this.password = password;
        }
    }
}