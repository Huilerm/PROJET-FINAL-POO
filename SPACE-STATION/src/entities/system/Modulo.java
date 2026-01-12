package entities.system;
import interfaces.*;

public abstract class Modulo implements Alertavel, Monitoravel, Reparavel {
    protected int integridade;
    @Override
    public void emitirAlerta(){

    }
    @Override
    public void status(){

    }
    @Override
    public void reparar(){

    }

    public void getIntegridade() {
        System.out.println(this.integridade);
    }

    public abstract void nomeModulo();
    public abstract void tipoModulo();
    public abstract void estadoModulo();
}
