package main;

import entities.Astronauta;
import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class ConstrucaoAstronauta {
    static final String PASTA = "ASTRONAUTAS"; // definir nome da pasta
    static Scanner sc = new Scanner(System.in);

    public static void iniciar() {
        criarPastaSeNaoExistir();

        int opcao;
        do {
            System.out.println("\n======= MENU =======");
            System.out.println("1 -> Criar novo Astronauta");
            System.out.println("2 -> Listar astronautas salvos");
            System.out.println("0 -> Sair");
            System.out.println("====================");
            System.out.print("\nDigite sua escolha: ");
            opcao = sc.nextInt();
            sc.nextLine();

            switch (opcao) {
                case 1 -> criarAstronauta();
                case 2 -> listarAstronauta();
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
        sc.nextLine(); // evitar problema de buffer

        System.out.print("Especialidade: ");
        String especialidade = sc.nextLine();

        String estado = "normal";

        Astronauta a = new Astronauta(nome, especialidade, idade, estado);
        salvarAstronauta(a);

        System.out.println("\nAstronauta salvo com sucesso!");
    }

    // =================== SALVAR ===================

    public static void salvarAstronauta(Astronauta a) {
        criarPastaSeNaoExistir();

        String caminho = PASTA + "/" + a.id + ".dat";

        try (ObjectOutputStream out =
                     new ObjectOutputStream(new FileOutputStream(caminho))) {

            out.writeObject(a);

        } catch (IOException e) {
            System.out.println("\nErro ao salvar astronauta.");
        }
    }

    // =================== LISTAR ===================

    static List<File> listarArquivos() {
        File pasta = new File(PASTA);
        File[] arquivos = pasta.listFiles();

        if (arquivos == null) return new ArrayList<>();
        return Arrays.asList(arquivos);
    }

    static void listarAstronauta() {
        List<File> arquivos = listarArquivos();

        if (arquivos.isEmpty()) {
            System.out.println("\nNenhum astronauta salvo.");
            return;
        }

        System.out.println("\n======= ASTRONAUTAS REGISTRADOS =======");

        for (int i = 0; i < arquivos.size(); i++) {
            try (ObjectInputStream in =
                         new ObjectInputStream(new FileInputStream(arquivos.get(i)))) {

                Astronauta a = (Astronauta) in.readObject();
                System.out.println("\n" + i + " -> " + a);

            } catch (Exception e) {
                System.out.println("\nErro ao ler arquivo: " + arquivos.get(i).getName());
            }
        }
    }

    // =================== SELECIONAR ===================

    public static Astronauta selecionarAstronauta() {
        List<File> arquivos = listarArquivos();

        if (arquivos.isEmpty()) {
            System.out.println("\nNenhum astronauta para selecionar.");
            return null;
        }

        while (true) {
            listarAstronauta();

            System.out.print("\nDigite sua escolha: ");
            int index = sc.nextInt();
            sc.nextLine();

            if (index < 0 || index >= arquivos.size()) {
                System.out.println("\nEscolha inválida.");
                continue;
            }

            try (ObjectInputStream in =
                         new ObjectInputStream(new FileInputStream(arquivos.get(index)))) {

                Astronauta a = (Astronauta) in.readObject();
                return a; // apenas retorna, sem mensagens de boas-vindas
            } catch (Exception e) {
                System.out.println("\nErro ao carregar astronauta.");
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