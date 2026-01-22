package entities.system;
import entities.Astronauta;

public class ModuloHabitacao extends Modulo{
    public ModuloHabitacao(String nome, int desgaste){
        super(nome, desgaste);
    }

    private int conforto = 100;

    @Override
    public int consumoEnergia(){
        return 5;
    }

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

    @Override
    public boolean receberReparo(String executor){
        if(estado.equals("normal")){
            return false;
        }

        boolean reparado = false;

        if(executor.equalsIgnoreCase("robo") || executor.equalsIgnoreCase("rôbo")){
            if(estado.equals("atenção") || estado.equals("falha")){
                reduzirDesgaste(25);
                conforto += 5;
                reparado = true;
            }

        }
        if(executor.equalsIgnoreCase("astronauta")){
            if(estado.equals("atenção")){
                reduzirDesgaste(20);
                conforto += 10;
                reparado = true;
            }else if(estado.equals("falha")){
                reduzirDesgaste(40);
                conforto += 20;
                reparado = true;
            }else if(estado.equals("emergência")){
                reduzirDesgaste(60);
                conforto += 30;
                reparado = true;
            }
        }

        return reparado;
    }

    @Override
    public int prioridade(){
        return 2;
    }

    public void avaliarConforto(ModuloEnergia energia, Astronauta astronauta){
        if(energia.getEnergia() > 50){
            estado = "normal";
        }else if(energia.getEnergia() > 40){
            estado = "atenção";
            aumentarDesgaste(3);
            astronauta.diminuirFadiga();
            conforto -= 1;
        }else if(energia.getEnergia() > 30){
            estado = "falha";
            aumentarDesgaste(3);
            astronauta.diminuirFadiga();
            conforto -= 5;
        }else{
            estado = "emergência";
            aumentarDesgaste(3);
            astronauta.diminuirFadiga();
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

    public void impactarAstronautas(Astronauta astronauta){
        if(estado.equals("normal")){
            return;
        }
        if(estado.equals("atenção")){
            astronauta.aumentarFadiga(1);
        }else if(estado.equals("falha")){
            astronauta.aumentarFadiga(3);
        }else{
            astronauta.aumentarFadiga(15);
        }
    }
}
