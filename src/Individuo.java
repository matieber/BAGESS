import java.util.ArrayList;


public class Individuo {
   private ArrayList<Escenario> escenarios;
   
   
   public Individuo(ArrayList<Escenario> escenarios){
    ArrayList<ArrayList<Integer>> variaciones = OptPost.getVariaciones();
    ArrayList<Escenario> esce = new ArrayList<>();
    esce.add(escenarios.get(0));
    for(int i = 0; i < escenarios.size() -1; i++){
        Escenario esc = generarSiguiente(escenarios.get(i+1), escenarios.get(i), variaciones.get(escenarios.get(i).getIdentificador()));
        esce.add(esc);
    }
    this.escenarios = esce;
   }

    public Individuo(Individuo ind){
        this.escenarios = new ArrayList<>(ind.getEscenarios());
    }

    public Individuo() {
        this.escenarios = new ArrayList<>();
    }


    public ArrayList<Escenario> getEscenarios(){
        return new ArrayList<>(escenarios);
    }

    public void autoGenerar() {
        ArrayList<Escenario> escenarios =Optimizador.getEscenarios();
        int cantEscenarios = escenarios.size();
        ArrayList<Integer> numsAleatorios = generarNumerosAleatorios(cantEscenarios); //genera arreglo con numeros aleatorios que indica el orden de los escenarios
        this.escenarios = generarIndividuo(numsAleatorios,escenarios);
    }
    
    
    //---------------------------------------
    
    public void autoGenerarAlternativo() {
    	
        ArrayList<Escenario> escenarios = Optimizador.getEscenarios();
        int cantEscenarios = escenarios.size();
        ArrayList<Integer> numsAleatorios = generarNumerosAleatoriosAlternativo(cantEscenarios, escenarios); //indica orden aleatorio de los escenarios (una copia por escenario)
        this.escenarios = generarIndividuo(numsAleatorios,escenarios);
        
    }
    
    
    //------------------------------------------
    
    public void autoGenerarAlternativoH1() {
    	
        ArrayList<Escenario> escenarios = Optimizador.getEscenarios();
        int cantEscenarios = escenarios.size();
        ArrayList<Integer> numsAleatorios = generarNumerosAleatoriosAlternativoH1(cantEscenarios, escenarios); //indica orden aleatorio de los escenarios (una copia por escenario)
        this.escenarios = generarIndividuo(numsAleatorios,escenarios);
        
    }
    
    
    //-----------------------------------------------
    
    
    /*
    * Genera un nuevo escenario segun el orden indicado 
    */
    private ArrayList<Escenario> generarIndividuo(ArrayList<Integer> numsAleatorios, ArrayList<Escenario> escenarios) {
        ArrayList<Escenario> ind = new ArrayList<>();
        ind.add(escenarios.get(0));
        ArrayList<ArrayList<Integer>> variaciones = OptPost.getVariaciones();
        for(int i = 1; i < numsAleatorios.size(); i++){
            int pos = numsAleatorios.get(i);
            Escenario sig = generarSiguiente(escenarios.get(pos),ind.get(i-1), variaciones.get(ind.get(i-1).getIdentificador()));
            ind.add(sig);
        }
        return new ArrayList<>(ind);
    }

    /*
    * Genera la siguiente configuracion a partir de la anterior
    */
    private Escenario generarSiguiente(Escenario escenarioActual, Escenario escenarioAnterior, ArrayList<Integer> variacion) {
        Escenario nuevo = new Escenario();
        //paso necesario para que las diferentes iteraciones no compartan el puntero de escenarioActual
        for (int i = 0; i < escenarioActual.size(); i++){
            String nombreActual = escenarioActual.getNombre(i);
            Integer a[] = new Integer[2];
            a[0] = new Integer(escenarioActual.get(i)[0]);
            a[1] = new Integer(escenarioActual.get(i)[1]);
            nuevo.add(a,nombreActual);
        }
        for(int i = 0; i < escenarioActual.size(); i++){
            int nuevoValor = escenarioAnterior.get(i)[1] - (escenarioAnterior.get(i)[1] * variacion.get(i) / 100);
            nuevo.get(i)[0] = nuevoValor;
        }
        nuevo.setIdentificador(escenarioActual.getIdentificador());
        return nuevo;
    }

    private ArrayList<Integer> generarNumerosAleatorios(int cantEscenarios) {
        ArrayList<Integer> numeros = new ArrayList<>();
        for(int i = 1; i < cantEscenarios ; i++){
            numeros.add(i);
        }

        ArrayList<Integer> individuo = new ArrayList<>();
        individuo.add(0);
        while(numeros.size() != 0){
            int random =(int) (Math.random() * (numeros.size())); //si hay 4 escenarios, devuelve un numero entre 1 y 3
            individuo.add(numeros.get(random));
            numeros.remove(random);
        }
    
        return individuo;
    }
    
    
    //-----------------------------------------------
    // genera un individuo con una copia de cada escenario
    
    private ArrayList<Integer> generarNumerosAleatoriosAlternativo(int cantEscenarios, ArrayList<Escenario> escenarios) {
    	
        ArrayList<Integer> numeros = new ArrayList<>();
        
        for(int i = 1; i <= Optimizador.numEscenariosPorScheduler ; i++){
        	
        	int escenario = ((i-1) * Optimizador.numSchedulers) + 1;
        	
            numeros.add(escenario);
        }
        
        
        ArrayList<Integer> individuo = new ArrayList<>();
        individuo.add(0);
        while(numeros.size() != 0){
            int random =(int) (Math.random() * (numeros.size())); //si hay 4 escenarios, devuelve un numero entre 1 y 3
            individuo.add(numeros.get(random));
            numeros.remove(random);
        }
    
        return individuo;
    }
    
        
    //-----------------------------------------------------
    
    
    // genera un individuo con una copia de cada escenario en el orden de H1
    
    private ArrayList<Integer> generarNumerosAleatoriosAlternativoH1(int cantEscenarios, ArrayList<Escenario> escenarios) {
    	
        ArrayList<Integer> numeros = new ArrayList<>();
        
        
        for(int i = 1; i <= Optimizador.numEscenariosPorScheduler ; i++){
        	
        	int escenario = ((i-1) * Optimizador.numSchedulers) + 1;
        	
            numeros.add(escenario);
        }
        
        
        ArrayList<Integer> individuo = new ArrayList<>();
        individuo.add(0);
        
        for(int i = 0; i < numeros.size(); i++){
            
            individuo.add(numeros.get(i));
            
        }
    
        return individuo;
    }
    
      
    
    //---------------------------------------------------------

    public void mostrar() {
        for(int i = 0; i < escenarios.size(); i++){
            Escenario duplas = escenarios.get(i); 
            for(int j = 0; j < duplas.size(); j++){
                System.out.println("<" + duplas.get(j)[0] + "," + duplas.get(j)[1] + ">");
            }
            System.out.println();
        }
    }

    public Escenario getEscenario(int i){
        return escenarios.get(i);
    }

    public Double evaluar(Double[][] matEval){
        Double res = 0.0;
        Integer anterior = escenarios.get(0).getIdentificador();    
        for(int i = 0; i < escenarios.size(); i++){
            Integer actual = escenarios.get(i).getIdentificador();
            res += matEval[anterior][actual] != null ? matEval[anterior][actual] : 0.0;
            anterior = actual;
        }
        return res;
    }
    
    public ArrayList<Integer> getIdentificadores(){
        ArrayList<Integer> identificadores = new ArrayList<>();
        for(Escenario esc: escenarios){
            identificadores.add(esc.getIdentificador());
        }
        return identificadores;
    }
    @Override
    public String toString() {
        StringBuffer str= new StringBuffer();
        for(Escenario esc:escenarios){
            str.append(esc);
            str.append("\n");
        
        }
        return str.toString();
    }

    public String printWithId(){
        StringBuffer str= new StringBuffer();
        for(Escenario esc:escenarios){
            str.append("Event " + esc.getIdentificador() + ": Scenario" + "\n");
            str.append(esc);
            str.append("\n");
        
        }
        return str.toString();
 
    }
    
    public String printWithIdSinInitial(){
        StringBuffer str= new StringBuffer();
        for(Escenario esc:escenarios){
        	
        	if(esc.getIdentificador()>0) {
            str.append("Event " + esc.getIdentificador() + ": Scenario" + "\n");
            str.append(esc);
            str.append("\n");}
        
        }
        return str.toString();
 
    }

    public void setEscenarios(ArrayList<Escenario> escenarios) {
        ArrayList<ArrayList<Integer>> variaciones = OptPost.getVariaciones();
        ArrayList<Escenario> esce = new ArrayList<>();
        esce.add(escenarios.get(0));
        for(int i = 0; i < escenarios.size() -1; i++){
            Escenario esc = generarSiguiente(escenarios.get(i+1), escenarios.get(i), variaciones.get(escenarios.get(i).getIdentificador()));
            esce.add(esc);
        }
        this.escenarios = esce;
    }

    public Individuo[] cruzar(Individuo ind2) {
        return Optimizador.cruce.cruzar(this, ind2);
    }

    public Individuo mutar() {
        Optimizador.mutador.mutar(this);
        return this;        
    }
    
  
    //---------------------------------------------------------------------------
    
    
    // Crear H1  - todos los schedulers sobre 1 escenario - el archivo de entrada ser\E1 as\ED - tomo los escenarios como vienen
    
    public void generarH1() {
    	
    	ArrayList<Escenario> escenarios = Optimizador.getEscenarios();
        this.escenarios = generarIndividuoH1(escenarios);
    	
    }
    
    private ArrayList<Escenario> generarIndividuoH1(ArrayList<Escenario> escenarios) {
    	
        ArrayList<Escenario> ind = new ArrayList<>();
        ind.add(escenarios.get(0));
        ArrayList<ArrayList<Integer>> variaciones = OptPost.getVariaciones();
        
        for(int i = 1; i < escenarios.size(); i++){
            Escenario sig = generarSiguiente(escenarios.get(i),ind.get(i-1), variaciones.get(i-1));
            ind.add(sig);
        }
        return new ArrayList<>(ind);
    }
    
    //------------------------------------------------------------------------------
    
    public void generarH2() {
    	
    	ArrayList<Escenario> escenarios = Optimizador.getEscenarios();
        this.escenarios = generarIndividuoH2(escenarios);
    	
    	
    }
    
    //---------------------------------------------------------------------------
    
    
    // Crear H2  - todos los escenarios para un scheduler - el archivo de entrada no ser\E1 as\ED 
    
    
    private ArrayList<Escenario> generarIndividuoH2(ArrayList<Escenario> escenarios) {
    	
    	
    	//System.out.println("N\FAmero de escenarios" + escenarios.size());
    	
    	ArrayList<Escenario> ordenH2 = new ArrayList<>();
        ordenH2.add(escenarios.get(0));
    	
    	ArrayList<Escenario> ind = new ArrayList<>();
        ind.add(escenarios.get(0));
        
        ArrayList<ArrayList<Integer>> variaciones = OptPost.getVariaciones();
        
        //System.out.println("N\FAmero de variaciones " + variaciones.size());
        
        //System.out.println("N\FAmero de escenarios por scheduler  " + Optimizador.numEscenariosPorScheduler);
        
        for(int e = 1; e <= Optimizador.numSchedulers ; e++)
        	for(int s = 1; s <= Optimizador.numEscenariosPorScheduler; s++) {
        		
        		int pos = e + (s-1) * Optimizador.numSchedulers;
        		ordenH2.add(escenarios.get(pos));
        		
        		//System.out.println("pos tomada de lista escenarios " + pos);
        	        		
        	}
        
        //System.out.println("N\FAmero de escenarios en ordenH2" + ordenH2.size());
        
        for(int i = 1; i < escenarios.size(); i++){
            Escenario sig = generarSiguiente(ordenH2.get(i),ind.get(i-1), variaciones.get(ind.get(i-1).getIdentificador()));
            ind.add(sig);
        }
        return new ArrayList<>(ind);
    }
    
    
    
    
    //--------------------------------------------------------------------------------

   // completa la mejor solucion lograda por el algoritmo
   // esta solucion tiene solo una copia de cada escenario (la primer copia de cada escenario, tengo tantas copias como schedulers)
   // tengo que sumar las otras copias de los escenarios, respetando el hecho de mantener las copias pegadas 
    
    public void generarSolucionAE() {
    	
    	ArrayList<Escenario> escenarios = Optimizador.getEscenarios();
        this.escenarios = generarIndividuoAE(escenarios);
    	
    }
    
    private ArrayList<Escenario> generarIndividuoAE(ArrayList<Escenario> escenarios) {
    	
    	ArrayList<Escenario> orden = new ArrayList<>();
        orden.add(escenarios.get(0));
    	
    	ArrayList<Escenario> ind = new ArrayList<>();
        ind.add(escenarios.get(0));
        
        ArrayList<ArrayList<Integer>> variaciones = OptPost.getVariaciones();
        
        
        Individuo ms = Optimizador.mejorSolucion;
        
        ArrayList<Integer> msi = ms.getIdentificadores();
        
        for(int i = 1; i < msi.size(); i++){
        	
        	int j = msi.get(i);
        	orden.add(escenarios.get(j));  // sumo la primer copia de cada escenario
        	
        	//System.out.println("pos tomada de lista escenarios " + j);
        	
        	int tope = j + (Optimizador.numSchedulers - 1);
        	
        	for(int k = j+1; k <= tope ; k++) {  // sumo el resto de las copias de cada escenario 
        		orden.add(escenarios.get(k));
        		
        		//System.out.println("pos tomada de lista escenarios " + k);
        	}
        	
        }
                
        //System.out.println("N\FAmero de escenarios en orden" + orden.size());
        
        for(int i = 1; i < escenarios.size(); i++){
            Escenario sig = generarSiguiente(orden.get(i),ind.get(i-1), variaciones.get(ind.get(i-1).getIdentificador()));
            ind.add(sig);
        }
        
                
        return new ArrayList<>(ind);
    }
    
    //------------------------------------------------------------------------------
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
}
