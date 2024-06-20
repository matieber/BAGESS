import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;

/*Evalua todos los escenarios posibles previo a la ejecucion del algoritmo evolutivo (2) */
public class OptPrevio extends Optimizador {

    public OptPrevio(String escenariosTxt, String variacionesTxt) {
        super(escenariosTxt, variacionesTxt);
    }

    public void run() throws IOException, InterruptedException{ 
        //Calculo inicial de los escenarios
        cleanDirectories(dirInstancias,dirResultados);
         matEval = CalculadorEscenarios.calcularEscenarios(dirInstancias, dirResultados);

        //Algoritmo evolutivo
        Poblacion poblacion = new Poblacion(poblacionInicial);
        poblacion.autoGenerar();
        if (debug != 0){
            System.out.println("Poblacion inicial: ");
            System.out.println(poblacion.getPoblacionPorIdentificador());
            System.out.println("Funcion de evaluacion: ");
            System.out.println(poblacion.evaluarIndividuos());
        }
        
        ArrayList<Double> evaluacion = poblacion.evaluarIndividuos();
        mejorFitness.add(Collections.min(evaluacion));
        for(int i = 0 ; i < Optimizador.iteraciones; i++){
            Poblacion padres = sp.seleccionar(poblacion, evaluacion);
            Poblacion hijos = padres.cruzar();
            poblacion = ss.seleccionar(padres, hijos);
            evaluacion = poblacion.evaluarIndividuos();
            mejorFitness.add(Collections.min(evaluacion));
        
            //out
            if( debug !=0){
                System.out.println("------------------------------------------------------------------------------------------");
                System.out.println("Padres: ");
                System.out.println(padres.getPoblacionPorIdentificador());
                System.out.println("------------------------------------------------------------------------------------------");
                System.out.println("Hijos:");
                System.out.println(hijos.getPoblacionPorIdentificador());
                System.out.println("------------------------------------------------------------------------------------------");
                System.out.println("Generacion " + i);
                System.out.println(poblacion.getPoblacionPorIdentificador());
                System.out.println("Funcion de evaluacion: "); 
                System.out.println(poblacion.evaluarIndividuos());
            }
         
        }
        int mejor = poblacion.evaluarIndividuos().indexOf(mejorFitness.get(mejorFitness.size()-1));
        mejorSolucion = poblacion.getIndividuos().get(mejor);
    }

    @Override
    public String getName() {
        return "Optimizador con evaluacion previa a ejecucion";
    }
}
