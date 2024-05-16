package com.example.loginpage.controller.mainClient;

import com.example.loginpage.HelloApplication;
import com.example.loginpage.module.books.Book;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.util.Objects;
/**
 * Controller for managing the display of book information.
 */
public class BookController {
    /**
     * Label for displaying the author's name
     */
    @FXML
    private Label authorName;
    /**
     * ImageView for displaying the book cover image
     */
    @FXML
    private ImageView bookImage;
    /**
     * Initializes a new instance of the BookController class.
     */
    public BookController() {}

    /**
     * Label for displaying the book's title
     */
    @FXML
    private Label bookName;
    /**
     * Sets the data for the book.
     * @param book The Book object containing book information.
     */
    public void setData(Book book){
        Image image;

        if (book.getImageSrc().startsWith("http://") || book.getImageSrc().startsWith("https://")) {
            image = new Image(book.getImageSrc());
        } else {
            image = new Image(Objects.requireNonNull(HelloApplication.class.getResourceAsStream(book.getImageSrc())));
        }

        bookImage.setImage(image);
        bookName.setText(book.getTitle());
        authorName.setText(book.getAuthor());
    }
}
