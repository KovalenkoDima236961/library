package com.example.loginpage.controller.topSection;

import com.example.loginpage.controller.browse.BrowseController;
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
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.*;

import static com.example.loginpage.HelloApplication.getBooksToSerialize;

/**
 * Controller class for the top section of the application.
 * This controller handles the display of top books, authors, and genres.
 */
public class TopSectionController implements Initializable, Movement {
    /**
     * VBox for displaying the best book
     */
    @FXML
    private VBox boxForBestBook;
    /**
     * BarChart for displaying the top genres
     */
    @FXML
    private BarChart<String, Number> barPlotGenre;
    /**
     * BarChart for displaying the top authors
     */
    @FXML
    private BarChart<String, Number> autrhorBarPlot;
    /**
     * VBox for displaying the top author
     */
    @FXML
    private VBox boxForTheBestAuthor;
    /**
     * VBox for displaying the top genre
     */
    @FXML
    private VBox boxForTheBestGenre;
    /**
     * VBox for displaying the first book
     */
    @FXML
    private VBox firstBox;
    /**
     * Label for displaying username
     */
    @FXML
    private Label nameLabel;
    /**
     * Data Access Object for interacting with the book database
     */
    private BookDAO bookDAO;
    /**
     * The current user
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
        TopSectionController.user = user;
    }
    /**
     * Constructor for the TopSectionController class.
     * Initializes the BookDAO instance.
     */
    public TopSectionController(){
        this.bookDAO = BookDAO.getInstance();
    }



    /**
     * Initializes the controller.
     * Retrieves and displays information about the top books, authors, and genres.
     * @param url The location used to resolve relative paths for the root object, or null if the location is not known.
     * @param resourceBundle The resources used to localize the root object, or null if the root object was not localized.
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        nameLabel.setText(user.getName());
        // Отримуємо список топових книг
        List<Book> topBooksList = bookDAO.topBooks();

        try {
            if (!topBooksList.isEmpty()) {
                FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/com/example/loginpage/book.fxml"));
                VBox bookBox = fxmlLoader.load();

                BookController bookController = fxmlLoader.getController();
                bookController.setData(topBooksList.get(0));

                firstBox.getChildren().add(bookBox);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        if (!topBooksList.isEmpty()) {
            Book topBook = topBooksList.get(0);

            Label topAuthor = new Label();
            topAuthor.setText(topBook.getAuthor());
            topAuthor.setStyle("-fx-font-size: 21pt; -fx-font-weight: bold; -fx-text-fill: #333333;");
            ObservableList<Node> children = boxForTheBestAuthor.getChildren();
            children.add(topAuthor);

            String genre = topBook.getGenre();

            Label topGenre = new Label();
            topGenre.setText(genre);
            topGenre.setStyle("-fx-font-size: 21pt; -fx-font-style: italic; -fx-text-fill: #0066FF;");
            children = boxForTheBestGenre.getChildren();
            children.add(topGenre);
        }

        // Count occurrences of each author and their votes
        Map<String, Integer> authorVotes = new HashMap<>();
        for (Book book : topBooksList) {
            String author = book.getAuthor();
            int votes = book.getVotes();
            authorVotes.put(author, authorVotes.getOrDefault(author, 0) + votes);
        }

        // Create BarChart for authors and their votes
        CategoryAxis authorXAxis = new CategoryAxis();
        NumberAxis authorYAxis = new NumberAxis();
        BarChart<String, Number> authorBarChart = new BarChart<>(authorXAxis, authorYAxis);

        // Set BarChart data for authors and their votes
        XYChart.Series<String, Number> authorSeries = new XYChart.Series<>();
        for (Map.Entry<String, Integer> entry : authorVotes.entrySet()) {
            authorSeries.getData().add(new XYChart.Data<>(entry.getKey(), entry.getValue()));
        }

        // Add series to BarChart for authors and their votes
        authorBarChart.getData().add(authorSeries);
        autrhorBarPlot.getStyleClass().add("custom-bar-chart");

        // Add CSS styles for the authorBarPlot
        String authorBarChartStyle = "-fx-background-color: #f4f4f4;";
        autrhorBarPlot.setStyle(authorBarChartStyle);

        // Add authorBarChart to VBox
        autrhorBarPlot.setAnimated(false); // Disable animation
        autrhorBarPlot.setLegendVisible(false); // Hide legend
        autrhorBarPlot.setBarGap(0); // Remove gap between bars
        autrhorBarPlot.setCategoryGap(10); // Add gap between categories
        autrhorBarPlot.getData().addAll(authorSeries);



        // Count occurrences of each genre
        Map<String, Integer> genreCounts = new HashMap<>();
        for (Book book : topBooksList) {
            String genre = book.getGenre();
            genreCounts.put(genre, genreCounts.getOrDefault(genre, 0) + 1);
        }

        // Create BarChart
        CategoryAxis xAxis = new CategoryAxis();
        NumberAxis yAxis = new NumberAxis();
        BarChart<String, Number> barChart = new BarChart<>(xAxis, yAxis);

        // Set BarChart data
        XYChart.Series<String, Number> series = new XYChart.Series<>();
        for (Map.Entry<String, Integer> entry : genreCounts.entrySet()) {
            series.getData().add(new XYChart.Data<>(entry.getKey(), entry.getValue()));
        }

        // Add series to BarChart
        barChart.getData().add(series);
        barPlotGenre.getStyleClass().add("custom-bar-chart");

        // Add CSS styles for the BarChart
        String barChartStyle = "-fx-background-color: #f4f4f4;";
        barPlotGenre.setStyle(barChartStyle);

        // Add BarChart to VBox
        barPlotGenre.setAnimated(false); // Disable animation
        barPlotGenre.setLegendVisible(false); // Hide legend
        barPlotGenre.setBarGap(0); // Remove gap between bars
        barPlotGenre.setCategoryGap(10); // Add gap between categories
        barPlotGenre.getData().addAll(series);
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
            Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
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
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(categoryScene);
            stage.setResizable(true);
            stage.setFullScreen(true); // Set full screen mode after loading the new scene
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    /**
     * Navigates to the browser page on user action.
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
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(profileScene);
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
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/loginpage/sectionWithTopBooks.fxml"));
            Parent root = loader.load();
            TopBooksController topBooksController = loader.getController();
            TopBooksController.setUser(user);
            Scene topBookScene = new Scene(root);
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(topBookScene);
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
    @Override
    public void moveToQuiz(MouseEvent event){
        try {
            QuizPageController.setUser(user);
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/loginpage/quizPage.fxml"));
            Parent root = loader.load();
            Scene quizScene = new Scene(root);
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
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
    public void goToFavouritePage(MouseEvent event){
        try {
            FavouritesController.setUser(user);
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/loginpage/favourite.fxml"));
            Parent root = loader.load();
            Scene profileScene = new Scene(root);
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(profileScene);
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
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
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
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
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
