//package com.example.loginpage.controller.topBookSection;
//
//import com.example.loginpage.controller.browse.ControllerForEveryBook;
//import com.example.loginpage.controller.dao.BookDAO;
//import com.example.loginpage.controller.mainClient.BookController;
//import com.example.loginpage.controller.mainClient.MainClientConroller;
//import com.example.loginpage.controller.profile.ProfileController;
//import com.example.loginpage.module.books.Book;
//import com.example.loginpage.module.users.User;
//import javafx.event.ActionEvent;
//import javafx.event.EventHandler;
//import javafx.fxml.FXML;
//import javafx.fxml.FXMLLoader;
//import javafx.fxml.Initializable;
//import javafx.geometry.Insets;
//import javafx.scene.Node;
//import javafx.scene.Parent;
//import javafx.scene.Scene;
//import javafx.scene.control.Button;
//import javafx.scene.image.ImageView;
//import javafx.scene.input.MouseEvent;
//import javafx.scene.layout.GridPane;
//import javafx.scene.layout.VBox;
//import javafx.stage.Stage;
//
//import java.io.IOException;
//import java.net.URL;
//import java.util.List;
//import java.util.ResourceBundle;
//
//public class TopBooksController implements Initializable {
//    @FXML
//    private GridPane bookContainer2;
//
//    private List<Book> topBooks;
//    private static User user;
//    private BookDAO bookDAO;
//    private Scene scene;
//    private Stage stage;
//    private Book selectedBook;
//
//    @FXML
//    private Button selectButton;
//
//    @FXML
//    private ImageView profileIcon;
//    private boolean isButtonPressed = false;
//    public TopBooksController(){
//        bookDAO = new BookDAO();
//    }
//    public static void setUser(User user1) {
//        user = user1;
//    }
//
//    public static User getUser() {
//        return user;
//    }
//
//
//    @FXML
//    public void moveToProfile(MouseEvent event) {
//        try {
//            ProfileController.setUser(user);
//            // Load the FXML file for the profile page
//            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/loginpage/profile.fxml"));
//
//
//
//            Parent root = loader.load();
//
//            // Get the controller for the profile page
//            ProfileController profileController = loader.getController();
//            // Set any necessary data in the profile controller
//
//            // Create a new scene with the profile page
//            Scene profileScene = new Scene(root);
//
//            // Get the stage from the event source
//            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
//            // Set the new scene on the stage
//            stage.setScene(profileScene);
//            // Show the stage with the profile page
//            stage.show();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//    }
//    @FXML
//    void moveToMainPage(MouseEvent event) {
//        try {
//            MainClientConroller.setUser(user);
//            // Load the FXML file for the profile page
//            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/loginpage/mainClient.fxml"));
//
//
//
//            Parent root = loader.load();
//
//            // Get the controller for the profile page
//            MainClientConroller mainClientConroller = loader.getController();
//            // Set any necessary data in the profile controller
//
//            // Create a new scene with the profile page
//            Scene profileScene = new Scene(root);
//
//            // Get the stage from the event source
//            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
//            // Set the new scene on the stage
//            stage.setScene(profileScene);
//            // Show the stage with the profile page
//            stage.show();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//    }
//
//    @Override
//    public void initialize(URL url, ResourceBundle resourceBundle) {
//        int column = 0;
//        int row = 1;
//        topBooks = bookDAO.topBooks();
//        try {
//            for (Book book : topBooks){
//                FXMLLoader fxmlLoader = new FXMLLoader();
//                fxmlLoader.setLocation(getClass().getResource("/com/example/loginpage/book.fxml"));
//                VBox bookBox = fxmlLoader.load();
//                BookController bookController = fxmlLoader.getController();
//                bookController.setData(book);
//
//                bookBox.setOnMouseClicked(event -> {
//                    selectedBook = book;
//                    if(!isButtonPressed){
//                        ControllerForEveryBook.handleBookClicked(event, bookContainer2.getScene(), book,user);
//                    }
//                });
//
//                if(column == 6){
//                    column = 0;
//                    ++row;
//                }
//                bookContainer2.add(bookBox,column++,row);
//                GridPane.setMargin(bookBox,new Insets(10));
//
//                // Встановлюємо обробник подій для кожного VBox окремо
//                if(isButtonPressed){
//                    bookBox.setOnMouseClicked(event -> {
//                        if (!isButtonPressed) {
//                            // Скасовуємо вибір попереднього блоку
//                            for (Node n : bookContainer2.getChildren()) {
//                                if (n instanceof VBox && !n.equals(bookBox)) {
//                                    ((VBox) n).setStyle("-fx-background-color: transparent;");
//                                }
//                            }
//                            // Виділяємо вибраний блок
//                            bookBox.setStyle("-fx-background-color: lightblue;");
//                            // Тут ви можете виконати додаткові дії з обраним блоком
//                        }
//                    });
//                }
//            }
//        }catch (IOException e){
//            e.printStackTrace();
//        }
//        System.out.println("All OK");
//    }
//
////    @FXML
////    public void selectTopBook(ActionEvent event) {
////        isButtonPressed = true;
////        // Скасовуємо вибір попереднього блоку
////        for (Node node : bookContainer2.getChildren()) {
////            if (node instanceof VBox) {
////                ((VBox) node).setStyle("-fx-background-color: transparent;");
////            }
////        }
////        // Встановлюємо обробник подій для кожного VBox окремо
////        for (Node node : bookContainer2.getChildren()) {
////            if (node instanceof VBox) {
////                VBox bookBox = (VBox) node;
////                bookBox.setOnMouseClicked(event1 -> {
////                    if (!isButtonPressed) {
////                        // Скасовуємо вибір попереднього блоку
////                        for (Node n : bookContainer2.getChildren()) {
////                            if (n instanceof VBox && !n.equals(bookBox)) {
////                                ((VBox) n).setStyle("-fx-background-color: transparent;");
////                            }
////                        }
////                        // Виділяємо вибраний блок
////                        bookBox.setStyle("-fx-background-color: lightblue;");
////                        // Тут ви можете виконати додаткові дії з обраним блоком
////                    }
////                });
////            }
////        }
////    }
//@FXML
//public void selectTopBook(ActionEvent event) {
//    if (!isButtonPressed) {
//        isButtonPressed = true;
//        // Скасовуємо вибір попереднього блоку, якщо він є
//        for (Node node : bookContainer2.getChildren()) {
//            if (node instanceof VBox) {
//                ((VBox) node).setStyle("-fx-background-color: transparent;");
//            }
//        }
//        // Встановлюємо обробник подій для кожного VBox окремо
//        for (Node node : bookContainer2.getChildren()) {
//            if (node instanceof VBox) {
//                VBox bookBox = (VBox) node;
//                bookBox.setOnMouseClicked(event1 -> {
//                    // Видаляємо виділення з попереднього блоку, якщо воно є
//                    if (selectedBook != null) {
//                        for (Node n : bookContainer2.getChildren()) {
//                            if (n instanceof VBox) {
//                                ((VBox) n).setStyle("-fx-background-color: transparent;");
//                            }
//                        }
//                    }
//                    // Виділяємо поточний блок
//                    bookBox.setStyle("-fx-background-color: lightblue;");
//                    // Зберігаємо вибрану книгу
//                    selectedBook = (Book) bookBox.getUserData();
//                });
//            }
//        }
//    } else {
//        isButtonPressed = false;
//    }
//}
//}


package com.example.loginpage.controller.topBookSection;

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
import javafx.scene.input.MouseButton;
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

import static com.example.loginpage.HelloApplication.getBooksToSerialize;

/**
 * A controller class for displaying the most popular books.
 * This controller retrieves and displays information about the most popular books.
 */
public class TopBooksController implements Initializable, Movement {
    /**
     * GridPane for displaying the top books
     */
    @FXML
    private GridPane bookContainer2;
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
     * The scene
     */
    private Scene scene;
    /**
     * The stage
     */
    private Stage stage;
    /**
     * The currently selected book
     */
    private Book selectedBook;
    /**
     * Label for displaying username
     */
    @FXML
    private Label nameLabel;
    /**
     * Flag to track if the "Select Top Book" button is pressed
     */
    private boolean isButtonPressed = false;
    /**
     * Constructor for initializing the BookDAO.
     */
    public TopBooksController() {
        bookDAO = BookDAO.getInstance();
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
     * Handles the event when moving to the profile page.
     * @param event The MouseEvent triggering the action.
     */
    @FXML
    public void moveToProfile(MouseEvent event) {
        try {
            ProfileController.setUser(user);
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/loginpage/profile.fxml"));
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
     * Handles the event when moving to the main page.
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
     * Handles the event when moving to all book page.
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
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(browseScene);
            stage.setResizable(true);
            stage.setFullScreen(true); // Set full screen mode after loading the new scene
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    /**
     * Initializes the controller by retrieving and displaying information about the top books.
     * @param url The location used to resolve relative paths for the root object, or null if the location is not known.
     * @param resourceBundle The resources used to localize the root object, or null if the root object was not localized.
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        nameLabel.setText(user.getName());
        int column = 0;
        int row = 1;
        topBooks = bookDAO.topBooks();
        try {
            for (Book book : topBooks) {
                FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/com/example/loginpage/book.fxml"));
                VBox bookBox = fxmlLoader.load();
                BookController bookController = fxmlLoader.getController();
                bookController.setData(book);

                // Встановлюємо обробник подій на vbox
                bookBox.setOnMouseClicked(event -> {
                    selectedBook = book;
                    handleBookClick(bookBox,book);
                });

                if (column == 6) {
                    column = 0;
                    ++row;
                }
                bookContainer2.add(bookBox, column++, row);
                GridPane.setMargin(bookBox, new Insets(10));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println("All OK");
    }
    /**
     * Handles the click event on a book.
     * @param bookBox The VBox representing the book.
     * @param book The Book object associated with the book.
     */
    private void handleBookClick(VBox bookBox, Book book) {
        // Очищення виділення попередньої книги
        clearSelection();
        if (isButtonPressed) {
            // Встановлення виділення для поточної книги
            bookBox.setStyle("-fx-background-color: lightblue;");
            selectedBook = book;
        } else {
            ControllerForEveryBook.handleBookClicked(new MouseEvent(MouseEvent.MOUSE_CLICKED, 0, 0, 0, 0, MouseButton.PRIMARY, 1, false, false, false, false, true, false, false, true, false, false, null), bookContainer2.getScene(), book, user,null,null);
        }
    }
    /**
     * Handles the event when the "Select Top Book" button is clicked.
     * @param event The ActionEvent triggering the action.
     */
    @FXML
    public void selectTopBook(ActionEvent event) {
        isButtonPressed = !isButtonPressed;
    }

    /**
     * Clears the selection of the book.
     */
    private void clearSelection() {
        // Очищення виділення попередньої книги
        if (selectedBook != null) {
            for (Node n : bookContainer2.getChildren()) {
                if (n instanceof VBox) {
                    ((VBox) n).setStyle("-fx-background-color: transparent;");
                }
            }
        }
        selectedBook = null;
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
     * Saves the selected book as the top book.
     * @param event The ActionEvent triggering the action.
     */
    @FXML
    public void saveResult(ActionEvent event) {
        if(isButtonPressed){
            if(bookDAO.voteForTopBook(selectedBook)){
                System.out.println("The book has been successfully added");
                try {
                    StatisticsAboutTopBookController.setUser(user);
                    FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/loginpage/statisticsAboutTopBooks.fxml"));
                    Parent root = loader.load();
                    Scene profileScene = new Scene(root);
                    Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                    stage.setScene(profileScene);
                    stage.setResizable(true);
                    stage.setFullScreen(true);
                    stage.show();
                } catch (IOException e) {
                    e.printStackTrace();
                }
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
     * Handles the event when the user wants to see history .
     * Navigates to the history page.
     * @param event MouseEvent that triggers this navigation.
     */
    @FXML
    @Override
    public void moveToHistory(MouseEvent event) {
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
     * Handles the event when the user wants to vote.
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

