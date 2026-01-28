package main;

import entities.Astronauta;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class construcaoAstronauta {
    static final String PASTA = "ASTRONAUTAS"; //definir nome da pasta
    static Scanner sc = new Scanner(System.in);
    public static void iniciar() {
        criarPastaSeNaoExistir();

        int opcao;
        do {
            System.out.println("\n=== MENU ===");
            System.out.println("1 - Criar novo Astronauta");
            System.out.println("2 - Listar astronautas salvos");
            System.out.println("3 - Dar boas-vindas ao astronauta");
            System.out.println("0 - Sair");
            System.out.print("Escolha: ");
            opcao = sc.nextInt();
            sc.nextLine();

            switch (opcao) {
                case 1 -> criarAstronauta();
                case 2 -> listarAstronauta();
                case 3 -> selecionarAstronauta();
                case 0 -> System.out.println("Saindo...");
                default -> System.out.println("Opção inválida!");
            }
        } while (opcao != 0);
    }


     static void criarAstronauta() {
        System.out.print("Nome: ");
        String nome = sc.nextLine();

        System.out.print("Idade: ");
        int idade = sc.nextInt();
        sc.nextLine();//evitar probleama de buffer, nextInt nao gero o /n que o next line faz
        System.out.print("Especialidade: ");
        String especialidade = sc.nextLine();
        System.out.print("Estado: ");
        String estado = sc.nextLine();

        Astronauta a = new Astronauta(nome,especialidade ,idade,estado);
        salvarAstronauta(a);

        System.out.println("Pessoa salva com sucesso!");
    }

    // =================== SALVAR ===================

    static void salvarAstronauta(Astronauta a) {
        criarPastaSeNaoExistir();

        String caminho = PASTA + "/" + a.id + ".dat";

        try (ObjectOutputStream out =
                     new ObjectOutputStream(new FileOutputStream(caminho))) {

            out.writeObject(a);

        } catch (IOException e) {
            System.out.println("Erro ao salvar astronauta.");
        }
    }


    // =================== LISTAR ===================

    static List<File> listarArquivos() { //List<File> retorna lista de arquivos
        File pasta = new File(PASTA);
        File[] arquivos = pasta.listFiles();

        if (arquivos == null) return new ArrayList<>();
        return Arrays.asList(arquivos);
    }

     static void listarAstronauta() {
        List<File> arquivos = listarArquivos();

        if (arquivos.isEmpty()) {//se tiver vazio retorna daqui
            System.out.println("Nenhum astronauta salvo.");
            return;
        }

        for (int i = 0; i < arquivos.size(); i++) {
            try (ObjectInputStream in =
                         new ObjectInputStream(new FileInputStream(arquivos.get(i)))) {

                entities.Astronauta a = (Astronauta) in.readObject();//reconstroi o objeto
                System.out.println(i + " - " + a);

            } catch (Exception e) {
                System.out.println("Erro ao ler arquivo.");
            }
        }
    }

    // =================== SELECIONAR ===================

     static Astronauta selecionarAstronauta() {
        List<File> arquivos = listarArquivos();

        if (arquivos.isEmpty()) {
            System.out.println("Nenhuma pessoa para selecionar.");
            return null;
        }
        while(true) {
            listarAstronauta();

            System.out.print("Escolha o número: ");
            int index = sc.nextInt();
            sc.nextLine();

            if (index < 0 || index >= arquivos.size()) {
                System.out.println("Escolha inválida.");
                continue;
            }

            try (ObjectInputStream in =
                         new ObjectInputStream(new FileInputStream(arquivos.get(index)))) {

                Astronauta a = (Astronauta) in.readObject();

                System.out.println("\nBem-vindo(a), " + a.nome + "!");
                System.out.println("Idade: " + a.idade + " anos");
                return a;
            } catch (Exception e) {
                System.out.println("Erro ao carregar astronauta.");
            }
        }
    }

    // =================== PASTA ===================

     static void criarPastaSeNaoExistir() {
        File pasta = new File(PASTA);
        if (!pasta.exists()) {
            pasta.mkdir();
        }
    }
}
