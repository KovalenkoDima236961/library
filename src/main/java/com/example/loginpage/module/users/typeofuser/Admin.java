package com.example.loginpage.module.users.typeofuser;

import com.example.loginpage.controller.dao.UserDAO;
import com.example.loginpage.module.users.User;

import java.util.List;
/**
 * Represents an administrator user with peer-to-peer access to the entire application.
 *  This class extends {@link User},providing additional administrative functionalities.
 */
public class Admin extends User {
    /** The singleton instance of the Admin class. */
    private static Admin instance;
    /** The list of users managed by the admin. */
    private List<User> users;
    /** Data access object for managing user data. */
    private UserDAO userDAO;
    /**
     * Private constructor to prevent external instantiation and ensure singleton pattern.
     * Initializes user list and user data access object.
     */
    private Admin() {
        userDAO = new UserDAO();
        users = userDAO.index();
        setAdmin(true);
    }
    /**
     * Provides global access to the Admin instance, creating it if it does not already exist.
     * @return The singleton instance of Admin.
     */
    public static Admin getInstance() {
        if (instance == null) {
            instance = new Admin();
        }
        return instance;
    }
    /**
     * Returns a string representation of the Admin including id, name, and email.
     * @return String that represents the Admin object.
     */
    @Override
    public String toString() {
        return "Admin{" +
                "id=" + getId() +
                ", name='" + getName() + '\'' +
                ", email='" + getEmail() + '\'' +
                '}';
    }
    //TODO ДОБАВИТИ КОНТРОЛЛЕР
    /**
     * Prints all users managed by the Admin.
     * If no users are found, it prints an informative message.
     */
    public void viewAllUsers() {
        if (users.isEmpty()) {
            System.out.println("No users found.");
        } else {
            for (User user : users) {
                System.out.println(user);
            }
        }
    }
}
