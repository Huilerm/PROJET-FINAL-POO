package entities.system;
import interfaces.*;

public abstract class Modulo implements SistemaCritico{
    // Atributos protected: podem ser acessados diretamente pelas subclasses (herança)
    protected String estado; // normal, atenção, falha ou emergência
    protected int desgaste; // 0 a 100

    public Modulo(int desgaste) {
        this.desgaste = desgaste;
        this.estado = "normal";
    }

    // Implementação do metodo estado() da interface SistemaCritico
    @Override
    public String estado(){
        return estado;
    }

    // Implementação do metodo desgaste() da interface SistemaCritico
    @Override
    public int desgaste(){
        return desgaste;
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
}
