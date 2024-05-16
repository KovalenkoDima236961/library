package com.example.loginpage.controller.mainClient;

import com.example.loginpage.controller.browse.BrowseController;
import com.example.loginpage.controller.browse.ControllerForEveryBook;
import com.example.loginpage.controller.categorie.CategorieController;
import com.example.loginpage.controller.dao.BookDAO;
import com.example.loginpage.controller.dao.UserDAO;
import com.example.loginpage.controller.favourites.FavouritesController;
import com.example.loginpage.controller.history.HistoryController;
import com.example.loginpage.controller.interf.Movement;
import com.example.loginpage.controller.profile.ProfileController;
import com.example.loginpage.controller.quizSection.QuizPageController;
import com.example.loginpage.controller.serialization.SerializationBooks;
import com.example.loginpage.controller.serialization.SerializationForCountOfPeople;
import com.example.loginpage.controller.topBookSection.TopBooksController;
import com.example.loginpage.controller.topSection.TopSectionController;
import com.example.loginpage.controller.voting.VotingController;
import com.example.loginpage.module.books.Book;
import com.example.loginpage.module.books.typeofbooks.DramaBook;
import com.example.loginpage.module.users.User;
import javafx.application.Platform;
import javafx.collections.ObservableList;
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
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import static com.example.loginpage.HelloApplication.getBooksToSerialize;

/**
 * Controller for managing the main client functionality.
 */
public class MainClientConroller implements Initializable, Movement {
    /**
     * HBox container for displaying book cards
     */
    @FXML
    private HBox cardLayoout;
    /**
     * GridPane container for displaying books
     */
    @FXML
    private GridPane bookContainer;
    /**
     * Label displaying the username
     */
    @FXML
    private Label usernameLabel;
    /**
     *  TextField for searching books
     */
    @FXML
    private TextField filterField;
    /**
     * List to store recently added books
     */
    private List<Book> recentlyAdded;
    /**
     * List to store recommended books
     */
    private List<Book> recommended;
    /**
     * Current user
     */
    private static User user;
    /**
     * Data access object for user-related operations
     */
    private UserDAO userDAO;
    /**
     * Data access object for book-related operation
     */
    private BookDAO bookDAO;
    /**
     * Scene object for the current window
     */
    private Scene scene;
    /**
     * Stage object for the current window
     */
    private Stage stage;
    /**
     * Parent object for the current window
     */
    private Parent parent;
    private ExecutorService executorService = Executors.newFixedThreadPool(1);
    /**
     * Default constructor initializing UserDAO and BookDAO objects.
     */
    public MainClientConroller(){
        userDAO = new UserDAO();
        bookDAO = new BookDAO();
    }
    /**
     * Sets the current user.
     * @param user1 The user to set.
     */
    public static void setUser(User user1) {
        user = user1;
    }
    /**
     * Retrieves the current user.
     * @return The current user.
     */
    public static User getUser() {
        return user;
    }

    /**
     * ImageView for displaying the user's profile image
     */
    @FXML
    private ImageView profileImage;
    /**
     * Handles the event when the user wants to see all books.
     * Navigates to the Browse page.
     * @param event MouseEvent that triggers this navigation.
     */
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
     * Handles the event when the user wants to quiz.
     * Navigates to the quiz page.
     * @param event MouseEvent that triggers this navigation.
     */
    @FXML
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
    /**
     * Navigates to the favourites page.
     * @param event MouseEvent that triggers this navigation.
     */
    @FXML
    public void goToFavouritePage(MouseEvent event){
        try {
            FavouritesController.setUser(user);
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/loginpage/favourite.fxml"));
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
    @FXML
    @Override
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
     * Handles the event when the user wants to top books .
     * Navigates to the top books page.
     * @param event MouseEvent that triggers this navigation.
     */
    @FXML
    @Override
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
     * Handles the event when the user wants to main .
     * Navigates to the main page.
     * @param event MouseEvent that triggers this navigation.
     */
    @Override
    public void moveToMainPage(MouseEvent event) {
        System.out.println("It already here");
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
     * Handles the event when the user wants to top section .
     * Navigates to the top section.
     * @param event MouseEvent that triggers this navigation.
     */
    @FXML
    private void moveToTopSection(MouseEvent event){
        try {
            TopSectionController.setUser(user);
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/com/example/loginpage/topSection.fxml"));
            Parent root = fxmlLoader.load();
            TopSectionController topSectionController = fxmlLoader.getController();
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
     * Initializes the controller after its root element has been completely processed.
     * @param location  The location used to resolve relative paths for the root object, or null if the location is not known.
     * @param resourceBundle  The resources used to localize the root object, or null if the root object was not localized.
     */
    @Override
    public void initialize(URL location, ResourceBundle resourceBundle) {
        usernameLabel.setText(user.getName());
        System.out.println(user);

        CompletableFuture.supplyAsync(() -> userDAO.getFavoriteBooks(user.getId()), executorService)
                .thenAccept(favorites -> {
                    Platform.runLater(() -> {
                        recentlyAdded = favorites;
                        populateRecentlyAddedBooks();
                    });
                });

        Recommend recommend = new Recommend();
        CompletableFuture.supplyAsync(() -> new ArrayList<>(recommend.recommendBooks(user)), executorService)
                .thenAccept(recommendedBooks -> {
                    Platform.runLater(() -> {
                        recommended = recommendedBooks;
                        populateRecommendedBooks();
                    });
                });
    }
    public void refreshFavorites() {
        CompletableFuture.supplyAsync(() -> userDAO.getFavoriteBooks(user.getId()), executorService)
                .thenAccept(favorites -> {
                    Platform.runLater(() -> {
                        recentlyAdded = favorites;
                        populateRecentlyAddedBooks();
                    });
                });
    }

    private void populateRecentlyAddedBooks() {
        cardLayoout.getChildren().clear(); // Clear existing cards
        try {
            for (Book value : recentlyAdded) {
                FXMLLoader fxmlLoader = new FXMLLoader();
                fxmlLoader.setLocation(getClass().getResource("/com/example/loginpage/card.fxml"));
                HBox cardBox = fxmlLoader.load();
                CardController cardController = fxmlLoader.getController();
                cardController.setData(value);
                cardLayoout.getChildren().add(cardBox);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    private void populateRecommendedBooks() {
        int column = 0;
        int row = 1;
        for (Book book : recommended) {
            FXMLLoader fxmlLoader = new FXMLLoader();
            fxmlLoader.setLocation(getClass().getResource("/com/example/loginpage/book.fxml"));
            VBox bookBox;
            try {
                bookBox = fxmlLoader.load();
                BookController bookController = fxmlLoader.getController();
                bookController.setData(book);

                bookBox.setOnMouseClicked(event -> ControllerForEveryBook.handleBookClicked(event, bookContainer.getScene(), book, user, this, null)); // Pass the main controller reference
                if (column == 6) {
                    column = 0;
                    ++row;
                }
                bookContainer.add(bookBox, column++, row);
                GridPane.setMargin(bookBox, new Insets(10));
            } catch (IOException e) {
                e.printStackTrace();
            }
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

        // Ensures that this alert must be responded to before the user can interact with other application windows.
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
}
