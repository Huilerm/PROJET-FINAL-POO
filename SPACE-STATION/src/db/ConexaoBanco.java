package db;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConexaoBanco {
    public static java.sql.Connection getConexaoMySQL(){
        Connection connection = null;
        String serverName = "localhost";
        String mydatabase = "space_db";
        String url = "jdbc:mysql://localhost:3307/space_db";
        String username = "root";
        String password = "4711";
        try{
            connection = DriverManager.getConnection(url, username, password);
        } catch (SQLException e){
            System.out.println("Erro ao conectar!");
            e.printStackTrace();
        }
        return connection;
    }
}
