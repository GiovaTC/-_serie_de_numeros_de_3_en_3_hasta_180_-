import config.ConexionOracle;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class Main
{
    public static void main(String[] args)
    {

        // variables del programa.
        int  inicio = 3;
        int limite = 180;
        int incremento = 3;

        int contador = 0;

        // ESTABLECER CONEXION con oracle.
        try (Connection conexion = (Connection) ConexionOracle.conectar())
        {

            if (conexion == null)
            {
                System.out.println(
                        "No fue posible establecer la conexion."
                );
                return;
            }

            // SQL para insertar los numeros.
            String sql =
                    "INSERT INTO SERIE_NUMEROS (NUMERO) VALUES (?)";

            try (PreparedStatement sentencia = conexion.prepareStatement(sql)) {

                // ENCABEZADO.
                // Encabezado
                System.out.println("--------------------------------");
                System.out.println("SERIE DE NUMEROS DE 3 EN 3");
                System.out.println("--------------------------------");

                // GENERAR la serie.
                for (int numero = inicio; numero <= limite; numero += incremento)
                {
                    // IMPRIMIR el numero en consola.
                    System.out.println(
                            "Numero generado: " + numero
                    );

                    // PREPARAR insercion en Oracle!
                    sentencia.setInt(1, numero);

                    // EJECUTAR insercion.
                    sentencia.executeUpdate();

                    contador ++;
                }

                // confirmar transaccion!
                conexion.commit();

                // MOSTRAR resultado.
                System.out.println("--------------------------------");
                System.out.println(
                        "Proceso finalizado correctamente."
                );

                System.out.println(
                        "Total de numeros generados: " + contador
                );

                System.out.println(
                        "Ultimo numero generado: " + limite
                );

                System.out.println(
                        "Informacion almacenada en Oracle 19c."
                );

                System.out.println("--------------------------------");

            } catch (SQLException e) {
                // Revertir inserciones si ocurre un error
                conexion.rollback();

                System.out.println(
                        "Error durante el procesamiento de los datos."
                );

                System.out.println(e.getMessage());
            }
        } catch (SQLException e) {
            System.out.println(
                    "Error al cerrar o utilizar la conexion."
            );

            System.out.println(e.getMessage()); 
        }
    }
}