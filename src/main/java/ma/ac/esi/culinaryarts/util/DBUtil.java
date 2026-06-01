package ma.ac.esi.culinaryarts.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBUtil {
    // URL locale 
    private static final String URL = "jdbc:mysql://localhost:3306/culinaryarts_db";
    private static final String USER = "root"; 
    private static final String PASSWORD = "YOUR_PASSWORD"; 

    static {
        try {
            // Chargement du driver MySQL
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            throw new RuntimeException("MySQL JDBC Driver not found", e);
        }
    }

    public static Connection getConnection() {
        try {
            Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
            if (conn != null) {
                conn.setAutoCommit(true); // Force MySQL à enregistrer immédiatement
            }
            return conn;
        } catch (SQLException e) {
            System.err.println("Erreur de connexion !");
            e.printStackTrace();
        }
        return null;
    }
}