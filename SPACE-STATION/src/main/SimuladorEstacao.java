package main;
import entities.system.*;
import db.*;
import java.sql.Connection;

public class SimuladorEstacao {
    public static void main(String[] args) {
        try (Connection conexao = ConexaoBanco.getConexaoMySQL()) {
            if (conexao != null) {
                System.out.println("Conectado!");
            } else {
                System.out.println("Falha ao conectar.");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}