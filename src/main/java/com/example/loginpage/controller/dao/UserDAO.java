package com.example.loginpage.controller.dao;

import com.example.loginpage.controller.factoryPattern.BookFactory;
import com.example.loginpage.module.books.Book;
import com.example.loginpage.module.books.typeofbooks.DramaBook;
import com.example.loginpage.module.users.User;
import com.example.loginpage.module.users.typeofuser.Admin;
import com.example.loginpage.module.users.typeofuser.Client;
import com.example.loginpage.module.users.typeofuser.typeOfClient.NotBannedClient;

import java.io.*;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;

/**
 * The {@code UserDAO} class is responsible for managing the interaction of user data with the database.
 * This class handles creating, reading, updating, and deleting user information.
 * Includes methods for performing asynchronous database operations, which allows me to avoid blocking calls
 */
public class UserDAO implements DAO {
    /**
     * Total count of people.
     */
    private static int PEOPLE_COUNT;
    /**
     * File path for serializing user data. This variable specifies the location and filename where user data is stored locally.
     */
    private static final String USERS_FILE = "users.ser";
    /**
     * URL of the PostgreSQL database.
     */
    private static final String URL = "jdbc:postgresql://localhost:5432/for_lib";
    /**
     * Username for accessing the database.
     */
    private static final String USERNAME = "postgres";
    /**
     * Password for accessing the database.
     */
    private static final String PASSWORD = "dimaborec";
    /**
     * Connection object for managing the database connection.
     */
    private static Connection connection;
    // Helps to optimise resource usage by reusing a limited number of threads to handle a potentially large number of tasks.
    // Allows background tasks to run asynchronously without blocking the main thread of the application.
    //Simplifies the task of managing parallel executions and provides utilities for tracking and controlling task execution.
    /**
     * An executor service that manages a fixed pool of threads to perform asynchronous tasks.
     * This allows DAO to perform database operations in the background, improving application responsiveness.
     */
    private final ExecutorService executor = Executors.newFixedThreadPool(1); // You can adjust the number of threads as per your requirement
    /**
     * Default constructor for creating a book object without initializing fields.
     */
    public UserDAO(){}

    /**
     * Executes a task in the background using a thread from the executor.
     * @param task The task to execute.
     */
    public void executeInBackground(Runnable task) {
        executor.submit(task);
    }

    static {
        try {
            Class.forName("org.postgresql.Driver");
        }catch (ClassNotFoundException e){
            e.printStackTrace();
        }

        try {
            connection = DriverManager.getConnection(URL,USERNAME,PASSWORD);
        }catch (SQLException throwables){
            System.out.println("DATABASE FALLS");
            throwables.printStackTrace();
        }
    }

    /**
     * Retrieves all users from the database.
     * This method executes the database query asynchronously and waits for the operation to complete using a {@link CountDownLatch}.
     * @return A list of {@link User} objects representing all users in the database.
     */
    @Override
    public List<User> index() {
        List<User> users = new ArrayList<>();
        CountDownLatch latch = new CountDownLatch(1); // Create a latch to wait for background execution

        executeInBackground(() -> {
            try {
                String SQL = "SELECT * FROM users";
                try (PreparedStatement preparedStatement = connection.prepareStatement(SQL)) {
                    ResultSet resultSet = preparedStatement.executeQuery();

                    while (resultSet.next()) {
                        User user = new NotBannedClient();

                        user.setId(resultSet.getInt("id"));
                        user.setName(resultSet.getString("name"));
                        user.setEmail(resultSet.getString("email"));
                        user.setPhone(resultSet.getString("phone"));
                        user.setPassword(resultSet.getString("password"));

                        users.add(user);
                    }
                }
                latch.countDown();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        });

        try {
            latch.await();
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }

        return users;
    }
    /**
     * Attempts to save the specified object in the database.
     * This method is designed to handle saving {@link User} objects to the database
     *
     * @param entity The object to be saved. Ideally, this should be an instance of {@link User}.
     * @return {@code false} to indicate that the method is not yet implemented.
     */
    @Override
    public boolean save(Object entity) {
        if (!(entity instanceof User user)) {
            return false;
        }

        CompletableFuture<Boolean> future = CompletableFuture.supplyAsync(() -> {
            try {
                String SQL = "INSERT INTO users (id, name, email, phone, password) VALUES (?, ?, ?, ?, ?)";
                try (PreparedStatement preparedStatement = connection.prepareStatement(SQL)) {
                    preparedStatement.setInt(1, user.getId());
                    preparedStatement.setString(2, user.getName());
                    preparedStatement.setString(3, user.getEmail());
                    preparedStatement.setString(4, user.getPhone());
                    preparedStatement.setString(5, user.getPassword());

                    int rowsInserted = preparedStatement.executeUpdate();
                    return rowsInserted > 0;
                } catch (SQLException e) {
                    System.out.println("Error inserting user: " + e.getMessage());
                    return false;
                }
            } catch (Exception e) {
                System.out.println("Unexpected error: " + e.getMessage());
                return false;
            }
        }, executor);

        try {
            return future.get();
        } catch (InterruptedException | ExecutionException e) {
            System.out.println("Error saving user: " + e.getMessage());
            Thread.currentThread().interrupt();
            return false;
        }
    }

    /**
     * Deletes a user from the database based on the provided user ID.
     * @param userId The ID of the user to delete.
     * @param currentUser The ID of the current user.
     * @return True if the user was deleted successfully, false otherwise.
     */
    @Override
    //Using CompletableFuture in Java is useful when solving
    // asynchronous programming tasks. It helps you manage operations that are // potentially time-consuming.
    // Potentially time-consuming operations, such as database operations, file I/O, // network requests, or computations that block the main thread,
    // network queries or other computations that may block the main thread.
    public boolean delete(int userId, int currentUser) {
        // Create a CompletableFuture to handle the asynchronous deletion of a user.
        CompletableFuture<Boolean> future = CompletableFuture.supplyAsync(() -> {
            try {
                // Перевірка, чи користувач не намагається видалити себе
                if (userId == currentUser) {
                    System.out.println("You cannot delete yourself.");
                    return false;
                }

                String deleteFavouritesSQL = "DELETE FROM user_favourite_book WHERE user_id = ?";
                try (PreparedStatement preparedStatement = connection.prepareStatement(deleteFavouritesSQL)) {
                    preparedStatement.setInt(1, userId);
                    preparedStatement.executeUpdate();
                }

                // Now, attempt to delete the user
                String deleteSQL = "DELETE FROM users WHERE id = ?";
                try (PreparedStatement preparedStatement = connection.prepareStatement(deleteSQL)) {
                    preparedStatement.setInt(1, userId);

                    int rowsDeleted = preparedStatement.executeUpdate();
                    if (rowsDeleted > 0) {
                        System.out.println("User with ID " + userId + " deleted successfully.");
                        return true;
                    } else {
                        System.out.println("User with ID " + userId + " not found.");
                        return false;
                    }
                }
            } catch (SQLException e) {
                System.out.println("Error deleting user: " + e.getMessage());
                return false;
            }
        });

        try {
            return future.get();
        } catch (InterruptedException | ExecutionException e) {
            System.out.println("Error deleting user: " + e.getMessage());
            return false;
        }
    }


    /**
     * Retrieves a user from the database based on the provided email and password.
     * @param email The email of the user.
     * @param password The password of the user.
     * @return The User object if found, null otherwise.
     */
    public User show(String email, String password) {
        CompletableFuture<User> future = CompletableFuture.supplyAsync(() -> {
            try {
                String SQL = "SELECT * FROM users WHERE email = ? AND password = ?";
                try (PreparedStatement preparedStatement = connection.prepareStatement(SQL)) {
                    preparedStatement.setString(1, email);
                    preparedStatement.setString(2, password);

                    ResultSet resultSet = preparedStatement.executeQuery();

                    if (resultSet.next()) {
                        if (resultSet.getString("email").equals("kovalenkodima@gmail.com") && resultSet.getString("password").equals("123456789")) {
                            User user = Admin.getInstance();
                            user.setId(resultSet.getInt("id"));
                            user.setName(resultSet.getString("name"));
                            user.setEmail(resultSet.getString("email"));
                            user.setPhone(resultSet.getString("phone"));
                            user.setPassword(resultSet.getString("password"));
                            return user;
                        } else {
                            User user = new NotBannedClient();
                            user.setId(resultSet.getInt("id"));
                            user.setName(resultSet.getString("name"));
                            user.setEmail(resultSet.getString("email"));
                            user.setPhone(resultSet.getString("phone"));
                            user.setPassword(resultSet.getString("password"));
                            return user;
                        }
                    }
                }
            } catch (SQLException e) {
                System.out.println("We don't have: " + e.getMessage());
            }
            return null;
        });

        try {
            return future.get();
        } catch (InterruptedException | ExecutionException e) {
            System.out.println("Error retrieving user: " + e.getMessage());
            return null;
        }
    }

    //SERIALIZATION
    /**
     * Serializes a list of users to a file.
     * @param users The list of User objects to serialize.
     */
    public void serializeUsers(List<User> users) {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(USERS_FILE))) {
            oos.writeObject(users);
            System.out.println("Users serialized successfully.");
        } catch (IOException e) {
            throw new RuntimeException("Error serializing users: " + e.getMessage(), e);
        }
    }

    /**
     * Deserializes a list of users from a file.
     * @return The list of User objects.
     */
    public List<User> deserializeUsers() {
        List<User> users = new ArrayList<>();
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(USERS_FILE))) {
            users = (List<User>) ois.readObject();
            System.out.println("Users deserialized successfully.");
        } catch (IOException | ClassNotFoundException e) {
            // If file doesn't exist or failed to deserialize, return an empty list
            System.err.println("Error deserializing users: " + e.getMessage());
        }
        return users;
    }
    /**
     * Retrieves a list of favorite books for a given user.
     * @param userId The ID of the user.
     * @return A list of favorite books.
     */
    public List<Book> getFavoriteBooks(int userId) {
        CompletableFuture<List<Book>> future = CompletableFuture.supplyAsync(() -> {
            List<Book> favoriteBooks = new ArrayList<>();
            String SQL = "SELECT b.* FROM user_favourite_book fb JOIN books b ON fb.book_id = b.id WHERE fb.user_id = ?";  // Corrected column name from bool_id to book_id
            try (PreparedStatement preparedStatement = connection.prepareStatement(SQL)) {
                preparedStatement.setInt(1, userId);
                ResultSet resultSet = preparedStatement.executeQuery();

                while (resultSet.next()) {
                    Book book = BookFactory.createBook(resultSet.getInt("id"),resultSet.getString("genre"),resultSet.getString("title"),
                            resultSet.getString("author"),resultSet.getInt("year"),resultSet.getString("description"),resultSet.getString("imagesrc"),resultSet.getInt("votes"),"");
                    favoriteBooks.add(book);
                }
            } catch (SQLException e) {
                throw new RuntimeException("Failed to fetch favorite books: " + e.getMessage(), e);
            }
            return favoriteBooks;
        }, executor);

        try {
            return future.get();
        } catch (InterruptedException | ExecutionException e) {
            System.out.println("Error retrieving favorite books: " + e.getMessage());
            Thread.currentThread().interrupt();
            return new ArrayList<>();
        }
    }
    /**
     * Adds a book to the user's list of favorite books.
     * @param userId The ID of the user.
     * @param bookId The ID of the book to add.
     * @return True if the book was added successfully, false otherwise.
     */
    public boolean addFavoriteBook(int userId, int bookId) {
        try {
            String SQL = "INSERT INTO user_favorite_books (user_id, book_id) VALUES (?, ?)";
            try (PreparedStatement preparedStatement = connection.prepareStatement(SQL)) {
                preparedStatement.setInt(1, userId);
                preparedStatement.setInt(2, bookId);

                // Execute the statement
                preparedStatement.executeUpdate();

                System.out.println("Favorite book added successfully.");
                return true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
    /**
     * Updates user information in the database.
     * @param user The User object containing updated information.
     * @return True if the user was updated successfully, false otherwise.
     */
    public boolean updateUser(User user) {
        CompletableFuture<Boolean> future = CompletableFuture.supplyAsync(() -> {
            try {
                String SQL = "UPDATE users SET name = ?, email = ?, phone = ?, password = ? WHERE id = ?";
                try (PreparedStatement preparedStatement = connection.prepareStatement(SQL)) {
                    preparedStatement.setString(1, user.getName());
                    preparedStatement.setString(2, user.getEmail());
                    preparedStatement.setString(3, user.getPhone());
                    preparedStatement.setString(4, user.getPassword());
                    preparedStatement.setInt(5, user.getId());

                    int rowsUpdated = preparedStatement.executeUpdate();
                    return rowsUpdated > 0;
                } catch (SQLException e) {
                    System.out.println("Error updating user: " + e.getMessage());
                    return false;
                }
            } catch (Exception e) {
                System.out.println("Unexpected error: " + e.getMessage());
                return false;
            }
        }, executor);

        try {
            return future.get();
        } catch (InterruptedException | ExecutionException e) {
            System.out.println("Error updating user: " + e.getMessage());
            Thread.currentThread().interrupt();
            return false;
        }
    }
    /**
     * Retrieves a user from the database based on the provided name.
     * @param name The name of the user to find.
     * @return The User object if found, null otherwise.
     */
    public User findByName(String name) {
        CompletableFuture<User> future = CompletableFuture.supplyAsync(() -> {
            try {
                String SQL = "SELECT * FROM users WHERE name = ?";
                try (PreparedStatement preparedStatement = connection.prepareStatement(SQL)) {
                    preparedStatement.setString(1, name);

                    ResultSet resultSet = preparedStatement.executeQuery();

                    if (resultSet.next()) {
                        User user;
                        if (resultSet.getString("email").equals("kovalenkodima@gmail.com") && resultSet.getString("password").equals("123456789")) {
                            user = Admin.getInstance();
                        } else {
                            user = new NotBannedClient();
                        }
                        user.setId(resultSet.getInt("id"));
                        user.setName(resultSet.getString("name"));
                        user.setEmail(resultSet.getString("email"));
                        user.setPhone(resultSet.getString("phone"));
                        user.setPassword(resultSet.getString("password"));
                        return user;
                    }
                }
            } catch (SQLException e) {
                System.out.println("Error finding user by name: " + e.getMessage());
            }
            return null;
        });

        try {
            return future.get();
        } catch (InterruptedException | ExecutionException e) {
            System.out.println("Error retrieving user: " + e.getMessage());
            return null;
        }
    }

}
