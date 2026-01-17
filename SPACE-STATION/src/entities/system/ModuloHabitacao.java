package entities.system;

import java.util.Random;

public class ModuloHabitacao extends Modulo{
    int pontosdeconforto=100;

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
    public void avaliarConforto(ModuloEnergia me,ModuloSuporteVida sv){
        if (sv.integridade<30 && me.pontosdeEnergia<30){
            this.pontosdeconforto=30;
            System.out.println("Conforto em 30 pontos");
            this.integridade=20;
        }
        else if (me.pontosdeEnergia<30){
            this.pontosdeconforto=70;
            System.out.println("Conforto em 70 pontos");
            this.integridade=60;
        }
        else if (sv.integridade<30){
            this.pontosdeconforto=60;
            System.out.println("Conforto em 60 pontos");
            this.integridade=50;
        }else{
            System.out.println("Tudo perfeito!");
        }
    }
    public void avaliarIntegridade(){
        if(integridade >= 80){
            System.out.println("INTEGRIDADE NA HABITAÇÃO:ÓTIMO");
        }else if(integridade >= 60){
            System.out.println("INTEGRIDADE NA HABITAÇÃO:ESTÁVEL");
        }else if(integridade >= 40){
            System.out.println("INTEGRIDADE NA HABITAÇÃO:INSTÁVEL");
        }else{
            System.out.println("INTEGRIDADE NA HABITAÇÃO:CRÍTICO");
        }
    }
    public void impactarAstronautas(){}

}
