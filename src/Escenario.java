import java.io.BufferedReader;
import java.util.ArrayList;

public class Escenario{
    private ArrayList<Integer[]> esc;
    private int size;
    private ArrayList<String> nombres;
    private Integer identificador;
    
    public Escenario(){
        esc = new ArrayList<>();
        nombres = new ArrayList<>();
        size = 0;
        identificador = 0;
    }

    public Escenario(ArrayList<Integer[]> esc, ArrayList<String> nombres){
        this.esc = esc;
        size = esc.size();
        this.nombres = nombres;
        identificador = 0;
    }

    public ArrayList<Integer[]> getEscenario(){
        ArrayList<Integer[]> copiaArray = new ArrayList<>();
        for(Integer[] in:esc){
            Integer[] copia = new Integer[2];
            copia[0] = new Integer(in[0]);
            copia[1] = new Integer(in[1]);
            copiaArray.add(copia);
        }
        return copiaArray;
    }

    public void add(Integer[] nuevo,String nombre){
        esc.add(nuevo);
        nombres.add(nombre);
        size++;
    }

    public int size(){
        return size;
    }

    public Integer[] get(int i){
        if(i < size)
            return esc.get(i);
        else
            return null;
    }

    public String getNombre(int i) {
        return new String(nombres.get(i));
    }
    
    public void setIdentificador(int identificador){
        this.identificador = identificador;
    }

    @Override
    public String toString() {
        StringBuffer str= new StringBuffer();
        int cont = 0;
        // str.append("Identificador: " + identificador + "\n");
         for(Integer[] i:esc){
                str.append(cont+1 + " " + nombres.get(cont) + " " + i[0] + " " + i[1] + " " + "\n");
                cont++;
            }
        return str.toString();
    }

    public int getIdentificador() {
        return this.identificador;
    }
    
    
    public boolean igual(Escenario e) {
    	
    	boolean iguales = true;
    	
    	for(int i = 0; (i < esc.size()) && iguales; i++ ) {
    		
    	  if( (esc.get(i))[1] != (e.get(i))[1] )	  		  
    		  
    		  iguales = false;
    		  
    		    		
    	}
    	
    	
    	return iguales;
    	
    }
    
    
    
    
}