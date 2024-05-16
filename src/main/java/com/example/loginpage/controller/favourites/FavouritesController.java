package com.example.loginpage.controller.favourites;

import com.example.loginpage.controller.AllPages;
import com.example.loginpage.controller.browse.BrowseController;
import com.example.loginpage.controller.browse.ControllerForEveryBook;
import com.example.loginpage.controller.categorie.CategorieController;
import com.example.loginpage.controller.dao.UserDAO;
import com.example.loginpage.controller.history.HistoryController;
import com.example.loginpage.controller.interf.Movement;
import com.example.loginpage.controller.mainClient.BookController;
import com.example.loginpage.controller.mainClient.MainClientConroller;
import com.example.loginpage.controller.profile.ProfileController;
import com.example.loginpage.controller.quizSection.QuizPageController;
import com.example.loginpage.controller.serialization.SerializationBooks;
import com.example.loginpage.controller.serialization.SerializationForCountOfPeople;
import com.example.loginpage.controller.topBookSection.TopBooksController;
import com.example.loginpage.controller.voting.VotingController;
import com.example.loginpage.module.books.Book;
import com.example.loginpage.module.users.User;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import static com.example.loginpage.HelloApplication.getBooksToSerialize;

/**
 * Manages the display and management of the user's favourite books.
 * This class implements the {@link Initializable} and {@link Movement} interfaces to perform initialisation and navigation tasks.
 */
public class FavouritesController implements Initializable, Movement {
    /**
     * A GridPane that dynamically displays the books marked as favourites by the user.
     */
    @FXML
    private GridPane bookContainer;
    /**
     * The list contains the books that the user has currently marked as favourites.
     */
    private List<Book> favouritesBook;
    /**
     * Static instance of the current user.
     */
    private static User user;
    /**
     * DAO object for accessing user data.
     */
    private UserDAO userDAO;
    /**
     * Stage on which the current scene is displayed.
     */
    private Stage stage;
    /**
     * Label displaying the username.
     */
    @FXML
    private Label nameLabel;
    /**
     * Constructor for FavouritesController.
     * Initializes UserDAO to perform database operations.
     */
    public FavouritesController(){
        userDAO = new UserDAO();
    }
    private ExecutorService executorService = Executors.newFixedThreadPool(1);

    /**
     * Sets the current user.
     * @param user The User object representing the current user.
     */
    public static void setUser(User user) {
        FavouritesController.user = user;
    }

    public void refreshFavorites() {
        CompletableFuture.supplyAsync(() -> userDAO.getFavoriteBooks(user.getId()), executorService)
                .thenAccept(favorites -> {
                    Platform.runLater(() -> {
                        favouritesBook = favorites;
                        populateFavouriteBooks();
                    });
                });
    }

    private void populateFavouriteBooks() {
        bookContainer.getChildren().clear(); // Clear existing books
        int column = 0;
        int row = 1;
        try {
            for (Book value : favouritesBook) {
                FXMLLoader fxmlLoader = new FXMLLoader();
                fxmlLoader.setLocation(getClass().getResource("/com/example/loginpage/book.fxml"));
                VBox bookBox = fxmlLoader.load();
                BookController bookController = fxmlLoader.getController();
                bookController.setData(value);
                bookBox.setOnMouseClicked(event -> ControllerForEveryBook.handleBookClicked(event, bookContainer.getScene(), value, user, null, this));

                if (column == 6) {
                    column = 0;
                    ++row;
                }
                bookContainer.add(bookBox, column++, row);
                GridPane.setMargin(bookBox, new Insets(10));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    /**
     * Initializes the controller.
     * @param url The location used to resolve relative paths for the root object.
     * @param resourceBundle The resources used to localize the root object.
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        nameLabel.setText(user.getName());
        refreshFavorites();
    }

    /**
     * Handles the event to go to the main page.
     * @param event The MouseEvent triggering the method.
     */
    @Override
    @FXML
    public void moveToTopBooks(MouseEvent event) {
        try {
            TopBooksController.setUser(user);
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/loginpage/sectionWithTopBooks.fxml"));
            Parent root = loader.load();
            TopBooksController topBooksController = loader.getController();
            Scene topBookScene = new Scene(root);
            stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(topBookScene);
            stage.setResizable(true);
            stage.setFullScreen(true); // Set full screen mode after loading the new scene
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    /**
     * Navigates to the main client page on user action.
     * @param event MouseEvent that triggers this navigation.
     */
    @Override
    @FXML
    public void moveToMainPage(MouseEvent event) {
        try {
            MainClientConroller.setUser(user);
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/loginpage/mainClient.fxml"));
            Parent root = loader.load();
            Scene profileScene = new Scene(root);
            stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(profileScene);
            stage.setResizable(true);
            stage.setFullScreen(true); // Set full screen mode after loading the new scene
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    /**
     * Navigates to the user's profile page.
     * @param event MouseEvent that triggers this navigation.
     */
    @Override
    @FXML
    public void goToProfilePage(MouseEvent event) {
        try {
            ProfileController.setUser(user);
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/loginpage/profile.fxml"));
            Parent root = loader.load();
            ProfileController profileController = loader.getController();
            Scene profileScene = new Scene(root);
            stage = (Stage)((Node)event.getSource()).getScene().getWindow();
            stage.setScene(profileScene);
            stage.setResizable(true);
            stage.setFullScreen(true); // Set full screen mode after loading the new scene
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    /**
     * Navigates to the move to all books page.
     * @param event MouseEvent that triggers this navigation.
     */
    @Override
    @FXML
    public void moveToAllBooks(MouseEvent event) {
        try {
            BrowseController.setUser(user);
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/loginpage/browse.fxml"));
            Parent root = loader.load();
            BrowseController browseController = loader.getController();
            Scene browseScene = new Scene(root);
            stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(browseScene);
            stage.setResizable(true);
            stage.setFullScreen(true); // Set full screen mode after loading the new scene
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    /**
     * Navigates to the category page.
     * @param event MouseEvent that triggers this navigation.
     */
    @FXML
    @Override
    public void goToCategoryPage(MouseEvent event) {
        try {
            CategorieController.setUser(user);
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/loginpage/categoriePage.fxml"));
            Parent root = loader.load();
            Scene categoryScene = new Scene(root);
            stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(categoryScene);
            stage.setResizable(true);
            stage.setFullScreen(true); // Set full screen mode after loading the new scene
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    /**
     * Handles the event when the user wants to vote.
     * Navigates to the voting page.
     * @param event MouseEvent that triggers this navigation.
     */
    @FXML
    @Override
    public void goToVoting(MouseEvent event){
        try {
            VotingController.setUser(user);
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/loginpage/voting.fxml"));
            Parent parent = loader.load();
            VotingController votingController = loader.getController();
            Scene votingBookScene = new Scene(parent);
            stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(votingBookScene);
            stage.setResizable(true);
            stage.setFullScreen(true); // Set full screen mode after loading the new scene
            stage.show();
        } catch (IOException e){
            e.printStackTrace();
        }
    }
    /**
     * Navigates to the favourites page.
     * @param event MouseEvent that triggers this navigation.
     */
    @Override
    public void goToFavouritePage(MouseEvent event) {

    }

    /**
     * Handles the event when the user wants to see history .
     * Navigates to the history page.
     * @param event MouseEvent that triggers this navigation.
     */
    @FXML
    @Override
    public void moveToHistory(MouseEvent event){
        try {
            HistoryController.setUser(user);
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/com/example/loginpage/history.fxml"));
            Parent root = fxmlLoader.load();
            HistoryController historyController = fxmlLoader.getController();
            Scene topSection = new Scene(root);
            stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(topSection);
            stage.setResizable(true);
            stage.setFullScreen(true); // Set full screen mode after loading the new scene
            stage.show();
        }catch (IOException e){
            e.printStackTrace();
        }
    }
    /**
     * Handles an action event caused by selecting an exit option. This method creates a confirmation notification
     * A dialogue box that asks the user if they are sure they want to exit the application. If the user confirms, the method
     * attempts to serialise the current state of the workbooks to a file and then exits the application.
     * @param event The {@link ActionEvent} triggering this method, typically from a user interaction with a GUI element.
     */
    @FXML
    @Override
    public void Exit(ActionEvent event) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Exit Confirmation");
        alert.setHeaderText("Exit Application");
        alert.setContentText("Are you sure you want to exit?");

        alert.initModality(Modality.APPLICATION_MODAL);

        Optional<ButtonType> result = alert.showAndWait();
        if (result.isPresent() && result.get() == ButtonType.OK) {
            try {
                // Serialize the books before exiting
                if (getBooksToSerialize() != null) {
                    SerializationBooks.serializeLatestBooks(getBooksToSerialize(), "latestBooks.ser");
                }
                SerializationForCountOfPeople.saveCount(User.getCountOfPeople());
            } catch (IOException e) {
                e.printStackTrace();
                // Handle exception, possibly notify the user that data saving failed
                Alert errorAlert = new Alert(Alert.AlertType.ERROR);
                errorAlert.setTitle("Error");
                errorAlert.setHeaderText("Save Error");
                errorAlert.setContentText("Failed to save book data!");
                errorAlert.showAndWait();
            }
            Platform.exit();
            System.exit(0);
        }
    }
    /**
     * Handles the event when the user wants to quiz.
     * Navigates to the quiz page.
     * @param event MouseEvent that triggers this navigation.
     */
    @FXML
    @Override
    public void moveToQuiz(MouseEvent event){
        try {
            QuizPageController.setUser(user);
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/loginpage/quizPage.fxml"));
            Parent root = loader.load();
            Scene quizScene = new Scene(root);
            stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(quizScene);
            stage.setResizable(true);
            stage.setFullScreen(true); // Set full screen mode after loading the new scene
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
