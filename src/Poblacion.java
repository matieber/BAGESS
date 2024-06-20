import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class Poblacion {
    private ArrayList<Individuo> pob; 
    private int poblacionInicial;


    public Poblacion(int poblacionInicial){
        pob = new ArrayList<>();
        this.poblacionInicial = poblacionInicial;
    }

    public void autoGenerar() {
        for(int i = 0; i < poblacionInicial; i++){
            Individuo individuo = new Individuo();
            individuo.autoGenerar(); //genera un individuo de forma aleatoria
            pob.add(individuo);
        }
        
    }
    
    
    //---------------------------------------------------------
    // genero individuos con una copia de cada escenario
    public void autoGenerarAlternativo() {
    	
    	Individuo individuo = new Individuo();
    	individuo.autoGenerarAlternativoH1(); 
    	pob.add(individuo);
    	
        for(int i = 1; i < poblacionInicial; i++){
            individuo = new Individuo();
            individuo.autoGenerarAlternativo(); //genera un individuo con una copia de cada escenario
            pob.add(individuo);
        }
        
        
    }
    
    //--------------------------------------------------------
    

    public Poblacion(ArrayList<Individuo> pob){
        this.pob = pob;
    }

    public void addIndividuo(Individuo ind){
        pob.add(ind);
    }
    public void mostrar() {
        int cont = 0;
        System.out.println(this.toString());
        for(int i = 0; i < pob.size(); i++){
            System.out.println("Escenario " + cont);
            cont++;
            pob.get(i).mostrar();
            System.out.println();
        }
    }

    public ArrayList<Individuo> getIndividuos(){
        return new ArrayList<Individuo>(pob);
    }

    public int getPoblacionInicial(){
        return poblacionInicial;    
    }
    /**
     * Devuelve a la poblacion segun el identificador que tiene cada individuo
     * @return Lista de listas, donde cada lista es un individuo con sus respectivos identificadores
     */
    public ArrayList<ArrayList<Integer>> getPoblacionPorIdentificador(){
        ArrayList<ArrayList<Integer>> individuos = new ArrayList<>();
        for(Individuo ind: pob){
            individuos.add(ind.getIdentificadores());
        }
        return individuos;

    }

    /**
     * Cruza a todos los individuos de la poblacion
     * @return nueva poblacion con los hijos generados
     */
    public Poblacion cruzar(){
        Poblacion pobHijos = new Poblacion(0);
        Individuo individuos[] = new Individuo[pob.size()];
        List<Integer> lista = IntStream.range(0,pob.size()).boxed().collect(Collectors.toList());
        lista.stream().forEach(number -> {
            if(!Optimizador.cruce.getName().equals("Cruce basado en arcos puro")){
                if(number%2 == 0 && number < pob.size() - 1){
                    Individuo ind1 = pob.get(number);
                    Individuo ind2 = pob.get(number + 1);
                    Individuo[] hijos = ind1.cruzar(ind2);
                    individuos[number] = hijos[0];
                    individuos[number + 1] = hijos[1];
                }
            } 
            else{
                if (number < pob.size() -1){
                    Individuo ind1 = pob.get(number);
                    Individuo ind2 = pob.get(number + 1);
                    Individuo[] hijos = ind1.cruzar(ind2);
                    individuos[number] = hijos[0];
                }
                else{
                    Individuo ind1 = pob.get(number);
                    Individuo ind2 = pob.get(0);
                    Individuo[] hijos = ind1.cruzar(ind2);
                    individuos[number] = hijos[0]; 
                }
            }


        });
        pobHijos = new Poblacion(new ArrayList<>(Arrays.asList(individuos)));
        return pobHijos;
    }

    public ArrayList<Double> evaluarIndividuos(){
        Double evaluacion[] = new Double[pob.size()];
        List<Integer> lista = IntStream.range(0,pob.size()).boxed().collect(Collectors.toList());
        lista.stream().forEach(number -> {
            Double valor = pob.get(number).evaluar(Optimizador.matEval);
            evaluacion[number] = valor;
        });
        return new ArrayList<>(Arrays.asList(evaluacion));
    }
    @Override
    public String toString() {
        StringBuffer str= new StringBuffer();
        int cont = 0;
        for(Individuo ind:pob){
            str.append("Individuo " + cont + "\n");
            str.append(ind.toString());
            cont ++;
        }
        return str.toString();
    }
}
