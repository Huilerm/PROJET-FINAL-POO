package db;

import java.sql.Connection;
import java.sql.DriverManager;

public class ConexaoBanco {

    public static Connection getConexaoMySQL() {
        Connection connection = null;

        String url = "jdbc:mysql://localhost:3306/space_db?useSSL=false&serverTimezone=UTC";
        String username = "root";
        String password = "aluno";

        try {
            // FORÇA o carregamento do driver
            Class.forName("com.mysql.cj.jdbc.Driver");

            connection = DriverManager.getConnection(url, username, password);
            System.out.println("Conectado ao banco!");
        } catch (Exception e) {
            e.printStackTrace();
        }

        return connection;
    }
}
