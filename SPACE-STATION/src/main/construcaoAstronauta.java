package main;

import entities.Astronauta;

import java.io.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class construcaoAstronauta {

    static Scanner sc = new Scanner(System.in);
    private Connection conn;


    public construcaoAstronauta(Connection conn) {
        this.conn = conn;
    }
    public void insertData(Astronauta a)throws SQLException {
        String sql= "INSERT INTO Astronauta(nome,idade,especialidade,estado) VALUES(?,?,?,?)";
        PreparedStatement pstmt = this.conn.prepareStatement(sql);
        pstmt.setString(1,a.nome);
        pstmt.setInt(2,a.idade);
        pstmt.setString(3,a.especialidade);
        pstmt.setString(4,a.estado);
        pstmt.executeUpdate();
        System.out.println("Dados inseridos com sucesso.");

    }
    public ArrayList<Astronauta> selectData() throws SQLException {
        String sql = "SELECT * FROM Astronauta";
        ArrayList<Astronauta> lista = new ArrayList<>();

        try (PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                Astronauta a = new Astronauta(
                        rs.getString("nome"),
                        rs.getString("especialidade"),
                        rs.getInt("idade"),
                        rs.getString("estado")
                );

                a.setId(rs.getInt("id")); // se tiver ID
                lista.add(a);
            }
        }

        return lista;
    }
    public Astronauta selecionarAstronauta() throws SQLException {
        ArrayList<Astronauta> lista = selectData();
        Scanner sc = new Scanner(System.in);

        if (lista.isEmpty()) {
            System.out.println("Nenhum astronauta cadastrado.");
            return null;
        }

        for (int i = 0; i < lista.size(); i++) {
            System.out.println(i + " - " + lista.get(i));
        }

        System.out.print("Escolha o astronauta: ");
        int opcao = sc.nextInt();

        if (opcao < 0 || opcao >= lista.size()) {
            System.out.println("Opção inválida.");
            return null;
        }

        return lista.get(opcao);
    }



    public static void selectData(Connection conn)throws SQLException{

    }
    public void iniciar() {


        int opcao;
        do {
            System.out.println("\n=== MENU ===");
            System.out.println("1 - Criar novo Astronauta");
            System.out.println("2 - Listar astronautas salvos");
            System.out.println("3 - Deletar astronauta");
            System.out.println("0 - Sair");
            System.out.print("Escolha: ");
            opcao = sc.nextInt();
            sc.nextLine();

            switch (opcao) {
                case 1 -> criarAstronauta();
                case 2 -> {
                    try {
                        ArrayList<Astronauta> lista = selectData();
                        for (Astronauta a : lista) {
                            System.out.println(a);
                        }
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                }

                case 3 ->{
                    System.out.println("digite o id do astronauta a ser deletado");
                    int x=sc.nextInt();
                    try {
                        deletarAstronauta(x);
                    } catch (SQLException e) {
                        throw new RuntimeException(e);
                    }
                }
                case 0 -> System.out.println("Saindo...");
                default -> System.out.println("Opção inválida!");
            }
        } while (opcao != 0);
    }
    public void deletarAstronauta(int id) throws SQLException {

        String sql = "DELETE FROM Astronauta WHERE id = ?";

        try (PreparedStatement stmt = conn.prepareStatement(sql)) {

            // Define o valor do parâmetro "?"
            stmt.setInt(1, id);

            // Executa o comando DELETE
            int linhasAfetadas = stmt.executeUpdate();

            // Só para confirmar se funcionou
            if (linhasAfetadas > 0) {
                System.out.println("Astronauta deletado com sucesso!");
            } else {
                System.out.println("Nenhum astronauta encontrado com esse ID.");
            }
        }
    }


    public void criarAstronauta() {

        System.out.print("Nome: ");
        String nome = sc.nextLine();

        System.out.print("Idade: ");
        int idade = sc.nextInt();
        sc.nextLine();

        System.out.print("Especialidade: ");
        String especialidade = sc.nextLine();

        System.out.print("Estado: ");
        String estado = sc.nextLine();

        Astronauta a = new Astronauta(nome, especialidade, idade, estado);

        try {
            insertData(a);
            System.out.println("Astronauta salvo no banco!");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public void atualizarVitorias(Astronauta a) throws SQLException {
        String sql = "UPDATE Astronauta SET wins = ? WHERE id = ?";

        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, a.getWins());
            stmt.setString(2, a.getId());
            stmt.executeUpdate();
        }
    }



}
