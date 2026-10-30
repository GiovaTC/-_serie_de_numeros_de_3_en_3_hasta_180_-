package config;

import java.sql.Connection;
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

            // Desactivar autocommit para controlar la transaccion
            conexion.setAutoCommit(false);

            System.out.println(
                    "CONEXION exitosa con Oracle 19c!"
            );

            return conexion;

        } catch (SQLException e) {

            System.out.println(
                    "Error al conectar con Oracle."
            );

            System.out.println(e.getMessage());

            return null;
        }
    }
}