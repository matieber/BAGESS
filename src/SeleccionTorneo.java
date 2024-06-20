import java.util.ArrayList;

public class SeleccionTorneo implements SeleccionadorPadres {

    /**
     * Selecciona aleatoriamente "p" individuos, no pueden ser repetidos
     * @param poblacion poblacion donde se van a seleccionar los individuos
     * @param size tamano de la poblacion
     * @return lista con los individuos seleccionados aleatoriamente
     */
    private ArrayList<Integer> seleccionarAleatoriamente(Poblacion poblacion, int size) {
        Integer aleatorio;
        ArrayList<Integer> individuos = new ArrayList<>();
        Integer p = Optimizador.indsPorTorneo; 
        for(int i = 0; i < p; i++){
            do{
                aleatorio = (int) (Math.random() * size);
            } while(individuos.contains(aleatorio) );
            individuos.add(aleatorio);
        }
        return individuos;
    }
    
    /**
     * Selecciona el individuo con mejor fitness
     * @param individuos lista con los individuos a seleccionar
     * @param evaluacion lista que contiene la evaluacion de cada individuo
     * @return numero entero que indica el individuo con mejor fitness
     */
    private Integer seleccionarMejorFitness(ArrayList<Integer> individuos, ArrayList<Double> evaluacion) {
        Double min = Double.MAX_VALUE;
        Integer indMax = -1;
        for(Integer ind: individuos){
            Double fitnessActual = evaluacion.get(ind);
            if(fitnessActual < min){
                min = fitnessActual;
                indMax = ind;
            }
        }
        return indMax;

    }
    
    /**
     * Dada una lista de padres que describe la posicion de cada individuo, lo busca y genera una nueva poblacion
     * @param poblacion
     * @param padres
     * @return
     */
    private Poblacion getPadres(Poblacion poblacion,ArrayList<Integer> padres) {
        Poblacion pob = new Poblacion(poblacion.getPoblacionInicial());
        ArrayList<Individuo> individuos = poblacion.getIndividuos();
        for(Integer padre:padres){
            Individuo nuevoInd = individuos.get(padre);
            pob.addIndividuo(nuevoInd); 
        }
        return pob;
    }


    @Override
    public Poblacion seleccionar(Poblacion poblacion, ArrayList<Double> evaluacion) {
        ArrayList<Integer> padres = new ArrayList<>();
        int size = poblacion.getIndividuos().size();
        int i = 0;
        while(i < size ){ 
            Integer mejorFitness;
            ArrayList<Integer> individuosAleatorios = seleccionarAleatoriamente(poblacion,size);
            mejorFitness = seleccionarMejorFitness(individuosAleatorios,evaluacion);      
            padres.add(mejorFitness);  
            i++;
            
       }
        return getPadres(poblacion,padres);
    }
  
}
