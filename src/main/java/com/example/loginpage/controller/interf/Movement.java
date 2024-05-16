package com.example.loginpage.controller.interf;

import com.example.loginpage.controller.mainClient.MainClientConroller;
import com.example.loginpage.module.users.User;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.io.IOException;

/**
 * Interface for defining movement actions in the application.
 */
public interface Movement {

    /** Moves to the top books section.
     * @param event The MouseEvent triggering the action.
     */
    void moveToTopBooks(MouseEvent event);
    /**
     * Moves to the main page.
     * @param event The MouseEvent triggering the action.
     */
    void moveToMainPage(MouseEvent event);
    /**
     * Goes to the profile page.
     * @param event The MouseEvent triggering the action.
     */
    void goToProfilePage(MouseEvent event);
    /**
     * Moves to the all books section of the application.
     *
     * @param event The MouseEvent that triggers this navigation.
     */
    void moveToAllBooks(MouseEvent event);
    /**
     * Handles the event when the user wants to see history .
     * Navigates to the history page.
     * @param event MouseEvent that triggers this navigation.
     */
    void moveToHistory(MouseEvent event);
    /**
     * Handles the event when the user wants to vote.
     * Navigates to the voting page.
     * @param event MouseEvent that triggers this navigation.
     */
    void goToVoting(MouseEvent event);
    /**
     * Navigates to the favourites page.
     * @param event MouseEvent that triggers this navigation.
     */
    void goToFavouritePage(MouseEvent event);
    /**
     * Handles the event when the user wants to quiz.
     * Navigates to the quiz page.
     * @param event MouseEvent that triggers this navigation.
     */
    void moveToQuiz(MouseEvent event);
    /**
     * Handles an action event caused by selecting an exit option. This method creates a confirmation notification
     * A dialogue box that asks the user if they are sure they want to exit the application. If the user confirms, the method
     * attempts to serialise the current state of the workbooks to a file and then exits the application.
     * @param event The {@link ActionEvent} triggering this method, typically from a user interaction with a GUI element.
     */
    void Exit(ActionEvent event);
    /**
     * Navigates to the category page.
     * @param event MouseEvent that triggers this navigation.
     */
    void goToCategoryPage(MouseEvent event);
}

