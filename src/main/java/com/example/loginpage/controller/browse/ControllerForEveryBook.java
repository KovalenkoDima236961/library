package com.example.loginpage.controller.browse;

import com.example.loginpage.HelloApplication;
import com.example.loginpage.controller.dao.BookDAO;
import com.example.loginpage.controller.favourites.FavouritesController;
import com.example.loginpage.controller.mainClient.MainClientConroller;
import com.example.loginpage.controller.observe.FavouritesObserver;
import com.example.loginpage.controller.serialization.SerializationBooks;
import com.example.loginpage.module.books.Book;
import com.example.loginpage.module.users.User;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.Objects;
import java.util.ResourceBundle;

/**
 * This class represents a controller for displaying information about each book and handling user interaction
 * with individual books in a particular section of the application.
 */
public class ControllerForEveryBook{
    private MainClientConroller mainClientController;
    private FavouritesController favouritesController;

    public void setMainClientController(MainClientConroller mainClientController) {
        this.mainClientController = mainClientController;
    }

    public void setFavouritesController(FavouritesController favouritesController) {
        this.favouritesController = favouritesController;
    }
    /**
     * A button that starts the action of adding or removing the displayed book from the user's list of favourite books.
     */

    @FXML
    private Button addToFavouriteButton;
    /**
     * Label displaying the author of the book.
     */
    @FXML
    private Label author;
    /**
     * Label displaying the title of the book.
     */
    @FXML
    private Label bookTitle;
    /**
     * Label for showing a detailed description of the book.
     */
    @FXML
    private Label description;
    /**
     * Button to close the book detail view.
     */
    @FXML
    private Button exitButton;
    /**
     * Label displaying the genre of the book.
     */
    @FXML
    private Label genre;
    /**
     * ImageView for displaying the cover image of the book.
     */
    @FXML
    private ImageView bookImage;
    /**
     * Label displaying the publication year of the book.
     */
    @FXML
    private Label year;
    /**
     * Static variable to hold the current user interacting with the book details.
     */
    private static User user;
    /**
     * Static variable to store the book that is currently being displayed in the detail view.
     */
    private static Book book;
    /**
     * Data Access Object for interacting with book data in the database.
     */
    private BookDAO bookDAO;
    /**
     * Flag indicating whether the displayed book is currently marked as a favorite by the user.
     */
    private boolean isBookInFavorites = false;

    /**
     * Default constructor that initializes the BookDAO instance.
     */
    public ControllerForEveryBook(){
        this.bookDAO = BookDAO.getInstance();
    }
    /**
     * Sets the user context for book operations such as adding to favorites.
     * @param user The user to set for the current context.
     */
    public static void setUser(User user) {
        ControllerForEveryBook.user = user;
    }

    /**
     * Sets the book to be displayed in the detail view.
     * @param book The book to display.
     */
    public void setData(Book book){
        this.book = book;
        System.out.println("Book - " + book.getId());
        System.out.println(this.book.getImageSrc());
        Image image;
        if (this.book.getImageSrc().startsWith("http://") || this.book.getImageSrc().startsWith("https://")) {
            image = new Image(this.book.getImageSrc());
        } else {
            image = new Image(Objects.requireNonNull(
                    HelloApplication.class.getResourceAsStream(this.book.getImageSrc()),
                    "Resource not found: " + this.book.getImageSrc()
            ));
        }

        bookImage.setImage(image);
        genre.setText(book.getGenre());
        author.setText(book.getAuthor());
        year.setText(String.valueOf(book.getYear()));
        bookTitle.setText(book.getTitle());
        description.setText(book.getDescription());

        bookDAO.isFavorite(user.getId(), book.getId()).thenAccept(isFavorite -> {
            Platform.runLater(() -> {
                isBookInFavorites = isFavorite;
                if (isFavorite) {
                    addToFavouriteButton.setText("Remove from Favourites");
                } else {
                    addToFavouriteButton.setText("Add to Favourites");
                }
            });
        }).exceptionally(e -> {
            e.printStackTrace();
            return null;
        });
    }
    @FXML
    public void addToFavourite(ActionEvent event) {
        if (isBookInFavorites) {
            bookDAO.deleteFromFavorites(user.getId(), book.getId()).thenAccept(success -> {
                if (success) {
                    user.onFavoriteBookRemoved(book);
                    Platform.runLater(() -> {
                        isBookInFavorites = false;
                        addToFavouriteButton.setText("Add to Favourites");
                        if (mainClientController != null) {
                            mainClientController.refreshFavorites(); // Notify main controller to refresh favorites
                        }
                        if (favouritesController != null) {
                            favouritesController.refreshFavorites(); // Notify favourites controller to refresh favorites
                        }
                    });
                } else {
                    Platform.runLater(() -> showErrorDialog("Failed to remove book from favorites"));
                }
            }).exceptionally(e -> {
                Platform.runLater(() -> showErrorDialog("Failed to remove book from favorites"));
                return null;
            });
        } else {
            bookDAO.addToFavorites(user.getId(), book.getId()).thenAccept(success -> {
                if (success) {
                    user.onFavoriteBookAdded(book);
                    Platform.runLater(() -> {
                        isBookInFavorites = true;
                        addToFavouriteButton.setText("Remove from Favourites");
                        if (mainClientController != null) {
                            mainClientController.refreshFavorites(); // Notify main controller to refresh favorites
                        }
                        if (favouritesController != null) {
                            favouritesController.refreshFavorites(); // Notify favourites controller to refresh favorites
                        }
                    });
                } else {
                    Platform.runLater(() -> showErrorDialog("Failed to add book to favorites"));
                }
            }).exceptionally(e -> {
                Platform.runLater(() -> showErrorDialog("Failed to add book to favorites"));
                return null;
            });
        }
    }

    /**
     * Closes the current book detail window.
     * @param event ActionEvent triggered by the exit button.
     */
    @FXML
    public void exit(ActionEvent event){
        Stage stage = (Stage) exitButton.getScene().getWindow();
        stage.close();
    }

    /**
     * Static method to handle mouse click events on books, opening a detailed view for the clicked book.
     * @param event MouseEvent that triggered this action.
     * @param scene1 The current scene where the event is triggered.
     * @param book The book to display details for.
     * @param user1 The user currently logged in.
     */
    public static void handleBookClicked(MouseEvent event, Scene scene1, Book book, User user1, MainClientConroller mainClientController, FavouritesController favouritesController) {
        user = user1;
        user.addToLatestBook(book);
        HelloApplication.setBooksToSerialize(user.getLatestBook());
        SerializationBooks.setFlag(false);
        Stage bookDetailsStage = new Stage();
        FXMLLoader loader = new FXMLLoader(ControllerForEveryBook.class.getResource("/com/example/loginpage/addBookDialogWindow.fxml"));
        Parent root;
        try {
            root = loader.load();
            ControllerForEveryBook controller = loader.getController();
            controller.setData(book);
            controller.setMainClientController(mainClientController); // Set the reference to the main controller
            controller.setFavouritesController(favouritesController); // Set the reference to the favourites controller

            bookDetailsStage.setScene(new Scene(root));
            bookDetailsStage.setTitle("Additional information about book");
            bookDetailsStage.initModality(Modality.APPLICATION_MODAL);
            bookDetailsStage.initOwner(scene1.getWindow());
            bookDetailsStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    /**
     * Creates an EventHandler to manage the addition or removal of a book from favorites.
     * @param userId The ID of the user for whom the book will be added or removed.
     * @param bookId The ID of the book to toggle in favorites.
     * @param addToFavouriteButton The button triggering the favorite action.
     * @param bookDAO The data access object for book operations.
     * @return An EventHandler for the action event.
     */
    private static EventHandler<ActionEvent> addToFavouriteHandler(int userId, int bookId, Button addToFavouriteButton, BookDAO bookDAO) {
        return event -> {
            bookDAO.isFavorite(userId, bookId).thenAccept(isFavorite -> {
                if (isFavorite) {
                    bookDAO.deleteFromFavorites(userId, bookId).thenRun(() -> {
                        user.onFavoriteBookRemoved(book);
                        Platform.runLater(() -> addToFavouriteButton.setText("Add to Favourites"));
                    }).exceptionally(e -> {
                        Platform.runLater(() -> showErrorDialog("Failed to remove book from favorites"));
                        return null;
                    });
                } else {
                    bookDAO.addToFavorites(userId, bookId).thenRun(() -> {
                        user.onFavoriteBookAdded(book);
                        Platform.runLater(() -> addToFavouriteButton.setText("Remove from Favourites"));
                    }).exceptionally(e -> {
                        Platform.runLater(() -> showErrorDialog("Failed to add book to favorites"));
                        return null;
                    });
                }
            }).exceptionally(e -> {
                Platform.runLater(() -> showErrorDialog("Failed to check if book is favorite"));
                return null;
            });
        };
    }
    private static void showErrorDialog(String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
