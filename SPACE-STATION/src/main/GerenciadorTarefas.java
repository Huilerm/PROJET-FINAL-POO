package main;

import entities.Astronauta;
import entities.RoboReparo;
import entities.system.*;
import java.sql.SQLException;
import java.util.Scanner;

public class GerenciadorTarefas {

    private ModuloEnergia energia;
    private ModuloComunicacao comunicacao;
    private ModuloSuporteVida vida;
    private ModuloHabitacao habitacao;
    private Astronauta astronauta;
    private ConstrucaoAstronauta ca;
    private RoboReparo robo;

    Scanner scanner = new Scanner(System.in);

    public GerenciadorTarefas(ModuloEnergia energia, ModuloComunicacao comunicacao,
                              ModuloSuporteVida vida, ModuloHabitacao habitacao,
                              Astronauta astronauta, RoboReparo robo, ConstrucaoAstronauta ca) {
        this.energia = energia;
        this.comunicacao = comunicacao;
        this.vida = vida;
        this.habitacao = habitacao;
        this.astronauta = astronauta;
        this.robo = robo;
        this.ca = ca;
    }

    public Astronauta getAstronauta() {
        return astronauta;
    }

    private final String[][] tarefasDia = { // Aqui é guardado as tarefas para exibição no menu
            {"Selecionar ou criar astronauta"},
            {"Otimizar painéis solares", "Reconfigurar distribuição elétrica"},
            {"Substituir filtros de CO² no Módulo Suporte Vida", "Checar reservas de água e oxigênio", "Teste de emergência de selagem de módulos"},
            {"Recalibrar antena de comunicação", "Coletar dados do experimento botânico", "Transmitir relatório para Terra"},
            {"Ativar Módulo Habitação adicional", "Iniciar novo experimento científico", "Manutenção preventiva em sistemas críticos", "Exercícios de equipe para moral"},
            {"Reparar falha no sistema de refrigeração", "Reconfigurar rede elétrica após surto", "Monitorar saúde da tripulação"},
            {"Reparar painéis solares", "Analisar causa das falhas", "Atualizar protocolos de emergência"},
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
        energia.consumirEnergia(20);
        comunicacao.verificarSinal(energia);
        habitacao.avaliarConforto(energia, astronauta);

        // Aqui o painel da estação é atualizado
        PainelEstacao.atualizarEstadoEnergia(energia.verificarSobrecarga());
        PainelEstacao.atualizarEstadoComunicacao(comunicacao.estado());
        PainelEstacao.atualizarEstadoVida(vida.estado());
        PainelEstacao.atualizarEstadoHabitacao(habitacao.estado());
        PainelEstacao.atualizarEnergia(energia.getEnergia(), energia.desgaste());
        PainelEstacao.atualizarOxigenio(vida.getOxigenio());
        PainelEstacao.atualizarDesgastes(comunicacao.desgaste(), vida.desgaste(), habitacao.desgaste());
        if (astronauta != null) {
            PainelEstacao.atualizarAstronauta(astronauta.getNome(), astronauta.estado(), astronauta.getFadiga());
            PainelEstacao.atualizarEspecialidade(astronauta.getEspecialidade());
            PainelEstacao.atualizarIdade(astronauta.getIdade());
        }
        PainelEstacao.atualizarRobo(robo.getBateria());
        PainelEstacao.atualizarPressao(vida.getPressaoValor());
        PainelEstacao.atualizarTemperatura(vida.getTemp());
    }

    public boolean executarDias() {

        for (int dia = 1; dia <= 10; dia++) { // Começa no dia 1 e vai até o dia 10
            System.out.println("======= DIA " + dia + " =======");
            System.out.println("Objetivo: " + getObjetivoDia(dia)); // Metodo objetivoDia
            System.out.println("=====================\n");

            PainelEstacao.atualizarDia(dia, getObjetivoDia(dia));

            if (dia > 1) {
                aplicarDesgasteDiario(); // O desgaste começa a partir do dia dois
            }

            // Contem as descrições de todas as tarefas de todos os dias
            // É aqui em tarefas que controla qual linha da matriz das tarefas é escolhida
            String[] tarefas = tarefasDia[dia - 1]; // dia - 1 porque o indice começa em 0, então como dia começa em 1, 1 - 1 = 0 que seria "Selecionar ou criar astronauta"
            boolean[] feitas = new boolean[tarefas.length]; // Controla quais tarefas já foram feitas
            int tarefasRestantes = tarefas.length; // Conta quantas tarefas ainda precisam ser feitas

            while (tarefasRestantes > 0) {
                // Aqui é exibido o menu
                exibirMenuTarefas(tarefas, feitas); // A partir daqui que o metodo exibirMenuTarefas recebe as tarefas pendentes daquele dia
                String entrada = scanner.nextLine(); // Captura a entrada do usuário

                if (entrada.equalsIgnoreCase("Z")) {
                    if (astronauta == null) {
                        System.out.println("\nEscolha um astronauta no dia 1 primeiro!\n");
                    } else {
                        astronauta.diminuirFadiga();
                        PainelEstacao.atualizarAstronauta(astronauta.getNome(), astronauta.estado(), astronauta.getFadiga());
                    }
                    continue;
                }

                if (entrada.isEmpty()) {
                    System.out.println("\nEntrada vazia. Digite um número de tarefa ou Z.\n");
                    continue;
                }

                int escolha; // É aqui que as colunas onde ficam as atividades do dia é escolhida
                // Exemplo: suponha que hoje é Dia 2, com estas tarefas:
                // No menu visto pelo usuário a tarefa tem entrada 1
                // Mas o indice real do array é 0
                // Logo, 1 - 1 = 0
                // Integer.parseInt converte uma String (texto) em um número inteiro (int)
                // Isso é necessário porque entrada é uma string e escolha é int
                try {
                    escolha = Integer.parseInt(entrada) - 1;
                } catch (Exception e) {
                    System.out.println("\nDigite um número válido ou Z.\n");
                    continue;
                }
                if (escolha < 0) {
                    System.out.println("\nEssa tarefa não existe (número menor que 1).\n");
                    continue;
                }
                if (escolha >= tarefas.length) {
                    System.out.println("\nEssa tarefa não existe (número maior que o total de tarefas).\n");
                    continue;
                }
                if (feitas[escolha]) {
                    System.out.println("\nEssa tarefa já foi feita.\n");
                    continue;
                }

                boolean usarRobo = false;

                if (dia == 1) {
                    usarRobo = false;
                } else {
                    // Verifica se o robô tem bateria suficiente (por exemplo, > 0)
                    if (robo.getBateria() < 3) {
                        System.out.println("\nRobô sem bateria! A tarefa será feita sem ele.\n");
                        PainelEstacao.atualizarRobo(robo.getBateria());
                        usarRobo = false;
                    } else {
                        String respostaRobo;
                        while (true) {
                            System.out.print("\nUsar robô?");
                            System.out.println("\n1 -> Sim");
                            System.out.println("2 -> Não");
                            System.out.print("\nDigite sua escolha: ");
                            respostaRobo = scanner.nextLine().trim(); // .trim() remove espaços em branco no início/fim

                            if (respostaRobo.equals("1")) {
                                usarRobo = true;
                                break;
                            } else if (respostaRobo.equals("2")) {
                                usarRobo = false;
                                break;
                            } else {
                                System.out.println("Opção inválida! Digite 1 para Sim ou 2 para Não.");
                            }
                        }
                    }
                }

                boolean sucesso = executarTarefa(dia, escolha); // A partir daqui que o metodo executarTarefa recebe o indice

                if (!sucesso) {
                    System.out.println("\nNão foi possível concluir a tarefa.\n");
                    continue;
                }

                feitas[escolha] = true;
                tarefasRestantes--; // É aqui que a variavel tarefasRestantes é diminuida

                if (!usarRobo && dia != 1) {
                    astronauta.aumentarFadiga(2);
                    PainelEstacao.atualizarAstronauta(astronauta.getNome(), astronauta.estado(), astronauta.getFadiga());
                }

                if (usarRobo) {
                    robo.consumirBateria(3);
                    System.out.println("\nRobô utilizado. Bateria restante: " + robo.getBateria() + "/15\n");
                    PainelEstacao.atualizarRobo(robo.getBateria());
                }

                // Atualiza pressão e temperatura após cada tarefa (garantia)
                PainelEstacao.atualizarPressao(vida.getPressaoValor());
                PainelEstacao.atualizarTemperatura(vida.getTemp());
                // Atualiza os estados dos módulos após a tarefa
                PainelEstacao.atualizarEstadoEnergia(energia.verificarSobrecarga());
                PainelEstacao.atualizarEstadoComunicacao(comunicacao.estado());
                PainelEstacao.atualizarEstadoVida(vida.estado());
                PainelEstacao.atualizarEstadoHabitacao(habitacao.estado());

                if (verificarFalhaCatastrofica()) { // Checar se algum módulo atingiu 100% de desgaste ou se o astronauta entrou em estado de emergência
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
        System.out.println("======== Tarefas pendentes ========");
        for (int i = 0; i < tarefasHoje.length; i++) {
            if (!tarefasFeitas[i]) {
                System.out.println((i + 1) + " - " + tarefasHoje[i]);
            }
        }
        System.out.println("Z - Descansar (reduz fadiga do astronauta)");
        System.out.print("\nDigite sua escolha: ");
    }

    // int dia -> identifica o dia atual
    // int indice -> identifica qual tarefa específica foi escolhida naquele dia
    // boolean usarRobo -> indica se a tarefa vai ser feita usando o robo
    // o programa sabe qual indice usar através do metodo executarDias
    // Aqui as tarefas são exibidas novamente para serem de fato executadas
    private boolean executarTarefa(int dia, int indice) {
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
                        scanner.nextLine();
                        energia.reduzirDesgaste(10);
                        PainelEstacao.atualizarEnergia(energia.getEnergia(), energia.desgaste());
                        return true;
                    case 1:
                        System.out.println("Reconfigurando distribuição elétrica...\nEficiência melhorada.");
                        scanner.nextLine();
                        energia.reduzirDesgaste(5);
                        PainelEstacao.atualizarEnergia(energia.getEnergia(), energia.desgaste());
                        return true;
                    default:
                        return false;
                }

            case 3:
                switch (indice) {
                    case 0:
                        System.out.println("Substituindo filtros de CO²...\nQualidade do ar melhorada.");
                        scanner.nextLine();
                        vida.reduzirDesgaste(10);
                        vida.aumentarOxigenio(10);
                        PainelEstacao.atualizarOxigenio(vida.getOxigenio());
                        PainelEstacao.atualizarDesgastes(comunicacao.desgaste(), vida.desgaste(), habitacao.desgaste());
                        return true;
                    case 1:
                        System.out.println("Checando reservas de água e oxigênio...\nNíveis adequados.");
                        scanner.nextLine();
                        vida.reduzirDesgaste(5);
                        vida.aumentarOxigenio(5);
                        PainelEstacao.atualizarOxigenio(vida.getOxigenio());
                        PainelEstacao.atualizarPressao(vida.getPressaoValor());
                        PainelEstacao.atualizarDesgastes(comunicacao.desgaste(), vida.desgaste(), habitacao.desgaste());
                        return true;
                    case 2:
                        System.out.println("Teste de emergência de selagem...\nMódulos selados com sucesso.");
                        scanner.nextLine();
                        habitacao.reduzirDesgaste(5);
                        vida.aumentarPressao(0.1);
                        PainelEstacao.atualizarDesgastes(comunicacao.desgaste(), vida.desgaste(), habitacao.desgaste());
                        return true;
                    default:
                        return false;
                }

            case 4:
                switch (indice) {
                    case 0:
                        System.out.println("Recalibrando antena de comunicação...\nSinal otimizado.");
                        scanner.nextLine();
                        comunicacao.reduzirDesgaste(10);
                        PainelEstacao.atualizarDesgastes(comunicacao.desgaste(), vida.desgaste(), habitacao.desgaste());
                        return true;
                    case 1:
                        System.out.println("Coletando dados do experimento botânico...\nDados salvos.");
                        scanner.nextLine();
                        astronauta.diminuirFadiga();
                        PainelEstacao.atualizarAstronauta(astronauta.getNome(), astronauta.estado(), astronauta.getFadiga());
                        return true;
                    case 2:
                        System.out.println("Transmitindo relatório para Terra...\nComunicação estabelecida.");
                        scanner.nextLine();
                        comunicacao.reduzirDesgaste(5);
                        PainelEstacao.atualizarDesgastes(comunicacao.desgaste(), vida.desgaste(), habitacao.desgaste());
                        return true;
                    default:
                        return false;
                }

            case 5:
                switch (indice) {
                    case 0:
                        System.out.println("Ativando Módulo Habitação adicional...\nEspaço extra disponível.");
                        scanner.nextLine();
                        habitacao.reduzirDesgaste(10);
                        PainelEstacao.atualizarDesgastes(comunicacao.desgaste(), vida.desgaste(), habitacao.desgaste());
                        return true;
                    case 1:
                        System.out.println("Iniciando novo experimento científico...\nPesquisa em andamento.");
                        scanner.nextLine();
                        astronauta.diminuirFadiga();
                        PainelEstacao.atualizarAstronauta(astronauta.getNome(), astronauta.estado(), astronauta.getFadiga());
                        return true;
                    case 2:
                        System.out.println("Realizando manutenção preventiva em sistemas críticos...\nTudo em ordem.");
                        scanner.nextLine();
                        energia.reduzirDesgaste(5);
                        comunicacao.reduzirDesgaste(5);
                        vida.reduzirDesgaste(5);
                        habitacao.reduzirDesgaste(5);
                        PainelEstacao.atualizarEnergia(energia.getEnergia(), energia.desgaste());
                        PainelEstacao.atualizarOxigenio(vida.getOxigenio());
                        PainelEstacao.atualizarDesgastes(comunicacao.desgaste(), vida.desgaste(), habitacao.desgaste());
                        return true;
                    case 3:
                        System.out.println("Exercícios de equipe para moral...\nAstronauta mais disposto.");
                        scanner.nextLine();
                        astronauta.diminuirFadiga();
                        astronauta.diminuirFadiga();
                        PainelEstacao.atualizarAstronauta(astronauta.getNome(), astronauta.estado(), astronauta.getFadiga());
                        return true;
                    default:
                        return false;
                }

            case 6:
                switch (indice) {
                    case 0:
                        System.out.println("Reparando falha no sistema de refrigeração...\nTemperatura controlada.");
                        scanner.nextLine();
                        vida.reduzirDesgaste(10);
                        vida.aumentarTemperatura(2);
                        PainelEstacao.atualizarTemperatura(vida.getTemp());
                        PainelEstacao.atualizarDesgastes(comunicacao.desgaste(), vida.desgaste(), habitacao.desgaste());
                        return true;
                    case 1:
                        System.out.println("Reconfigurando rede elétrica após surto...\nEnergia estabilizada.");
                        scanner.nextLine();
                        energia.reduzirDesgaste(10);
                        PainelEstacao.atualizarEnergia(energia.getEnergia(), energia.desgaste());
                        return true;
                    case 2:
                        System.out.println("Monitorando saúde da tripulação...\nAstronauta saudável.");
                        scanner.nextLine();
                        astronauta.diminuirFadiga();
                        PainelEstacao.atualizarAstronauta(astronauta.getNome(), astronauta.estado(), astronauta.getFadiga());
                        return true;
                    default:
                        return false;
                }

            case 7:
                switch (indice) {
                    case 0:
                        System.out.println("Reparando painéis solares em EVA virtual...\nGeração de energia restaurada.");
                        scanner.nextLine();
                        energia.reduzirDesgaste(15);
                        PainelEstacao.atualizarEnergia(energia.getEnergia(), energia.desgaste());
                        return true;
                    case 1:
                        System.out.println("Analisando causa das falhas...\nRelatório gerado.");
                        scanner.nextLine();
                        comunicacao.reduzirDesgaste(5);
                        PainelEstacao.atualizarDesgastes(comunicacao.desgaste(), vida.desgaste(), habitacao.desgaste());
                        return true;
                    case 2:
                        System.out.println("Atualizando protocolos de emergência...\nProcedimentos otimizados.");
                        scanner.nextLine();
                        energia.reduzirDesgaste(3);
                        comunicacao.reduzirDesgaste(3);
                        vida.reduzirDesgaste(3);
                        habitacao.reduzirDesgaste(3);
                        PainelEstacao.atualizarEnergia(energia.getEnergia(), energia.desgaste());
                        PainelEstacao.atualizarDesgastes(comunicacao.desgaste(), vida.desgaste(), habitacao.desgaste());
                        return true;
                    default:
                        return false;
                }

            case 8:
                switch (indice) {
                    case 0:
                        System.out.println("Substituindo baterias principais...\nArmazenamento de energia renovado.");
                        scanner.nextLine();
                        energia.reduzirDesgaste(15);
                        PainelEstacao.atualizarEnergia(energia.getEnergia(), energia.desgaste());
                        return true;
                    case 1:
                        System.out.println("Limpando dutos de ventilação...\nCirculação de ar melhorada.");
                        scanner.nextLine();
                        vida.reduzirDesgaste(10);
                        vida.aumentarOxigenio(5);
                        vida.aumentarPressao(0.05);
                        PainelEstacao.atualizarOxigenio(vida.getOxigenio());
                        PainelEstacao.atualizarPressao(vida.getPressaoValor());
                        PainelEstacao.atualizarDesgastes(comunicacao.desgaste(), vida.desgaste(), habitacao.desgaste());
                        return true;
                    case 2:
                        System.out.println("Atualizando software de controle...\nSistemas mais responsivos.");
                        scanner.nextLine();
                        comunicacao.reduzirDesgaste(10);
                        PainelEstacao.atualizarDesgastes(comunicacao.desgaste(), vida.desgaste(), habitacao.desgaste());
                        return true;
                    case 3:
                        System.out.println("Checagem estrutural externa...\nIntegridade confirmada.");
                        scanner.nextLine();
                        habitacao.reduzirDesgaste(10);
                        PainelEstacao.atualizarDesgastes(comunicacao.desgaste(), vida.desgaste(), habitacao.desgaste());
                        return true;
                    default:
                        return false;
                }

            case 9:
                switch (indice) {
                    case 0:
                        System.out.println("Teste completo de todos os módulos...\nTodos operacionais.");
                        scanner.nextLine();
                        energia.reduzirDesgaste(5);
                        comunicacao.reduzirDesgaste(5);
                        vida.reduzirDesgaste(5);
                        habitacao.reduzirDesgaste(5);
                        PainelEstacao.atualizarEnergia(energia.getEnergia(), energia.desgaste());
                        PainelEstacao.atualizarDesgastes(comunicacao.desgaste(), vida.desgaste(), habitacao.desgaste());
                        return true;
                    case 1:
                        System.out.println("Backup de dados críticos...\nDados seguros.");
                        scanner.nextLine();
                        comunicacao.reduzirDesgaste(5);
                        PainelEstacao.atualizarDesgastes(comunicacao.desgaste(), vida.desgaste(), habitacao.desgaste());
                        return true;
                    case 2:
                        System.out.println("Preparando kits de emergência...\nProntos para uso.");
                        scanner.nextLine();
                        vida.reduzirDesgaste(5);
                        PainelEstacao.atualizarDesgastes(comunicacao.desgaste(), vida.desgaste(), habitacao.desgaste());
                        return true;
                    case 3:
                        System.out.println("Simulação de evacuação...\nTripulação treinada.");
                        scanner.nextLine();
                        astronauta.diminuirFadiga();
                        PainelEstacao.atualizarAstronauta(astronauta.getNome(), astronauta.estado(), astronauta.getFadiga());
                        return true;
                    default:
                        return false;
                }

            case 10:
                switch (indice) {
                    case 0:
                        System.out.println("Ativando blindagem contra radiação...\nProteção aumentada.");
                        scanner.nextLine();
                        habitacao.reduzirDesgaste(10);
                        PainelEstacao.atualizarDesgastes(comunicacao.desgaste(), vida.desgaste(), habitacao.desgaste());
                        return true;
                    case 1:
                        System.out.println("Reduzindo consumo ao mínimo essencial...\nEnergia conservada.");
                        scanner.nextLine();
                        energia.reduzirDesgaste(10);
                        PainelEstacao.atualizarEnergia(energia.getEnergia(), energia.desgaste());
                        return true;
                    case 2:
                        System.out.println("Mantendo comunicação com Terra...\nContato regular.");
                        scanner.nextLine();
                        comunicacao.reduzirDesgaste(5);
                        PainelEstacao.atualizarDesgastes(comunicacao.desgaste(), vida.desgaste(), habitacao.desgaste());
                        return true;
                    case 3:
                        System.out.println("Monitorando condições da tripulação...\nAstronauta estável.");
                        scanner.nextLine();
                        astronauta.diminuirFadiga();
                        PainelEstacao.atualizarAstronauta(astronauta.getNome(), astronauta.estado(), astronauta.getFadiga());
                        return true;
                    case 4:
                        System.out.println("======= DECISÃO CRÍTICA: Abandonar módulo danificado? =======");
                        System.out.print("Deseja abandonar o módulo?");
                        System.out.print("1 -> Sim");
                        System.out.print("2 -> Não");
                        System.out.print("\nDigite sua escolha: ");
                        int escolha;
                        try {
                            escolha = Integer.parseInt(scanner.nextLine());
                        } catch (NumberFormatException e) {
                            escolha = 2;
                        }
                        if (escolha == 1) {
                            System.out.println("\nMódulo abandonado, integridade estrutural comprometida.\n");
                            scanner.nextLine();
                            habitacao.aumentarDesgaste(50);
                        } else {
                            System.out.println("\nDecidiu permanecer, reforços estruturais aplicados.\n");
                            scanner.nextLine();
                            habitacao.reduzirDesgaste(10);
                        }
                        PainelEstacao.atualizarDesgastes(comunicacao.desgaste(), vida.desgaste(), habitacao.desgaste());
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
            System.out.println("Missão fracassada!\n");
            return true;
        }
        if (astronauta != null && astronauta.estado().equals("emergência")) {
            System.out.println("\n======= EMERGÊNCIA MÉDICA =======");
            System.out.println("O astronauta não resistiu às condições extremas.");
            System.out.println("Missão fracassada!\n");
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
                try {
                    astronauta = ca.selecionarAstronauta();
                } catch (SQLException e) {
                    System.out.println("\nErro ao acessar banco de dados: " + e.getMessage() + "\n");
                    continue;
                }
                if (astronauta != null) {
                    System.out.println("\nAstronauta " + astronauta.getNome() + " selecionado.\n");
                    // Atualiza o painel, se necessário
                    PainelEstacao.atualizarAstronauta(astronauta.getNome(), astronauta.estado(), astronauta.getFadiga());
                    PainelEstacao.atualizarEspecialidade(astronauta.getEspecialidade());
                    PainelEstacao.atualizarIdade(astronauta.getIdade());
                } else {
                    System.out.println("\nNenhum astronauta foi selecionado. Tente novamente.\n");
                }
            } else if (opcao.equals("2")) {
                astronauta = ca.criarAstronauta();// Agora retorna Astronauta
                if (astronauta != null) {
                    System.out.println("\nAstronauta " + astronauta.getNome() + " criado e selecionado.\n");
                    PainelEstacao.atualizarAstronauta(astronauta.getNome(), astronauta.estado(), astronauta.getFadiga());
                    PainelEstacao.atualizarEspecialidade(astronauta.getEspecialidade());
                    PainelEstacao.atualizarIdade(astronauta.getIdade());
                } else {
                    System.out.println("\nFalha ao criar astronauta. Tente novamente.\n");
                }
            } else {
                System.out.println("\nOpção inválida.\n");
            }
        }
        return true;
    }
}