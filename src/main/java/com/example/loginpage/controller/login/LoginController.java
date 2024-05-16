package com.example.loginpage.controller.login;

import com.example.loginpage.controller.dao.UserDAO;
import com.example.loginpage.controller.mainClient.MainClientConroller;
import com.example.loginpage.module.users.User;
import com.example.loginpage.module.users.typeofuser.Admin;
import com.example.loginpage.module.users.typeofuser.Client;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.Objects;
import java.util.ResourceBundle;

/**
 * Controller for managing login and registration functionality.
 */
public class LoginController implements Initializable {

    /**
     * TextField for user email during registration
     */
    @FXML
    private TextField emailRegisterField;
    /**
     * Button for user login
     */
    @FXML
    private Button loginButton;
    /**
     * TextField for user email during login
     */
    @FXML
    private TextField loginField;
    /**
     * PasswordField for user password during login
     */
    @FXML
    private PasswordField passwordField;
    /**
     * Button for user registration
     */
    @FXML
    private Button registerButton;
    /**
     * TextField for user name during registration
     */
    @FXML
    private TextField registerNameField;
    /**
     * PasswordField for user password during registration
     */
    @FXML
    private PasswordField registerPasswordField;
    /**
     * TextField for user phone number during registration
     */
    @FXML
    private TextField registerPhoneField;
    /**
     * RadioButton for user role selection during registration
     */
    @FXML
    private RadioButton registerRadioButton;
    /**
     * Label to display login error messages
     */
    @FXML
    private Label incorrectLabel;
    /**
     * Label to display registration error messages
     */
    @FXML
    private Label registerErrorLabel;
    /**
     * Stage object for window management
     */
    private Stage stage;
    /**
     * Default constructor for the LoginController class.
     */
    public LoginController(){}

    /**
     * Handles the login action.
     * @param event The ActionEvent triggering the method.
     * @throws IOException If an error occurs while loading the main client page.
     */
    public void login(ActionEvent event) throws IOException {
        UserDAO userDAO = new UserDAO();
        User user = userDAO.show(loginField.getText(),passwordField.getText());
        if(user != null){
            MainClientConroller.setUser(user);
            System.out.println(user);
            if(user instanceof Admin){
                System.out.println("ADmin");
                Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/com/example/loginpage/mainClient.fxml")));
                stage = (Stage)((Node)event.getSource()).getScene().getWindow();
                Scene scene = new Scene(root);
                stage.setScene(scene);
                stage.setResizable(true);
                stage.setFullScreen(true); // Set full screen mode after loading the new scene
                stage.show();
            }else {
                Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/com/example/loginpage/mainClient.fxml")));
                stage = (Stage)((Node)event.getSource()).getScene().getWindow();
                Scene scene = new Scene(root);
                stage.setScene(scene);
                stage.setResizable(true);
                stage.setFullScreen(true); // Set full screen mode after loading the new scene
                stage.show();
            }
        }else {
            incorrectLabel.setText("You have wrong password or email");
            incorrectLabel.setVisible(true);
        }
    }
    /**
     * Handles the registration action.
     * @param event The ActionEvent triggering the method.
     * @throws IOException If an error occurs while loading the main client page.
     */
    public void registration(ActionEvent event) throws IOException {
        UserDAO userDAO = new UserDAO();
        try {
            checkName(registerNameField.getText());
            checkEmail(emailRegisterField.getText());
            checkPassword(registerPasswordField.getText());
            checkPhone(registerPhoneField.getText());

            User user = new Client(registerNameField.getText(), emailRegisterField.getText(), registerPhoneField.getText(), registerPasswordField.getText(), false);

            if (userDAO.save(user) && registerRadioButton.isSelected()) {
                MainClientConroller.setUser(user);
                Parent root = FXMLLoader.load(getClass().getResource("/com/example/loginpage/mainClient.fxml"));
                stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                Scene scene = new Scene(root);
                stage.setScene(scene);
                stage.setResizable(true);
                stage.setFullScreen(true);
                stage.show();
            }
        } catch (ValidationException e) {
            registerErrorLabel.setText(e.getMessage());
            registerErrorLabel.setVisible(true);
        }
    }

    // HELPER METHODS
    /**
     * Checks if the given name is valid.
     * @param name The name to check.
     * @return True if the name is valid, false otherwise.
     */
    private boolean checkName(String name) throws ValidationException {
        if (!name.matches("[a-zA-Z\\s]+")) {
            throw new ValidationException("Your name should not contain numbers or special characters");
        }
        return true;
    }


    /**
     * Checks if the given email is valid.
     * @param email The email to check.
     * @return True if the email is valid, false otherwise.
     */
    private boolean checkEmail(String email) throws ValidationException {
        if (!email.matches("\\b[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Z|a-z]{2,}\\b")) {
            throw new ValidationException("Invalid email format");
        }
        return true;
    }
    /**
     * Checks if the given password is valid.
     * @param password The password to check.
     * @return True if the password is valid, false otherwise.
     */
    private boolean checkPassword(String password) throws ValidationException {
        if (password.length() < 6) {
            throw new ValidationException("Password should be at least 6 characters long");
        }
        return true;
    }
    /**
     * Checks if the given phone number is valid.
     * @param phone The phone number to check.
     * @return True if the phone number is valid, false otherwise.
     */
    private boolean checkPhone(String phone) throws ValidationException {
        if (!phone.matches("\\d{10}")) {
            throw new ValidationException("Phone number should be 10 digits long");
        }
        return true;
    }
    //TODO Change
    /**
     * Initializes controller components, sets up book display and search filter functionality.
     * @param location URL for the root object.
     * @param resources ResourceBundle used to localize the root object.
     */
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        loginField.setText("kovalenkodima@gmail.com");
        passwordField.setText("123456789");
    }
}
