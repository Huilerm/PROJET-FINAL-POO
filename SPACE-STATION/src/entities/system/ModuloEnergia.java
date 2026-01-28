package entities.system;

public class ModuloEnergia extends Modulo{
    public ModuloEnergia(String nome, int desgaste){
        super(nome, desgaste);
    }

    private int unidadesDeEnergia = 40;
    private int producao = 30;
    private int capacidadeMaxima = 100;

    public int consumoEnergia(){
        return 0;
    }

    public int getEnergia(){
        return unidadesDeEnergia;
    }

    public boolean energiaRobo(int valor){
        if(unidadesDeEnergia < valor){
            return false;
        }

        unidadesDeEnergia -= valor;
        verificarSobrecarga();
        return true;
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

    @Override
    public boolean receberReparo(String executor){
        if(estado.equals("normal")){
            return false;
        }

        boolean reparado = false;

        if(executor.equalsIgnoreCase("robo") || executor.equalsIgnoreCase("rôbo")){
            if(estado.equals("atenção") || estado.equals("falha")){
                reduzirDesgaste(25);
                reparado = true;
            }

        }
        if(executor.equalsIgnoreCase("astronauta")){
            if(estado.equals("atenção")){
                reduzirDesgaste(20);
                reparado = true;
            }else if(estado.equals("falha")){
                reduzirDesgaste(40);
                reparado = true;
            }else if(estado.equals("emergência")){
                reduzirDesgaste(60);
                reparado = true;
            }
        }

        return reparado;
    }

    @Override
    public int prioridade(){
        return 2;
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

    public boolean fornecerEnergia(Modulo modulo){ // Tipo de referência e a referência
        if(unidadesDeEnergia >= modulo.consumoEnergia()){
            unidadesDeEnergia -= modulo.consumoEnergia();
            return true;
        }
        return false;
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
