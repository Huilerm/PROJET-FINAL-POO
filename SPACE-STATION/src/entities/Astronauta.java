package entities;
import interfaces.SistemaCritico;

public class Astronauta implements SistemaCritico {
    private int id;
    private String nome;
    private String especialidade;
    private int idade;
    private String estado;
    private int fadiga = 0;
    private int wins=0;

    public Astronauta(String nome, String especialidade, int idade, String estado) {
        this.nome = nome;
        this.especialidade = especialidade;
        this.idade = idade;
        this.estado = estado;
    }

    private void atualizarEstado(){
        if(fadiga < 3){
            estado = "normal";
        }else if(fadiga < 7){
            estado = "atenção";
        }else if(fadiga < 12){
            estado = "falha";
        }else{
            estado = "emergência";
        }
    }

    public void aumentarFadiga(int valor){
        fadiga += valor;
        atualizarEstado();
    }

    public void diminuirFadiga(){
        fadiga -= 2;

        if(fadiga < 0){
            fadiga = 0;
        }

        atualizarEstado();
    }

    @Override
    public String toString() {
        return "\nNome: " + nome + "\n" +
                "Idade: " + idade +
                " anos" +
                "\nEspecialidade: " + especialidade +
                "\nVitórias: " + wins + "\n"; // Obrigatório para o objeto ser legível
    }

    //Retorna o estado atual do astronauta
    @Override
    public String estado() {
        return estado;
    }

    // Não é usado, mas como é um metodo herdado do SistemaCritco precisa iniciar
    @Override
    public int desgaste() {
        return 0;
    }

    // Getters e Setters (encapsulamento), já que alguns atributos são private
    public int getFadiga(){
        return fadiga;
    }

    public int getWins() {
        return this.wins;
    }

    public int getId() {
        return this.id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public int getIdade() {
        return idade;
    }

    public void setIdade(int idade) {
        this.idade = idade;
    }

    public String getEspecialidade() {
        return especialidade;
    }

    public void setEspecialidade(String especialidade) {
        this.especialidade = especialidade;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    // Incrementa o contador de vitórias do astronauta
    // Chamado quando ele sobrevive aos 10 dias da missão
    public void upwins() {
        this.wins++;
    }
}