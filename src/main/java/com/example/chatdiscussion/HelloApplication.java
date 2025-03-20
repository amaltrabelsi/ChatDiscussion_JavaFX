package com.example.chatdiscussion;

import com.example.chatdiscussion.controller.LoginController;
import com.example.chatdiscussion.model.Database;
import com.example.chatdiscussion.service.UserService;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class HelloApplication extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {
        // Initialiser UserService avec Database
        Database database = new Database();
        UserService userService = new UserService(database);

        // Charger la vue login.fxml
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/chatdiscussion/login.fxml"));
        Parent root = loader.load();

        // Récupérer le contrôleur et injecter UserService
        LoginController loginController = loader.getController();
        loginController.setServiceUser(userService);

        // Afficher la scène
        primaryStage.setTitle("Chat - Connexion");
        primaryStage.setScene(new Scene(root, 600, 400));
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
