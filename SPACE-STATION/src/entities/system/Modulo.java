package entities.system;
import interfaces.*;

abstract class Modulo implements Alertavel, Monitoravel, Reparavel {
    @Override
    public void emitirAlerta(){

    }
    @Override
    public void status(){

    }
    @Override
    public void reparar(){

    }
    public abstract void nomeModulo();
    public abstract void tipoModulo();
    public abstract void estadoModulo();
}
