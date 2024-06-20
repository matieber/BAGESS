import java.util.ArrayList;
import java.util.Collections;

public class CruceBasadoEnArcos extends Cruce {

  
    
    public CruceBasadoEnArcos(Double probCruce) {
        super(probCruce);
    }

    /**
     * Encuentra la posicion de un escenario en una lista de escenarios
     * @param escenario Lista de escenarios
     * @param valor Valor buscado
     * @return Posicion
     */
    protected Integer encontrarPosicion(ArrayList<Escenario> escenario,Integer valor){
        for(int i = 0; i < escenario.size(); i++){
            if(escenario.get(i).getIdentificador() == valor){
                return i;
            }
        }
        return -1;
    }

    /**
     * Dado un valor, encuentra todos los adyacentes en ambos individuos
     * @param escenario1 Escenarios del individuo 1
     * @param escenario2 Escenario del individuo 2
     * @param i Valor buscado
     * @return Lista de adyacentes
     */
    protected ArrayList<Integer> encontrarAdyacentes(ArrayList<Escenario> escenario1, ArrayList<Escenario> escenario2,int i) {
        ArrayList<Integer> adyacentes = new ArrayList<>();
        Integer pos1 = encontrarPosicion(escenario1, i);
        Integer pos2 = encontrarPosicion(escenario2, i);

        Integer pos;

        if(pos1 != 0){
            pos = pos1-1;
            adyacentes.add(escenario1.get(pos).getIdentificador());
        }
        if(pos1 < escenario1.size()-1){
            pos = pos1+1;
            adyacentes.add(escenario1.get(pos).getIdentificador());
        }

        if(pos2 != 0){
            pos = pos2-1;
            adyacentes.add(escenario1.get(pos).getIdentificador());
        }
        if(pos2 < escenario2.size()-1){
            pos = pos2+1;
            adyacentes.add(escenario1.get(pos).getIdentificador());
        }

        return adyacentes;
    }

    /**
     * Busca todos los adyacentes para cada valor existente
     * @param ind1 Individuo 1
     * @param ind2 Individuo 2
     * @return Lista de listas donde cada lista representa los adyacentes a un valor
     */
    protected ArrayList<ArrayList<Integer>> generarListaAdyacencia(Individuo ind1, Individuo ind2) {
        ArrayList<Escenario> escenario1 = ind1.getEscenarios();
        ArrayList<Escenario> escenario2 = ind2.getEscenarios();
        ArrayList<ArrayList<Integer>> sol = new ArrayList<>();

        for(int i = 0; i < escenario1.size(); i++){
        	i = escenario1.get(i).getIdentificador();
            ArrayList<Integer> adyacentes = encontrarAdyacentes(escenario1,escenario2,i);
            sol.add(adyacentes);
        }
        return sol;
    }
    
    /**
     * Elimina de las listas de adyacencia el valor dado
     * @param listaAdyacencia Lista de listas de adyacencia
     * @param random Valor a elimiar
     */
    protected void eliminarLista(ArrayList<ArrayList<Integer>> listaAdyacencia, Integer random){
        for (ArrayList<Integer> e : listaAdyacencia){
            ArrayList<Integer> eliminar = new ArrayList<>();
            eliminar.add(random);
            e.removeAll(eliminar);
        }

    }

    /**
     * Selecciona el proximo elemento a agregar a la lista
     * @param listaActual Lista de adyacencia del ultimo elemento agregado 
     * @param listaAdyacencia Lista de listas de adyacencia
     * @return Posicion del elemento a agegar
     */
    protected Integer elegirElemento(ArrayList<Integer> listaActual, ArrayList<ArrayList<Integer>> listaAdyacencia) {
        Integer tamanoActual = Integer.MAX_VALUE;
        Integer elementoActual = -1;
        for (Integer e  : listaActual){
            Integer cantElementos = Collections.frequency(listaActual,e);
            if (cantElementos > 1){
                return e;
            }
            else{
                Integer tamanoLista = listaAdyacencia.get(e).size();
                if (tamanoLista < tamanoActual ){
                    elementoActual = e;
                    tamanoActual = tamanoLista;
                }
            }

        }
        return elementoActual;
    }
    
    /**
     * Genera un individuo a partir del arreglo de posiciones que representa cada escenario
     * @param escenario Arreglo de posiciones
     * @return Nuevo individuo
     */
    public static Individuo generarIndividuo(ArrayList<Integer> escenario) {
        ArrayList<Escenario> escenarios = Optimizador.getEscenarios();
        ArrayList<Escenario> escInd = new ArrayList<>();
        for(int i = 0; i < escenario.size(); i++){
            Escenario esc = escenarios.get(escenario.get(i));
            escInd.add(esc);
        }
        Individuo hijo = new Individuo(escInd).mutar();
        return hijo;
        
    }

    /**
     * Realiza el crizamiento de dos individuos a partir de la lista de adyacencia generada
     * @param ind1 Individuo 1
     * @param ind2 Individuo 2
     * @param listaAdyacencia Lista de adyacencia de los dos individuos
     * @return Nuevo individuo
     */
    protected Individuo cruce(Individuo ind1, Individuo ind2, ArrayList<ArrayList<Integer>> listaAdyacencia) {
            Integer cantEscenarios = ind1.getEscenarios().size();
            ArrayList<Integer> hijo = new ArrayList<>();;
    
           
            ArrayList<Integer> listaActual = new ArrayList<>();
    
    
            //agrego el primero en valor 0 por la restriccion del problema 
            Integer pos = 0;//(int) (Math.random() * (cantEscenarios));
            hijo.add(pos);
            eliminarLista(listaAdyacencia,pos);
    
            for (int i = 0 ; i < cantEscenarios -1 ; i++){ 
                listaActual = listaAdyacencia.get(pos);
                if (!listaActual.isEmpty()){
                    pos = elegirElemento(listaActual,listaAdyacencia);
                }
                else{                   
                    do{
                        pos = (int) (Math.random() * (listaAdyacencia.size()));
                    }while ( hijo.contains(pos));
                }
                hijo.add(pos);
                
                eliminarLista(listaAdyacencia,pos);
            }

        Individuo ind = generarIndividuo(hijo);
        return ind;
    }


    @Override
    public Individuo[] cruzar(Individuo ind1, Individuo ind2) {
        Individuo sol[] = new Individuo[1];
        Double prob = Math.random();
        if(prob > this.probCruce){
            sol[0] = new Individuo(ind1);
        }    
        else{
            ArrayList<ArrayList<Integer>> listaAdyacencia = generarListaAdyacencia(ind1,ind2);
            sol[0] = cruce(ind1,ind2,listaAdyacencia);
        }
        return sol;
    }

    @Override
    public String getName() {
        return "Cruce basado en arcos puro";
    }

   
}
