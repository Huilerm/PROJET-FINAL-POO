package main;
import entities.system.*;
import entities.*;

import java.util.ArrayList;
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
                    ModuloSuporteVida suporte = new ModuloSuporteVida();
                    ModuloComunicacao comunicacao = new ModuloComunicacao();
                    ModuloHabitacao habitacao = new ModuloHabitacao();
                    ModuloPesquisa pesquisa = new ModuloPesquisa();
                    ArrayList<Modulo> m=new ArrayList<>();
                    m.add(energia);
                    m.add(suporte);
                    m.add(comunicacao);
                    m.add(habitacao);
                    m.add(pesquisa);
                    for( Modulo md:m){
                        md.estadoModulo();
                    }
                    for (int i=1;i<=10;i++){
                        scanner.nextLine();
                        System.out.println("Dia "+i);
                        for( Modulo md:m){
                            md.estadoModulo();
                        }
                        energia.produzirEnergia();
                        energia.calcularConsumo();
                        energia.atualizarProducao();
                        energia.verificarSobrecarga();
                        suporte.verificarCondicoes();
                        //suporte.forcarEmergencia();
                        //suporte.degradarCondicoes();
                        comunicacao.verificarSinal(energia);
                        if (comunicacao.contatoTerra==false){
                            comunicacao.reparar();
                        }
                        suporte.degradarCondicoes();

                        habitacao.avaliarConforto(energia,suporte);
                        habitacao.avaliarIntegridade();
                        pesquisa.desligarExperimentos(energia);
                    }
            }
        }while (opcao != -1);
    }
}