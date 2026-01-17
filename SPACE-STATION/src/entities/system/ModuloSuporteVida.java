package entities.system;
import exceptions.EmergenciaException;

import java.util.Random;

public class ModuloSuporteVida extends Modulo{


    @Override
    public void nomeModulo(){
        System.out.println("Unity"); //Primeiro módulo de suporte à vida da NASA
    }
    @Override
    public void tipoModulo(){
        System.out.println("Suporte à Vida");
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
            System.out.println("-> Módulo de Suporte à Vida: "+this.integridade);
        }else{
            System.out.println("-> Módulo de Suporte à Vida: FALHA");
        }
    }
    public void verificarCondicoes(){
            if(integridade >= 80){
                System.out.println("Oxigenio: 100% Pressão:Excelente Temperatura:25°");
            }else if(integridade >= 60){
                System.out.println("Oxigenio: 80% Pressão:Ok Temperatura:20°");
            }else if(integridade >= 40){
                System.out.println("Oxigenio: 50% Pressão:Ruim Temperatura:10°");
            }else{
                System.out.println("CRÍTICO Oxigenio: 30% Pressão:Horrível Temperatura:-5°");
            }
        }
    public void forcarEmergencia(){
        try{
            throw new EmergenciaException("Emergência!!!!!");
        }catch (EmergenciaException e){
            System.out.println(e.getMessage());
        }

    };
    public void impactarAstronautas(){};
    public void degradarCondicoes(){
        this.integridade-=20;
    };
}


