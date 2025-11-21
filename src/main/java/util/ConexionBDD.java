package util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConexionBDD {
    // Conexion a la base de datos mediante la URL
    private static String url = "jdbc:mysql://localhost:3306/sistemaventas?serverTimezone=UTC";
    private static String username = "root";
    private static String password = "";

    // Constructor para obtener la conexion
    public static Connection getConnection() {
        try {
            // Intentar conectar a la base de datos
            Connection connection = DriverManager.getConnection(url, username, password);
            System.out.println("Conexión exitosa a la base de datos.");
            return connection;
        } catch (SQLException e) {
            System.out.println("No se pudo establecer la conexión a la base de datos.");
            e.printStackTrace();
            return null;
        }
    }

    // Metodo principal para probar la conexión
    public static void main(String[] args) {
        getConnection();
    }
}
