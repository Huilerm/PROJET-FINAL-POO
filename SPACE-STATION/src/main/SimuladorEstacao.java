package main;
import entities.Astronauta;
import entities.system.*;
import db.*;
import java.sql.Connection;
import java.util.Scanner;

public class SimuladorEstacao {
    public static void partidaExecucao(ModuloEnergia me,ModuloComunicacao mc,ModuloSuporteVida ms,ModuloHabitacao mh,ModuloPesquisa mp){
        Scanner scanner = new Scanner(System.in);
        int opc;
        Astronauta a=null;
        construcaoAstronauta x=new construcaoAstronauta();
        a=x.selecionarAstronauta();
        if (a==null){
            System.out.println("\nCrie um astronauta primeiro!!!\n");
            return;
        }

        for (int i=1;i<11;i++){
            System.out.println("Dia " + i);

            do {
                opc=0;

                System.out.println("Selecione o módulo para ver detalhes:");
                System.out.println("1 -> ENERGIA");
                System.out.println("2 -> COMUNICAÇÃO");
                System.out.println("3 -> SUPORTE A VIDA");
                System.out.println("4 -> HABITAÇÃO");
                System.out.println("5 -> PESQUISA");
                System.out.println("6 -> IR PRO PRÓXIMO DIA");
                opc = scanner.nextInt();
                switch (opc) {
                    case 1:
                        System.out.println("Unidades de Energia:" + me.getEnergia());
                        System.out.println("Estado do módulo:"+me.verificarSobrecarga());
                        scanner.nextLine();
                        break;

                    case 2:
                        System.out.print("Sinal com a Terra: "+mc.gerarAlerta());
                        scanner.nextLine();
                        break;
                    case 3:
                        System.out.println("Oxigenio:" + ms.getOxigenio() + " Pressão: " + ms.getPressao() + " Temperatura: " + ms.getTemp() + "°C");
                        break;
                    case 4:
                        System.out.println("Conforto: " + mh.gerarAlerta());
                        mh.avaliarIntegridade();
                        break;
                    case 5:
                        System.out.println("Integridade da pesquisa:"+mp.gerarAlerta());
                        break;
                    case 6:
                        break;
                    default:
                        System.out.println("Opção inválida!");


                }
            }while(opc!=6);

        }
        System.out.println("Parabéns vc sobreviveu por 10 dias");
        System.out.println("Você mais uma vitória pro seu Astronauta");
        a.upwins();
        x.salvarAstronauta(a);
        return;
    }
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
            System.out.println("2 -> Gerenciar/Criar Astronautas");
            System.out.println("3 -> Como jogar?");
            System.out.println("4 -> Sair");
            Astronauta protagonista=null;
            opcao = scanner.nextInt();

            switch (opcao) {
                case 1:
                    System.out.println("Inicializando sistemas da Estação Órbita-1...");
                    System.out.println("Carregando módulos...");
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
                    partidaExecucao(energia,comunicacao,vida,habitacao,pesquisa);
                    break;
                case 2:
                    construcaoAstronauta a=new construcaoAstronauta();
                    a.iniciar();
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