import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;

public class MutadorPorIntercambio extends Mutador {

    public MutadorPorIntercambio(Double probMutar) {
        super(probMutar);
    }
    @Override
    public void mutar(Individuo ind) {
        Double prob = Math.random();
        if(prob > probMutar){
            return;
        }
        Integer pos[] = new Integer[2];
        ArrayList<Escenario> escenarios = ind.getEscenarios();
        getPosicionesRandom(pos, escenarios);
        Collections.swap(escenarios, pos[0], pos[1]);     
        ind.setEscenarios(escenarios);   
    }
    @Override
    public String getName() {
        return "Mutador por intercambio";
    }

    
}
