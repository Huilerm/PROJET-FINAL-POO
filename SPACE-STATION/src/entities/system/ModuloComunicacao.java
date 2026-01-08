package entities.system;
import java.util.Random;
import interfaces.*;

public class ModuloComunicacao extends Modulo{
    private int integridade;

    @Override
    public void nomeModulo(){
        System.out.println("Telstar 1"); //Primeiro módulo de comunicação da NASA
    }
    @Override
    public void tipoModulo(){
        System.out.println("Comunicação");
    }
    private void calcularIntegridade(){
        Random random = new Random();
        integridade = random.nextInt(101);
    }
    private void statusIntegridade(){
        if(integridade >= 80){
            System.out.println("ÓTIMO");
        }else if(integridade >= 60){
            System.out.println("ESTÁVEL");
        }else if(integridade >= 40){
            System.out.println("INSTÁVEL");
        }else{
            System.out.println("CRÍTICO");
        }
    }
    @Override
    public void estadoModulo(){
        if (integridade == 0) {
            calcularIntegridade();
        }
        if(integridade >= 40){
            System.out.println("-> Módulo de Comunicação: OK");
        }else{
            System.out.println("-> Módulo de Comunicação: FALHA");
        }
    }
}
