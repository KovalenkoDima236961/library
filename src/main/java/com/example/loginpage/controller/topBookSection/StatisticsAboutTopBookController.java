package com.example.loginpage.controller.topBookSection;

import com.example.loginpage.controller.dao.BookDAO;
import com.example.loginpage.controller.mainClient.CardController;
import com.example.loginpage.controller.mainClient.MainClientConroller;
import com.example.loginpage.controller.profile.ProfileController;
import com.example.loginpage.controller.topSection.TopSectionController;
import com.example.loginpage.module.books.Book;
import com.example.loginpage.module.users.User;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
/**
 * A controller class for displaying statistics about the most popular books.
 * This controller receives and displays information about the most popular books.
 */
public class StatisticsAboutTopBookController implements Initializable {
    /**
     * Layout for displaying cards of top books
     */
    @FXML
    private HBox cardLayout;
    /**
     * List of top books retrieved from the database
     */
    private List<Book> topBooks;
    /**
     * The current user
     */
    private static User user;
    /**
     * Data Access Object for interacting with the book database
     */
    private BookDAO bookDAO;
    /**
     * Retrieves the current user.
     * @return The current user.
     */
    public static User getUser() {
        return user;
    }
    /**
     * Constructor for initializing the BookDAO.
     */
    public StatisticsAboutTopBookController(){
        bookDAO = BookDAO.getInstance();
    }
    /**
     * Sets the current user.
     * @param user The user to set.
     */
    public static void setUser(User user) {
        StatisticsAboutTopBookController.user = user;
    }
    /**
     * Initializes the controller by retrieving and displaying statistics about top books.
     * @param url The location used to resolve relative paths for the root object, or null if the location is not known.
     * @param resourceBundle The resources used to localize the root object, or null if the root object was not localized.
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        topBooks = bookDAO.topBooks();
        try {
            for(Book book : topBooks){
                FXMLLoader fxmlLoader = new FXMLLoader();
                fxmlLoader.setLocation(getClass().getResource("/com/example/loginpage/card.fxml"));
                HBox cardBox = fxmlLoader.load();
                CardController cardController = fxmlLoader.getController();
                cardController.setData(book);
                cardLayout.getChildren().add(cardBox);
            }
        }catch (IOException e){
            e.printStackTrace();
        }
    }
    /**
     * Handles the event when moving to the main page.
     * @param event The MouseEvent triggering the action.
     */
    @FXML
    void MoveToMainPage(MouseEvent event) {
        try {
            TopSectionController.setUser(user);
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/loginpage/topSection.fxml"));
            Parent root = loader.load();
            Scene mainClient = new Scene(root);
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(mainClient);
            stage.setResizable(true);
            stage.setFullScreen(true); // Set full screen mode after loading the new scene
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
