package entities.system;
import interfaces.*;

public abstract class Modulo implements SistemaCritico{
    protected String nome;
    protected String estado; // normal, atenção, falha ou emergência
    protected int desgaste; // 0 a 100

    public Modulo(String nome, int desgaste) {
        this.nome = nome;
        this.desgaste = desgaste;
        this.estado = "normal";
    }

    @Override
    public String estado(){
        return estado;
    }

    @Override
    public int desgaste(){
        return desgaste;
    }

    public String nome(){
        return nome;
    }

    public void aumentarDesgaste(int valor) {
        desgaste += valor;
        if (desgaste > 100) {
            desgaste = 100;
        }
        atualizarEstado();
    }

    public void reduzirDesgaste(int valor) {
        desgaste -= valor;
        if (desgaste < 0) {
            desgaste = 0;
        }
        atualizarEstado();
    }

    protected void atualizarEstado() {
        if (desgaste < 40) {
            estado = "normal";
        } else if (desgaste < 70) {
            estado = "atenção";
        } else if (desgaste < 90) {
            estado = "falha";
        } else {
            estado = "emergência";
        }
    }

    @Override
    public abstract String gerarAlerta();
}
