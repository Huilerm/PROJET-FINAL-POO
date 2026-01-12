package entities.system;

import java.util.Random;

public class ModuloHabitacao extends Modulo{


    @Override
    public void nomeModulo(){
        System.out.println("BEAM"); //Primeiro módulo de habitação da NASA
    }
    @Override
    public void tipoModulo(){
        System.out.println("Habitação");
    }
    private void calcularIntegridade(){
        Random random = new Random();
        integridade = 70+random.nextInt(31);
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
            System.out.println("-> Módulo de Habitação: "+this.integridade);
        }else{
            System.out.println("-> Módulo de Habitação: FALHA");
        }
    }
}
