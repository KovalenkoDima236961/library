package com.example.loginpage;

import com.example.loginpage.controller.serialization.SerializationBooks;
import com.example.loginpage.controller.serialization.SerializationForCountOfPeople;
import com.example.loginpage.module.books.Book;
import com.example.loginpage.module.users.User;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.List;
import java.util.Optional;
/**
 * The HelloApplication class represents the main entry point for the JavaFX application.
 * It extends the Application class and provides the start method to initialize the GUI.
 */
public class HelloApplication extends Application {
    /**
     * List of books to serialize before exiting the application.
     */
    private static List<Book> booksToSerialize;

    /**
     *  Get Book to serialize
     * @return List Book to serialize
     */
    public static List<Book> getBooksToSerialize() {
        return booksToSerialize;
    }

    /**
     * Default constructor for the HelloApplication class.
     */
    public HelloApplication(){}

    /**
     * Sets the list of books to be serialized.
     *
     * @param books The list of books to be serialized.
     */
    public static void setBooksToSerialize(List<Book> books) {
        booksToSerialize = books;
    }
    /**
     * The start method is called when the JavaFX application is launched.
     *
     * @param stage The primary stage for the application.
     * @throws IOException if an error occurs during the loading of the FXML file.
     */
    @Override
    public void start(Stage stage) throws IOException {
        System.out.println("DATABASE_URL: " + System.getenv("DATABASE_URL"));
        System.out.println("DATABASE_USERNAME: " + System.getenv("DATABASE_USERNAME"));
        System.out.println("DATABASE_PASSWORD: " + System.getenv("DATABASE_PASSWORD"));
        SerializationBooks.setFlag(true);
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("sample.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        stage.setTitle("Login");
        stage.setResizable(true);
        stage.setScene(scene);
        stage.setFullScreen(true);
        stage.show();

        stage.setOnCloseRequest(event -> {
            event.consume();
            exitApplication();
        });
    }
    /**
     * The main method is the entry point of the application.
     *
     * @param args The command-line arguments.
     */
    public static void main(String[] args) {
        launch();
    }
    /**
     * Exits the application, prompting the user for confirmation.
     * Serializes the list of books before exiting if available.
     */
    public static void exitApplication() {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Exit Confirmation");
        alert.setHeaderText("Exit Application");
        alert.setContentText("Are you sure you want to exit?");

        alert.initModality(Modality.APPLICATION_MODAL);

        Optional<ButtonType> result = alert.showAndWait();
        if (result.isPresent() && result.get() == ButtonType.OK) {
            try {
                // Serialize the books before exiting
                if (booksToSerialize != null) {
                    SerializationBooks.serializeLatestBooks(booksToSerialize, "latestBooks.ser");
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