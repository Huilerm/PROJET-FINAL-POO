package entities.system;

public class ModuloPesquisa extends Modulo{
    public ModuloPesquisa(String nome, int desgaste){
        super(nome, desgaste);
    }

    private boolean ligado = true;

    @Override
    public int consumoEnergia(){
        if(ligado != true) {
            return 0;
        }else{
            return 7;
        }
    }

    @Override
    public String gerarAlerta(){
        if(estado.equals("normal")){
            return "As pesquisas estão ocorrendo sem falhas.";
        }else if(estado.equals("atenção")){
            return "Algumas pesquisas apresentaram oscilações.";
        }else if(estado.equals("falha")) {
            return "Falha nos sistemas de pesquisa! Experimentos foram interrompidos.";
        }else{
            return "EMERGÊNCIA: sistemas de pesquisa inoperantes! Dados científicos comprometidos!";
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
        return 1;
    }

    public void executarExperimentos(ModuloEnergia energia){
        if(energia.getEnergia() > 50){
            estado = "normal";
            ligado = true;
        }else if(energia.getEnergia() > 40){
            estado = "atenção";
            ligado = true;
            aumentarDesgaste(5);
        }else if(energia.getEnergia() > 30){
            estado = "falha";
            ligado = false;
            aumentarDesgaste(10);
        }else{
            estado = "emergência";
            ligado = false;
            aumentarDesgaste(20);
        }
    }
}
