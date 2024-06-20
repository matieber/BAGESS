import java.io.IOException;
import java.util.ArrayList;

public abstract class Optimizador {
    protected static ArrayList<Escenario> escenarios;
    protected static ArrayList<ArrayList<Integer>> variaciones;
    protected static int poblacionInicial; 
    protected static Double[][] matEval;
    protected SeleccionadorPadres sp;
    protected static Cruce cruce;
    protected static Mutador mutador;
    protected static Double probMutacion;
    protected static Double probCruce;
    protected static Double porcentajeDeRemplazo;
    protected static int iteraciones;
    protected static int indsPorTorneo;
    protected static SeleccionadorSobrevivientes ss = new SteadyState();
    protected static ArrayList<Double> mejorFitness = new ArrayList<>();
    protected static Individuo mejorSolucion;
    protected static String dirInstancias = "./instances";
    protected static String dirResultados = "./results";
    protected static String dirInstanciasCopy = "./instancesCopy";
    protected static String dirResultadosCopy = "./resultsCopy";
    protected static Integer debug;
    
    public static int numSchedulers;
    public static int numEscenariosPorScheduler;
    
    protected static Individuo solucionH1;
    protected static Individuo solucionH2;
    protected static Individuo solucionAE;
    protected static double tiempoH1H2;
    
    protected static double tiempoAE;
    protected static double tiempoH1;
    protected static double tiempoH2;
    
    protected static String numEscenarios = "Numero de escenarios evaluados durante el optimizacion \n\n";
    protected static int cantEscenarios ;


   private static Mutador configurarMutador(ArrayList<String> inst) {
	   
	   return new MutadorPorInversion(probMutacion);
	   
   }

   private static Cruce configurarCruce(ArrayList<String> inst) {
	   
	   return new CruceDeOrden(probCruce);
	   
   }
   
      
   private static int numSchedulers(String archivoEscenarios) {
	   
	   int numSchedulers = 0;
	   
	   if(archivoEscenarios.contains("G2")) 
		   numSchedulers = 2;
	   else
	   if(archivoEscenarios.contains("G3")) 
		   numSchedulers = 3;
	   else
	   if(archivoEscenarios.contains("G4")) 
		   numSchedulers = 4;
	   else
	   if(archivoEscenarios.contains("G6")) 
		   numSchedulers = 6;	
	   
	   return numSchedulers;
			   
   }
   
   
   private static int numEscenariosPorScheduler(String archivoEscenarios) {
	   	   
       int numEscenarios = 0;
       
       
	   if(archivoEscenarios.contains("R3")) 
		   numEscenarios = 3;
	   else  
	   if(archivoEscenarios.contains("R4")) 
		   numEscenarios = 4;
	   else
	   if(archivoEscenarios.contains("R10")) 
		   numEscenarios = 10;
	   else
	   if(archivoEscenarios.contains("R15")) 
		   numEscenarios = 15;
	   else
	   if(archivoEscenarios.contains("R20")) 
		   numEscenarios = 20;
	   else
	   if(archivoEscenarios.contains("R2")) 
		   numEscenarios = 2;
		  	   
	   return numEscenarios;
	   
   }
   
   
   public Optimizador(String escenariosTxt, String variacionesTxt){
        ArrayList<String> instancia = Archivo.getInstance().readEntrada("./parameterSettingEA.txt");
        escenarios = Archivo.getInstance().readEscenarios(escenariosTxt);
        variaciones = Archivo.getInstance().readVariaciones(variacionesTxt);
        Optimizador.probMutacion = Double.parseDouble(instancia.get(3));
        Optimizador.probCruce = Double.parseDouble(instancia.get(2));
        Optimizador.poblacionInicial = Integer.parseInt(instancia.get(0));
        Optimizador.cruce = configurarCruce(instancia);
        Optimizador.mutador = configurarMutador(instancia);
        Optimizador.debug = Integer.parseInt("0");
        this.sp = new SeleccionTorneo();
        porcentajeDeRemplazo = Double.parseDouble(instancia.get(4));
        iteraciones = Integer.parseInt(instancia.get(5));
        indsPorTorneo = Integer.parseInt(instancia.get(1));

        int size = escenarios.size();
        matEval = new Double[size][size];
        initMat();
        
        numSchedulers = numSchedulers(escenariosTxt);
        numEscenariosPorScheduler = numEscenariosPorScheduler(escenariosTxt);
        
   }

    private void initMat() {
        int size = escenarios.size();
        for(int i = 0; i < size; i++)
            for(int j = 0; j < size; j++)
                matEval[i][j] = -1.0;
    }


    public static ArrayList<Escenario> getEscenarios() {
        return escenarios;
    }

    public static ArrayList<ArrayList<Integer>> getVariaciones() {
        return variaciones;
    }

    public int getPoblacionInicial(){
        return poblacionInicial;
    }
     
    public abstract void run() throws IOException, InterruptedException;
    

    protected ArrayList<Double> evaluarIndividuos(Poblacion poblacion) {
        ArrayList<Double> evaluacion = new ArrayList<>();
        for(Individuo ind: poblacion.getIndividuos()){
            Double valor = ind.evaluar(matEval);
            evaluacion.add(valor);
        }
        return evaluacion;
    }
     
   protected void cleanDirectories(String instancias, String instanciasResultados) {
        Archivo.getInstance().cleanDirectory(instancias);
        Archivo.getInstance().cleanDirectory(instanciasResultados);
    }

   
   public  abstract String getName();
    
        
    
    
    
    
    
    
    
    

}
