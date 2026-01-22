package interfaces;

public interface SistemaCritico {
    String gerarAlerta();
    boolean receberReparo(String executor);
    String estado();
    int desgaste();
}
