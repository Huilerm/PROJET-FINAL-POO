package main;

import entities.Astronauta;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Scanner;

public class ConstrucaoAstronauta {

    static Scanner sc = new Scanner(System.in);
    private Connection conn;

    public ConstrucaoAstronauta(Connection conn) {
        this.conn = conn; // Guarda a conexão para ser usada nos métodos de banco
    }

    // Metodo READ (lista todos os astronautas)
    public ArrayList<Astronauta> selectData() throws SQLException {
        String sql = "SELECT * FROM Astronauta"; // Consulta SQL simples
        ArrayList<Astronauta> lista = new ArrayList<>(); // Lista que armazenará os objetos

        try (PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            // Percorre cada linha do resultado
            while (rs.next()) {
                // Cria um objeto Astronauta com os dados do banco
                Astronauta a = new Astronauta(
                        rs.getString("nome"),
                        rs.getString("especialidade"),
                        rs.getInt("idade"),
                        "normal"  // estado inicial sempre normal (não é salvo no banco)
                );
                a.setId(rs.getInt("id")); // Define o ID vindo do banco
                lista.add(a); // Define a lista
            }
        }
        return lista; // Retorna a lista com todos os astronautas
    }

    // Metodo READ (selecionar um astronauta da lista pelo índice)
    public Astronauta selecionarAstronauta() throws SQLException {
        ArrayList<Astronauta> lista = selectData(); // Obtém a lista completa

        if (lista.isEmpty()) {
            System.out.println("Nenhum astronauta cadastrado.");
            return null;
        }

        // Exibe a lista com índices (0, 1, 2...) para o usuário escolher
        for (int i = 0; i < lista.size(); i++) {
            System.out.println("ID - " + i + lista.get(i));
        }

        System.out.print("Escolha o astronauta: ");
        int opcao = sc.nextInt();
        sc.nextLine(); // limpa o buffer do scanner (importante após nextInt)

        if (opcao < 0 || opcao >= lista.size()) {
            System.out.println("Opção inválida.");
            return null;
        }

        return lista.get(opcao); // Retorna o astronauta escolhido (pelo índice)
    }

    // Metodo UPDATE (atualiza todos os campos de um astronauta)
    public void atualizarAstronauta(Astronauta a) throws SQLException {
        String sql = "UPDATE Astronauta SET nome = ?, idade = ?, especialidade = ?, estado = ? WHERE id = ?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            // Preenche os parâmetros da consulta (os ? são substituídos)
            stmt.setString(1, a.getNome());
            stmt.setInt(2, a.getIdade());
            stmt.setString(3, a.getEspecialidade());
            stmt.setString(4, a.getEstado());
            stmt.setInt(5, a.getId());

            int linhasAfetadas = stmt.executeUpdate(); // Executa a atualização
            if (linhasAfetadas > 0) {
                System.out.println("\nAstronauta atualizado com sucesso!\n");
            } else {
                System.out.println("\nNenhum astronauta encontrado com esse ID.\n");
            }
        }
    }

    // Metodo UPDATE (apenas o campo de vitórias)
    public void atualizarVitorias(Astronauta a) throws SQLException {
        String sql = "UPDATE Astronauta SET wins = ? WHERE id = ?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, a.getWins()); // Seta o número de vitórias
            stmt.setInt(2, a.getId()); // Seta o ID do astronauta
            stmt.executeUpdate(); // Executa, não precisa verificar linhas afetadas
        }
    }

    // Metodo DELETE (remove um astronauta pelo ID)
    public void deletarAstronauta(int id) throws SQLException {
        String sql = "DELETE FROM Astronauta WHERE id = ?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            int linhasAfetadas = stmt.executeUpdate();
            if (linhasAfetadas > 0) {
                System.out.println("Astronauta deletado com sucesso!");
            } else {
                System.out.println("Nenhum astronauta encontrado com esse ID.");
            }
        }
    }

    // Metodo CREATE (insere um novo astronauta a partir da entrada do usuário)
    public Astronauta criarAstronauta() {
        System.out.print("Nome: ");
        String nome = sc.nextLine();

        System.out.print("Idade: ");
        int idade = sc.nextInt();
        sc.nextLine();

        System.out.print("Especialidade: ");
        String especialidade = sc.nextLine();

        String estado = "normal";

        Astronauta a = new Astronauta(nome, especialidade, idade, estado);

        // SQL para inserir, com opção de retornar a chave gerada (ID auto increment)
        String sql = "INSERT INTO Astronauta(nome, idade, especialidade, estado) VALUES(?,?,?,?)";
        try (PreparedStatement pstmt = conn.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS)) {
            pstmt.setString(1, a.getNome());
            pstmt.setInt(2, a.getIdade());
            pstmt.setString(3, a.getEspecialidade());
            pstmt.setString(4, a.getEstado());
            pstmt.executeUpdate(); // Aqui é executado a insercao

            // Recupera o ID gerado automaticamente pelo banco
            try (ResultSet rs = pstmt.getGeneratedKeys()) {
                if (rs.next()) {
                    a.setId(rs.getInt(1));
                }
            }
            System.out.println("Astronauta salvo no banco!");
            return a; // Retorna o objeto já com ID preenchido
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    // Metodo auxiliar para editar um astronauta (coleta novos dados)
    public void editarAstronauta(Astronauta a) throws SQLException {
        System.out.println("Deixe em branco para manter o valor atual.\n");

        System.out.print("Novo nome (" + a.getNome() + "): ");
        String nome = sc.nextLine();
        if (!nome.isBlank()) { // Se não estiver em branco, atualiza
            a.setNome(nome);
        }

        System.out.print("Nova idade (" + a.getIdade() + "): ");
        String idadeStr = sc.nextLine();
        if (!idadeStr.isBlank()) {
            try {
                int idade = Integer.parseInt(idadeStr);
                a.setIdade(idade);
            } catch (NumberFormatException e) {
                System.out.println("Idade inválida, mantida a anterior.");
            }
        }

        System.out.print("Nova especialidade (" + a.getEspecialidade() + "): ");
        String especialidade = sc.nextLine();
        if (!especialidade.isBlank()) {
            a.setEspecialidade(especialidade);
        }

        // Chama o metodo de update no banco com os novos dados
        atualizarAstronauta(a);
    }

    // Menu principal
    public void iniciar() {
        int opcao;
        do {
            System.out.println("\n======= MENU =======");
            System.out.println("1 -> Criar novo Astronauta");
            System.out.println("2 -> Listar astronautas salvos");
            System.out.println("3 -> Deletar astronauta");
            System.out.println("4 -> Editar astronauta");
            System.out.println("0 -> Sair");
            System.out.print("\nDigite sua escolha: ");
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

                case 3 -> {
                    System.out.print("Digite o id do astronauta a ser deletado: ");
                    int x = sc.nextInt();
                    sc.nextLine();
                    try {
                        deletarAstronauta(x);
                    } catch (SQLException e) {
                        throw new RuntimeException(e);
                    }
                }

                case 4 -> {
                    try {
                        Astronauta astro = selecionarAstronauta();
                        if (astro != null) {
                            editarAstronauta(astro);
                        }
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                }

                case 0 -> System.out.println("Saindo...");
                default -> System.out.println("Opção inválida!");
            }
        } while (opcao != 0);
    }
}