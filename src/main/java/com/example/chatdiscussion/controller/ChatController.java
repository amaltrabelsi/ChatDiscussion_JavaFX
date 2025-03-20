package com.example.chatdiscussion.controller;

import com.example.chatdiscussion.model.Database;
import com.example.chatdiscussion.model.Message;
import com.example.chatdiscussion.model.User;
import com.example.chatdiscussion.service.MessageService;
import com.example.chatdiscussion.service.UserService;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import java.time.LocalDateTime;
import java.util.List;

public class ChatController {

    @FXML
    private VBox chatContainer;
    @FXML
    private VBox messageContainer;
    @FXML
    private VBox userButtonsContainer;

    private UserService serviceUser;
    private MessageService serviceMessage;
    private User currentUser;
    private User selectedUser;
    @FXML
    private TextField messageField;
    private Database database;

    /**
     * Initialise le contrôleur et assure que les services sont correctement initialisés.
     */
    @FXML
    public void initialize() {
        database = new Database();  // Si Database n'est pas déjà instanciée

        if (serviceUser != null && serviceMessage != null && currentUser != null) {
            loadUserButtons();
        } else {
            System.err.println("Les services ou l'utilisateur actuel ne sont pas initialisés.");
        }
        if (serviceMessage == null) {
            serviceMessage = new MessageService(database); // Assurez-vous d'utiliser l'initialisation correcte
        }

    }

    /**
     * Charge automatiquement les utilisateurs connectés sans boutons.
     */
    private void loadUserButtons() {
        if (serviceUser != null) {
            userButtonsContainer.getChildren().clear();
            List<User> users = serviceUser.getUsers(currentUser.getId());
            if (users == null || users.isEmpty()) {
                System.out.println("Aucun utilisateur trouvé.");
            } else {
                // Ajouter automatiquement les utilisateurs dans un format de label ou tout autre élément visuel
                for (User user : users) {
                    // Créez un label ou un autre élément visuel pour chaque utilisateur
                    Label userLabel = new Label(user.getUsername());
                    userLabel.setStyle("-fx-background-color: #f0f0f0; -fx-padding: 5px; -fx-border-radius: 10px; -fx-margin: 5px;");
                    userLabel.setOnMouseClicked(event -> openChatWithUser(user));
                    userButtonsContainer.getChildren().add(userLabel);
                }
            }
        } else {
            System.err.println("Le service des utilisateurs n'est pas initialisé.");
        }
    }

    /**
     * Ouvre une discussion avec l'utilisateur sélectionné.
     */
    private void openChatWithUser(User user) {
        setSelectedUser(user);
        messageContainer.getChildren().clear();
        loadMessages(user);
    }

    /**
     * Charge les messages avec l'utilisateur sélectionné.
     */
    private void loadMessages(User user) {
        if (serviceMessage != null) {
            List<Message> messages = serviceMessage.getMessages(currentUser.getId(), user.getId());
            if (messages == null || messages.isEmpty()) {
                System.out.println("Aucun message trouvé avec " + user.getUsername());
            } else {
                for (Message message : messages) {
                    addMessageToChat(message, user);
                }
            }
        } else {
            System.err.println("Le service des messages n'est pas initialisé.");
        }
    }

    /**
     * Ajoute un message à la zone de chat.
     */
    private void addMessageToChat(Message message, User user) {
        Label messageLabel = new Label(message.getMessage());
        messageLabel.setStyle("-fx-padding: 10px; -fx-background-radius: 10px;");
        messageLabel.setMaxWidth(300);

        HBox messageBox = new HBox();
        messageBox.setAlignment(message.getSenderId() == currentUser.getId() ? Pos.CENTER_RIGHT : Pos.CENTER_LEFT);

        if (message.getSenderId() == currentUser.getId()) {
            messageLabel.setStyle("-fx-background-color: #007bff; -fx-text-fill: white;");
        } else {
            messageLabel.setStyle("-fx-background-color: #e9ecef; -fx-text-fill: black;");
        }

        messageBox.getChildren().add(messageLabel);
        messageContainer.getChildren().add(messageBox);
    }

    /**
     * Gère l'envoi d'un message.
     */
    @FXML
    public void handleSendMessage() {
        String messageText = messageField.getText().trim();
        if (!messageText.isEmpty() && serviceMessage != null) {
            if (selectedUser != null) {
                serviceMessage.sendMessage(currentUser.getId(), selectedUser.getId(), messageText);
                Message message = new Message(currentUser.getId(), selectedUser.getId(), messageText, LocalDateTime.now().toString());
                addMessageToChat(message, selectedUser);
                messageField.clear();
            } else {
                System.err.println("Aucun utilisateur sélectionné pour envoyer le message.");
            }
        } else {
            System.err.println("Le message est vide ou le serviceMessage est nul.");
        }
    }

    /**
     * Définit l'utilisateur sélectionné.
     */
    public void setSelectedUser(User user) {
        this.selectedUser = user;
    }

    /**
     * Définit l'utilisateur actuellement connecté.
     */
    public void setCurrentUser(User currentUser) {
        this.currentUser = currentUser;
        if (serviceUser != null) {
            loadUserButtons();
        }
    }

    /**
     * Définit le service pour gérer les utilisateurs.
     */
    public void setServiceUser(UserService serviceUser) {
        this.serviceUser = serviceUser;
        if (currentUser != null) {
            loadUserButtons();
        }
    }

    /**
     * Définit le service pour gérer les messages.
     */
    public void setServiceMessage(MessageService serviceMessage) {
        this.serviceMessage = serviceMessage;
    }
}
