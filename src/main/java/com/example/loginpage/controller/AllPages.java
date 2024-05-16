package com.example.loginpage.controller;

import com.example.loginpage.controller.dao.UserDAO;
import com.example.loginpage.controller.interf.Movement;
import com.example.loginpage.module.users.User;
import javafx.application.Platform;
import javafx.fxml.Initializable;

import java.util.concurrent.CompletableFuture;

public abstract class AllPages implements Initializable, Movement {

    public abstract void refreshFavorites();
}
