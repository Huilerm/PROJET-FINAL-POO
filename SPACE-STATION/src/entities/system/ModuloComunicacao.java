package entities.system;

public class ModuloComunicacao extends Modulo{
    public ModuloComunicacao(String nome, int desgaste){
        super(nome, desgaste);
    }

    private boolean contatoTerra = true;

    @Override
    public String gerarAlerta(){
        if(estado.equals("normal")){
            return "Sinal excelente (ping ~1ms).";
        }else if(estado.equals("atenção")){
            return "Sinal instável (ping ~100ms).";
        }else if(estado.equals("falha")) {
            return "Sinal degradado (ping ~990ms).";
        }else{
            return "EMERGÊNCIA: sinal perdido. Comunicação com a Terra interrompida.";
        }
    }

    public void verificarSinal(ModuloEnergia energia){
        if(energia.getEnergia() > 50){
            estado = "normal";
        }else if(energia.getEnergia() > 40){
            estado = "atenção";
            aumentarDesgaste(5);
        }else if(energia.getEnergia() > 30){
            estado = "falha";
            aumentarDesgaste(10);
        }else{
            estado = "emergência";
            aumentarDesgaste(20);
            perderContato();
        }
        if(energia.getEnergia() > 55){
            restabelecerContato();
        }
    }

    public void perderContato(){
        System.out.println("Contato com a base na Terra foi perdido!");
        contatoTerra = false;
        aumentarDesgaste(15);
    }

    public void restabelecerContato(){
        System.out.println("Sinal restabelecido. Comunicação com a Terra retomada.");
        contatoTerra = true;
        aumentarDesgaste(5);
    }
}
