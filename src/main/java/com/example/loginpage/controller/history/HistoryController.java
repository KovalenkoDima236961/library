package com.example.loginpage.controller.history;

import com.example.loginpage.controller.browse.BrowseController;
import com.example.loginpage.controller.categorie.CategorieController;
import com.example.loginpage.controller.favourites.FavouritesController;
import com.example.loginpage.controller.interf.Movement;
import com.example.loginpage.controller.mainClient.BookController;
import com.example.loginpage.controller.mainClient.MainClientConroller;
import com.example.loginpage.controller.profile.ProfileController;
import com.example.loginpage.controller.quizSection.QuizPageController;
import com.example.loginpage.controller.serialization.SerializationBooks;
import com.example.loginpage.controller.serialization.SerializationForCountOfPeople;
import com.example.loginpage.controller.topBookSection.TopBooksController;
import com.example.loginpage.controller.topSection.TopSectionController;
import com.example.loginpage.controller.voting.VotingController;
import com.example.loginpage.module.books.Book;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

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

import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

import static com.example.loginpage.HelloApplication.getBooksToSerialize;

/**
 * The HistoryController class controls the functionality of the history page in the application.
 * It initialises the user's reading history and provides methods for navigating to other sections of the application.
 */
public class HistoryController implements Initializable, Movement {
    /**
     * GridPane to hold the books displayed in the history page
     */
    @FXML
    private GridPane bookContainer;
    /**
     * Label to display the username
     */
    @FXML
    private Label usernameLabel;
    /**
     * List to store the user's favourite history
     */
    private List<Book> history;
    /**
     * Static variable to hold the current user
     */
    private static User user;
    /**
     * Stage object to manage the application window
     */
    private Stage stage;
    /**
     * Sets the user for the HistoryController.
     * @param user1 The user to set.
     */
    public static void setUser(User user1) {
        user = user1;
    }
    /**
     * Default constructor for the HistoryController class.
     */
    public HistoryController(){}

    /**
     * Gets the user associated with the HistoryController.
     * @return The user associated with the controller.
     */
    public static User getUser() {
        return user;
    }

    /**
     * Initializes the controller after its root element has been completely processed.
     * @param location  The location used to resolve relative paths for the root object, or null if the location is not known.
     * @param resources The resources used to localize the root object, or null if the root object was not localized.
     */
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        System.out.println(user);
        usernameLabel.setText(user.getName());
        try {
            if(SerializationBooks.isFlag()) {
                history = SerializationBooks.deserializeLatestBooks("latestBooks.ser"); // Assume this method is correctly implemented
                System.out.println(history);
            }else {
                history = user.getLatestBook();
            }
            int column = 0;
            int row = 1;
            for (Book book : history) {
                FXMLLoader fxmlLoader = new FXMLLoader();
                fxmlLoader.setLocation(getClass().getResource("/com/example/loginpage/book.fxml"));
                VBox bookBox = fxmlLoader.load();
                BookController bookController = fxmlLoader.getController();
                bookController.setData(book);

                if(column == 6){
                    column = 0;
                    ++row;
                }
                bookContainer.add(bookBox,column++,row);
                GridPane.setMargin(bookBox,new Insets(10));
            }
        } catch (Exception e) {
            e.printStackTrace();
            // Handle errors, possibly show an alert to the user
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
     * Navigates to the top books section.
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
     * Navigates to the browse page.
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
     * Handles the event when the user wants to see history .
     * Navigates to the history page.
     * @param event MouseEvent that triggers this navigation.
     */
    @Override
    public void moveToHistory(MouseEvent event) {

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
     * Navigates to the quiz page.
     * @param event MouseEvent that triggers this navigation.
     */
    @FXML
    @Override
    public void moveToQuiz(MouseEvent event) {
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
    @Override
    public void goToFavouritePage(MouseEvent event) {
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
     * Navigates to the voting page.
     * @param event MouseEvent that triggers this navigation.
     */
    @FXML
    @Override
    public void goToVoting(MouseEvent event) {
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
     * Navigates to the top section page.
     * @param event MouseEvent that triggers this navigation.
     */
    @FXML
    public void moveToTopSection(MouseEvent event) {
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
}
