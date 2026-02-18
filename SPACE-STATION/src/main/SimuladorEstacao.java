package main;

import entities.Astronauta;
import entities.RoboReparo;
import entities.system.*;
import db.ConexaoBanco;
import java.sql.Connection;
import java.util.Random;
import java.util.Scanner;

public class SimuladorEstacao {

    public static void partidaExecucao(ModuloEnergia energia, ModuloComunicacao comunicacao,
                                       ModuloSuporteVida vida, ModuloHabitacao habitacao,
                                       Astronauta astro, RoboReparo robo) {

        Scanner scanner = new Scanner(System.in);

        GerenciadorTarefas gerenciador = new GerenciadorTarefas(energia, comunicacao, vida, habitacao, astro, robo);
        boolean venceu = gerenciador.executarDias();

        if (venceu) {
            System.out.println("\n============ PARABÉNS! ============");
            System.out.println("Você sobreviveu por 10 dias!");
            System.out.println("Estatísticas finais:");
            System.out.println("Energia: " + energia.getEnergia() + "%");
            System.out.println("Oxigênio: " + vida.getOxigenio() + "%");
            System.out.println("Desgaste médio dos módulos: " +
                    (energia.desgaste() + comunicacao.desgaste() + vida.desgaste() + habitacao.desgaste()) / 4 + "%");

            Astronauta astroFinal = gerenciador.getAstronauta();
            if (astroFinal != null) {
                astroFinal.upwins();
                ConstrucaoAstronauta.salvarAstronauta(astroFinal);
                System.out.println("Vitória registrada para o astronauta " + astroFinal.nome);
            } else {
                System.out.println("Erro: astronauta não encontrado ao final da missão.");
            }
        }
    }

    public static void main(String[] args) {
        try (Connection conexao = ConexaoBanco.getConexaoMySQL()) {
            if (conexao != null) {
                System.out.println("Conectado ao banco de dados!");
            } else {
                System.out.println("Falha ao conectar.");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        Scanner scanner = new Scanner(System.in);
        int opcao;
        Random rand = new Random();

        do {
            System.out.println("============================");
            System.out.println("    SIMULADOR DE ESTAÇÃO");
            System.out.println("          ESPACIAL");
            System.out.println("============================");
            System.out.println("1 -> Iniciar o jogo");
            System.out.println("2 -> Gerenciar Astronautas");
            System.out.println("3 -> Como jogar?");
            System.out.println("4 -> Sair");
            System.out.print("\nDigite sua escolha: ");
            opcao = scanner.nextInt();
            scanner.nextLine();

            switch (opcao) {
                case 1:
                    System.out.println("\nInicializando sistemas da Estação Órbita-1...\n");
                    System.out.println("------------------------------");
                    System.out.println("Carregando módulos...");
                    System.out.println("------------------------------");

                    try {
                        int desgasteInicialEnergia = rand.nextInt(31);
                        int desgasteInicialComunicacao = rand.nextInt(31);
                        int desgasteInicialHabitacao = rand.nextInt(31);
                        int desgasteInicialVida = rand.nextInt(31);

                        ModuloComunicacao comunicacao = new ModuloComunicacao("Telstar 1", desgasteInicialComunicacao);
                        ModuloEnergia energia = new ModuloEnergia("Zarya", desgasteInicialEnergia);
                        ModuloHabitacao habitacao = new ModuloHabitacao("BEAM", desgasteInicialHabitacao);
                        ModuloSuporteVida vida = new ModuloSuporteVida("Unity", desgasteInicialVida);
                        RoboReparo robo = new RoboReparo("Robonaut-2");

                        System.out.println("-> Módulo de Energia: Ok");
                        System.out.println("-> Módulo de Suporte à Vida: OK");
                        System.out.println("-> Módulo de Comunicação: Ok");
                        System.out.println("-> Módulo de Habitação: Ok");
                        System.out.println("\nEstação prontamente operacional.");
                        scanner.nextLine();

                        System.out.println("------------------------------");
                        System.out.println("Status Atual da Estação:");
                        System.out.println("------------------------------");
                        System.out.println("Energia: " + energia.getEnergia() + "%");
                        System.out.println("Oxigênio: " + vida.getOxigenio() + "%");
                        System.out.println("Pressão Interna: " + vida.getPressao());
                        System.out.println("Temperatura: " + vida.getTemp() + "°C");
                        scanner.nextLine();

                        Astronauta astro = null;

                        partidaExecucao(energia, comunicacao, vida, habitacao, astro, robo);

                    } catch (Exception e) {
                        System.out.println("Falha ao iniciar os módulos!");
                        e.printStackTrace();
                    }
                    break;

                case 2:
                    ConstrucaoAstronauta.iniciar();
                    break;

                case 3:
                    System.out.println("\n======= COMO JOGAR =======");
                    System.out.println("-> Crie um astronauta no menu.");
                    System.out.println("-> Inicie o jogo e gerencie a estação por 10 dias.");
                    System.out.println("-> Cada dia você tem um número limitado de tarefas.");
                    System.out.println("-> Monitore a fadiga do astronauta e faça-o descansar quando necessário.");
                    System.out.println("-> Se qualquer módulo atingir 100% de desgaste, a missão falha.");
                    System.out.println("-> Se o astronauta atingir estado de emergência, a missão falha.");
                    System.out.println("-> Sobreviva 10 dias para vencer!");
                    System.out.println("\n======= INFORMAÇÕES EXTRAS =======");
                    System.out.println("-> Durante todo jogo, pressione ENTER sempre\n que precisar avançar ou continuar.");
                    System.out.println("-> Cuidado ao usar o robo de reparo, uma vez que a sua bateria acabe ele ficara inutilizável.");
                    scanner.nextLine();
                    break;

                case 4:
                    System.out.println("Saindo...");
                    break;

                default:
                    System.out.println("Opção inválida!");
            }
        } while (opcao != 4);
    }
}