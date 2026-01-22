package main;
import entities.system.*;
import db.*;
import java.sql.Connection;
import java.util.Scanner;

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

        Scanner scanner = new Scanner(System.in);
        int opcao;

        ModuloComunicacao comunicacao = null;
        ModuloEnergia energia = null;
        ModuloHabitacao habitacao = null;
        ModuloPesquisa pesquisa = null;
        ModuloSuporteVida vida = null;

        do {

            System.out.println("============================");
            System.out.println("    SIMULADOR DE ESTAÇÃO");
            System.out.println("          ESPACIAL");
            System.out.println("============================");

            System.out.println("1 -> Iniciar o jogo");
            System.out.println("2 -> Configurações");
            System.out.println("3 -> Como jogar?");
            System.out.println("4 -> Sair");

            opcao = scanner.nextInt();

            switch (opcao) {
                case 1:
                    System.out.println("Inicializando sistemas da Estação Órbita-1...");
                    System.out.println("Carregando módulos...");
                    scanner.nextLine();
                    scanner.nextLine();

                    try{
                        comunicacao = new ModuloComunicacao("Telstar 1", 0);
                        energia = new ModuloEnergia("Zarya", 0);
                        habitacao = new ModuloHabitacao("BEAM", 0);
                        pesquisa = new ModuloPesquisa("Project Mercury", 0);
                        vida = new ModuloSuporteVida("Unity", 0);

                        System.out.println("-> Módulo de Energia: Ok");
                        System.out.println("-> Módulo de Suporte à Vida: Ok");
                        System.out.println("-> Módulo de Comunicação: Ok");
                        System.out.println("-> Módulo de Habitação: Ok");
                        System.out.println("-> Módulo de Pesquisa: Ok");
                        System.out.println("\nEstação prontamente operacional.");
                        scanner.nextLine();
                    }catch (Exception e){
                        System.out.println("Falha ao iniciar os módulos!");
                        scanner.nextLine();
                }
                    System.out.println("------------------------------");
                    System.out.println("Status Atual da Estação:");
                    System.out.println("------------------------------");
                    System.out.println("Energia: " + energia.getEnergia() + "%");
                    System.out.println("Oxigênio: " + vida.getOxigenio() + "%");
                    System.out.println("Pressão Interna: " + vida.getPressao());
                    System.out.println("Temperatura: " + vida.getTemp() + "°C");
                    break;
                case 2:
                    break;
                case 3:
                    break;
                case 4:
                    System.out.println("Saindo...");
                    break;
                default:
                    System.out.println("Opção inválida!");
                    break;
            }

        }while(opcao != 4);
    }
}