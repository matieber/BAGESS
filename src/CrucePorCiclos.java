import java.util.ArrayList;
import java.util.Collections;

public class CrucePorCiclos extends Cruce {

  

    public CrucePorCiclos(Double probCruce) {
        super(probCruce);
    }


    /**
     * Dado dos individuos, encuentra todos los ciclos que se producen entre ambos
     * @param ind1 Individuo 1
     * @param ind2 Individuo 2
     * @return Lista de listas donde cada lista representa un ciclo
     */
    private ArrayList<ArrayList<Integer>> encontrarCiclos(Individuo ind1, Individuo ind2) {
        ArrayList<Escenario> escenario1 = ind1.getEscenarios();
        ArrayList<Escenario> escenario2 = ind2.getEscenarios();
        ArrayList<ArrayList<Integer>> sol = new ArrayList<>();
        Boolean visitados[] = new Boolean[escenario1.size()]; 

        for (int i = 0; i < visitados.length; i++){
            visitados[i] = false;
        }
        
        Integer pos = getMovilDisponible(visitados);
        Integer primerMovil = escenario1.get(pos).getIdentificador();
        while(primerMovil != -1){
            ArrayList<Integer> ciclo = encontrarCiclo(escenario1,escenario2,pos,visitados,primerMovil);
            sol.add(ciclo);
            pos = getMovilDisponible(visitados);
            primerMovil = pos.equals(-1) ? -1 : escenario1.get(pos).getIdentificador();
        } 


        return sol;
    }
 

    /**
     * Encuentra el proximo ciclo que tienen dos individuos
     * @param escenario1 Escenarios del individuo 1
     * @param escenario2 Escenarios del individuo 2
     * @param pos Posicion en el arreglo del primer escenario no utilizado
     * @param visitados Arreglo que indica que posiciones de los arreglos ya se visitaron
     * @param primerMovil Indica cual fue el primer escenario utilizado para crear el ciclo actual
     * @return Ciclo
     */
    private ArrayList<Integer> encontrarCiclo(ArrayList<Escenario> escenario1, ArrayList<Escenario> escenario2, Integer pos, Boolean[] visitados, Integer primerMovil) {
        Integer movilActual = -1;
        ArrayList<Integer> ciclo = new ArrayList<>();
        while (!primerMovil.equals(movilActual)){

            movilActual = escenario2.get(pos).getIdentificador();

            pos = encontrarPosMovil(escenario1,movilActual);
            visitados[pos] = true;
            ciclo.add(pos);
        }
        return ciclo;

    }

    /**
     * Dado los escenarios de un individuo y un escenario particular, encuentra la posicion de dicho escenario en el arreglo
     * @param escenario1 Escenarios del individuo
     * @param movilActual Escenario que se desea buscar
     * @return POsicion encontrada, retorna -1 si no la encuentra
     */
    private Integer encontrarPosMovil(ArrayList<Escenario> escenario1, Integer movilActual) {
        for(int i = 0; i < escenario1.size(); i++){
            if(movilActual == escenario1.get(i).getIdentificador()){
                return i;
            }
        }
        return -1;
    }

    /**
     * Busca cual es el proximo escenario que todavia no se visito
     * @param visitados Arreglo que indica que escenarios ya se visitaron
     * @return Posicion del escenario disponible, retorna -1 en caso de no encontrar alguno
     */
    private Integer getMovilDisponible(Boolean[] visitados) {
       for (int i = 0 ; i < visitados.length; i++){
            if (!visitados[i])
                return i;
        }
        return -1;
    }

    /**
     * Dado dos individuos y una lista de ciclos, realiza el cruzamiento
     * @param ind1 Individuo 1
     * @param ind2 Individuo 2
     * @param ciclos Lista de ciclos
     * @return Dos listas que representan la solucion del cruzamiento
     */
    private ArrayList<ArrayList<Integer>> cruce(Individuo ind1, Individuo ind2, ArrayList<ArrayList<Integer>> ciclos){
        ArrayList<Escenario> escenario1 = ind1.getEscenarios();
        ArrayList<Escenario> escenario2 = ind2.getEscenarios();
        Integer sol1[] = new Integer[escenario1.size()];
        Integer sol2[] = new Integer[escenario2.size()];
        Boolean intercambiar = false;

        for (int i = 0; i < ciclos.size(); i++){
            ArrayList<Integer> ciclo = ciclos.get(i);
            for (int j = 0; j < ciclo.size(); j++){
                if(!intercambiar){
                    Integer pos = ciclo.get(j);
                    sol1[pos] = escenario1.get(pos).getIdentificador();
                    sol2[pos] = escenario2.get(pos).getIdentificador();
                }
                else{
                    Integer pos = ciclo.get(j);
                    sol1[pos] = escenario2.get(pos).getIdentificador();
                    sol2[pos] = escenario1.get(pos).getIdentificador();
                }
            }
            intercambiar = !intercambiar;
            
        }
        ArrayList<ArrayList<Integer>> sol = new ArrayList<>();
        ArrayList<Integer> aux1 = new ArrayList<Integer>();
        Collections.addAll(aux1,sol1);
        ArrayList<Integer> aux2 = new ArrayList<Integer>();
        Collections.addAll(aux2,sol2);
        sol.add(aux1);
        sol.add(aux2);
        return sol;

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
        Individuo hijo = new Individuo(escInd).mutar();
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
            ArrayList<ArrayList<Integer>> ciclos = encontrarCiclos(ind1,ind2);
            ArrayList<ArrayList<Integer>> escenarios = cruce(ind1, ind2, ciclos);
            sol[0] = generarIndividuo(escenarios.get(0));
            sol[1] = generarIndividuo(escenarios.get(1));
        }
        return sol;
    }

    @Override
    public String getName() {
        return "Cruce por ciclos";
    }


   
}
