package com.example.loginpage.controller.quizSection;


import com.example.loginpage.controller.browse.BrowseController;
import com.example.loginpage.controller.categorie.CategorieController;
import com.example.loginpage.controller.favourites.FavouritesController;
import com.example.loginpage.controller.history.HistoryController;
import com.example.loginpage.controller.interf.Movement;
import com.example.loginpage.controller.mainClient.MainClientConroller;
import com.example.loginpage.controller.profile.ProfileController;
import com.example.loginpage.controller.serialization.SerializationBooks;
import com.example.loginpage.controller.serialization.SerializationForCountOfPeople;
import com.example.loginpage.controller.topBookSection.TopBooksController;
import com.example.loginpage.controller.voting.VotingController;
import com.example.loginpage.module.users.User;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

import static com.example.loginpage.HelloApplication.getBooksToSerialize;

// Треба визвати QuizController
/**
 * A controller class for the quiz page interface.
 * This controller controls the navigation and launch of the quiz.
 */
public class QuizPageController implements Movement,Initializable {
    /**
     * The current user viewing the profile
     */
    private static User user;
    /**
     * QuizContoller
     */
    private QuizController quizController;
    /**
     * Name label for username
     */
    @FXML
    private Label nameLabel;
    /**
     * Stage JavaFX
     */
    private Stage stage;
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
     * Default constructor for the QuizPageController class.
     */
    public QuizPageController(){}

    /**
     * Handles the event when moving to the top books section.
     * @param event The MouseEvent triggering the action.
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
     * Handles the event when moving to the main page.
     * @param event The MouseEvent triggering the action.
     */
    @FXML
    @Override
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
     * Handles the event when navigating to the profile page.
     * @param event The MouseEvent triggering the action.
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
     * Handles the start button action to initiate the quiz.
     * @param event The ActionEvent triggering the action.
     */
    @FXML
    void start(ActionEvent event) {
        try {
            System.out.println("Loading quiz...");

            // Load the FXML for the quiz
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/loginpage/quizGameField.fxml"));
            Parent root = loader.load();

            // Retrieve the controller for the quiz and set the user
            quizController = loader.getController();
            quizController.setUser(user);

            // Create a new Stage for the quiz
            Stage quizStage = new Stage();
            quizStage.setScene(new Scene(root));
            quizStage.setTitle("Quiz Game");

            // Set this stage as modal and specify the owner window
            quizStage.initModality(Modality.APPLICATION_MODAL);
            quizStage.initOwner(((Node) event.getSource()).getScene().getWindow());

            // Show the quiz window
            quizStage.show();

        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Failed to load the quiz game field.");
        }
    }
    /**
     * Handles the event when navigating to the all books page.
     * @param event The MouseEvent triggering the action.
     */
    @FXML
    @Override
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
     * Handles the event when navigating to the category page.
     * @param event The MouseEvent triggering the action.
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
     * Initializes the controller after its root element has been completely processed.
     * @param location  The location used to resolve relative paths for the root object, or null if the location is not known.
     * @param resources  The resources used to localize the root object, or null if the root object was not localized.
     */
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        nameLabel.setText(user.getName());
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
    /**
     * Navigates to the favourites page.
     * @param event MouseEvent that triggers this navigation.
     */
    @FXML
    @Override
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
     * Handles the event when the user wants to quiz.
     * Navigates to the quiz page.
     * @param event MouseEvent that triggers this navigation.
     */
    @Override
    public void moveToQuiz(MouseEvent event) {

    }

}
