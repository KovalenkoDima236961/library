package com.example.loginpage.controller.quizSection;

import com.example.loginpage.controller.browse.BrowseController;
import com.example.loginpage.controller.browse.ControllerForEveryBook;
import com.example.loginpage.controller.categorie.CategorieController;
import com.example.loginpage.controller.dao.BookDAO;
import com.example.loginpage.controller.interf.Movement;
import com.example.loginpage.controller.mainClient.BookController;
import com.example.loginpage.controller.mainClient.MainClientConroller;
import com.example.loginpage.controller.profile.ProfileController;
import com.example.loginpage.controller.topBookSection.TopBooksController;
import com.example.loginpage.module.books.Book;
import com.example.loginpage.module.users.User;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.ResourceBundle;

/**
 * Manages the quiz section of the app, guiding the tournament-style book selection process.
 * Users go through several rounds, selecting their preferred books from pairs, until the final book is chosen.
 * This controller initialises with a predefined number of books and halves the selection in each subsequent round.
 * round until only one book remains. It interacts with {@link BookDAO} to retrieve books and uses {@link BookController}
 * to display information about individual books.
 *
 * @see BookDAO
 * @see BookController
 */
public class QuizController implements Initializable{
    /**
     * DAO for retrieving books from the database
     */
    private BookDAO bookDAO;
    /**
     * The user participating in the quiz
     */
    private static User user;
    /**
     * List of all books available for the quiz
     */
    private List<Book> allBooks;
    /**
     *  List of books for the current round
     */
    private List<Book> currentRoundBooks = new ArrayList<>();
    /**
     * Iterator for traversing the list of books
     */
    private Iterator<Book> bookIterator;

    /**
     * GridPane for displaying books
     */
    @FXML
    private GridPane bookContainer;
    /**
     * Label displaying the current round number
     */
    @FXML
    private Label numberOfRound;
    /**
     * Default constructor initializing the book DAO.
     */
    public QuizController() {
        bookDAO = BookDAO.getInstance();
    }
    /**
     * Sets the current user for the quiz.
     * @param user The user participating in the quiz.
     */
    public static void setUser(User user) {
        QuizController.user = user;
    }
    /**
     * Initialises the quiz controller, setting the initial list of books and showing the first pair.
     * This method takes a predefined number of books (24) and prepares the view for the first round.
     *
     * @param url The location used to resolve relative paths for the root object, or null if not known.
     * @param resourceBundle The resources used to localize the root object, or null if the root object was not localized.
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        allBooks = bookDAO.returnRandomTwentyForBook(); // Assuming the DAO returns exactly 24 books
        bookIterator = allBooks.iterator();
        updateRoundLabel(1); // Start with round 1
        showNextPair();
    }
    /**
     * Displays the next pair of books from the current list. Clears previous entries and updates the UI with new book details.
     */
    private void showNextPair() {
        if (!bookIterator.hasNext()) {
            prepareNextRound();
            return;
        }

        bookContainer.getChildren().clear();

        if (bookIterator.hasNext()) {
            displayBook(bookIterator.next(), 0);
        }

        if (bookIterator.hasNext()) {
            displayBook(bookIterator.next(), 1);
        }
    }
    /**
     * Displays a single book in the specified column of the GridPane.
     * Loads the book details into a VBox through an FXML loader and sets up click event handling.
     *
     * @param book The book to display.
     * @param column The column in the GridPane where the book should be displayed.
     */
    private void displayBook(Book book, int column) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/com/example/loginpage/book.fxml"));
            VBox bookBox = fxmlLoader.load();
            BookController bookController = fxmlLoader.getController();
            bookController.setData(book);

            bookBox.setOnMouseClicked(event -> handleBookClick(book));
            bookContainer.add(bookBox, column, 0);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    /**
     * Handles user clicks on a book, adding the selected book to the current round's selection and preparing the next pair.
     *
     * @param book The book that was clicked.
     */
    private void handleBookClick(Book book) {
        currentRoundBooks.add(book);
        System.out.println("Selected book: " + book.getTitle());

        if (!bookIterator.hasNext()) {
            prepareNextRound();
        } else {
            showNextPair();
        }
    }
    /**
     * Prepares for the next round of book selection or ends the quiz if there is only one book left.
     * Resets the book iterator with the selected books and clears the current round's selections.
     */
    private void prepareNextRound() {
        if (currentRoundBooks.size() == 1) {
            endQuiz();
            return;
        }

        // Setup for the next round
        System.out.println("Preparing next round with " + currentRoundBooks.size() + " books");
        allBooks = new ArrayList<>(currentRoundBooks); // Prepare the next round's books
        bookIterator = allBooks.iterator();
        currentRoundBooks.clear();
        updateRoundLabel(allBooks.size() / 2);
        showNextPair();
    }
    /**
     * Updates the round label with the current round number and the number of books left to choose from.
     *
     * @param round The current round number.
     */
    private void updateRoundLabel(int round) {
        numberOfRound.setText("Round " + round + ": Choose from " + allBooks.size() + " Books");
    }


    /**
     * Ends the quiz by displaying the last book and adding a button to close the quiz.
     * Clears the book display container and uses {@link #displayBook(Book, int)} to display the final selection.
     */
    private void endQuiz() {
        if (currentRoundBooks.isEmpty()) {
            System.out.println("No books left to display.");
            return;
        }

        Book finalBook = currentRoundBooks.get(0);
        System.out.println("Quiz completed! Final selection: " + finalBook.getTitle());

        bookContainer.getChildren().clear();

        displayBook(finalBook, 1);

        Label finalSelectionLabel = new Label("Final Selection:");
        finalSelectionLabel.setStyle("-fx-font-size: 24px; -fx-font-weight: bold; -fx-text-fill: darkblue;");
        bookContainer.add(finalSelectionLabel, 0, 0); // Add the label at the top

        Button closeButton = new Button("Close Quiz");
        closeButton.setOnAction(event -> {
            Stage stage = (Stage) bookContainer.getScene().getWindow();
            stage.close();
        });
        bookContainer.add(closeButton, 0, 2);
    }
}