package entities.system;

public class ModuloEnergia extends Modulo{
    public ModuloEnergia(String nome, int desgaste){
        super(nome, desgaste);
    }

    private int unidadesDeEnergia = 40;
    private int producao = 30;
    private int capacidadeMaxima = 100;

    public int getEnergia(){
        return unidadesDeEnergia;
    }

    @Override
    public String gerarAlerta(){
        if(estado.equals("normal")){
            return "Produção de energia em níveis normais.";
        }else if(estado.equals("atenção")){
            return "Variação na produção de energia. Recomenda-se o monitoramento.";
        }else if(estado.equals("falha")) {
            return "Falha no sistema de energia! Módulos dependentes poderam ser desligados!";
        }else{
            return "EMERGÊNCIA: perda de energia muito grande! Suporte à vida e comunicação comprometidos!";
        }
    }

    public void produzirEnergia(){
        aumentarDesgaste(3);
        if(unidadesDeEnergia >= capacidadeMaxima) {
            System.out.println("Bateria cheia! Produção interrompida.");
            unidadesDeEnergia = capacidadeMaxima;
            return;
        }
        if(desgaste >= 40){
            unidadesDeEnergia += producao - 10;
            System.out.println("Produção desgastada.");
            System.out.println("Total de " + unidadesDeEnergia + " unidades.");
            aumentarDesgaste(1);
        }else{
            unidadesDeEnergia += producao;
            System.out.println("Energia produzida sem falhas!");
            System.out.println("Total de " + unidadesDeEnergia + " unidades.");
        }
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
        }else if(producao < 15){
            estado = "falha";
            return "falha";
        }else if(producao < 25){
            estado = "atenção";
            return "atenção";
        }else{
            estado = "normal";
            return "normal";
        }
    }
}
