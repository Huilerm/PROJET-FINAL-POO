package entities.system;
import entities.Astronauta;

public class ModuloHabitacao extends Modulo{
    public ModuloHabitacao(String nome, int desgaste){
        super(nome, desgaste);
    }

    private int conforto = 100;

    @Override
    public String gerarAlerta(){
        if(estado.equals("normal")){
            return "Habitação está confortável.";
        }else if(estado.equals("atenção")){
            return "Habitação está levemente desconfortável.";
        }else if(estado.equals("falha")) {
            return "Habitação apresenta alguns problemas.";
        }else{
            return "EMERGÊNCIA: estrutura e conforto da habitação em risco!.";
        }
    }

    public void avaliarConforto(ModuloEnergia energia, Astronauta astronauta){
        if(energia.getEnergia() > 50){
            estado = "normal";
        }else if(energia.getEnergia() > 40){
            estado = "atenção";
            aumentarDesgaste(3);
            astronauta.aumentarFadiga(1);
            conforto -= 1;
        }else if(energia.getEnergia() > 30){
            estado = "falha";
            aumentarDesgaste(3);
            astronauta.aumentarFadiga(1);
            conforto -= 5;
        }else{
            estado = "emergência";
            aumentarDesgaste(3);
            astronauta.aumentarFadiga(1);
            conforto -= 10;
        }
    }

    public void avaliarIntegridade(){
        if(desgaste >= 90){
            System.out.println("Integridade comprometida! Recomenda-se reparos com urgência!");
        }else{
            System.out.println("Integridade dentro dos padrões.");
        }
    }
}
