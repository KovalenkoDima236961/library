package com.example.loginpage.controller.dao;

import com.example.loginpage.controller.factoryPattern.BookFactory;
import com.example.loginpage.controller.observe.FavouritesObservable;
import com.example.loginpage.controller.observe.FavouritesObserver;
import com.example.loginpage.module.books.Book;
import com.example.loginpage.module.books.typeofbooks.*;

import java.sql.*;
import java.util.*;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.function.Consumer;

/**
 * The {@code BookDAO} class is responsible for managing all database access related to books in the application.
 * This class uses the Singleton design pattern to ensure that only one instance manages database connections
 * and operations, which increases efficiency and reduces overhead.
 * This class also implements {@link FavouritesObservable} to manage favourite books
 * The application is notified when a book is added to or removed from the user's favourites.
 * Interaction with databases includes CRUD operations on books, searching for books by various criteria, and managing
 * user's favourites, as well as notifying observers of changes in the favourites.
 */
public class BookDAO implements DAO, FavouritesObservable {
    /**
     * Total count of people.
     */
    private static int PEOPLE_COUNT;
    /**
     * URL of the PostgreSQL database.
     */
    private static final String URL = "jdbc:postgresql://database-library.cl22ko6iumdh.eu-central-1.rds.amazonaws.com:5432/libraryjava";
    /**
     * Username for accessing the database.
     */
    private static final String USERNAME = "postgres";
    /**
     * Password for accessing the database.
     */
    private static final String PASSWORD = "eWQAmYr24n30";
    /**
     * Connection object for managing the database connection.
     */
    private static Connection connection;
    private static final ExecutorService dbExecutor = Executors.newFixedThreadPool(1);
    /**
     * Random object for generating random values.
     */
    private Random random;
    /**
     * Singleton instance of the BookDAO class.
     */
    private static BookDAO instance;
    /**
     * ID for the book.
     */
    private static int id = 0;
    /**
     * Default constructor for creating a BookDAO.
     */
    public BookDAO() {}
    /**
     * Retrieves the singleton instance of the BookDAO class.
     * If the instance is null, creates a new instance.
     * @return The singleton instance of BookDAO.
     */
    public static synchronized BookDAO getInstance() {
        if (instance == null) {
            instance = new BookDAO();
        }
        return instance;
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
            throwables.printStackTrace();
        }
    }

    /**
     * List of observers for the DAO.
     */
    private List<FavouritesObserver> observers = new ArrayList<>();
    /**
     * Adds an observer to the list.
     * @param observer The observer to be added.
     */
    @Override
    public void addObserver(FavouritesObserver observer) {
        observers.add(observer);
    }
    /**
     * Removes an observer from the list.
     * @param observer The observer to be removed.
     */
    @Override
    public void removeObserver(FavouritesObserver observer) {
        observers.remove(observer);
    }
    /**
     * Notifies all observers about the addition of a new favorite book.
     * @param book The book that was added to favorites.
     */
    @Override
    public void notifyObserversBookAdded(Book book) {
        if (observers.isEmpty()) {
            System.out.println("No observers registered.");
        }
        for (FavouritesObserver observer : observers) {
            System.out.println("Notifying observers about a new favorite book: " + book.getTitle());
            observer.onFavoriteBookAdded(book);
        }
    }
    /**
     * Notifies all observers about the removal of a favorite book.
     * @param book The book that was removed from favorites.
     */
    @Override
    public void notifyObserversBookRemoved(Book book) {
        if (observers.isEmpty()) {
            System.out.println("No observers have registered to receive deletion notifications.");
        }
        for (FavouritesObserver observer : observers) {
            System.out.println("Notify observers when a favourite book is removed: " + book.getTitle());
            observer.onFavoriteBookRemoved(book);
        }
    }

    /**
     * Creates a book object from the provided ResultSet.
     * @param resultSet The ResultSet containing book information.
     * @return The Book object created from the ResultSet.
     * @throws SQLException If a database access error occurs or this method is called on a closed result set.
     */
    private Book createBookFromResultSet(ResultSet resultSet) throws SQLException {
        String genre = resultSet.getString("genre");
        int id = resultSet.getInt("id");
        String title = resultSet.getString("title");
        String author = resultSet.getString("author");
        int year = resultSet.getInt("year");
        String description = resultSet.getString("description");
        int votes = resultSet.getInt("votes");
        String imageSrc = resultSet.getString("imagesrc");
        String some = "";
        return BookFactory.createBook(id,genre,title,author,year,description,imageSrc,votes,some);
    }

    /**
     * Extracts all individual book genres from the database.
     * @return A set containing all distinct genres of books.
     */
    public Set<String> getAllGenres() {
        CompletableFuture<Set<String>> future = CompletableFuture.supplyAsync(() -> {
            Set<String> genres = new HashSet<>();
            String SQL = "SELECT DISTINCT genre FROM books";
            try (PreparedStatement preparedStatement = connection.prepareStatement(SQL)) {
                ResultSet resultSet = preparedStatement.executeQuery();
                while (resultSet.next()) {
                    genres.add(resultSet.getString("genre"));
                }
            } catch (SQLException e) {
                System.err.println("Database error while fetching genres: " + e.getMessage());
                throw new RuntimeException("Database error occurred while fetching genres", e);
            }
            return genres;
        }, dbExecutor);

        try {
            return future.get();
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            throw new RuntimeException("Thread was interrupted while fetching genres", e);
        } catch (ExecutionException e) {
            throw new RuntimeException("Error occurred while fetching genres", e.getCause());
        }
    }
    /**
     * Returns all book authors from the database.
     * @return A set containing all distinct authors of books.
     */
    public Set<String> getAllAuthors() {
        CompletableFuture<Set<String>> future = CompletableFuture.supplyAsync(() -> {
            Set<String> authors = new HashSet<>();
            String SQL = "SELECT DISTINCT author FROM books";
            try (PreparedStatement preparedStatement = connection.prepareStatement(SQL)) {
                ResultSet resultSet = preparedStatement.executeQuery();
                while (resultSet.next()) {
                    authors.add(resultSet.getString("author"));
                }
            } catch (SQLException e) {
                System.err.println("Database error while fetching authors: " + e.getMessage());
                throw new RuntimeException("Database error occurred while fetching authors", e);
            }
            return authors;
        }, dbExecutor);

        try {
            return future.get();
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            throw new RuntimeException("Thread was interrupted while fetching authors", e);
        } catch (ExecutionException e) {
            throw new RuntimeException("Error occurred while fetching authors", e.getCause());
        }
    }
    /**
     * Returns all books from the database.
     *
     * @return A list of {@link Book} objects, each representing a book in the database.
     */
    @Override
    public List<Book> index() {
        CompletableFuture<List<Book>> future = CompletableFuture.supplyAsync(() -> {
            List<Book> books = new ArrayList<>();
            String SQL = "SELECT * FROM books";
            try (PreparedStatement preparedStatement = connection.prepareStatement(SQL)) {
                ResultSet resultSet = preparedStatement.executeQuery();
                while (resultSet.next()) {
                    Book book = createBookFromResultSet(resultSet);
                    books.add(book);
                }
            } catch (SQLException e) {
                System.err.println("Database error while fetching books: " + e.getMessage());
                throw new RuntimeException("Database error occurred while fetching books", e);
            }
            return books;
        }, dbExecutor);

        try {
            return future.get();
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            throw new RuntimeException("Thread was interrupted while fetching books", e);
        } catch (ExecutionException e) {
            throw new RuntimeException("Error occurred while fetching books", e.getCause());
        }
    }
    /**
     * Returns the first book from the database that matches the specified author.
     * This method uses a SQL query to find the first book that matches the given author name.
     *
     * @param author The name of the author whose book is to be retrieved.
     * @return A {@link Book} object representing the first book found for the specified author, or {@code null} if no book is found.
     * @throws SQLException If there is an error during the database query execution. This exception must be caught
     *         and handled by the caller to manage database connection issues or query errors appropriately.
     */
    public Book findFirstBookByAuthor(String author) throws SQLException {
        CompletableFuture<Book> future = CompletableFuture.supplyAsync(() -> {
            String SQL = "SELECT * FROM books WHERE author = ? LIMIT 1";
            try (PreparedStatement preparedStatement = connection.prepareStatement(SQL)) {
                preparedStatement.setString(1, author);
                ResultSet resultSet = preparedStatement.executeQuery();
                if (resultSet.next()) {
                    return createBookFromResultSet(resultSet);
                } else {
                    return null;
                }
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }, dbExecutor);

        try {
            return future.get(); // Block and wait for the future to complete
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            throw new SQLException("Thread was interrupted while fetching the book", e);
        } catch (ExecutionException e) {
            Throwable cause = e.getCause();
            if (cause instanceof SQLException) {
                throw (SQLException) cause; // Rethrow the underlying SQLException
            } else {
                throw new SQLException("Error occurred while fetching the book", cause);
            }
        }
    }
    /**
     * Returns the first book from the database that matches the specified genre.
     * This method queries the database to find books filtered by the specified genre and limits the result to only one book
     *
     * @param genre The genre of the book to find.
     * @return A {@link Book} object representing the first book found for the specified genre, or {@code null} if no matching book is found.
     * @throws SQLException If there is an error accessing the database, such as a connection issue or malformed SQL query.
     *         Callers of this method must handle this exception, typically in a user interface or higher-level service layer.
     */
    public Book findFirstBookByGenre(String genre) throws SQLException {
        CompletableFuture<Book> future = CompletableFuture.supplyAsync(() -> {
            String SQL = "SELECT * FROM books WHERE genre = ? LIMIT 1";
            try (PreparedStatement preparedStatement = connection.prepareStatement(SQL)) {
                preparedStatement.setString(1, genre);
                ResultSet resultSet = preparedStatement.executeQuery();
                if (resultSet.next()) {
                    return createBookFromResultSet(resultSet);
                } else {
                    return null;
                }
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }, dbExecutor);

        try {
            return future.get();
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            throw new SQLException("Thread was interrupted while fetching the book by genre", e);
        } catch (ExecutionException e) {
            Throwable cause = e.getCause();
            if (cause instanceof SQLException) {
                throw (SQLException) cause;
            } else {
                throw new SQLException("Error occurred while fetching the book by genre", cause);
            }
        }
    }

    /**
     * Increases the number of votes for the specified book by one.
     * This method updates the votes field in the database for the specified book ID.
     *
     * @param book The {@link Book} object for which the votes should be increased.
     * @return true if the operation was successful and the vote was incremented; false otherwise.
     * @throws RuntimeException If an SQLException occurs during database access, a RuntimeException is thrown,
     *         encapsulating the SQLException to simplify error handling.
     */
    public boolean incrementBookVote(Book book) {
        if (book == null) {
            throw new IllegalArgumentException("Book cannot be null");
        }

        CompletableFuture<Boolean> future = CompletableFuture.supplyAsync(() -> {
            String SQL = "UPDATE books SET votes = votes + 1 WHERE id = ?";
            try (PreparedStatement preparedStatement = connection.prepareStatement(SQL)) {
                preparedStatement.setInt(1, book.getId());
                int affectedRows = preparedStatement.executeUpdate();
                if (affectedRows > 0) {
                    System.out.println("Vote incremented for book ID: " + book.getId());
                    return true;
                } else {
                    System.out.println("No book found with ID: " + book.getId());
                    return false;
                }
            } catch (SQLException e) {
                System.err.println("SQL error occurred during the update operation: " + e.getMessage());
                throw new RuntimeException("Error updating votes for book with ID: " + book.getId(), e);
            }
        }, dbExecutor);

        try {
            return future.get();
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            throw new RuntimeException("Thread was interrupted while updating book votes", e);
        } catch (ExecutionException e) {
            Throwable cause = e.getCause();
            if (cause instanceof RuntimeException) {
                throw (RuntimeException) cause;
            } else {
                throw new RuntimeException("Unexpected exception occurred while updating book votes", cause);
            }
        }
    }
    /**
     * Saves a {@link Book} object into the database. This method inserts a new book record into the books table.
     *
     * @param entity The object to be saved to the database. This must be an instance of {@link Book}.
     * @return {@code true} if the book was successfully saved (i.e., the book was inserted into the database);
     *         {@code false} if the book could not be saved (e.g., not a {@link Book} instance, or no rows were affected).
     * @throws RuntimeException Wraps and rethrows any {@link SQLException} as a {@link RuntimeException},
     *         simplifying error handling in higher layers of the application by avoiding the need to declare
     *         {@code SQLException} or handle SQL-specific issues outside of the DAO.
     */
    @Override
    public boolean save(Object entity) {
        if (!(entity instanceof Book book)) {
            return false;
        }

        String SQL = "INSERT INTO books (id, genre, title, author, year, description, votes, imagesrc) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";

        try (PreparedStatement preparedStatement = connection.prepareStatement(SQL)) {
            preparedStatement.setInt(1, book.getId());
            preparedStatement.setString(2, book.getGenre());
            preparedStatement.setString(3, book.getTitle());
            preparedStatement.setString(4, book.getAuthor());
            preparedStatement.setInt(5, book.getYear());
            preparedStatement.setString(6, book.getDescription());
            preparedStatement.setInt(7, book.getVotes());
            preparedStatement.setString(8, book.getImageSrc());

            int affectedRows = preparedStatement.executeUpdate();
            return affectedRows > 0;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }


    /**
     * Deletes a book from the database based on its ID.
     * @param bookId The ID of the book to delete.
     * @return true if the book was successfully deleted, false otherwise.
     */
    @Override
    public boolean delete(int bookId,int need) {
        CompletableFuture<Boolean> future = CompletableFuture.supplyAsync(() -> {
            String SQL = "DELETE FROM books WHERE id = ?";
            try (PreparedStatement preparedStatement = connection.prepareStatement(SQL)) {
                preparedStatement.setInt(1, bookId);
                int affectedRows = preparedStatement.executeUpdate();
                if (affectedRows > 0) {
                    System.out.println("Book with ID " + bookId + " was deleted successfully.");
                    return true;
                } else {
                    System.out.println("No book found with ID " + bookId + ".");
                    return false;
                }
            } catch (SQLException e) {
                System.err.println("SQL error occurred during the delete operation: " + e.getMessage());
                throw new RuntimeException("Error deleting book with ID: " + bookId, e);
            }
        }, dbExecutor);

        try {
            return future.get();
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            throw new RuntimeException("Thread was interrupted while deleting the book", e);
        } catch (ExecutionException e) {
            Throwable cause = e.getCause();
            if (cause instanceof RuntimeException) {
                throw (RuntimeException) cause;
            } else {
                throw new RuntimeException("Unexpected exception occurred while deleting the book", cause);
            }
        }
    }

    /**
     * Retrieves books of a certain genre from the database.
     * @param genre The genre of books to retrieve.
     * @return A list containing books of the specified genre.
     */
    public List<Book> indexGenre(String genre){
        CompletableFuture<List<Book>> future = CompletableFuture.supplyAsync(() -> {
            List<Book> books = new ArrayList<>();
            String SQL = "SELECT * FROM books WHERE genre = ?";
            try (PreparedStatement preparedStatement = connection.prepareStatement(SQL)) {
                preparedStatement.setString(1, genre);
                ResultSet resultSet = preparedStatement.executeQuery();
                while (resultSet.next()) {
                    Book book = createBookFromResultSet(resultSet);
                    books.add(book);
                }
            } catch (SQLException e) {
                System.err.println("SQL error occurred during the query operation: " + e.getMessage());
                throw new RuntimeException("Error fetching books by genre: " + genre, e);
            }
            return books;
        }, dbExecutor);

        try {
            return future.get();
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            throw new RuntimeException("Thread was interrupted while fetching books by genre", e);
        } catch (ExecutionException e) {
            Throwable cause = e.getCause();
            if (cause instanceof RuntimeException) {
                throw (RuntimeException) cause;
            } else {
                throw new RuntimeException("Unexpected exception occurred while fetching books by genre", cause);
            }
        }
    }

    /**
     * Increases the number of votes for a particular book.
     * @param book The book to vote for.
     * @return true if the vote is successful, false otherwise.
     */
    public boolean voteForTopBook(Book book) {
        try {
            String SQL = "UPDATE books SET votes = votes + 1 WHERE id = ?";
            try (PreparedStatement preparedStatement = connection.prepareStatement(SQL)) {
                preparedStatement.setInt(1, book.getId());
                int rowsAffected = preparedStatement.executeUpdate();
                if (rowsAffected > 0) {
                    return true; // Book's vote successfully incremented
                } else {
                    return false; // Book with the specified id not found
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    /**
     * Displays the top 10 books based on the number of votes.
     * @return A list containing the top 10 books.
     */
    public List<Book> topBooks() {
        List<Book> topBooks = new ArrayList<>();
        try {
            String SQL = "SELECT * FROM books ORDER BY votes DESC LIMIT 10";  // Directly use SQL for sorting and limiting
            try (PreparedStatement preparedStatement = connection.prepareStatement(SQL)) {
                ResultSet resultSet = preparedStatement.executeQuery();
                while (resultSet.next()) {
                    Book book = createBookFromResultSet(resultSet);
                    topBooks.add(book);
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return topBooks;
    }

    /**
     * Checks if a book is marked as favorite by a user.
     * @param userId The ID of the user.
     * @param bookId The ID of the book.
     * @return true if the book is marked as favorite by the user, false otherwise.
     */
    public CompletableFuture<Boolean> isFavorite(int userId, int bookId) {
        return CompletableFuture.supplyAsync(() -> {
            String checkSQL = "SELECT COUNT(*) FROM user_favourite_book WHERE user_id = ? AND bool_id = ?";
            try (PreparedStatement checkStatement = connection.prepareStatement(checkSQL)) {
                checkStatement.setInt(1, userId);
                checkStatement.setInt(2, bookId);
                ResultSet resultSet = checkStatement.executeQuery();
                if (resultSet.next()) {
                    int count = resultSet.getInt(1);
                    return count > 0;
                }
                return false;
            } catch (SQLException e) {
                System.err.println("SQL error occurred during the favorite check operation: " + e.getMessage());
                throw new RuntimeException("Error checking if the book is a favorite", e);
            }
        }, dbExecutor);
    }
    /**
     * Adds a book to the user's favorites.
     * @param userId The ID of the user.
     * @param bookId The ID of the book.
     * @return true if the book is successfully added to favorites, false otherwise.
     */
    public CompletableFuture<Boolean> addToFavorites(int userId, int bookId) {
        return isFavorite(userId, bookId).thenCompose(isFavorite -> {
            if (isFavorite) {
                return CompletableFuture.completedFuture(false);
            } else {
                return CompletableFuture.supplyAsync(() -> {
                    String insertSQL = "INSERT INTO user_favourite_book (user_id, bool_id) VALUES (?, ?)";
                    try (PreparedStatement insertStatement = connection.prepareStatement(insertSQL)) {
                        insertStatement.setInt(1, userId);
                        insertStatement.setInt(2, bookId);
                        insertStatement.executeUpdate();
                        Book book = retrieveBookById(bookId);
                        if (book != null) {
                            notifyObserversBookAdded(book);
                            return true;
                        }
                        return false;
                    } catch (SQLException e) {
                        throw new RuntimeException(e);
                    }
                }, dbExecutor);
            }
        });
    }

    /**
     * Retrieves a book from the database based on its unique identifier.
     * This method requests a book with the specified {@code bookId} from the database and creates an instance of it
     * of the corresponding subclass {@link Book} based on the genre of the retrieved book.
     *
     * @param bookId The unique identifier of the book to retrieve.
     * @return The {@link Book} object representing the book retrieved from the database;
     *         {@code null} if no book with the given ID exists.
     * @throws RuntimeException If there is an SQL error during the database query execution.
     *         This exception wraps the original {@link SQLException} to avoid exposing database
     *         details and to simplify error handling in higher layers of the application.
     */
    public Book retrieveBookById(int bookId) {
        String SQL = "SELECT * FROM books WHERE id = ?";
        Book book = null;

        try (PreparedStatement preparedStatement = connection.prepareStatement(SQL)) {
            preparedStatement.setInt(1, bookId);
            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                int id = resultSet.getInt("id");
                String genre = resultSet.getString("genre");
                String title = resultSet.getString("title");
                String author = resultSet.getString("author");
                int year = resultSet.getInt("year");
                String description = resultSet.getString("description");
                String imageSrc = resultSet.getString("imagesrc");
                int votes = resultSet.getInt("votes");
                String some = "";

                book = BookFactory.createBook(id, genre, title, author, year, description, imageSrc, votes, some);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("Error retrieving book with ID: " + bookId, e);
        }

        return book;
    }


    /**
     * Removes a book from the user's favorites.
     * @param userId The ID of the user.
     * @param bookId The ID of the book.
     * @return true if the book is successfully removed from favorites, false otherwise.
     */
    public CompletableFuture<Boolean> deleteFromFavorites(int userId, int bookId) {
        return CompletableFuture.supplyAsync(() -> {
            String deleteSQL = "DELETE FROM user_favourite_book WHERE user_id = ? AND bool_id = ?";
            try (PreparedStatement deleteStatement = connection.prepareStatement(deleteSQL)) {
                deleteStatement.setInt(1, userId);
                deleteStatement.setInt(2, bookId);
                int affectedRows = deleteStatement.executeUpdate();
                if (affectedRows > 0) {
                    Book book = retrieveBookById(bookId);
                    if (book != null) {
                        notifyObserversBookRemoved(book);
                        return true;
                    }
                }
                return false;
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }, dbExecutor);
    }
    /**
     * Retrieves a list of 24 random books from the database.
     * @return A list containing 24 random books.
     */
    public List<Book> returnRandomTwentyForBook(){
        List<Book> randomBooks = new ArrayList<>();
        int count = 0;
        try {
            String SQL = "SELECT * FROM books";
            try (PreparedStatement preparedStatement = connection.prepareStatement(SQL)) {
                ResultSet resultSet = preparedStatement.executeQuery();
                while (resultSet.next() && count < 24) { // Використовуємо && і перевіряємо count < 24
                    Book book;
                    if(resultSet.getString("genre").equals("FictionBook")){
                        book = new FictionBook();
                    } else {
                        book = new DramaBook();
                    }
                    book.setId(resultSet.getInt("id"));
                    book.setTitle(resultSet.getString("title"));
                    book.setAuthor(resultSet.getString("author"));
                    book.setYear(resultSet.getInt("year"));
                    book.setGenre(resultSet.getString("genre"));
                    book.setDescription(resultSet.getString("description"));
                    book.setVotes(resultSet.getInt("votes"));
                    book.setImageSrc(resultSet.getString("imagesrc"));

                    randomBooks.add(book);
                    count++;
                }

                // Sort the list of books by votes in descending order
                randomBooks.sort(Comparator.comparing(Book::getVotes).reversed());
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return randomBooks;
    }

}
