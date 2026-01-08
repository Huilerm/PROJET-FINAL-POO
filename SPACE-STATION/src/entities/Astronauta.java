package entities;
import interfaces.Monitoravel;

public class Astronauta implements Monitoravel{
    private int id;
    private String nome;
    private String patente;
    private String especialidade;
    private int idade;

    public Astronauta(int id, String nome, String patente, String especialidade, int idade){
        this.id = id;
        this.nome = nome;
        this.patente = patente;
        this.especialidade = especialidade;
        this.idade = idade;
    }
    @Override
    public void status(){

    }
}
