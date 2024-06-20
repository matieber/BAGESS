import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;

/*Evalua cada escenario a medida que se van generando los individuos (1) */
public class OptPost extends Optimizador{

    public OptPost(String escenariosTxt, String variacionesTxt) {
        super(escenariosTxt, variacionesTxt);
    }

    /*Escribe en la carpeta instancias todos los escenarios de los individuos que aun no fueron evaluados */
    private void writeInstancias(Poblacion poblacion) throws IOException{
        for(Individuo ind: poblacion.getIndividuos()){
            int anterior = 0;
            for(Escenario esc: ind.getEscenarios()){
                if (matEval[anterior][esc.getIdentificador()] == -1.0){
                    Archivo.getInstance().write(esc,anterior,dirInstancias);
                    matEval[anterior][esc.getIdentificador()] = 0.0;
                   
                }
                anterior = esc.getIdentificador();
            }
        }
    }

    private ArrayList<Double> evaluar(Poblacion poblacion) throws IOException {
        writeInstancias(poblacion);
        //Archivo.copyFolder(new File(dirInstancias), new File(dirInstanciasCopy));
        CalculadorEscenarios.ejecutar(dirInstancias);
        //Archivo.copyFolder(new File(dirResultados),new File (dirResultadosCopy));
        CalculadorEscenarios.llenarMatriz(matEval,dirResultados);
        return poblacion.evaluarIndividuos();
    }
    
    private void generarCarpetas(File instanciasCopy, File instanciasResultadosCopy){

        if(!instanciasCopy.exists())
            instanciasCopy.mkdirs();
        if(!instanciasResultadosCopy.exists())
            instanciasResultadosCopy.mkdirs();    
    }

    private void borrarCarpetas(File instanciasCopy, File instanciasResultadosCopy) throws IOException{
        Archivo.copyFolder(new File(dirInstanciasCopy), new File(dirInstancias));
        Archivo.copyFolder(new File(dirResultadosCopy), new File(dirResultados));
        cleanDirectories(dirInstanciasCopy, dirResultadosCopy);
        instanciasCopy.delete();
        instanciasResultadosCopy.delete();
    }

    public void run() throws IOException, InterruptedException{  
    	
    	System.out.println("Running EA ...");
        
    	cleanDirectories(dirInstancias, dirResultados);
        
        Poblacion poblacion = new Poblacion(poblacionInicial);
        
        poblacion.autoGenerarAlternativo();
        
        if (debug != 0){
            System.out.println("Poblacion inicial: ");
            System.out.println(poblacion.getPoblacionPorIdentificador());
            //System.out.println("Funcion de evaluacion: ");
            //System.out.println(poblacion.evaluarIndividuos());
            //System.out.println(evaluar(poblacion));
        }
        
        //Inicio del algoritmo while()...
        ArrayList<Double> evaluacion = evaluar(poblacion);
        cleanDirectories(dirInstancias, dirResultados);
        mejorFitness.add(Collections.min(evaluacion));
        
        for(int i = 0 ; i < Optimizador.iteraciones ; i++){
            
        	Poblacion padres = sp.seleccionar(poblacion, evaluacion);
            Poblacion hijos = padres.cruzar();
            evaluar(hijos);
            cleanDirectories(dirInstancias, dirResultados);
            
            Poblacion copia = new Poblacion(poblacion.getIndividuos());
            
            poblacion = ss.seleccionar(copia, hijos);
            evaluacion = poblacion.evaluarIndividuos();
            mejorFitness.add(Collections.min(evaluacion));

            // out
            if( debug != 0){
                System.out.println("------------------------------------------------------------------------------------------");
                System.out.println("Padres: ");
                System.out.println(padres.getPoblacionPorIdentificador());
                //System.out.println(padres.evaluarIndividuos()); 
                System.out.println("------------------------------------------------------------------------------------------");
                System.out.println("Hijos:");
                System.out.println(hijos.getPoblacionPorIdentificador());
                //System.out.println(hijos.evaluarIndividuos());
                System.out.println("------------------------------------------------------------------------------------------");
                System.out.println("Generacion " + i);
                System.out.println(poblacion.getPoblacionPorIdentificador());
                //System.out.println("Funcion de evaluacion: "); 
                //System.out.println(poblacion.evaluarIndividuos());
 
            }
            
        }
        
        int mejor = poblacion.evaluarIndividuos().indexOf(mejorFitness.get(mejorFitness.size()-1));
        mejorSolucion = poblacion.getIndividuos().get(mejor);
        
        long numero1=System.currentTimeMillis();
        
        long tiempoAEStart = System.currentTimeMillis();
        Individuo individuoAE = new Individuo();
        individuoAE.generarSolucionAE();
        solucionAE = individuoAE;
        long tiempoAEFin = System.currentTimeMillis() ;
        tiempoAE = tiempoAEFin - tiempoAEStart;
        //System.out.println("tiempoAEFin " + System.currentTimeMillis() + "\n");
        
        //System.out.println("tiempoH1Start " + System.currentTimeMillis() + "\n");
        long tiempoH1Start = System.currentTimeMillis();
        Individuo individuoH1 = new Individuo();
        individuoH1.generarH1();
        solucionH1 = individuoH1;
        long tiempoH1Fin = System.currentTimeMillis() ;
        tiempoH1 = tiempoH1Fin - tiempoH1Start;
        //System.out.println("tiempoH1Fin " + System.currentTimeMillis() + "\n");
        
        long tiempoH2Start = System.currentTimeMillis();
        //System.out.println("tiempoH2Start " + System.currentTimeMillis() + "\n");
        Individuo individuoH2 = new Individuo();
        individuoH2.generarH2();
        solucionH2 = individuoH2;
        long tiempoH2Fin = System.currentTimeMillis() ;
        //System.out.println("tiempoH2Fin " + System.currentTimeMillis() + "\n");
        tiempoH2 = tiempoH2Fin - tiempoH2Start;
        
        Poblacion poblacionH = new Poblacion(3);
        poblacionH.addIndividuo(individuoAE);
        poblacionH.addIndividuo(individuoH1);
        poblacionH.addIndividuo(individuoH2);
        
       // System.out.println("Escenarios adicionales de H1 H2");
        
        numEscenarios = numEscenarios + "\nEscenarios adicionales de H1 y H2 \n";
                
        evaluar(poblacionH);
        
        cleanDirectories(dirInstancias, dirResultados);
        
        long numero2=System.currentTimeMillis();
        
        tiempoH1H2 = numero2-numero1;
        
              
        numEscenarios = numEscenarios + "\nTiempo adicional para crear y evaluar H1 y H2 : " + tiempoH1H2 + " ms \n";
        numEscenarios = numEscenarios + "Tiempo adicional para crear y evaluar H1 y H2 : " + tiempoH1H2/1000 + " s \n";
        numEscenarios = numEscenarios + "Tiempo adicional para crear y evaluar H1 y H2 : " + (tiempoH1H2/1000)/60 + " m \n";
        
       
    }

    @Override
    public String getName() {
        //return "Optimizador con evaluacion a medida que se generan los individuos";
    	return "Optimizador 1: algoritmo que evalua los escenarios durante el proceso de buqueda";
    }
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
}
