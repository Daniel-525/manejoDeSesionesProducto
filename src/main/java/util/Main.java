package util;

import java.sql.Connection;

public class Main {
    public static void main(String[] args) {
        System.out.println("Intentando conectar a la base de datos...");

        try (Connection conn = ConexionBDD.getConnection()) {

            if (conn != null) {
                System.out.println("✅ Conexión a base de datos EXITOSA");
            } else {
                System.out.println("❌ Conexión NO EXITOSA");
            }

        } catch (Exception e) {
            System.out.println("❌ Conexión NO EXITOSA");
            System.out.println("Error: " + e.getMessage());
        }
    }
}
