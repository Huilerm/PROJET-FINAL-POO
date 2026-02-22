package entities.system;

public class ModuloEnergia extends Modulo{
    public ModuloEnergia(int desgaste){
        super(desgaste);
    }

    private int unidadesDeEnergia = 40;
    private int producao = 30;
    private int capacidadeMaxima = 100;

    public int getEnergia(){
        return unidadesDeEnergia;
    }

    public void produzirEnergia(){
        aumentarDesgaste(3);
        if(unidadesDeEnergia >= capacidadeMaxima) {
            unidadesDeEnergia = capacidadeMaxima;
            return;
        }
        if(desgaste >= 40){
            unidadesDeEnergia += producao - 10;
            aumentarDesgaste(1);
        }else{
            unidadesDeEnergia += producao;
        }
    }

    public void consumirEnergia(int quantidade) {
        this.unidadesDeEnergia -= quantidade;
        if (this.unidadesDeEnergia < 0) this.unidadesDeEnergia = 0;
    }

    public void atualizarProducao(){
        if(desgaste >= 70){
            producao = 20;
            System.out.println("Produção de energia reduzida para 20 unidades permanentemente.");
        }
    }

    public String verificarSobrecarga(){
        if(unidadesDeEnergia <= 5){
            estado = "emergência";
            return "emergência";
        }else if(producao <= 15){
            estado = "falha";
            return "falha";
        }else if(producao <= 25){
            estado = "atenção";
            return "atenção";
        }else{
            estado = "normal";
            return "normal";
        }
    }
}
