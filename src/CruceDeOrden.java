import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

public class CruceDeOrden extends Cruce {



    public CruceDeOrden(Double probCruce) {
        super(probCruce);
    }

    /**
     * Elige dos puntos al azar entre 1 y el tamano del individuo
     * @param size Tamano del individuo
     * @return Arreglo con dos puntos
     */
    private Integer[] elegirPuntosAlAzar(int size) {
        Integer punto1 = (int) (Math.random()*(size -1) + 1);
        Integer punto2;
        do{
           punto2 = (int) (Math.random()*(size -1) + 1); 
        }while(punto1 == punto2);

        Integer sol[] = new Integer[2];
        sol[0] = punto1 < punto2 ? punto1 : punto2;
        sol[1] = punto1 < punto2 ? punto2 :punto1;
        return sol;
    }
    
    /**
     * Ordena los valores no incluidos en la solucion con el orden del segundo individuo
     * @param escenario2 Elementos del escenario 2
     * @param valoresRestantes Valores faltantes en la solucion 1
     */
    private void ordenarValoresRestantes(ArrayList<Escenario> escenario2, ArrayList<Integer> valoresRestantes) {
        int i = 1;
        while(i < escenario2.size()){
            Integer valor = escenario2.get(i).getIdentificador();
            if(!valoresRestantes.contains(valor)){ //Al eliminar los valores no contenidos en valores restantes ya queda ordenado
                escenario2.remove(i);
            }else{
                i++;
            }
        }
    }

    /**
     * Completa los espacios vacios en el arreglo en el orden que indica el segundo individuo
     * @param sol Solucion parcial del cruzamiento
     * @param escenario2 Arreglo con los elementos ordenados a agregar
     * @param puntos Puntos que indican que parte de la solucion ya esta completada
     */
    
    /*
    private void completarRestantes(Integer[] sol, ArrayList<Escenario> escenario2, Integer[] puntos) {
        int i = 1;
        for(int j = puntos[1]+1; j < sol.length; j++){
            sol[j] = escenario2.get(i).getIdentificador();
            i++;
        }
        for(int j = 1; j < puntos[0]; j++){
            sol[j] = escenario2.get(i).getIdentificador();
            i++;
        }
    }*/
    
    // crossover LOX
    private void completarRestantes(Integer[] sol, ArrayList<Escenario> escenario2, Integer[] puntos) {
        
    	int i = 1;
        
        for(int j = 1; j < puntos[0]; j++){
            sol[j] = escenario2.get(i).getIdentificador();
            i++;
        }
                
        for(int j = puntos[1]+1; j < sol.length; j++){
            sol[j] = escenario2.get(i).getIdentificador();
            i++;
        }
        
    }
    
    

    /**
     * Genera un individuo a partir del arreglo de posiciones que representa cada escenario
     * @param escenario Arreglo de posiciones
     * @return Nuevo individuo
     */
    private Individuo generarIndividuo(ArrayList<Integer> escenario) {
        ArrayList<Escenario> escenarios = Optimizador.getEscenarios();
        ArrayList<Escenario> escInd = new ArrayList<>();
        for(int i = 0; i < escenario.size(); i++){
            Escenario esc = escenarios.get(escenario.get(i));
            escInd.add(esc);
        }
        return new Individuo(escInd);
        
    }

    /**
     * Realiza el cruzamiento de dos individuos a partir de los puntos generados
     * @param ind1 Individuo 1
     * @param ind2 Individuo 2
     * @param puntos Puntos elegidos al azar
     * @return Hijo generado en el cruce
     */
    private Individuo cruce(Individuo ind1, Individuo ind2, Integer[] puntos) {
        ArrayList<Escenario> escenario1 = ind1.getEscenarios();
        ArrayList<Escenario> escenario2 = ind2.getEscenarios();
        Integer sol[] = new Integer[ind1.getEscenarios().size()];
        ArrayList<Integer> valoresRestantes = new ArrayList<>();
        for(int i = 1; i < escenario1.size(); i++){
            Integer valor = escenario1.get(i).getIdentificador();
            if(i <= puntos[1] && i >= puntos[0]){
                sol[i] = valor;
            }else{
                valoresRestantes.add(valor);
            }
        }
        sol[0] = escenario1.get(0).getIdentificador();
        ordenarValoresRestantes(escenario2,valoresRestantes);
        completarRestantes(sol,escenario2,puntos);

        ArrayList<Integer> solucion = new ArrayList<>();
        Collections.addAll(solucion,sol);
        Individuo hijo = generarIndividuo(solucion).mutar();
        return hijo;
    }


    @Override
    public Individuo[] cruzar(Individuo ind1, Individuo ind2) {
        Individuo sol[] = new Individuo[2];
        Double prob = Math.random();
        if(prob > this.probCruce){
            sol[0] = new Individuo(ind1);
            sol[1] = new Individuo(ind2);
        }    
        else{
            Integer puntos[] = elegirPuntosAlAzar(ind1.getEscenarios().size());
            sol[0] = cruce(ind1,ind2,puntos);
            sol[1] = cruce(ind2,ind1,puntos);
        }
        return sol;
    }

    @Override
    public String getName() {
        return "Cruce de orden";
    }
   
}
