package entities.system;
import entities.Astronauta;

public class ModuloSuporteVida extends Modulo{
    public ModuloSuporteVida(String nome, int desgaste){
        super(nome, desgaste);
    }

    private int oxigenio = 100;
    private double pressao = 1.0;
    private int temperatura = 27;

    public int getOxigenio(){
        return oxigenio;
    }

    public String getPressao(){
        if(pressao >= 0.8){
            return "Estável";
        }else{
            return "Instável";
        }
    }

    public int getTemp(){
        return temperatura;
    }

    @Override
    public int consumoEnergia(){
        return 10;
    }

    @Override
    public String gerarAlerta(){
        if(estado.equals("normal")){
            return "Suporte à vida estável..";
        }else if(estado.equals("atenção")){
            return "Anomalias foram encontrada. Níveis de oxigênio e temperatura oscilando.";
        }else if(estado.equals("falha")) {
            return "FALHA CRÍTICA! risco crescente para a tripulação.";
        }else{
            return "EMERGÊNCIA: Suporte à vida colapsou! Evacue imediatamente!";
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
                reparado = true;
            }

        }
        if(executor.equalsIgnoreCase("astronauta")){
            if(estado.equals("atenção")){
                reduzirDesgaste(20);
                reparado = true;
            }else if(estado.equals("falha")){
                reduzirDesgaste(40);
                reparado = true;
            }else if(estado.equals("emergência")){
                reduzirDesgaste(60);
                reparado = true;
            }
        }

        return reparado;
    }

    @Override
    public int prioridade(){
        return 3;
    }

    public void verificarCondicoes(){
        if(oxigenio >= 70 || temperatura >= 22 || pressao >= 0.8){
            estado = "normal";
        }else if(oxigenio >= 50 || temperatura >= 18 || pressao >= 0.6){
            estado = "atenção";
            aumentarDesgaste(7);
        }else if(oxigenio >= 40 || temperatura >= 12 || pressao >= 0.4){
            estado = "falha";
            aumentarDesgaste(12);
        }else{
            estado = "emergência";
            aumentarDesgaste(25);
        }
    }

    public void degradarCondicoes(){
        oxigenio -= 5;
        temperatura -= 1;
        pressao -= 0.1;

        if(estado.equals("falha")){
            oxigenio -= 7;
            temperatura -= 2;
            pressao -= 0.2;
        }
        if(estado.equals("emergência")){
            oxigenio -= 15;
            temperatura -= 3;
            pressao -= 0.3;
        }
    }
}
