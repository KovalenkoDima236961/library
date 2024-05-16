package com.example.loginpage.controller.profile;

import com.example.loginpage.controller.dao.BookDAO;
import com.example.loginpage.controller.dao.UserDAO;
import com.example.loginpage.controller.factoryPattern.BookFactory;
import com.example.loginpage.controller.mainClient.MainClientConroller;
import com.example.loginpage.module.books.Book;
import com.example.loginpage.module.users.User;
import com.example.loginpage.module.users.typeofuser.Client;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

import javax.swing.*;
import java.io.IOException;
import java.net.URL;
import java.util.*;
/**
 * Controller class for the administrator profile interface.
 * This class provides functions for managing users and workbooks in the system,
 * including adding, deleting, searching, and displaying both entities.
 */
public class ProfileAdminController implements Initializable {
    /**
     * TextField for entering search keywords
     */
    @FXML
    private TextField filterField;
    /**
     * ListView for displaying users or books
     */
    @FXML
    private ListView<Object> list;

    /**
     * Label indicating whether the list displays users or books
     */
    @FXML
    private Label typeOfList;
    /**
     * Data access object for user-related operations
     */
    private UserDAO userDAO;
    /**
     * Data access object for book-related operations
     */
    private BookDAO bookDAO;
    /**
     * Flag to switch between displaying users or books
     */
    private boolean isChange = false;
    /**
     * The current user viewing the profile admin interface
     */
    private static User user;
    /**
     * Retrieves the current user.
     * @return The current user.
     */
    public static User getUser() {
        return user;
    }
    /**
     * Sets the current user.
     * @param user The user to set.
     */
    public static void setUser(User user) {
        ProfileAdminController.user = user;
    }
    /**
     * Default constructor initializes DAOs for interacting with user and book data.
     */
    public ProfileAdminController(){
        this.userDAO = new UserDAO();
        this.bookDAO = BookDAO.getInstance();
    }
    /**
     * Searches for users or books based on text entered into the filter field and updates the list view.
     * @param event The ActionEvent triggered by pressing the search button.
     */
    @FXML
    void search(ActionEvent event) {
        String searchText = filterField.getText().toLowerCase(); // Convert search text to lowercase for case-insensitive search
        list.getItems().clear();

        List<String> resultList = new ArrayList<>();

        // Search for users
        if (!isChange) {
            List<User> userList = userDAO.index();
            for (User user : userList) {
                // Check if the user's name contains the search text
                if (user.getName().toLowerCase().contains(searchText)) {
                    resultList.add(user.getName()); // Add user's name to the result list
                }
            }
        } else {
            List<Book> bookList = bookDAO.index();
            for (Book book : bookList) {
                // Check if the book's title contains the search text
                if (book.getTitle().toLowerCase().contains(searchText)) {
                    resultList.add(book.getTitle()); // Add book's title to the result list
                }
            }
        }
        // Add filtered names or titles to the list view
        list.getItems().addAll(resultList);
    }
    /**
     * Refreshes the ListView to display either users or books, depending on the current mode.
     */
    private void refreshListView() {
        list.getItems().clear(); // Очистити список
        // Завантаження списку користувачів знову
        List<String> userNames = new ArrayList<>();
        for (User user : userDAO.index()) {
            userNames.add(user.getName());
        }
        list.getItems().addAll(userNames); // Додати оновлені дані до списку
    }
    /**
     * Deletes a book from the database and updates the view.
     * @param event The ActionEvent triggered by pressing the delete book button.
     */
    @FXML
    void deleteBook(ActionEvent event) {
        Dialog<Integer> dialog = new Dialog<>();
        dialog.setTitle("Delete Book");
        dialog.setHeaderText("Select the book to delete:");

        // Set the button types.
        ButtonType deleteButtonType = new ButtonType("Delete", ButtonBar.ButtonData.OK_DONE);
        dialog.getDialogPane().getButtonTypes().addAll(deleteButtonType, ButtonType.CANCEL);

        // Create the layout.
        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(20, 150, 10, 10));

        // Create a ListView to display users.
        ListView<Book> bookList = new ListView<>();
        bookList.setPrefHeight(200);
        ObservableList<Book> items = FXCollections.observableArrayList();
        items.addAll(bookDAO.index());
        bookList.setItems(items);

        grid.add(new Label("Books:"), 0, 0);
        grid.add(bookList, 1, 0);

        dialog.getDialogPane().setContent(grid);

        Node deleteButton = dialog.getDialogPane().lookupButton(deleteButtonType);
        deleteButton.setDisable(true);

        bookList.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            deleteButton.setDisable(newValue == null);
        });

        Platform.runLater(bookList::requestFocus);

        // Convert the result to a user ID when the delete button is pressed.
        dialog.setResultConverter(dialogButton -> {
            if (dialogButton == deleteButtonType) {
                return bookList.getSelectionModel().getSelectedItem().getId();
            }
            return null;
        });

        Optional<Integer> result = dialog.showAndWait();

        int need = 0;
        result.ifPresent(bookId -> {
            boolean success = bookDAO.delete(bookId,need);
            if (success) {
                System.out.println("Book deleted with ID: " + bookId);
                items.removeIf(book -> book.getId()==(bookId));
            } else {
                System.out.println("No user found with ID: " + bookId);
            }
        });
    }
    /**
     * Deletes a user from the database and updates the view.
     * @param event The ActionEvent triggered by pressing the delete user button.
     */
    @FXML
    void deleteUser(ActionEvent event) {
        Dialog<Integer> dialog = new Dialog<>();
        dialog.setTitle("Delete User");
        dialog.setHeaderText("Select the user to delete:");

        ButtonType deleteButtonType = new ButtonType("Delete", ButtonBar.ButtonData.OK_DONE);
        dialog.getDialogPane().getButtonTypes().addAll(deleteButtonType, ButtonType.CANCEL);

        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(20, 150, 10, 10));

        ListView<User> userList = new ListView<>();
        userList.setPrefHeight(200);
        ObservableList<User> items = FXCollections.observableArrayList();
        items.addAll(userDAO.index());
        userList.setItems(items);

        grid.add(new Label("Users:"), 0, 0);
        grid.add(userList, 1, 0);

        dialog.getDialogPane().setContent(grid);

        // Initially disable the delete button until a user is selected.
        Node deleteButton = dialog.getDialogPane().lookupButton(deleteButtonType);
        deleteButton.setDisable(true);

        // Validation to enable the delete button when a user is selected.
        userList.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            deleteButton.setDisable(newValue == null);
        });

        Platform.runLater(userList::requestFocus);

        dialog.setResultConverter(dialogButton -> {
            if (dialogButton == deleteButtonType) {
                return userList.getSelectionModel().getSelectedItem().getId();
            }
            return null;
        });

        Optional<Integer> result = dialog.showAndWait();

        result.ifPresent(userId -> {
            boolean success = userDAO.delete(userId, user.getId());
            if (success) {
                System.out.println("User deleted with ID: " + userId);
                items.removeIf(user -> user.getId() == (userId));
            } else {
                System.out.println("No user found with ID: " + userId);
            }
        });
    }
    /**
     * Adds a book to the database
     * @param event The ActionEvent triggered by pressing the add book button.
     */
    @FXML
    void addBook(ActionEvent event){
        Dialog<Book> dialog = new Dialog<>();
        dialog.setTitle("Add Book");
        dialog.setHeaderText("Enter book details: ");
        ButtonType saveButtonType = new ButtonType("Save", ButtonBar.ButtonData.OK_DONE);
        dialog.getDialogPane().getButtonTypes().addAll(saveButtonType, ButtonType.CANCEL);

        GridPane grid = new GridPane();
        grid.setHgap(20);
        grid.setVgap(20);
        grid.setPadding(new Insets(20,150,10,10));

        TextField title = new TextField();
        title.setPromptText("Title");
        TextField author = new TextField();
        author.setPromptText("Author");
        TextField year = new TextField();
        year.setPromptText("Year");
        TextField genre = new TextField();
        genre.setPromptText("Genre");
        TextField description = new TextField();
        description.setPromptText("Description");
        TextField photoUrl = new TextField();
        photoUrl.setPromptText("Photo url");

        grid.add(new Label("Title:"), 0, 0);
        grid.add(title, 1, 0);
        grid.add(new Label("author:"), 0, 1);
        grid.add(author, 1, 1);
        grid.add(new Label("year:"), 0, 2);
        grid.add(year, 1, 2);
        grid.add(new Label("Genre:"), 0, 3);
        grid.add(genre, 1, 3);
        grid.add(new Label("Description:"), 0, 4);
        grid.add(description, 1, 4);
        grid.add(new Label("Photo Url:"), 0, 5);
        grid.add(photoUrl, 1, 5);


        dialog.getDialogPane().setContent(grid);

        Node saveButton = dialog.getDialogPane().lookupButton(saveButtonType);
        saveButton.setDisable(true);

        title.textProperty().addListener((observable, oldValue, newValue) -> {
            saveButton.setDisable(newValue.trim().isEmpty());
        });

        Platform.runLater(title::requestFocus);

        dialog.setResultConverter(dialogButton -> {
            Book book = null;
            if (dialogButton == saveButtonType) {
                book = BookFactory.createBook(Book.getCountOfBooks() + 1,genre.getText(),title.getText(),author.getText(),Integer.parseInt(year.getText()),description.getText(),photoUrl.getText(),0,"");
            }
            return book;
        });

        Optional<Book> result = dialog.showAndWait();

        result.ifPresent(book -> {
            bookDAO.save(book);
            System.out.println("User saved: " + book.getTitle());
        });
    }
    /**
     * Adds a user to the database using a dialog to collect user information.
     * @param event The ActionEvent triggered by pressing the add user button.
     */
    @FXML
    void addUser(ActionEvent event) {
        Dialog<User> dialog = new Dialog<>();
        dialog.setTitle("Add User");
        dialog.setHeaderText("Enter user details:");

        ButtonType saveButtonType = new ButtonType("Save", ButtonBar.ButtonData.OK_DONE);
        dialog.getDialogPane().getButtonTypes().addAll(saveButtonType, ButtonType.CANCEL);

        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(20, 150, 10, 10));

        TextField name = new TextField();
        name.setPromptText("Name");
        TextField email = new TextField();
        email.setPromptText("Email");
        TextField phone = new TextField();
        phone.setPromptText("Phone");
        PasswordField password = new PasswordField();
        password.setPromptText("Password");

        grid.add(new Label("Name:"), 0, 0);
        grid.add(name, 1, 0);
        grid.add(new Label("Email:"), 0, 1);
        grid.add(email, 1, 1);
        grid.add(new Label("Phone:"), 0, 2);
        grid.add(phone, 1, 2);
        grid.add(new Label("Password:"), 0, 3);
        grid.add(password, 1, 3);

        dialog.getDialogPane().setContent(grid);

        Node saveButton = dialog.getDialogPane().lookupButton(saveButtonType);
        saveButton.setDisable(true);

        name.textProperty().addListener((observable, oldValue, newValue) -> {
            saveButton.setDisable(newValue.trim().isEmpty());
        });

        Platform.runLater(name::requestFocus);

        dialog.setResultConverter(dialogButton -> {
            if (dialogButton == saveButtonType) {
                return new Client(name.getText(), email.getText(), phone.getText(), password.getText(),false);
            }
            return null;
        });

        Optional<User> result = dialog.showAndWait();

        result.ifPresent(user -> {
            userDAO.save(user);
            System.out.println("User saved: " + user.getName());
        });
    }


    /**
     * Handles the change action event.
     * @param event The ActionEvent object.
     */
    @FXML
    void change(ActionEvent event) {
        isChange = !isChange;
        if (isChange) {
            list.getItems().clear();
            typeOfList.setText("Books");
            List<String> books = new ArrayList<>();
            for (Book book : bookDAO.index()) {
                books.add(book.getTitle());
            }

            list.getItems().addAll(books);
        } else {
            list.getItems().clear();
            typeOfList.setText("Users");
            List<String> UsersName = new ArrayList<>();
            for (User user : userDAO.index()) {
                UsersName.add(user.getName());
            }

            list.getItems().addAll(UsersName);
        }
    }
    /**
     * Initializes the controller after its root element has been completely processed.
     * @param location The location used to resolve relative paths for the root object, or null if the location is not known.
     * @param resources The resources used to localize the root object, or null if the root object was not localized.
     */
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        typeOfList.setText("Users");

        List<String> userNames = new ArrayList<>();
        for (User user : userDAO.index()) {
            userNames.add(user.getName());
        }

        list.getItems().addAll(userNames);
    }
    /**
     * Handles the mouse click event to go back.
     * @param event The MouseEvent object.
     * @throws IOException If an error occurs while loading the main client page.
     */
    @FXML
    public void goBack(MouseEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/loginpage/mainClient.fxml"));
        Parent root = loader.load();
        MainClientConroller mainClientConroller = loader.getController();
        MainClientConroller.setUser(user);
        Scene profileScene = new Scene(root);
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(profileScene);
        stage.setResizable(true);
        stage.setFullScreen(true); // Set full screen mode after loading the new scene
        stage.show();
    }
    /**
     * Handles the mouse click event to go to the admin panel.
     * @param event The MouseEvent object.
     */
    public void goToAdminPanel(MouseEvent event) {

    }
    /**
     * Handles navigation to the user's profile page.
     * @param event The MouseEvent that triggers this navigation.
     */
    @FXML
    public void goToProfilePage(MouseEvent event) {
        try {
            ProfileController.setUser(user);
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/loginpage/profile.fxml"));
            Parent root = loader.load();
            ProfileController profileController = loader.getController();
            Scene profileScene = new Scene(root);
            Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
            stage.setScene(profileScene);
            stage.setResizable(true);
            stage.setFullScreen(true); // Set full screen mode after loading the new scene
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
