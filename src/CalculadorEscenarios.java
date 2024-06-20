import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class CalculadorEscenarios {
    public CalculadorEscenarios(){}

    /*Calcula toda los posibles escenarios tomando de a pares */
    public static Double[][] calcularEscenarios(String instancias, String instanciasResultados){
        ArrayList<Escenario> escenarios = Archivo.getInstance().readEscenarios(Main.escenariostxt);
        int size = escenarios.size();
        Double esc[][] = new Double[size][size];
        try {
            generarEscenarios(escenarios);
        } catch (IOException e) {
            e.printStackTrace();
        }
        ejecutar(instancias);
        llenarMatriz(esc,instanciasResultados);
        return esc;
    }

    private static void mostrarMatriz(Double[][] esc) {
        System.out.println("Matriz de evaluacion");
        for(int i = 0; i < esc.length; i++){
            for(int j = 0; j < esc.length; j++){
                System.out.print(esc[i][j] + " ");
            }
            System.out.println();
        }
    }

    /*Ejecuta en paralelo(siempre que haya mas de un core) el calculo de cada escenario */
    public static void ejecutar(String instancias) {
    	
    	Optimizador.cantEscenarios=0;
    	
        ArrayList<String> files = Archivo.getInstance().listFiles(instancias);
        // int cantArchivos = 4*4; //size
        List<Integer> lista = IntStream.range(0,files.size()).boxed().collect(Collectors.toList());
        lista.parallelStream().forEach(number -> {
            try {
                //System.out.println(Thread.currentThread().getName());
            	//System.out.println(files.get(number));
            	Optimizador.cantEscenarios++;
            	
            	Runtime.getRuntime().exec("java -jar ./preparationTime.jar " + files.get(number)).waitFor();
            	
            } catch (InterruptedException | IOException e) {
                e.printStackTrace();
            }
            
            
 
        });
        
        if(Optimizador.cantEscenarios>=0)
        	Optimizador.numEscenarios = Optimizador.numEscenarios + Optimizador.cantEscenarios + "\n";
        
    }

    private static void generarEscenario(int i, int j, ArrayList<Escenario> escenarios, Escenario anterior,ArrayList<Integer> variacion) throws IOException{
        Escenario nuevo;
        Escenario actual = escenarios.get(j);
        ArrayList<Integer[]> programas = actual.getEscenario();
        nuevo = new Escenario();
        for(int k = 0; k < programas.size(); k++){
            String nombreActual = actual.getNombre(k);
            Integer a[] = new Integer[2];
            a[0] = anterior.get(k)[1] - (anterior.get(k)[1] * variacion.get(k) / 100);
            a[1] = new Integer(actual.get(k)[1]);
            nuevo.add(a, nombreActual);
        }
        Archivo.getInstance().write(nuevo,i,j);

    }

    private static void generarEscenarioCero(ArrayList<Escenario> escenarios) throws IOException{
        Escenario nuevo = new Escenario();
        Escenario actual = escenarios.get(0);
        ArrayList<Integer[]> programas = actual.getEscenario();
        for(int k = 0; k < programas.size(); k++){
            String nombreActual = actual.getNombre(k);
            Integer a[] = new Integer[2];
            a[0] = new Integer(actual.get(k)[0]);
            a[1] = new Integer(actual.get(k)[1]);
            nuevo.add(a, nombreActual);
        }
        Archivo.getInstance().write(nuevo,0,0);
    }
    
    /*Creaa todos los archivos txt de cada posible escenario a evaluar. i = anteriro; j = actual */
    private static void generarEscenarios(ArrayList<Escenario> escenarios) throws IOException {
        ArrayList<ArrayList<Integer>> variaciones = Archivo.getInstance().readVariaciones(Main.variacionestxt);
        generarEscenarioCero(escenarios);
        for(int i = 0; i < escenarios.size(); i++){
            Escenario anterior = escenarios.get(i);
            ArrayList<Integer> variacion = variaciones.get(i);
            for(int j = 1; j < escenarios.size(); j++){
                if (i != j){
                    generarEscenario(i, j, escenarios, anterior, variacion);
                }

            }

        }
    }

    /*Completa la matriz con los calculos obtenidos */
    public static void llenarMatriz(Double[][] matEval, String instanciasResultados) {
        ArrayList<String> files = Archivo.getInstance().listFiles(instanciasResultados);
        for(String file:files){
            String[] name = file.split("_");
            if(name[0].equals("preparationTime")){
                int fila = Integer.parseInt(name[2]);
                int columna = Integer.parseInt(name[3].split("\\.")[0]);
                Double valor = Archivo.getInstance().readPreparationTime(instanciasResultados + "/" + file);
                matEval[fila][columna] = valor;
                
                //System.out.println("Se cargo en matriz " + fila + " " + columna);
                
                
                
            }
        }
    
    }
}
