package main;

import entities.Astronauta;
import entities.RoboReparo;
import entities.system.*;
import java.util.Scanner;

public class GerenciadorTarefas {

    private ModuloEnergia energia;
    private ModuloComunicacao comunicacao;
    private ModuloSuporteVida vida;
    private ModuloHabitacao habitacao;
    private Astronauta astronauta;
    private RoboReparo robo;

    Scanner scanner = new Scanner(System.in);

    public GerenciadorTarefas(ModuloEnergia energia, ModuloComunicacao comunicacao,
                              ModuloSuporteVida vida, ModuloHabitacao habitacao,
                              Astronauta astronauta, RoboReparo robo) {
        this.energia = energia;
        this.comunicacao = comunicacao;
        this.vida = vida;
        this.habitacao = habitacao;
        this.astronauta = astronauta;
        this.robo = robo;
    }

    public Astronauta getAstronauta() {
        return astronauta;
    }

    private final String[][] tarefasDia = { // Aqui é guardado as tarefas para exibição no menu
            {"Selecionar ou criar astronauta"},
            {"Otimizar painéis solares (produzir +20% energia)", "Reconfigurar distribuição elétrica"},
            {"Substituir filtros de CO² no Módulo Suporte Vida", "Checar reservas de água e oxigênio", "Teste de emergência de selagem de módulos"},
            {"Recalibrar antena de comunicação", "Coletar dados do experimento botânico", "Transmitir relatório para Terra"},
            {"Ativar Módulo Habitação adicional", "Iniciar novo experimento científico", "Manutenção preventiva em sistemas críticos", "Exercícios de equipe para moral"},
            {"Reparar falha no sistema de refrigeração", "Reconfigurar rede elétrica após surto", "Monitorar saúde da tripulação"},
            {"Reparar painéis solares (EVA virtual)", "Analisar causa das falhas", "Atualizar protocolos de emergência"},
            {"Substituir baterias principais", "Limpeza de dutos de ventilação", "Atualização de software de controle", "Checagem estrutural externa"},
            {"Teste completo de todos os módulos", "Backup de dados críticos", "Preparar kits de emergência", "Simulação de evacuação"},
            {"Ativar blindagem contra radiação", "Reduzir consumo ao mínimo essencial", "Manter comunicação com Terra", "Monitorar condições da tripulação", "Tomar decisão crítica: abandonar módulo danificado?"}
    };

    private String getObjetivoDia(int dia) { // Aqui é a exibição do tema de cada dia
        switch (dia) {
            case 1: return "Familiarização com a interface e sistemas básicos";
            case 2: return "Garantir fornecimento de energia estável";
            case 3: return "Manutenção de sistemas de sobrevivência";
            case 4: return "Restabelecer contatos e realizar pesquisas";
            case 5: return "Aumentar capacidades da estação";
            case 6: return "Lidar com múltiplas falhas simultâneas";
            case 7: return "Estabilizar após a crise";
            case 8: return "Realizar reparos especializados";
            case 9: return "Otimizar todos os sistemas";
            case 10: return "Superar desafio máximo";
            default: return "";
        }
    }

    private void aplicarDesgasteDiario() {
        energia.aumentarDesgaste(8);
        comunicacao.aumentarDesgaste(6);
        vida.aumentarDesgaste(10);
        habitacao.aumentarDesgaste(5);

        vida.degradarCondicoes();
        vida.avaliarCondicoes(astronauta);
        energia.atualizarProducao();
        energia.produzirEnergia();
        comunicacao.verificarSinal(energia);
        habitacao.avaliarConforto(energia, astronauta);
    }

    // Metodo chamado para executar cada dia
    public boolean executarDias() {

        for (int dia = 1; dia <= 10; dia++) { // Começa no dia 1 e vai até o dia 10
            System.out.println("\n======= DIA " + dia + " =======");
            System.out.println("Objetivo: " + getObjetivoDia(dia)); // Metodo objetivoDia

            if (dia > 1) {
                aplicarDesgasteDiario(); // O desgaste começa a partir do dia dois
            }

            // Contem as descrições de todas as tarefas de todos os dias
            String[] tarefas = tarefasDia[dia - 1]; // dia - 1 porque o indice começa em 0, então como dia começa em 1, 1 - 1 = 0 que seria "Selecionar ou criar astronauta"
            boolean[] feitas = new boolean[tarefas.length]; // Controla quais tarefas já foram feitas
            int tarefasRestantes = tarefas.length; // Conta quantas tarefas ainda precisam ser feitas

            while (tarefasRestantes > 0) {
                // Aqui é exibido o menu
                exibirMenuTarefas(tarefas, feitas); // A partir daqui que o metodo exibirMenuTarefas recebe as tarefas pendentes daquele dia
                String entrada = scanner.nextLine();

                if (entrada.equalsIgnoreCase("X") == true) {
                    exibirDetalhesModulos();
                    continue;
                }
                if (entrada.equalsIgnoreCase("Y") == true) {
                    exibirDetalhesAstronautaRobo();
                    continue;
                }
                if (entrada.equalsIgnoreCase("Z") == true) {
                    if (astronauta == null) {
                        System.out.println("Escolha um astronauta no dia 1 primeiro!");
                    } else {
                        astronauta.diminuirFadiga();
                        System.out.println("Fadiga atual: " + astronauta.getFadiga());
                    }
                    continue;
                }

                if (entrada.isEmpty() == true) {
                    System.out.println("Entrada vazia. Digite um número de tarefa ou X, Y, Z.");
                    continue;
                }

                int escolha;
                try {
                    // Exemplo: suponha que hoje é Dia 2, com estas tarefas:
                    // No menu visto pelo usuário a tarefa tem entrada 1
                    // Mas o indice real do array é 0
                    // Logo, 1 - 1 = 0
                    // Integer.parseInt converte uma String (texto) em um número inteiro (int)
                    // Isso é necessário porque entrada é uma string e escolha é int
                    escolha = Integer.parseInt(entrada) - 1;
                } catch (Exception e) {
                    System.out.println("Digite um número válido ou X, Y, Z.");
                    continue;
                }
                if (escolha < 0) {
                    System.out.println("Essa tarefa não existe (número menor que 1).");
                    continue;
                }
                if (escolha >= tarefas.length) {
                    System.out.println("Essa tarefa não existe (número maior que o total de tarefas).");
                    continue;
                }
                if (feitas[escolha] == true) {
                    System.out.println("Essa tarefa já foi feita.");
                    continue;
                }

                boolean usarRobo = false;

                if (dia == 1) {
                    usarRobo = false;
                } else {
                    // Verifica se o robô tem bateria suficiente (por exemplo, > 0)
                    if (robo.getBateria() < 3) {
                        System.out.println("Robô sem bateria! A tarefa será feita sem ele.");
                        usarRobo = false;
                    } else {
                        System.out.print("Usar robô?");
                        System.out.println("\n1 -> Sim");
                        System.out.println("2 -> Não");
                        String respostaRobo = scanner.nextLine();
                        if (respostaRobo.equals("1")) {
                            usarRobo = true;
                        } else {
                            usarRobo = false;
                        }
                    }
                }

                boolean sucesso = executarTarefa(dia, escolha, usarRobo); // A partir daqui que o metodo executarTarefa recebe o indice

                if (sucesso == false) {
                    System.out.println("Não foi possível concluir a tarefa.");
                    continue;
                }

                feitas[escolha] = true;
                tarefasRestantes = tarefasRestantes - 1; // É aqui que a variavel tarefasRestantes é diminuida

                if ((usarRobo == false) && (!(dia == 1))) {
                    astronauta.aumentarFadiga(2);
                }

                if (usarRobo) {
                    robo.consumirBateria(3);   // gasta 3 da bateria interna
                    System.out.println("Robô utilizado. Bateria restante: " + robo.getBateria() + "/15");
                }

                if (verificarFalhaCatastrofica() == true) { // Checar se algum módulo atingiu 100% de desgaste ou se o astronauta entrou em estado de emergência
                    return false;
                }
            }
        }
        return true; // Quando o loop interno termina (tarefasRestantes == 0), significa que todas as tarefas do dia foram concluídas, então o loop externo avança para o próximo dia
    }

    // Metodo usado em executarDias
    // String[] tarefasHoje lista com a descrição de todas as tarefas do dia
    //boolean[] tarefasFeitas onde cada posição indica se a tarefa correspondente já foi concluída (true = já feita, false = pendente)
    // o programa sabe quais tarefas receber em String[] tarefasHoje através do metodo executarDias
    private void exibirMenuTarefas(String[] tarefasHoje, boolean[] tarefasFeitas) {
        System.out.println("\n======== Tarefas pendentes ========");
        for (int i = 0; i < tarefasHoje.length; i++) {
            if (!tarefasFeitas[i]) {
                System.out.println((i + 1) + " - " + tarefasHoje[i]);
            }
        }
        System.out.println("X - Ver detalhes dos módulos");
        System.out.println("Y - Ver detalhes do astronauta e robô");
        System.out.println("Z - Descansar (reduz fadiga do astronauta)");
        System.out.print("\nDigite sua escolha: ");
    }

    // int dia -> identifica o dia atual
    // int indice -> identifica qual tarefa específica foi escolhida naquele dia
    // boolean usarRobo -> indica se a tarefa vai ser feita usando o robo
    // o programa sabe qual indice usar através do metodo executarDias
    // Aqui as tarefas são exibidas novamente para serem de fato executadas
    private boolean executarTarefa(int dia, int indice, boolean usarRobo) {
        switch (dia) {
            case 1:
                if (indice == 0) {
                    return selecionarOuCriarAstronauta();
                }
                return false;

            case 2:
                switch (indice) {
                    case 0:
                        System.out.println("Otimizando painéis solares...\nProdução de energia aumentada.");
                        energia.reduzirDesgaste(10);
                        return true;
                    case 1:
                        System.out.println("Reconfigurando distribuição elétrica...\nEficiência melhorada.");
                        energia.reduzirDesgaste(5);
                        return true;
                    default:
                        return false;
                }

            case 3:
                switch (indice) {
                    case 0:
                        System.out.println("Substituindo filtros de CO²...\nQualidade do ar melhorada.");
                        vida.reduzirDesgaste(10);
                        return true;
                    case 1:
                        System.out.println("Checando reservas de água e oxigênio...\nNíveis adequados.");
                        vida.reduzirDesgaste(5);
                        return true;
                    case 2:
                        System.out.println("Teste de emergência de selagem...\nMódulos selados com sucesso.");
                        habitacao.reduzirDesgaste(5);
                        return true;
                    default:
                        return false;
                }

            case 4:
                switch (indice) {
                    case 0:
                        System.out.println("Recalibrando antena de comunicação...\nSinal otimizado.");
                        comunicacao.reduzirDesgaste(10);
                        return true;
                    case 1:
                        System.out.println("Coletando dados do experimento botânico...\nDados salvos.");
                        astronauta.diminuirFadiga();
                        return true;
                    case 2:
                        System.out.println("Transmitindo relatório para Terra...\nComunicação estabelecida.");
                        comunicacao.reduzirDesgaste(5);
                        return true;
                    default:
                        return false;
                }

            case 5:
                switch (indice) {
                    case 0:
                        System.out.println("Ativando Módulo Habitação adicional...\nEspaço extra disponível.");
                        habitacao.reduzirDesgaste(10);
                        return true;
                    case 1:
                        System.out.println("Iniciando novo experimento científico...\nPesquisa em andamento.");
                        astronauta.diminuirFadiga();
                        return true;
                    case 2:
                        System.out.println("Realizando manutenção preventiva em sistemas críticos...\nTudo em ordem.");
                        energia.reduzirDesgaste(5);
                        comunicacao.reduzirDesgaste(5);
                        vida.reduzirDesgaste(5);
                        habitacao.reduzirDesgaste(5);
                        return true;
                    case 3:
                        System.out.println("Exercícios de equipe para moral...\nAstronauta mais disposto.");
                        astronauta.diminuirFadiga();
                        astronauta.diminuirFadiga();
                        return true;
                    default:
                        return false;
                }

            case 6:
                switch (indice) {
                    case 0:
                        System.out.println("Reparando falha no sistema de refrigeração...\nTemperatura controlada.");
                        vida.reduzirDesgaste(10);
                        return true;
                    case 1:
                        System.out.println("Reconfigurando rede elétrica após surto...\nEnergia estabilizada.");
                        energia.reduzirDesgaste(10);
                        return true;
                    case 2:
                        System.out.println("Monitorando saúde da tripulação...\nAstronauta saudável.");
                        astronauta.diminuirFadiga();
                        return true;
                    default:
                        return false;
                }

            case 7:
                switch (indice) {
                    case 0:
                        System.out.println("Reparando painéis solares em EVA virtual...\nGeração de energia restaurada.");
                        energia.reduzirDesgaste(15);
                        return true;
                    case 1:
                        System.out.println("Analisando causa das falhas...\nRelatório gerado.");
                        comunicacao.reduzirDesgaste(5);
                        return true;
                    case 2:
                        System.out.println("Atualizando protocolos de emergência...\nProcedimentos otimizados.");
                        energia.reduzirDesgaste(3);
                        comunicacao.reduzirDesgaste(3);
                        vida.reduzirDesgaste(3);
                        habitacao.reduzirDesgaste(3);
                        return true;
                    default:
                        return false;
                }

            case 8:
                switch (indice) {
                    case 0:
                        System.out.println("Substituindo baterias principais...\nArmazenamento de energia renovado.");
                        energia.reduzirDesgaste(15);
                        return true;
                    case 1:
                        System.out.println("Limpando dutos de ventilação...\nCirculação de ar melhorada.");
                        vida.reduzirDesgaste(10);
                        return true;
                    case 2:
                        System.out.println("Atualizando software de controle...\nSistemas mais responsivos.");
                        comunicacao.reduzirDesgaste(10);
                        return true;
                    case 3:
                        System.out.println("Checagem estrutural externa...\nIntegridade confirmada.");
                        habitacao.reduzirDesgaste(10);
                        return true;
                    default:
                        return false;
                }

            case 9:
                switch (indice) {
                    case 0:
                        System.out.println("Teste completo de todos os módulos...\nTodos operacionais.");
                        energia.reduzirDesgaste(5);
                        comunicacao.reduzirDesgaste(5);
                        vida.reduzirDesgaste(5);
                        habitacao.reduzirDesgaste(5);
                        return true;
                    case 1:
                        System.out.println("Backup de dados críticos...\nDados seguros.");
                        comunicacao.reduzirDesgaste(5);
                        return true;
                    case 2:
                        System.out.println("Preparando kits de emergência...\nProntos para uso.");
                        vida.reduzirDesgaste(5);
                        return true;
                    case 3:
                        System.out.println("Simulação de evacuação...\nTripulação treinada.");
                        astronauta.diminuirFadiga();
                        return true;
                    default:
                        return false;
                }

            case 10:
                switch (indice) {
                    case 0:
                        System.out.println("Ativando blindagem contra radiação...\nProteção aumentada.");
                        habitacao.reduzirDesgaste(10);
                        return true;
                    case 1:
                        System.out.println("Reduzindo consumo ao mínimo essencial...\nEnergia conservada.");
                        energia.reduzirDesgaste(10);
                        return true;
                    case 2:
                        System.out.println("Mantendo comunicação com Terra...\nContato regular.");
                        comunicacao.reduzirDesgaste(5);
                        return true;
                    case 3:
                        System.out.println("Monitorando condições da tripulação...\nAstronauta estável.");
                        astronauta.diminuirFadiga();
                        return true;
                    case 4:
                        System.out.println("======= DECISÃO CRÍTICA: Abandonar módulo danificado? =======");
                        System.out.print("Deseja abandonar o módulo?");
                        System.out.print("1 -> Sim");
                        System.out.print("2 -> Não");
                        int escolha;
                        try {
                            escolha = Integer.parseInt(scanner.nextLine());
                        } catch (NumberFormatException e) {
                            escolha = 2;
                        }
                        if (escolha == 1) {
                            System.out.println("Módulo abandonado, integridade estrutural comprometida.");
                            habitacao.aumentarDesgaste(50);
                        } else {
                            System.out.println("Decidiu permanecer, reforços estruturais aplicados.");
                            habitacao.reduzirDesgaste(10);
                        }
                        return true;
                    default:
                        return false;
                }
            default:
                return false;
        }
    }

    private boolean verificarFalhaCatastrofica() {
        if (energia.desgaste() >= 100 || comunicacao.desgaste() >= 100 ||
                vida.desgaste() >= 100 || habitacao.desgaste() >= 100) {
            System.out.println("\n======= FALHA CATASTRÓFICA =======");
            System.out.println("Um dos módulos atingiu 100% de desgaste e colapsou.");
            System.out.println("Missão fracassada!");
            return true;
        }
        if (astronauta != null && astronauta.estado().equals("emergência")) {
            System.out.println("\n======= EMERGÊNCIA MÉDICA =======");
            System.out.println("O astronauta não resistiu às condições extremas.");
            System.out.println("Missão fracassada!");
            return true;
        }
        return false;
    }

    private boolean selecionarOuCriarAstronauta() {
        while (astronauta == null) {
            System.out.println("\n======= SELECIONE OU CRIE UM ASTRONAUTA =======");
            System.out.println("1 -> Selecionar astronauta existente");
            System.out.println("2 -> Criar novo astronauta");
            System.out.print("\nDigite sua escolha: ");
            String opcao = scanner.nextLine();

            if (opcao.equals("1")) {
                astronauta = ConstrucaoAstronauta.selecionarAstronauta();
                if (astronauta != null) {
                    System.out.println("Astronauta " + astronauta.nome + " selecionado.");
                } else {
                    System.out.println("Nenhum astronauta foi selecionado. Tente novamente.");
                }
            } else if (opcao.equals("2")) {
                ConstrucaoAstronauta.criarAstronauta();
                System.out.println("Astronauta criado com sucesso! Agora, selecione-o na lista.");
                astronauta = ConstrucaoAstronauta.selecionarAstronauta();
                if (astronauta != null) {
                    System.out.println("Astronauta " + astronauta.nome + " selecionado.");
                } else {
                    System.out.println("Falha ao selecionar o astronauta recém-criado. Tente novamente.");
                }
            } else {
                System.out.println("Opção inválida.");
            }
        }
        return true;
    }

    private void exibirDetalhesModulos() {
        System.out.println("\n======= MÓDULO DE ENERGIA =======");
        System.out.println("Nome: " + energia.nome());
        System.out.println("Unidades de Energia: " + energia.getEnergia());
        System.out.println("Estado: " + energia.verificarSobrecarga());
        System.out.println("Desgaste: " + energia.desgaste() + "%");

        System.out.println("\n======= MÓDULO DE COMUNICAÇÃO =======");
        System.out.println("Nome: " + comunicacao.nome());
        System.out.println("Sinal: " + comunicacao.gerarAlerta());
        System.out.println("Desgaste: " + comunicacao.desgaste() + "%");

        System.out.println("\n======= MÓDULO DE SUPORTE À VIDA =======");
        System.out.println("Nome: " + vida.nome());
        System.out.println("Oxigênio: " + vida.getOxigenio() + "%");
        System.out.println("Pressão: " + vida.getPressao());
        System.out.println("Temperatura: " + vida.getTemp() + "°C");
        System.out.println("Desgaste: " + vida.desgaste() + "%");

        System.out.println("\n======= MÓDULO DE HABITAÇÃO =======");
        System.out.println("Nome: " + habitacao.nome());
        System.out.println("Conforto: " + habitacao.gerarAlerta());
        habitacao.avaliarIntegridade();
        System.out.println("Desgaste: " + habitacao.desgaste() + "%");

        scanner.nextLine();
    }

    private void exibirDetalhesAstronautaRobo() {
        if (astronauta == null) {
            System.out.println("\n======= NENHUM ASTRONAUTA SELECIONADO AINDA =======");
            System.out.println("Complete a tarefa do Dia 1 para definir seu astronauta.");
        } else {
            System.out.println("\n======= ASTRONAUTA =======");
            System.out.println("Nome: " + astronauta.nome);
            System.out.println("Estado: " + astronauta.gerarAlerta());
            System.out.println("Fadiga atual: " + astronauta.getFadiga());
            System.out.println("Especialidade: " + astronauta.especialidade);
        }

        System.out.println("\n======= ROBÔ =======");
        System.out.println("Bateria do robô: " + robo.getBateria() + " / 15");
        scanner.nextLine();
    }
}