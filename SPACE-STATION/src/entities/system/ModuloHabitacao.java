package entities.system;
import entities.Astronauta;

public class ModuloHabitacao extends Modulo{
    public ModuloHabitacao(int desgaste){
        super(desgaste);
    }

    public void avaliarConforto(ModuloEnergia energia, Astronauta astronauta){
        if(energia.getEnergia() > 50){
            estado = "normal";
        }else if(energia.getEnergia() > 40){
            estado = "atenção";
            aumentarDesgaste(3);
            astronauta.aumentarFadiga(1);
        }else if(energia.getEnergia() > 30){
            estado = "falha";
            aumentarDesgaste(3);
            astronauta.aumentarFadiga(1);
        }else{
            estado = "emergência";
            aumentarDesgaste(3);
            astronauta.aumentarFadiga(1);
        }
    }
}
