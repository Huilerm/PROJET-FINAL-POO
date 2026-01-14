package entities;
import interfaces.Reparavel;

public class RoboReparo implements Reparavel {
    String estado;

    @Override
    public boolean reparar(String executor){
        return false;
    }
}
