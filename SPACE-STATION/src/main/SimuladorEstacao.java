package main;

import entities.Astronauta;
import entities.RoboReparo;
import entities.system.*;
import db.ConexaoBanco;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Random;
import java.util.Scanner;

public class SimuladorEstacao {

    // Aqui executa a simulação principal após inicializar os módulos
    // Recebe os módulos já criados e o construtor do astronauta
    // Cria o gerenciadorTarefas e chama executarDias
    // Se vencer, exibe as estatísticas e registra a vitória no banco
    public static void partidaExecucao(ModuloEnergia energia, ModuloComunicacao comunicacao,
                                       ModuloSuporteVida vida, ModuloHabitacao habitacao,
                                       RoboReparo robo, ConstrucaoAstronauta constrAstro) {

        GerenciadorTarefas gerenciador = new GerenciadorTarefas(energia, comunicacao, vida, habitacao, null, robo, constrAstro);
        boolean venceu = gerenciador.executarDias(); // Roda os 10 dias

        if (venceu) {
            System.out.println("\n============ PARABÉNS! ============");
            System.out.println("Você sobreviveu por 10 dias!");
            System.out.println("Estatísticas finais:");
            System.out.println("Energia: " + energia.getEnergia() + "%");
            System.out.println("Oxigênio: " + vida.getOxigenio() + "%");
            System.out.println("Desgaste médio dos módulos: " +
                    (energia.desgaste() + comunicacao.desgaste() + vida.desgaste() + habitacao.desgaste()) / 4 + "%");

            Astronauta astroFinal = gerenciador.getAstronauta(); //Serve para obter o astronauta que foi utilizado durante a simulação após o término da partida
            if (astroFinal != null) {
                astroFinal.upwins();
                try {
                    constrAstro.atualizarVitorias(astroFinal);
                    System.out.println("Vitória registrada para o astronauta " + astroFinal.getNome());
                } catch (SQLException e) {
                    System.out.println("Erro ao salvar vitória no banco: " + e.getMessage());
                }
            }
        }
    }

    public static void main(String[] args) {
        PainelEstacao.iniciar(); // Aqui inicia a interface gráfica

        Connection conexao = null;
        ConstrucaoAstronauta constrAstro = null;
        try {
            conexao = ConexaoBanco.getConexaoMySQL();
            if (conexao != null) {
                System.out.println("Conectado ao banco de dados!");
                constrAstro = new ConstrucaoAstronauta(conexao); // cria um objeto da classe ConstrucaoAstronauta e passa a conexão com o banco de dados para ele
            } else {
                System.out.println("Falha ao conectar.");
                return;
            }
        } catch (Exception e) {
            e.printStackTrace(); // É um comando usado para imprimir no console o rastro da pilha de uma exceção, isso mostra exatamente onde o erro aconteceu
            return;
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
                    System.out.println("\nInicializando sistemas da Estação Órbita-1...");
                    scanner.nextLine();
                    System.out.println("------------------------------");
                    System.out.println("Carregando módulos...");
                    System.out.println("------------------------------");

                    try {
                        int desgasteInicialEnergia = rand.nextInt(31); // 31 porque a biblioteca random funciona assim: parametro - 1
                        int desgasteInicialComunicacao = rand.nextInt(31);
                        int desgasteInicialHabitacao = rand.nextInt(31);
                        int desgasteInicialVida = rand.nextInt(31);

                        ModuloComunicacao comunicacao = new ModuloComunicacao(desgasteInicialComunicacao);
                        ModuloEnergia energia = new ModuloEnergia(desgasteInicialEnergia);
                        ModuloHabitacao habitacao = new ModuloHabitacao(desgasteInicialHabitacao);
                        ModuloSuporteVida vida = new ModuloSuporteVida(desgasteInicialVida);
                        RoboReparo robo = new RoboReparo();

                        PainelEstacao.atualizarEnergia(energia.getEnergia(), energia.desgaste());
                        PainelEstacao.atualizarOxigenio(vida.getOxigenio());
                        PainelEstacao.atualizarPressao(vida.getPressaoValor());
                        PainelEstacao.atualizarTemperatura(vida.getTemp());
                        PainelEstacao.atualizarDesgastes(comunicacao.desgaste(), vida.desgaste(), habitacao.desgaste());
                        PainelEstacao.atualizarRobo(robo.getBateria());

                        System.out.println("-> Módulo de Energia: Ok");
                        System.out.println("-> Módulo de Suporte à Vida: OK");
                        System.out.println("-> Módulo de Comunicação: Ok");
                        System.out.println("-> Módulo de Habitação: Ok");
                        System.out.println("\nEstação prontamente operacional.");
                        scanner.nextLine();

                        partidaExecucao(energia, comunicacao, vida, habitacao, robo, constrAstro);

                    } catch (Exception e) {
                        System.out.println("Falha ao iniciar os módulos!");
                        e.printStackTrace();
                    }
                    break;

                case 2:
                    constrAstro.iniciar();
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
        try {
            if (conexao != null && !conexao.isClosed()) {
                conexao.close();
                System.out.println("Conexão fechada.");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}