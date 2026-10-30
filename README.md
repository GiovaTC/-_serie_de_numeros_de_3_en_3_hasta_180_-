# -_serie_de_numeros_de_3_en_3_hasta_180_- :.
SerieNumerosOracle19c:

<img width="1254" height="1254" alt="image" src="https://github.com/user-attachments/assets/e5cb9839-aee3-43fe-954c-8148dba49cb9" />  

<img width="2553" height="1079" alt="image" src="https://github.com/user-attachments/assets/8e7261a3-ed19-48a6-801d-d905c7b14ca5" />        
```

Programa desarrollado en Java con IntelliJ IDEA, JDBC y Oracle Database
19c. El programa genera una serie de números de 3 en 3, desde 3 hasta 180,
imprime los resultados en consola y almacena los 60 números en una tabla
de Oracle .
1. Tecnologías
- Java 21
- IntelliJ IDEA
- Maven
- Oracle Database 19c
- JDBC
2. Estructura del proyecto
SerieNumerosOracle19c/
├── pom.xml
└── src/
    └── main/
        └── java/
            ├── config/
            │   └── ConexionOracle.java
            └── Main.java
3. Crear la tabla en Oracle 19c
Ejecuta este script desde SQL Developer o SQL*Plus, conectado con el
usuario que utilizará el programa:
CREATE TABLE SERIE_NUMEROS (
    ID NUMBER GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    NUMERO NUMBER NOT NULL,
    FECHA_REGISTRO DATE DEFAULT SYSDATE
);
4. Código completo
Archivo pom.xml
Ubicación: raíz del proyecto.
<?xml version="1.0" encoding="UTF-8"?>
<project xmlns="http://maven.apache.org/POM/4.0.0"
         xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
         xsi:schemaLocation="http://maven.apache.org/POM/4.0.0
         https://maven.apache.org/xsd/maven-4.0.0.xsd">

    <modelVersion>4.0.0</modelVersion>

    <groupId>com.oracle</groupId>
    <artifactId>SerieNumerosOracle19c</artifactId>
    <version>1.0-SNAPSHOT</version>

    <properties>
        <maven.compiler.source>21</maven.compiler.source>
        <maven.compiler.target>21</maven.compiler.target>
        <project.build.sourceEncoding>UTF-8</project.build.sourceEncoding>
    </properties>

    <dependencies>
        <!-- Controlador JDBC de Oracle -->
        <dependency>
            <groupId>com.oracle.database.jdbc</groupId>
            <artifactId>ojdbc11</artifactId>
            <version>23.6.0.24.10</version>
        </dependency>
    </dependencies>

</project>
Archivo ConexionOracle.java
Ubicación: src/main/java/config/ConexionOracle.java
package config;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConexionOracle {

    private static final String URL =
            "jdbc:oracle:thin:@localhost:1521/orcl";

    private static final String USUARIO = "SYSTEM";

    private static final String PASSWORD = "TU_PASSWORD";

    public static Connection conectar() {

        try {
            Connection conexion = DriverManager.getConnection(
                    URL,
                    USUARIO,
                    PASSWORD
            );

            // Se desactiva autocommit para confirmar la transacción
            // después de insertar todos los números.
            conexion.setAutoCommit(false);

            System.out.println("Conexion exitosa con Oracle 19c.");

            return conexion;

        } catch (SQLException e) {

            System.out.println("Error al conectar con Oracle.");
            System.out.println(e.getMessage());

            return null;
        }
    }
}
Reemplaza TU_PASSWORD por la contraseña de tu usuario de Oracle. Si tu
base de datos utiliza otro servicio, modifica la URL JDBC.
Archivo Main.java
Ubicación: src/main/java/Main.java
import config.ConexionOracle;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class Main {

    public static void main(String[] args) {

        // Variables del programa
        int inicio = 3;
        int limite = 180;
        int incremento = 3;

        int contador = 0;

        // Establecer conexion con Oracle
        try (Connection conexion = ConexionOracle.conectar()) {

            if (conexion == null) {
                System.out.println(
                        "No fue posible establecer la conexion."
                );
                return;
            }

            // SQL para insertar los numeros
            String sql =
                    "INSERT INTO SERIE_NUMEROS (NUMERO) VALUES (?)";

            try (PreparedStatement sentencia =
                         conexion.prepareStatement(sql)) {

                // Encabezado
                System.out.println("--------------------------------");
                System.out.println("SERIE DE NUMEROS DE 3 EN 3");
                System.out.println("--------------------------------");

                // Generar la serie
                for (int numero = inicio;
                     numero <= limite;
                     numero += incremento) {

                    // Imprimir el numero en consola
                    System.out.println(
                            "Numero generado: " + numero
                    );

                    // Preparar insercion en Oracle
                    sentencia.setInt(1, numero);

                    // Ejecutar insercion
                    sentencia.executeUpdate();

                    contador++;
                }

                // Confirmar transaccion
                conexion.commit();

                // Mostrar resultado
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
5. Resultado esperado en consola
Conexion exitosa con Oracle 19c.
--------------------------------
SERIE DE NUMEROS DE 3 EN 3
--------------------------------
Numero generado: 3
Numero generado: 6
Numero generado: 9
Numero generado: 12
Numero generado: 15
Numero generado: 18
Numero generado: 21
Numero generado: 24
Numero generado: 27
Numero generado: 30
...
Numero generado: 168
Numero generado: 171
Numero generado: 174
Numero generado: 177
Numero generado: 180
--------------------------------
Proceso finalizado correctamente.
Total de numeros generados: 60
Ultimo numero generado: 180
Informacion almacenada en Oracle 19c.
--------------------------------
6. Consultar los datos almacenados
Consultar todos los números
SELECT
    ID,
    NUMERO,
    FECHA_REGISTRO
FROM SERIE_NUMEROS
ORDER BY NUMERO;
Consultar la cantidad de registros
SELECT COUNT(*) AS TOTAL_NUMEROS
FROM SERIE_NUMEROS;
Resultado esperado después de una ejecución sobre una tabla vacía:
TOTAL_NUMEROS
-------------
           60
Consultar el número mayor
SELECT MAX(NUMERO) AS NUMERO_MAYOR
FROM SERIE_NUMEROS;
Resultado esperado:
NUMERO_MAYOR
-------------
          180
7. Ejecución en IntelliJ IDEA
  1. Crear un proyecto Maven en IntelliJ IDEA.
  2. Seleccionar Java 21.
  3. Copiar el contenido del archivo pom.xml.
  4. Crear el paquete config.
  5. Crear la clase ConexionOracle.java dentro del paquete config.
  6. Crear la clase Main.java.
  7. Ejecutar el script SQL para crear la tabla.
  8. Configurar las credenciales y el servicio de Oracle en
   ConexionOracle.java.
  9. Ejecutar la clase Main.java.
8. Funcionamiento del algoritmo
El ciclo for genera la serie:
for (int numero = 3; numero <= 180; numero += 3) {
    System.out.println(numero);
}
- El número inicial es 3.
- El incremento es 3.
- El ciclo continúa mientras el número sea menor o igual a 180.
- El último número generado es 180.
La cantidad de números es:
180 / 3 = 60
Por lo tanto, se generan 60 números.
9. Limpiar la tabla antes de ejecutar nuevamente (opcional)
Si deseas que cada ejecución almacene solamente una serie de 60 números,
puedes limpiar la tabla antes de iniciar el programa:
TRUNCATE TABLE SERIE_NUMEROS;
Advertencia: TRUNCATE elimina todos los registros de la tabla y no
se puede deshacer con ROLLBACK. Utilízalo únicamente si deseas borrar
los datos existentes.

Si no limpias la tabla, cada ejecución insertará otros 60 registros.
Conclusión
Este proyecto permite practicar:
- Ciclos for en Java.
- Generación de series numéricas.
- Conexión JDBC con Oracle Database 19c.
- Inserción de datos mediante PreparedStatement.
- Manejo de transacciones con commit y rollback.
- Consultas SQL para validar los datos almacenados.
:. . / .
