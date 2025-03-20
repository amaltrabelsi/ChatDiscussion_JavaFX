package com.example.chatdiscussion.model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Database {

    private static final String URL = "jdbc:mysql://localhost:3306/message";
    private static final String USER = "root";
    private static final String PASSWORD = "";

    private Connection connection;

    // Constructeur qui essaie d'établir la connexion à la base de données
    public Database() {
        try {
            // Charger explicitement le driver JDBC pour MySQL
            Class.forName("com.mysql.cj.jdbc.Driver");

            // Établir la connexion
            connection = DriverManager.getConnection(URL, USER, PASSWORD);
            System.out.println("Connexion réussie à la base de données.");
        } catch (ClassNotFoundException e) {
            System.err.println("Driver JDBC non trouvé : " + e.getMessage());
            e.printStackTrace();
        } catch (SQLException e) {
            System.err.println("Erreur de connexion à la base de données : " + e.getMessage());
            e.printStackTrace();
        }
    }

    // Méthode pour récupérer la connexion
    public Connection getConnection() {
        // Vérifier si la connexion est encore ouverte
        if (connection == null) {
            System.err.println("La connexion à la base de données est null.");
        } else {
            try {
                // Si la connexion est fermée, rétablir la connexion
                if (connection.isClosed()) {
                    System.out.println("La connexion était fermée, nouvelle tentative de connexion.");
                    // Rétablir la connexion si elle est fermée
                    connection = DriverManager.getConnection(URL, USER, PASSWORD);
                    System.out.println("Nouvelle connexion établie.");
                } else {
                    System.out.println("Connexion est toujours valide.");
                }
            } catch (SQLException e) {
                System.err.println("Erreur lors de la vérification de la validité de la connexion : " + e.getMessage());
                e.printStackTrace();
            }
        }
        return connection;
    }
}
