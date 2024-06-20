import java.io.IOException;
import java.util.ArrayList;



public class Main {
	
   public static String escenariostxt;
   public static String variacionestxt;
   public static String escenariostxtArchivo;
   public static String variacionestxtArchivo;
   
   public static Double tiempoSolAE;
   public static Double tiempoSolH1;
   public static Double tiempoSolH2;
    
   
   private static Optimizador configurarOptimizador(ArrayList<String> inst) {    

      return new OptPost(escenariostxt,variacionestxt);
      
   }
  
   
   public static void main(String[] args) throws IOException, InterruptedException {
	   
	  ArrayList<String> inst = Archivo.getInstance().readEntrada("./parameterSettingEA.txt");
     
      if(args.length == 2){         
         escenariostxtArchivo = args[0];
         variacionestxtArchivo = args[1];                  
      }
      else{         
    	 escenariostxtArchivo = "ScePerEvent_G2_R3_M3_LD_I0.txt";
         variacionestxtArchivo = "StrPerEvent_G2_R3_M3_LD_I0.txt";
      }	 
      
      escenariostxt = "./instances/" + escenariostxtArchivo;
      variacionestxt = "./instances/" + variacionestxtArchivo;
        
      
      System.out.println("Start: run of the EA on " + escenariostxt);
      
      Optimizador opt = configurarOptimizador(inst);
      
      long numero1=System.currentTimeMillis();
      opt.run();
      long numero2=System.currentTimeMillis();
      double tiempo = numero2-numero1;
            
      tiempo = tiempo - Optimizador.tiempoH1H2;
      
      generarSalidaMejorSolucion();
      
      System.out.println("End: Time required to run the EA on " + escenariostxt + " :  " + (tiempo/1000)/60 + " minutes");
   }
       
   
   private static void generarSalidaMejorSolucion() throws IOException {
      
	  StringBuilder out = new StringBuilder();
      Individuo ms = Optimizador.solucionAE;
      
      out.append("Sequential order indicated by the EA to develop the events  \n\n");
      
      out.append(ms.printWithIdSinInitial());
      
      ArrayList<Integer> msi = ms.getIdentificadores();
      
      out.append("Estimated time to develop each event (to prepare the scenario of each event)  \n\n");
      
      Double time = (Optimizador.matEval)[0][0];
      Double timeTotal = 0.0;
      
      for(int i = 0; i < msi.size() -1; i++){
         time = (Optimizador.matEval)[msi.get(i)][msi.get(i+1)];
    	 out.append("Event " + msi.get(i + 1) + " : " + (time/1000)/60 + " minutes\n");
    	 timeTotal = timeTotal + time;

      }
      
      out.append("\n");
      
      out.append("Estimated total time to develop all the events : " + (timeTotal/1000)/60 + " minutes\n");
      
      Archivo.write(out,"./results/solution_EA_" + escenariostxtArchivo);
      
      tiempoSolAE = timeTotal;

   }

      
   
  
}
