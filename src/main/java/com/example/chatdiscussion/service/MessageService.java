package com.example.chatdiscussion.service;

import com.example.chatdiscussion.model.Message;
import com.example.chatdiscussion.model.Database;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class MessageService {
    private Database database;

    public MessageService(Database database) {
        this.database = database;
    }

    /**
     * Envoie un message.
     *
     * @param senderId   ID de l'expéditeur
     * @param receiverId ID du destinataire
     * @param message    Contenu du message
     */
    public void sendMessage(int senderId, int receiverId, String message) {
        String query = "INSERT INTO messages (sender_id, receiver_id, message) VALUES (?, ?, ?)";
        try (PreparedStatement stmt = database.getConnection().prepareStatement(query)) {
            stmt.setInt(1, senderId);
            stmt.setInt(2, receiverId);
            stmt.setString(3, message);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Récupère les messages entre deux utilisateurs.
     *
     * @param senderId   ID de l'expéditeur
     * @param receiverId ID du destinataire
     * @return Liste des messages
     */
    public List<Message> getMessages(int senderId, int receiverId) {
        List<Message> messages = new ArrayList<>();
        String query = "SELECT sender_id, message, timestamp FROM messages WHERE (sender_id = ? AND receiver_id = ?) OR (sender_id = ? AND receiver_id = ?) ORDER BY timestamp ASC";
        try (PreparedStatement stmt = database.getConnection().prepareStatement(query)) {
            stmt.setInt(1, senderId);
            stmt.setInt(2, receiverId);
            stmt.setInt(3, receiverId);
            stmt.setInt(4, senderId);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                messages.add(new Message(rs.getInt("sender_id"), receiverId, rs.getString("message"), rs.getString("timestamp")));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return messages;
    }
}