import java.util.ArrayList;
import java.util.Collections;

public class MutadorPorInsercion extends Mutador {

    public MutadorPorInsercion(Double probMutar) {
        super(probMutar);
    }
    
    /**
     * Desplaza los valores de un arreglo de escenarios uno a la dercha que se encuentran entre dos posiciones(el ultimo valor lo pone en el lugar del primero)
     * @param pos Arreglo que tiene las dos posiciones
     * @param escenarios Escenarios del individuo
     */
    private void desplazarValores(Integer[] pos, ArrayList<Escenario> escenarios) {
        Escenario escenario = escenarios.get(pos[1]);

        for(int i = pos[1]; i > pos[0] + 1; i--){
            escenarios.set(i, escenarios.get(i-1));    
        }
        escenarios.set(pos[0] + 1, escenario);
    }


    @Override
    public void mutar(Individuo ind) {
        Double prob = Math.random();
        if(prob > probMutar){
            return;
        }
        ArrayList<Escenario> escenarios = ind.getEscenarios();
        Integer pos[] = new Integer[2];
        getPosicionesRandom(pos,escenarios);
        desplazarValores(pos,escenarios);
        ind.setEscenarios(escenarios);
    }

    @Override
    public String getName() {
        return "Mutador por insercion";
    }

    
}
