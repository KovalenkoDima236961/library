package com.example.loginpage.controller.mainClient;

import com.example.loginpage.module.books.Book;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;

import java.io.InputStream;

/**
 * Controller for managing the display of book cards.
 */
public class CardController {
    /**
     * // Label for displaying the author's name
     */
    @FXML
    private Label authorName;
    /**
    * Label for displaying the book's title
    * */
    @FXML
    private Label bookName;

    /**
     HBox container for the book card
     */
    @FXML
    private HBox box;

    /**
     * ImageView for displaying the book cover image
     */
    @FXML
    private ImageView bookImage;
    /**
     * Label for displaying the number of votes received by the book
     */
    @FXML
    private Label howManyVotes;
    /**
     * Array of hexadecimal color codes used for styling the book card background
     */
    private String[] colors = {
            "B9E5FF", // Light Blue
            "BDB2FE", // Lavender
            "FB9AA8", // Pink
            "FF5056", // Red
            "A5FFD6", // Aqua
            "FFD952", // Yellow
            "7B7D7D", // Gray
            "FFBE0B", // Orange
            "83D6DE", // Sky Blue
            "84CEEB", // Baby Blue
            "C8E0DC", // Mint Green
            "A3D9FF", // Light Sky Blue
            "D4A5A5", // Pastel Red
            "B0E0E6"  // Powder Blue
            // Add more colors as needed
    };
    /**
     * Default constructor for the CardController class.
     */
    public CardController(){}

    /**
     * Sets the data for the book card.
     * @param book The Book object containing book information.
     */
    public void setData(Book book){
        Image image;

        if (book.getImageSrc().startsWith("http://") || book.getImageSrc().startsWith("https://")) {
            image = new Image(book.getImageSrc());
        } else {
            try {
                InputStream is = getClass().getResourceAsStream(book.getImageSrc());
                if (is == null) {
                    throw new IllegalArgumentException("Resource not found: " + book.getImageSrc());
                }
                image = new Image(is);
            } catch (Exception e) {
                e.printStackTrace();
                image = new Image("/com/example/loginpage/img/Confession.jpg");
            }
        }
        bookImage.setImage(image);

        howManyVotes.setText(String.valueOf(book.getVotes()));
        howManyVotes.setStyle(
                "-fx-font-size: 14px;" +
                        "-fx-font-weight: bold;" +
                        "-fx-text-fill: #333333;"
        );

        bookName.setText(book.getTitle());
        authorName.setText(book.getAuthor());
        box.setStyle("-fx-background-color: #" + colors[(int) (Math.random() * colors.length)] + ";" +
                " -fx-background-radius: 15;" +
                "-fx-effect: dropShadow(three-pass-box, rgba(0,0,0,0.1),10,0,0,10);"
        );
    }
}
