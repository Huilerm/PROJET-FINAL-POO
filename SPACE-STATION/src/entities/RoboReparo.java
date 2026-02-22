package entities;

public class RoboReparo {
    private int bateria = 15;

    public void consumirBateria(int valor) {
        bateria -= valor;
        if (bateria < 0) {
            bateria = 0;
        }
    }

    public int getBateria() {
        return bateria;
    }
}