package com.example.loginpage.controller.browse;

import com.example.loginpage.controller.categorie.CategorieController;
import com.example.loginpage.controller.dao.BookDAO;
import com.example.loginpage.controller.favourites.FavouritesController;
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
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;

import static com.example.loginpage.HelloApplication.getBooksToSerialize;

/**
 * Controller of the view interface in the application, controlling the display of books and navigation between different sections of the application.
 * Implements {@link Initializable} for initialising FXML components and {@link Movement} for page navigation.
 */
public class BrowseController implements Initializable, Movement {
    /**
     * A container for displaying books in a grid format.
     * This GridPane is dynamically populated with book data, displaying
     * Each book in its own cell in the grid.
     */
    @FXML
    private GridPane bookContainer;
    /**
     * A label for displaying username
     */
    @FXML
    private Label nameLabel;
    /**
     * A text field for entering search criteria to filter books.
     * Users can enter information in this field to filter the books displayed on the screen
     * based on title
     */
    @FXML
    private TextField filterField;//Search book
    /**
     * A list containing all the books currently available for viewing.
     * This list is used to populate the bookContainer and is updated
     * based on the filter criteria entered by the user in the filterField.
     */
    private List<Book> allBooks;
    /**
     * A static user instance representing the user who is currently using the application.
     */
    private static User user;
    /**
     * A Data Access Object (DAO) to interact with the book data in the database.
     * This object is used to retrieve or manipulate the workbook data required for the viewing functionality.
     */
    private BookDAO bookDAO;
    /**
     * JavaFX Stage, which used to display and control different windows and
     * Dialogues related to viewing books, such as book information.
     */
    private Stage stage;

    /**
     * Initialises a new instance of BrowseController, setting dependencies like BookDAO.
     */
    public BrowseController(){
        bookDAO = BookDAO.getInstance();
    }
    /**
     * Setter for the current user.
     * @param user1 The user to set.
     */
    public static void setUser(User user1) {
        user = user1;
    }
    /**
     * Getter for the current user.
     * @return The current user.
     */
    public static User getUser() {
        return user;
    }

    /**
     * Navigates to the main client page on user action.
     * @param event MouseEvent that triggers this navigation.
     */
    @FXML
    @Override
    public void moveToMainPage(MouseEvent event) {
        MainClientConroller.setUser(user);
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/loginpage/mainClient.fxml"));
        Parent root = null;
        try {
            root = loader.load();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        MainClientConroller mainClientConroller = loader.getController();
        Scene profileScene = new Scene(root);
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(profileScene);
        stage.setResizable(true);
        stage.setFullScreen(true);
        stage.show();
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
     * Handles the event when the user wants to see all books.
     * Navigates to the Browse page.
     * @param event MouseEvent that triggers this navigation.
     */
    @Override
    public void moveToAllBooks(MouseEvent event) {}

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
            stage.setFullScreen(true);
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
            Scene topBookScene = new Scene(root);
            stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(topBookScene);
            stage.setResizable(true);
            stage.setFullScreen(true);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    /**
     * Initializes controller components, sets up book display and search filter functionality.
     * @param url URL for the root object.
     * @param resourceBundle ResourceBundle used to localize the root object.
     */
    @FXML
    public void initialize(URL url, ResourceBundle resourceBundle){
        nameLabel.setText(user.getName());
        allBooks = bookDAO.index();
        displayBooks(allBooks);

        // Setting up an event handler for the search text input field
        filterField.textProperty().addListener((observable, oldValue, newValue) -> {
            // Filter books based on the entered text
            List<Book> filteredBooks = filterBooks(newValue);

            // Display filtered books
            displayBooks(filteredBooks);
        });
    }
    // Метод для фільтрації книг за назвою
    /**
     * Filters the list of books based on the provided search text.
     * @param searchText Text to filter the books.
     * @return List of books that match the search criteria.
     */
    private List<Book> filterBooks(String searchText) {
        searchText = searchText.trim();
        List<Book> filteredBooks = new ArrayList<>();
        if (searchText.isEmpty()) {
            return allBooks;
        }

        String[] words = searchText.split("\\s+");

        for (Book book : allBooks) {
            boolean containsAllWords = true;
            for (String word : words) {
                if (!book.getTitle().toLowerCase().contains(word.toLowerCase())) {
                    containsAllWords = false;
                    break;
                }
            }
            if (containsAllWords) {
                filteredBooks.add(book);
            }
        }

        return filteredBooks;
    }
    /**
     * Displays a list of books within the application's UI.
     * @param books List of books to display.
     */
    private void displayBooks(List<Book> books) {
        bookContainer.getChildren().clear();

        int column = 0;
        int row = 1;
        for (Book book : books) {
            FXMLLoader fxmlLoader = new FXMLLoader();
            fxmlLoader.setLocation(getClass().getResource("/com/example/loginpage/book.fxml"));
            VBox bookBox;
            try {
                bookBox = fxmlLoader.load();
                BookController bookController = fxmlLoader.getController();
                bookController.setData(book);
                bookBox.setOnMouseClicked(event -> ControllerForEveryBook.handleBookClicked(event, bookContainer.getScene(), book, user,null,null));

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

}
