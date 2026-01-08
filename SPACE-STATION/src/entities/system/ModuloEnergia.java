package entities.system;
import java.util.Random;

public class ModuloEnergia extends Modulo{
    private int integridade;

    @Override
    public void nomeModulo(){
        System.out.println("Zarya"); //Primeiro módulo de energia da NASA
    }
    @Override
    public void tipoModulo(){
        System.out.println("Energia");
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
            System.out.println("-> Módulo de Energia: OK");
        }else{
            System.out.println("-> Módulo de Energia: FALHA");
        }
    }
}
