import java.util.ArrayList;
import java.util.Collections;

public class MutadorPorInversion extends Mutador {

    public MutadorPorInversion(Double probMutar) {
        super(probMutar);
    }
    
    /**
     * Invierte los valores de un arreglo de escenarios entre dos posiciones dadas
     * @param pos Arreglo de posiciones
     * @param escenarios Escenarios del individuo
     */
    private void invertir(Integer[] pos, ArrayList<Escenario> escenarios) {
        int medio = ((pos[1] - pos[0])/2) + pos[0];
        int j = pos[1];
        for(int i = pos[0]; i <= medio && i != j; i++){
            Collections.swap(escenarios, i, j);
            j--;
        }
    }
 
    @Override
    public void mutar(Individuo ind) {
    	//System.out.println(ind.printWithId());
    	
        Double prob = Math.random();
        if(prob > probMutar){
            return;
        }
        Integer pos[] = new Integer[2];
        ArrayList<Escenario> escenarios = ind.getEscenarios();
        getPosicionesRandom(pos, escenarios); 
        invertir(pos,escenarios);
        ind.setEscenarios(escenarios);
        
        //System.out.println(ind.printWithId());
    }

    @Override
    public String getName() {
        return "Mutador por inversion";
    }

   
}
