package entities.system;
import java.util.Random;
import interfaces.*;

public class ModuloComunicacao extends Modulo{
    public Boolean contatoTerra=true;

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
            System.out.println("-> Módulo de Comunicação: "+this.integridade);
        }else{
            System.out.println("-> Módulo de Comunicação: FALHA");
        }
    }
    @Override
    public void reparar(){
        System.out.println("Reparando ...");
        restabelecerContato();
    }
    public void verificarSinal(ModuloEnergia me) {
        if (me.pontosdeEnergia > 50) {
            System.out.println("Sinal com ping de 1ms");
        } else if (me.pontosdeEnergia > 40) {
            System.out.println("Sinal com ping de 100ms");
        } else if (me.pontosdeEnergia > 30) {
            System.out.println("Sinal com ping de 990ms");
        }else if(me.pontosdeEnergia>20) {
            System.out.println("Sinal Horrível");
            perderContato();
        }
    }

    public void perderContato(){
        System.out.println("Contato perdido");
        this.contatoTerra=false;
    }
    public void restabelecerContato(){
        System.out.println("Contato restabelecido");
        this.contatoTerra=true;
    }
    public void falhadomodulo(){

    }
}
