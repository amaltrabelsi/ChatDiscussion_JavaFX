package  com.example.chatdiscussion.service ;



import com.example.chatdiscussion.model.Database;
import com.example.chatdiscussion.model.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class UserService {
    private Database database;

    public UserService(Database database) {
        this.database = database;
    }

    /**
     * Authentifie un utilisateur en vérifiant son mot de passe en clair.
     *
     * @param username Nom d'utilisateur
     * @param password Mot de passe
     * @return L'utilisateur authentifié ou null si échec
     */
    public User authenticate(String username, String password) {
        String query = "SELECT id, username, statuts FROM users WHERE username = ? AND password = ?";
        try (Connection conn = database.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setString(1, username);
            stmt.setString(2, password);

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return new User(rs.getInt("id"), rs.getString("username"), rs.getString("statuts"));
                }
            }
        } catch (SQLException e) {
            System.err.println("Erreur lors de l'authentification : " + e.getMessage());
        }
        return null;
    }
    /**
     * Récupère la liste des utilisateurs sauf l'utilisateur connecté.
     *
     * @param currentUserId ID de l'utilisateur actuel
     * @return Liste des utilisateurs
     */
    public List<User> getUsers(int currentUserId) {
        List<User> users = new ArrayList<>();
        String query = "SELECT id, username, statuts FROM users WHERE id != ?";

        try (Connection conn = database.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setInt(1, currentUserId);
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    users.add(new User(rs.getInt("id"), rs.getString("username"), rs.getString("statuts")));
                }
            }
        } catch (SQLException e) {
            System.err.println("Erreur lors de la récupération des utilisateurs : " + e.getMessage());
        }
        return users;
    }
}
