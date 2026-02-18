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

    public void avaliarCondicoes(Astronauta astronauta) {
        if (oxigenio < 20 || temperatura < 10 || pressao < 0.3) {
            estado = "emergência";
            aumentarDesgaste(10);
            if (astronauta != null) astronauta.aumentarFadiga(5);
        } else if (oxigenio < 40 || temperatura < 15 || pressao < 0.5) {
            estado = "falha";
            aumentarDesgaste(5);
            if (astronauta != null) astronauta.aumentarFadiga(2);
        } else if (oxigenio < 60 || temperatura < 20 || pressao < 0.7) {
            estado = "atenção";
            aumentarDesgaste(2);
            if (astronauta != null) astronauta.aumentarFadiga(1);
        } else {
            atualizarEstado();
        }
    }
}
