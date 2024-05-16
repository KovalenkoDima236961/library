/**
 * Module-info.java file defining the module structure and dependencies for the application.
 */
module com.example.loginpage {
    // Requires JavaFX modules for UI components
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.graphics;
    // Requires Java SQL and PostgreSQL JDBC driver for database access
    requires java.sql;
    requires org.postgresql.jdbc;
//    requires org.aspectj.tools;
    // Requires Java Desktop for AWT and Swing support
    requires java.desktop;


    opens com.example.loginpage to javafx.fxml;
    exports com.example.loginpage;

    opens com.example.loginpage.controller.login;
    exports com.example.loginpage.controller.login;

    opens com.example.loginpage.controller.mainClient;
    exports com.example.loginpage.controller.mainClient;

    opens com.example.loginpage.controller.profile;
    exports com.example.loginpage.controller.profile;

    opens com.example.loginpage.controller.topBookSection;
    exports com.example.loginpage.controller.topBookSection;

    opens com.example.loginpage.module.books;
    exports com.example.loginpage.module.books;

    opens com.example.loginpage.controller.browse;
    exports com.example.loginpage.controller.browse;

    opens com.example.loginpage.controller.categorie;
    exports com.example.loginpage.controller.categorie;

    opens com.example.loginpage.controller.favourites;
    exports com.example.loginpage.controller.favourites;

    opens com.example.loginpage.controller.quizSection;
    exports com.example.loginpage.controller.quizSection;

    opens com.example.loginpage.controller.topSection;
    exports com.example.loginpage.controller.topSection;

    opens com.example.loginpage.controller.voting;
    exports com.example.loginpage.controller.voting;

    opens com.example.loginpage.controller.history;
    exports com.example.loginpage.controller.history;

    opens com.example.loginpage.controller.dao;
    exports com.example.loginpage.controller.dao;

    opens com.example.loginpage.controller.factoryPattern;
    exports com.example.loginpage.controller.factoryPattern;

    opens com.example.loginpage.module.users;
    exports com.example.loginpage.module.users;

    opens com.example.loginpage.controller.observe;
    exports com.example.loginpage.controller.observe;

    opens com.example.loginpage.controller.serialization;
    exports com.example.loginpage.controller.serialization;

    opens com.example.loginpage.module.users.typeofuser;
    exports com.example.loginpage.module.users.typeofuser;

    opens com.example.loginpage.module.users.typeofuser.typeOfClient;
    exports com.example.loginpage.module.users.typeofuser.typeOfClient;

    opens com.example.loginpage.module.books.typeofbooks;
    exports com.example.loginpage.module.books.typeofbooks;

    opens com.example.loginpage.controller.interf;
    exports com.example.loginpage.controller.interf;
}