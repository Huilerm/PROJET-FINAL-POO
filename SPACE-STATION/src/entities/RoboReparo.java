package entities;
import entities.system.ModuloEnergia;
import interfaces.SistemaCritico;

public class RoboReparo implements SistemaCritico {
    private String estado;
    private String nome;
    private int bateria = 15;

    public RoboReparo(String nome){
        this.nome = nome;
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

    public boolean repararModulo(SistemaCritico modulo){
        if(bateria <= 0){
            System.out.println("Bateria esgotada! Robô inativo.");
            return false;
        }

        boolean reparado = modulo.receberReparo("robo");

        if(reparado == true){
            consumirBateria(3);
        }

        return reparado;
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

    public void carregarBateria(ModuloEnergia energia){
        if(energia.energiaRobo(5)){
            bateria += 5;

            if(bateria > 15){
                bateria = 15;
            }

            atualizarEstado();
        }
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
