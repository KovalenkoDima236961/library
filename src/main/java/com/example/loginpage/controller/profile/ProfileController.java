package com.example.loginpage.controller.profile;

import com.example.loginpage.controller.dao.UserDAO;
import com.example.loginpage.controller.mainClient.MainClientConroller;
import com.example.loginpage.module.users.User;
import com.example.loginpage.module.users.typeofuser.Admin;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

/**
 * Controller class for the user profile interface.
 */
public class ProfileController implements Initializable {
    /**
     * Button to enable editing of user profile data
     */
    @FXML
    private Button editButton;
    /**
     * Default constructor for the ProfileController class.
     */
    public ProfileController(){}

    /**
     * TextField for entering the user's email
     */
    @FXML
    private TextField emailTextField;

    /**
     * TextField for entering the user's name
     */
    @FXML
    private TextField nameTextField;

    /**
     * TextField for entering the user's phone number
     */
    @FXML
    private TextField phoneTextField;

    /**
     * Button to save changes made to the user profile
     */
    @FXML
    private Button saveButton;
    /**
     * VBox containing the user profile information
     */
    @FXML
    private VBox firstBlok;
    /**
     * VBox containing the user profile edit fields
     */
    @FXML
    private VBox secondBlok;

    /**
     * Label displaying the user's name
     */
    @FXML
    private Label nameLabel;

    /**
     * HBox containing the exit button
     */
    @FXML
    private HBox exitHB;
    /**
     * The scene associated with the profile interface
     */
    private Scene scene;
    /**
     * The stage associated with the profile interface
     */
    private Stage stage;
    /**
     * The root node of the profile interface
     */
    private Parent root;
    /**
     * The current user viewing the profile
     */
    private static User user;
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
     * Initializes the controller after its root element has been completely processed.
     * @param url The location used to resolve relative paths for the root object, or null if the location is not known.
     * @param resourceBundle The resources used to localize the root object, or null if the root object was not localized.
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        showAllInformation();
    }
    /**
     * Displays all user information.
     */
    public void showAllInformation(){
        nameTextField.setText(user.getName());
        nameTextField.setEditable(false);
        nameLabel.setText(user.getName());
        System.out.println(user.getPhone());
        phoneTextField.setText(user.getPhone());
        phoneTextField.setEditable(false);
        emailTextField.setText(user.getEmail());
        emailTextField.setEditable(false);
    }
    /**
     * Allows the user to edit profile data.
     * @param event The ActionEvent object.
     */
    public void editData(ActionEvent event){
        nameTextField.setEditable(true);
        emailTextField.setEditable(true);
        phoneTextField.setEditable(true);
    }
    /**
     * Saves the changes made to the user profile.
     * @param event The ActionEvent object.
     */
    public void saveChanges(ActionEvent event) {
        String newName = nameTextField.getText();
        String newPhone = phoneTextField.getText();
        String newEmail = emailTextField.getText();

        if (isValidName(newName) && isValidPhone(newPhone) && isValidEmail(newEmail)) {
            user.setName(newName);
            user.setPhone(newPhone);
            user.setEmail(newEmail);

            UserDAO userDAO = new UserDAO();
            userDAO.updateUser(user);
        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Invalid Input");
            alert.setHeaderText(null);
            alert.setContentText("Please enter valid data for name, phone, and email.");
            alert.showAndWait();
        }
    }
    /**
     * Handles the mouse click event to navigate to the admin panel.
     * @param event The MouseEvent object.
     */
    @FXML
    public void goToAdminPanel(MouseEvent event) {
        try {
            goToAdminPanelMethod(event);
        } catch (UnauthorizedAccessException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Access Denied");
            alert.setHeaderText(null);
            alert.setContentText(e.getMessage());
            alert.showAndWait();
        }
    }

    /**
     * Switch to the administrator panel if the current user is an administrator.
     * This method first checks to see if the user has administrator rights. If the user has administrator rights,
     * it loads the admin panel scene and displays it. If the user is not an administrator, it throws
     * UnauthorisedAccessException to prevent access.
     *
     * @param event The MouseEvent that triggered this method, typically from a button click or similar action.
     * @throws UnauthorizedAccessException if the current user is not an admin, preventing unauthorized access.
     */
    public void goToAdminPanelMethod(MouseEvent event) throws UnauthorizedAccessException {
        if (user instanceof Admin) {
            System.out.println(user.isAdmin());
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/loginpage/profileAdmin.fxml"));
                Parent root = loader.load();
                ProfileAdminController profileAdminController = loader.getController();
                ProfileAdminController.setUser(user);
                Scene profileAdminScene = new Scene(root);
                Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                stage.setScene(profileAdminScene);
                stage.setResizable(true);
                stage.setFullScreen(true);
                stage.show();
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            throw new UnauthorizedAccessException("Only Admin has access to this section");
        }
    }
    /**
     * Validates if the provided name is valid.
     * @param name The name to validate.
     * @return True if the name is valid, otherwise false.
     */
    private boolean isValidName(String name) {
        return name != null && !name.trim().isEmpty();
    }
    /**
     * Validates if the provided phone number is valid.
     * @param phone The phone number to validate.
     * @return True if the phone number is valid, otherwise false.
     */
    private boolean isValidPhone(String phone) {
        return phone != null && phone.matches("\\d{10}"); // Assuming phone number is 10 digits
    }


    /**
     * Validates if the provided email address is valid.
     * @param email The email address to validate.
     * @return True if the email address is valid, otherwise false.
     */
    private boolean isValidEmail(String email) {
        return email != null && email.contains("@") && email.contains(".");
    }
    /**
     * Handles the mouse click event to navigate back to the main client interface.
     * @param event The MouseEvent object.
     * @throws IOException If an I/O error occurs.
     */
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
}
