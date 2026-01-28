package entities;
import interfaces.SistemaCritico;

import java.io.Serializable;

public class Astronauta implements SistemaCritico, Serializable {
    private static final long serialVersionUID = 1L;//versão da classe
    public String id;
    public String nome;
    public String especialidade;
    public int idade;
    public String estado;
    private int fadiga = 0;
    private int descansar = 20;
    private int wins=0;

    public Astronauta(String nome, String especialidade, int idade, String estado){
        this.id = String.valueOf(System.currentTimeMillis());
        this.nome = nome;
        this.especialidade = especialidade;
        this.idade = idade;
        this.estado = "normal";
    }
    public void upwins(){
        this.wins=this.wins+1;
    }

    public boolean repararModulo(SistemaCritico modulo){
        if(fadiga >= 10){
            System.out.println(nome + " está exausto demais para reparar módulos!");
            return false;
        }

        return modulo.receberReparo("astronauta");
    }

    @Override
    public String gerarAlerta(){
        if(estado.equals("normal")){
            return "Condições físicas normais.";
        }else if(estado.equals("atenção")){
            return "Astronauta apresenta sinais de cansaço.";
        }else if(estado.equals("falha")) {
            return "Estado físico comprometido!";
        }else{
            return "EMERGÊNCIA: astronauta preste a colapsar! Enorme risco à vida!";
        }
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

    public void descansar(){
        fadiga -= descansar;

        if(fadiga < 0){
            fadiga = 0;
        }

        atualizarEstado();
    }
    @Override
    public String toString() {
        return nome + " (" + idade + " anos) Vitórias:"+wins;//Obrigatório pro objeto ser legível
    }
    @Override
    public boolean receberReparo(String executor) {
        return false;
    }

    @Override
    public String estado() {
        return "";
    }

    @Override
    public int desgaste() {
        return 0;
    }
}
