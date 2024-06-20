import java.util.ArrayList;

public abstract class Mutador {
    protected Double probMutar;

    public Mutador(Double probMutar) {
        this.probMutar = probMutar;
    }
   
    /**
     * Obtiene dos posiciones al azar entre 1 y el tamano del individuo
     * @param pos Arreglo de posiciones donde se guardan las possiciones
     * @param escenarios Escenarios del individuo
     */
    protected void getPosicionesRandom( Integer[] pos, ArrayList<Escenario> escenarios) {
        pos[0] = (int) (Math.random()*(escenarios.size() - 1) + 1);
        do{
            pos[1] = (int) (Math.random()*(escenarios.size() - 1) + 1);
        }while(pos[0] == pos[1]);
        if(pos[1] < pos[0]){
            int aux = pos[0];
            pos[0] = pos[1];
            pos[1] = aux;
        }
    }
    
    public abstract void mutar(Individuo ind);

    public abstract String getName();
    
}
