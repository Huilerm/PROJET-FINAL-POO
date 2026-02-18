package entities;
import entities.system.ModuloEnergia;
import interfaces.SistemaCritico;

public class RoboReparo implements SistemaCritico {
    private String estado;
    private String nome;
    private int bateria = 15;

    public RoboReparo(String nome){
        this.nome = nome;
        this.bateria = 15;
        atualizarEstado();
    }

    @Override
    public String gerarAlerta(){
        if(estado.equals("normal")){
            return "Robo totalmente carregado.";
        }else if(estado.equals("falha")) {
            return "Bateria baixa!";
        }else{
            return "ATENÇÃO! Robô está com a bateria descarregada!";
        }
    }

    private void atualizarEstado(){
        if(bateria <= 0){
            estado = "inativo";
        }else if(bateria <= 5){
            estado = "falha";
        }else{
            estado = "normal";
        }
    }

    public void consumirBateria(int valor){
         bateria -= valor;

        if(bateria <= 0){
            bateria = 0;
        }

        atualizarEstado();
    }

    @Override
    public String estado() {
        return "";
    }

    @Override
    public int desgaste() {
        return 0;
    }

    public String getNome() {
        return nome;
    }

    public int getBateria() {
        return bateria;
    }
}
