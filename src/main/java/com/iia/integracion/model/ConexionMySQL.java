package com.iia.integracion.model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConexionMySQL {

    // Datos de la conexión
    private static final String URL = "jdbc:mysql://tinovpn.duckdns.org:2025/bebidas";
    private static final String USUARIO = "App";
    private static final String CLAVE = "2025";
    private static Connection conexion;

    public static Connection getConexion() {
        if (conexion == null) {
            try {
                // Establece la conexión
                System.out.println("Estableciendo conexion...");
                conexion = DriverManager.getConnection(URL, USUARIO, CLAVE);
                System.out.println("Conexión exitosa a la base de datos.");
            } catch (SQLException e) {
                System.err.println("Error al conectar con la base de datos: " + e.getMessage());
            }
        }
        return conexion;
    }

}
