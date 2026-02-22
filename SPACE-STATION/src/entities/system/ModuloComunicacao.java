package entities.system;

public class ModuloComunicacao extends Modulo{
    public ModuloComunicacao(int desgaste){
        super(desgaste);
    }

    public void verificarSinal(ModuloEnergia energia){
        if(energia.getEnergia() > 50){
            estado = "normal";
        }else if(energia.getEnergia() > 40){
            estado = "atenção";
            aumentarDesgaste(5);
        }else if(energia.getEnergia() > 30){
            estado = "falha";
            aumentarDesgaste(10);
        }else{
            estado = "emergência";
            aumentarDesgaste(20);
        }
    }
}
