package main;

import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import javafx.scene.image.Image;

public class PainelEstacao {

    private static Stage mainStage;
    private static boolean toolkitIniciado = false;

    // Fonte
    private static Font pixelFont;

    // Topo
    // Label é usado para exibir um texto não editavel
    private static Label lblDia;
    private static Label lblObjetivo;

    // Dados Críticos
    private static Label lblPctEnergia;
    private static Label lblPctOxigenio;
    private static Label lblPctTemperatura;
    private static Label lblPctPressao;

    // Nível de Desgaste
    private static Label lblPctDesgEnergia;
    private static Label lblPctDesgCom;
    private static Label lblPctDesgVida;
    private static Label lblPctDesgHab;

    // Astronauta
    private static Label lblNome;
    private static Label lblEspecialidade;
    private static Label lblIdade;
    private static Label lblEstado;
    private static Label lblPctFadiga;
    private static Label lblPctBateriaRobo;

    // Estados
    private static String estadoEnergia = "";
    private static String estadoComunicacao = "";
    private static String estadoVida = "";
    private static String estadoHabitacao = "";

    public static void iniciar() {

        if (!toolkitIniciado) { // Conjunto de ferramentas
            Platform.startup(() -> {}); // Metodo que inicia o tollkit
            toolkitIniciado = true;
        }

        // Aqui carrega a fonte de texto
        // O metodo getResource busca o arquivo no classpath (pasta resources/fonts/)
        pixelFont = Font.loadFont(
                PainelEstacao.class.getResource("/fonts/PressStart2P.ttf").toExternalForm(),
                8
        );

        // Toda modificação na interface gráfica deve ser feita na thread do JavaFX
        // Platform.runLater envia um código para ser executado nessa thread
        Platform.runLater(() -> {

            // BorderPane é um layout que divide a tela em regiões: top, bottom, left, right, center
            BorderPane root = new BorderPane();
            // Define um espaçamento interno (padding) de 20 pixels em todas as direções dentro do contêiner root.
            root.setPadding(new Insets(20));

            // Gif de fundo, mesma lógica da fonte de texto
            Image gif = new Image(
                    PainelEstacao.class.getResource("/espaco.gif").toExternalForm()
            );

            // Configura o tamanho do fundo
            BackgroundSize backgroundSize = new BackgroundSize(
                    BackgroundSize.AUTO,
                    BackgroundSize.AUTO,
                    false,
                    false,
                    true,
                    true
            );

            // Cria a imagem de fundo com repetição desativada e centralizada
            BackgroundImage backgroundImage = new BackgroundImage(
                    gif,
                    BackgroundRepeat.NO_REPEAT,
                    BackgroundRepeat.NO_REPEAT,
                    BackgroundPosition.CENTER,
                    backgroundSize
            );

            // Aplica o fundo ao painel raiz
            root.setBackground(new Background(backgroundImage));

            // Topo
            // VBox organiza os elementos verticalmente
            VBox top = new VBox(8);
            top.setAlignment(Pos.CENTER);

            Label titulo = new Label("Painel da Estação");
            titulo.setTextFill(Color.web("#3cc8ff"));
            titulo.setFont(Font.font(pixelFont.getFamily(), 16));

            lblDia = new Label("Dia 1");
            lblDia.setTextFill(Color.web("#a8cfff"));
            lblDia.setFont(pixelFont);

            lblObjetivo = new Label("Objetivo: ---");
            lblObjetivo.setTextFill(Color.web("#ff9b3d"));
            lblObjetivo.setFont(pixelFont);

            // Operações do conteiner
            top.getChildren().addAll(titulo, lblDia, lblObjetivo);
            root.setTop(top);

            // Centro
            // GridPane organiza em linhas e colunas, aqui terá duas colunas de largura fixa
            GridPane center = new GridPane();
            center.setPadding(new Insets(30, 0, 0, 0));
            center.setAlignment(Pos.TOP_CENTER);
            center.setHgap(40); // Espaçamento horizontal entre colunas

            // Define as larguras das colunas
            center.getColumnConstraints().addAll(
                    new ColumnConstraints(280),
                    new ColumnConstraints(280)
            );

            center.getRowConstraints().add(new RowConstraints());

            // Coluna esquerda (conterá os cards de dados críticos e desgaste)
            VBox colEsquerda = new VBox(20);
            colEsquerda.setAlignment(Pos.TOP_CENTER);

            // Card de Dados Críticos
            VBox cardDadosCriticos = criarCard("Dados críticos");
            lblPctEnergia = criarValor("0u ()");
            lblPctOxigenio = criarValor("0% ()");
            lblPctTemperatura = criarValor("0ºC ()");
            lblPctPressao = criarValor("0.0 atm ()");

            cardDadosCriticos.getChildren().addAll(
                    criarLinha("Energia", lblPctEnergia),
                    criarLinha("Oxigênio", lblPctOxigenio),
                    criarLinha("Temperatura", lblPctTemperatura),
                    criarLinha("Pressão", lblPctPressao)
            );

            // Card de Nível de Desgaste
            VBox cardDesgaste = criarCard("Nível de desgaste");
            lblPctDesgEnergia = criarValor("0%");
            lblPctDesgCom = criarValor("0%");
            lblPctDesgVida = criarValor("0%");
            lblPctDesgHab = criarValor("0%");

            cardDesgaste.getChildren().addAll(
                    criarLinha("Desg. energia", lblPctDesgEnergia),
                    criarLinha("Desg. comunic.", lblPctDesgCom),
                    criarLinha("Desg. vida", lblPctDesgVida),
                    criarLinha("Desg. habitação", lblPctDesgHab)
            );

            colEsquerda.getChildren().addAll(cardDadosCriticos, cardDesgaste);

            // Card do Astronauta (coluna direita)
            VBox cardAstro = criarCard("Astronauta");

            lblNome = criarTexto("---");
            lblEspecialidade = criarTexto("---");
            lblIdade = criarTexto("---");
            lblEstado = criarTexto("---");
            lblPctFadiga = criarValor("0%");
            lblPctBateriaRobo = criarValor("0%");

            VBox conteudoAstro = new VBox(8);
            conteudoAstro.getChildren().addAll(
                    lblNome,
                    lblEspecialidade,
                    lblIdade,
                    lblEstado,
                    criarLinha("Fadiga", lblPctFadiga),
                    criarLinha("Bateria robô", lblPctBateriaRobo)
            );

            cardAstro.getChildren().add(conteudoAstro);

            // Adiciona as colunas ao grid
            center.add(colEsquerda, 0, 0);
            center.add(cardAstro, 1, 0);

            root.setCenter(center);

            // Cria a cena e exibe a janela
            Scene scene = new Scene(root, 800, 500);
            mainStage = new Stage();
            mainStage.setTitle("Estação Espacial");
            mainStage.setScene(scene);
            mainStage.show();
        });
    }

    // Estilo
    // Cria um card (VBox com borda e fundo semi-transparente)
    private static VBox criarCard(String titulo) {
        VBox box = new VBox(12);
        box.setPadding(new Insets(18));
        box.setStyle(
                "-fx-background-color: rgba(10,15,30,0.85);" +
                        "-fx-background-radius: 12;" +
                        "-fx-border-color: #ff4c6a;" + // É aqui que faz com que a borda do card fique rosa
                        "-fx-border-radius: 12;"
        );

        Label t = new Label(titulo);
        t.setTextFill(Color.WHITE);
        t.setFont(Font.font(pixelFont.getFamily(), 14));

        box.getChildren().add(t);
        return box;
    }

    // Cria uma linha com um texto fixo e um label de valor (ex: "Energia: 50%")
    private static HBox criarLinha(String texto, Label valor) {
        HBox linha = new HBox(10);
        linha.setAlignment(Pos.CENTER_LEFT);

        Label lbl = new Label(texto + ":");
        lbl.setTextFill(Color.web("#a8cfff"));
        lbl.setFont(pixelFont);

        linha.getChildren().addAll(lbl, valor);
        return linha;
    }

    // Cria um label simples com texto branco e fonte pixel
    private static Label criarTexto(String texto) {
        Label l = new Label(texto);
        l.setTextFill(Color.WHITE);
        l.setFont(pixelFont);
        return l;
    }

    // Cria um label para valores numéricos, com cor azul clara
    private static Label criarValor(String texto) {
        Label l = new Label(texto);
        l.setTextFill(Color.web("#3cc8ff"));
        l.setFont(pixelFont);
        return l;
    }

    // Metodos de atualizacao de estado
    public static void atualizarEstadoEnergia(String estado) {
        Platform.runLater(() -> estadoEnergia = estado);
    }

    public static void atualizarEstadoComunicacao(String estado) {
        Platform.runLater(() -> estadoComunicacao = estado);
    }

    public static void atualizarEstadoVida(String estado) {
        Platform.runLater(() -> estadoVida = estado);
    }

    public static void atualizarEstadoHabitacao(String estado) {
        Platform.runLater(() -> estadoHabitacao = estado);
    }

    // Metodo de atualizacao de valores
    public static void atualizarDia(int dia, String objetivo) {
        Platform.runLater(() -> {
            lblDia.setText("Dia " + dia);
            lblObjetivo.setText("Objetivo: " + objetivo);
        });
    }

    public static void atualizarEnergia(int valor, int desgaste) {
        Platform.runLater(() -> {
            lblPctEnergia.setText(valor + "u (" + estadoEnergia + ")");
            lblPctDesgEnergia.setText(desgaste + "% (" + estadoEnergia + ")");
        });
    }

    public static void atualizarOxigenio(int valor) {
        Platform.runLater(() ->
                lblPctOxigenio.setText(valor + "% (" + estadoVida + ")"));
    }

    public static void atualizarPressao(double valor) {
        Platform.runLater(() ->
                lblPctPressao.setText(String.format("%.1f ATM (%s)", valor, estadoVida)));
    }

    public static void atualizarTemperatura(int valor) {
        Platform.runLater(() ->
                lblPctTemperatura.setText(valor + "ºC (" + estadoVida + ")"));
    }

    public static void atualizarDesgastes(int com, int vida, int hab) {
        Platform.runLater(() -> {
            lblPctDesgCom.setText(com + "% (" + estadoComunicacao + ")");
            lblPctDesgVida.setText(vida + "% (" + estadoVida + ")");
            lblPctDesgHab.setText(hab + "% (" + estadoHabitacao + ")");
        });
    }

    public static void atualizarAstronauta(String nome, String estado, int fadiga) {
        Platform.runLater(() -> {
            lblNome.setText("Nome: " + nome);
            lblEstado.setText("Estado: " + estado);
            lblPctFadiga.setText(fadiga + "%");
        });
    }

    public static void atualizarEspecialidade(String especialidade) {
        Platform.runLater(() ->
                lblEspecialidade.setText("Especialidade: " + especialidade));
    }

    public static void atualizarIdade(int idade) {
        Platform.runLater(() ->
                lblIdade.setText("Idade: " + idade));
    }

    public static void atualizarRobo(int bateria) {
        Platform.runLater(() -> {
            int percentual = (bateria * 100) / 15;
            lblPctBateriaRobo.setText(percentual + "%");
        });
    }
}