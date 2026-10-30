package config;

import com.sun.jdi.connect.spi.Connection;

import java.sql.DriverManager;
import java.sql.SQLException;

public class ConexionOracle {

    private static final String URL = "jdbc:oracle:thin:@//localhost:1521/orcl";
    private static final String USUARIO = "system";
    private static final String PASSWORD = "Tapiero123";

    public static Connection conectar()
    {
        try {
            Connection conexion = (Connection) DriverManager.getConnection(
                    URL,
                    USUARIO,
                    PASSWORD
            );

            // Se desactiva autocommit para confirmar la transacción
            // después de insertar todos los números .
            ((java.sql.Connection) conexion).setAutoCommit(false);

            System.out.println("CONEXION exitosa con oracle!");

            return conexion;

        } catch (SQLException e) {
            System.out.println("Error al conectar con Oracle.");
            System.out.println(e.getMessage());

            return null;
        }
    }
}
