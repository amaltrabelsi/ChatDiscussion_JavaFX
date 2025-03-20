package com.example.chatdiscussion.controller;

import com.example.chatdiscussion.model.User;
import com.example.chatdiscussion.service.UserService;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;

public class LoginController {
    @FXML
    private TextField usernameField;

    @FXML
    private PasswordField passwordField;

    private UserService serviceUser;

    // Méthode d'initialisation automatique après le chargement de FXML
    @FXML
    public void initialize() {
        if (serviceUser == null) {
            System.err.println("⚠️ Attention : UserService n'a pas été injecté !");
        }
    }

    // Méthode pour injecter UserService
    public void setServiceUser(UserService serviceUser) {
        this.serviceUser = serviceUser;
        System.out.println("✅ UserService injecté avec succès !");
    }

    @FXML
    public void handleLogin() {
        if (serviceUser == null) {
            showAlert("Erreur", "Le service utilisateur n'est pas initialisé !");
            return;
        }

        String username = usernameField.getText().trim();
        String password = passwordField.getText().trim();

        if (username.isEmpty() || password.isEmpty()) {
            showAlert("Erreur", "Veuillez remplir tous les champs.");
            return;
        }

        try {
            User user = serviceUser.authenticate(username, password);
            if (user != null) {
                showAlert("Succès", "Connexion réussie !");
                ((Stage) usernameField.getScene().getWindow()).close();
                openChatWindow(user);
            } else {
                showAlert("Erreur", "Nom d'utilisateur ou mot de passe incorrect.");
            }
        } catch (Exception e) {
            showAlert("Erreur", "Une erreur est survenue lors de la connexion.");
            e.printStackTrace();
        }
    }

    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    private void openChatWindow(User user) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/chatdiscussion/chat.fxml"));
            Parent root = loader.load();

            ChatController chatController = loader.getController();
            chatController.setCurrentUser(user);
            chatController.setServiceUser(serviceUser);

            Stage chatStage = new Stage();
            chatStage.setTitle("Discussion");
            chatStage.setScene(new Scene(root));
            chatStage.show();
        } catch (IOException e) {
            e.printStackTrace();
            showAlert("Erreur", "Impossible d'ouvrir la fenêtre de chat.");
        }
    }
}
