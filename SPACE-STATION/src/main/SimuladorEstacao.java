package main;
import entities.system.*;
import entities.*;
import java.util.Scanner;
import db.*;
import java.sql.Connection;

public class SimuladorEstacao {
    public static void main(String[] args){
        try (Connection conexao = ConexaoBanco.getConexaoMySQL()) {
            if (conexao != null) {
                System.out.println("Conectado!");
            } else {
                System.out.println("Falha ao conectar.");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        Scanner scanner = new Scanner(System.in);
        int opcao;

        do {
            System.out.println("====================");
            System.out.println("SIMULADOR DE ESTAÇÃO");
            System.out.println("      ESPACIAL");
            System.out.println("====================");
            System.out.println();
            System.out.println("1 - Iniciar");
            opcao = scanner.nextInt();

            switch (opcao){
                case 1:
                    System.out.println("Inicializando sistemas da Estação Salyut-1...");
                    System.out.println("Carregando módulos...");
                    ModuloEnergia energia = new ModuloEnergia();
                    energia.estadoModulo();
                    ModuloSuporteVida suporte = new ModuloSuporteVida();
                    suporte.estadoModulo();
                    ModuloComunicacao comunicacao = new ModuloComunicacao();
                    comunicacao.estadoModulo();
                    ModuloHabitacao habitacao = new ModuloHabitacao();
                    habitacao.estadoModulo();
                    ModuloPesquisa pesquisa = new ModuloPesquisa();
                    pesquisa.estadoModulo();
            }
        }while (opcao != -1);
    }
}