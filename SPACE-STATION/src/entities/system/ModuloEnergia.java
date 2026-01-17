package entities.system;
import java.util.Random;

public class ModuloEnergia extends Modulo{
    int pontosdeEnergia=40;
    int producao=30;
    boolean isDesgastado;
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
            System.out.println("-> Módulo de Energia: "+this.integridade);
        }else{
            System.out.println("-> Módulo de Energia: FALHA");
        }
    }
    public void produzirEnergia(){
        if(this.isDesgastado==true){
            this.pontosdeEnergia+=this.producao-10;
            System.out.println("producao desgastada");
        }
        else{this.pontosdeEnergia+=this.producao;};
    }
    public void calcularConsumo(){
        System.out.println("Distribuindo 5pts de Energia pra cada módulo...");
        this.pontosdeEnergia-=25;
        System.out.println("pontos de Energia atuais: "+pontosdeEnergia);
    }
    public void atualizarProducao(){
        if (this.integridade<=40){
            this.isDesgastado=true;
            System.out.println("esta desgastado");
        }
    }
    public void verificarSobrecarga(){
        if(this.producao<25 ^ this.isDesgastado==true){
            this.integridade=50;
            System.out.println("integridade reduzida para 50");
        }
        else if(this.producao<25 && this.isDesgastado==true){
            this.integridade=20;
            System.out.println("integridade reduzida para 20");
        }
    }
    public void falhadomodulo(ModuloComunicacao cm,ModuloPesquisa pe,ModuloSuporteVida sv ){
        if(this.integridade<30){
            cm.integridade=50;
            pe.pesquisaEstaAtivada=false;
        }
        if (this.integridade<20){
            sv.integridade=40;
            sv.emitirAlerta();

        }
    }


}
