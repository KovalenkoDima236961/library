package com.example.loginpage.controller.voting;

import com.example.loginpage.controller.AllPages;
import com.example.loginpage.controller.browse.BrowseController;
import com.example.loginpage.controller.browse.ControllerForEveryBook;
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
import com.example.loginpage.controller.topSection.TopSectionController;
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
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.*;

import javafx.scene.control.Label;
import javafx.scene.layout.VBox;

import static com.example.loginpage.HelloApplication.getBooksToSerialize;

/**
 * Controller class for the voting functionality in the application.
 */
public class VotingController implements Initializable, Movement {
    /**
     * The GridPane container for displaying books.
     */
    @FXML
    private GridPane bookContainer;
    /**
     * The list of all books retrieved from the database.
     */
    private List<Book> allBooks;
    /**
     * The currently logged-in user.
     */
    private static User user;
    /**
     * An instance of the BookDAO class to interact with the database.
     */
    private BookDAO bookDAO;
    /**
     * The book selected by the user.
     */
    private Book selectedBook;
    /**
     * The genre selected by the user.
     */
    private String selectedGenre;
    /**
     * The author selected by the user.
     */
    private String selectedAuthor;
    @FXML
    private Label nameLabel;
    private Stage stage;
    /**
     * Flag indicating whether a button is currently pressed.
     */
    private boolean isButtonPressed = false;
    /**
     * Flag indicating whether the button was pressed for the first time.
     */
    private boolean isFirstButtonPress = true;
    /**
     * The count of button presses.
     */
    private int buttonPressCount = 0;
    /**
     * Constructor for the VotingController class.
     * Initializes the BookDAO instance.
     */
    public VotingController(){
        bookDAO = BookDAO.getInstance();
    }
    /**
     * Sets the current user.
     * @param user The user to set.
     */
    public static void setUser(User user) {
        VotingController.user = user;
    }
    /**
     * Retrieves the current user.
     * @return The current user.
     */
    public static User getUser() {
        return user;
    }
    /**
     * Initializes the controller after its root element has been completely processed.
     * @param url The location used to resolve relative paths for the root object, or null if the location is not known.
     * @param resourceBundle The resources used to localize the root object, or null if the root object was not localized.
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle){
        nameLabel.setText(user.getName());
        displayBooks();
    }
    /**
     * Displays the genres available for voting.
     */
    private void displayGenres() {
        Set<String> genres = new HashSet<>(bookDAO.getAllGenres());

        int column = 0;
        int row = 1;
        for(String genre : genres) {
            VBox genreBox = new VBox();
            Label genreLabel = new Label(genre);
            genreBox.getChildren().add(genreLabel);

            if (genre.equals(selectedGenre)) {
                genreBox.setStyle("-fx-background-color: red;");
            }

            genreBox.setOnMouseClicked(event -> {
                selectedGenre = genre;
                handleGenreClick(genre); // Додайте обробник подій для жанрів
            });

            if(column == 6) {
                column = 0;
                ++row;
            }
            bookContainer.add(genreBox, column++, row);
            GridPane.setMargin(genreBox, new Insets(10));
        }
    }
    /**
     * Handles the click event on a genre.
     *
     * @param genre The genre that was clicked.
     */
    private void handleGenreClick(String genre) {
        // Збереження обраного жанру
        selectedGenre = genre;
        // Оновлення стилю обраних жанрів
        updateGenreStyles();
    }
    /**
     * Updates the visual style of the genre boxes based on selection.
     */
    private void updateGenreStyles() {
        for (Node node : bookContainer.getChildren()) {
            if (node instanceof VBox) {
                VBox genreBox = (VBox) node;
                Label label = (Label) genreBox.getChildren().get(0); // Assuming label is the first child
                String genre = label.getText();
                if (genre.equals(selectedGenre)) {
                    genreBox.setStyle("-fx-background-color: red;");
                } else {
                    genreBox.setStyle("-fx-background-color: transparent;");
                }
            }
        }
    }
    /**
     * Displays the authors available for voting.
     */
    private void displayAuthors() {
        Set<String> authors = bookDAO.getAllAuthors();

        int column = 0;
        int row = 1;
        for (String author : authors) {
            VBox authorBox = new VBox();
            Label authorLabel = new Label(author);
            authorBox.getChildren().add(authorLabel);

            if (author.equals(selectedAuthor)) {
                authorBox.setStyle("-fx-background-color: red;");
            }

            authorBox.setOnMouseClicked(event -> {
                selectedAuthor = author;
                handleAuthorClick(author); // Додайте обробник подій для авторів
            });

            if (column == 6) {
                column = 0;
                ++row;
            }
            bookContainer.add(authorBox, column++, row);
            GridPane.setMargin(authorBox, new Insets(10));
        }
    }
    /**
     * Handles the click event on an author.
     *
     * @param author The author that was clicked.
     */
    private void handleAuthorClick(String author) {
        // Збереження обраного автора
        selectedAuthor = author;
        // Оновлення стилю обраних авторів
        updateAuthorStyles();
    }
    /**
     * Updates the visual style of the author boxes based on selection.
     */
    private void updateAuthorStyles() {
        for (Node node : bookContainer.getChildren()) {
            if (node instanceof VBox) {
                VBox authorBox = (VBox) node;
                Label label = (Label) authorBox.getChildren().get(0); // Assuming label is the first child
                String author = label.getText();
                if (author.equals(selectedAuthor)) {
                    authorBox.setStyle("-fx-background-color: red;");
                } else {
                    authorBox.setStyle("-fx-background-color: transparent;");
                }
            }
        }
    }
    /**
     * Displays all available books for voting.
     */
    private void displayBooks() {
        int column = 0;
        int row = 1;
        allBooks = bookDAO.index();
        try {
            for(Book book : allBooks){
                FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/com/example/loginpage/book.fxml"));
                VBox bookBox = fxmlLoader.load();
                BookController bookController = fxmlLoader.getController();
                bookController.setData(book);

                bookBox.setOnMouseClicked(event -> {
                    selectedBook = book;
                    handleBookClick(bookBox,book);
                });

                if(column == 6){
                    column = 0;
                    ++row;
                }
                bookContainer.add(bookBox,column++,row);
                GridPane.setMargin(bookBox,new Insets(10));
            }
        }catch (IOException e){
            e.printStackTrace();
        }
    }
    /**
     * Handles the click event on a book.
     *
     * @param bookBox The VBox representing the clicked book.
     * @param book    The book that was clicked.
     */
    private void handleBookClick(VBox bookBox,Book book){
        clearSelection();
        if(isButtonPressed){
            bookBox.setStyle("-fx-background-color: lightblue;");
            selectedBook = book;
        } else {
            ControllerForEveryBook.handleBookClicked(new MouseEvent(MouseEvent.MOUSE_CLICKED, 0, 0, 0, 0, MouseButton.PRIMARY, 1, false, false, false, false, true, false, false, true, false, false, null), bookContainer.getScene(), book, user,null,null);
        }
    }
    /**
     * Handles the selection of the top book.
     *
     * @param event The ActionEvent object representing the selection event.
     */
    @FXML
    public void selectTopBook(ActionEvent event) {
        isButtonPressed = !isButtonPressed;
    }
    /**
     * Handles the action to save the result and proceed with the next step based on the button press count.
     *
     * @param event The ActionEvent object representing the button press event.
     * @throws IOException If an error occurs while navigating or displaying information.
     * @throws SQLException if an error occurs while finding the book information in DAO
     */
    @FXML
    public void saveResult(ActionEvent event) throws IOException,SQLException {
        isButtonPressed = !isButtonPressed;
        if(isFirstButtonPress) {
            isFirstButtonPress = false;
            bookContainer.getChildren().clear(); // Clear the container before displaying genres
            displayGenres();
        } else if (buttonPressCount == 1) {
            bookContainer.getChildren().clear(); // Clear the container before displaying authors
            displayAuthors();
        } else if (buttonPressCount == 2) {
            // Clear the container before displaying selected information
            bookContainer.getChildren().clear();
            // Display selected book, genre, and author
            displaySelectedInfo();
        } else if (buttonPressCount == 3) {
            // Navigate to the main page
            navigateToMainPage(event);
        }
        // Increment button press count
        buttonPressCount++;

    }
    /**
     * Clears the selection by resetting the background color of selected books.
     */
    private void clearSelection(){
        if(selectedBook != null){
            for(Node n : bookContainer.getChildren()){
                if(n instanceof VBox){
                    ((VBox) n).setStyle("-fx-background-color: transparent;");
                }
            }
        }
        selectedBook = null;
    }
    /**
     * Displays the selected book's information, including genre and author.
     *
     * @throws IOException If an error occurs while loading the book information.
     * @throws SQLException if an error occurs while finding the book information in DAO
     */
    private void displaySelectedInfo() throws IOException, SQLException {
        VBox selectedInfoBox = new VBox();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/loginpage/book.fxml"));
        VBox bookBox = loader.load();
        BookController bookController = loader.getController();
        bookController.setData(selectedBook);

        bookDAO.incrementBookVote(selectedBook);

        Book firstAuthorBook = bookDAO.findFirstBookByAuthor(selectedAuthor);
        if (firstAuthorBook != null) {
            bookDAO.incrementBookVote(firstAuthorBook);
        }

        Book firstGenreBook = bookDAO.findFirstBookByGenre(selectedGenre);
        if (firstGenreBook != null) {
            bookDAO.incrementBookVote(firstGenreBook);
        }

        Label selectedGenreLabel = new Label("Selected Genre: " + selectedGenre);
        selectedGenreLabel.setStyle("-fx-font-size: 16px; -fx-text-fill: #333333; -fx-font-family: 'Arial'; -fx-padding: 5px; -fx-font-weight: bold; -fx-background-color: #f0f0f0; -fx-border-color: #cccccc; -fx-border-width: 1px; -fx-border-radius: 5px; -fx-padding: 10px; -fx-margin: 5px;");
        Label selectedAuthorLabel = new Label("Selected Author: " + selectedAuthor);
        selectedAuthorLabel.setStyle("-fx-font-size: 16px; -fx-text-fill: #333333; -fx-font-family: 'Arial'; -fx-padding: 5px; -fx-font-weight: bold; -fx-background-color: #f0f0f0; -fx-border-color: #cccccc; -fx-border-width: 1px; -fx-border-radius: 5px; -fx-padding: 10px; -fx-margin: 5px;");

        VBox.setMargin(bookBox, new Insets(400,0,0,0));
        VBox.setMargin(selectedInfoBox, new Insets(40));
        selectedInfoBox.getChildren().addAll(bookBox, selectedGenreLabel, selectedAuthorLabel);

        bookContainer.getChildren().clear();
        bookContainer.getChildren().add(selectedInfoBox);
    }
    /**
     * Navigates to the main page of the application.
     *
     * @param event The ActionEvent object representing the navigation event.
     * @throws IOException If an error occurs while navigating to the main page.
     */
    private void navigateToMainPage(ActionEvent event) throws IOException {
        TopSectionController.setUser(user);
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/loginpage/topSection.fxml"));
        Parent root = loader.load();
        TopSectionController topSectionController = loader.getController();

        Scene scene = new Scene(root);
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(scene);
        stage.setResizable(true);
        stage.setFullScreen(true);
        stage.show();
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
            stage.setFullScreen(true);
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
        MainClientConroller.setUser(user);
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/loginpage/mainClient.fxml"));
        Parent root = null;
        try {
            root = loader.load();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        MainClientConroller mainClientController = loader.getController();
        Scene mainScene = new Scene(root);
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(mainScene);
        stage.setResizable(true);
        stage.setFullScreen(true);
        stage.show();
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
     * Navigates to the browse page.
     * @param event MouseEvent that triggers this navigation.
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

    @Override
    public void goToVoting(MouseEvent event) {

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
            stage.setFullScreen(true);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

