import java.io.*;

import java.util.ArrayList;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.KeyStore.Entry;




public class Archivo {
	private static Archivo archivo;
	private Archivo() {};
	
	public static Archivo getInstance() {
		if ( archivo== null)
			archivo= new Archivo();
		return archivo;
	}   
	
	/**
	 * Genera una lista de escenarios que se leen desde un txt
	 * @param archivo Directorio absoluto que indica el archivo txt a leer
	 * @return Lista de escenarios
	 */
	public ArrayList<Escenario> readEscenarios(String archivo){
		ArrayList<Escenario> datos = new ArrayList<>();
		
		try {
			FileReader fr = new FileReader(archivo);  
			BufferedReader entry= new BufferedReader(fr);
			String s = new String(); //obtengo entrada
            Escenario list = new Escenario();
			int cont = 0;
			while ( (s = entry.readLine()) != null) {
                String space[] = s.split(" ");
                if (space.length == 4){
                    Integer data[] = new Integer[3];
                    data[0] = Integer.parseInt(space[2]);
                    data[1] = Integer.parseInt(space[3]);
                    list.add(data,space[1]);
                }
                else{
					list.setIdentificador(cont);
					cont ++;
                    datos.add(list);
                    list = new Escenario();
                }
			}
			list.setIdentificador(cont);
			cont ++;
            datos.add(list);
			entry.close();
			
		}catch (Exception e) {
			System.out.println("Archive do not find");
		}
		
		return datos;
	}   
	

	/**
	 * Genera una lista con la informacion de las variaciones de cada escenario extraido de un txt 
	 * @param archivo Directorio absoluto que indica el archivo txt a leer
	 * @return Lista de las variaciones
	 */
	public ArrayList<ArrayList<Integer>> readVariaciones(String archivo){
		ArrayList<ArrayList<Integer>> datos = new ArrayList<>();

		try {
			FileReader fr = new FileReader(archivo);  
			BufferedReader entry= new BufferedReader(fr);
			String s = new String(); //obtengo entrada
            ArrayList<Integer> list = new ArrayList<>();

			while ( (s = entry.readLine()) != null) {
                String space[] = s.split(" ");
                if (space.length == 2){
                    Integer data; 
                    data = Integer.parseInt(space[1]);
                    list.add(data);
                }
                else{
                    datos.add(list);
                    list = new ArrayList<>();
                }
			}
            datos.add(list);
			entry.close();
			
		}catch (Exception e) {
			System.out.println("Archive do not find");
		}
		
		return datos;
	}  

	/**
	 * Lee la configuracion del algoritmo que se va a ejecutar
	 * @param archivo Directorio absoluto del archivo txt a leer
	 * @return Lista de strings que indica cada atributo leido
	 */
	public ArrayList<String> readEntrada(String archivo){
		ArrayList<String> instancia = new ArrayList<>();
		try{
			FileReader fr = new FileReader(archivo);  
			BufferedReader entry= new BufferedReader(fr);
			String s = new String(); //obtengo entrada
            Escenario list = new Escenario();
			while ( (s = entry.readLine()) != null) {
                String space[] = s.split(":");
                if (space.length == 2){
                    String data; 
                    data = space[1];
                    instancia.add(data);
                }
			}
		}catch (Exception e) {
				System.out.println("Archive do not find");
			}
		
		return instancia;
	}


	/**
	 * Lee el archivo preparationTime y extrae el tiempo
	 * @param archivo Directorio absoluto que contiene el archivo txt a leer
	 * @return Tiempo que indica el archivo
	 */
	public static Double readPreparationTime(String archivo){
		String s[] = new String[5];
		try{
			FileReader fr = new FileReader(archivo);
			BufferedReader entry = new BufferedReader(fr);
			s = entry.readLine().split(" ");
			entry.close();
		}catch (Exception e){
			System.out.println("Error al leer el archivo" + " " + archivo);
		}
		
		return Double.parseDouble(s[s.length -1]); 
	}

	/**
	 * Escribe dentro de la carpeta instancias los archivos de cada instancia generada
	 * @param poblacion
	 * @throws IOException
	 */
	public void write(Poblacion poblacion) throws IOException{
		File salida = new File("instancias");
		if(!salida.exists()){
			salida.mkdirs();
		}
		int cont = 0;
		for(Individuo ind:poblacion.getIndividuos()){
			int numInd = 0;
			for(Escenario esc: ind.getEscenarios()){
				File file = new File(salida,"moviles_" + cont + "_" + numInd + ".txt");
				FileWriter writer = null;
				try{
					writer = new FileWriter(file,false);
					BufferedWriter bufferedWriter = new BufferedWriter(writer);
				bufferedWriter.append(esc.toString());
				bufferedWriter.close();
				}finally {
					if (writer != null) writer.close();
				}
			numInd ++;
			}
			cont++;
		}

	}

	/**
	 * Lista los archivos que contiene un directorio
	 * @param dir Directorio absoluto que se quiere leer
	 * @return Lista con los nombres de cada archivo
	 */
	public ArrayList<String> listFiles(String dir){
		ArrayList<String> out = new ArrayList<>();
		File folder = new File(dir);
		// if(!folder.exists()){
		// 	folder.mkdirs();
		// }
		for (final File fileEntry : folder.listFiles()) {
			out.add(fileEntry.getName());
		}
		return out;
	}

	/**
	 * Elimina todos los archivos que se encuentran en el directorio indicado
	 * @param dir Directorio absoluto que se quiere borrar
	 */
	public void cleanDirectory(String dir){
		File folder = new File(dir);
		for(File file:folder.listFiles()){
			file.delete();
		}
	}

	public void write(Escenario esc, int anterior,String instancias) throws IOException {
		File salida = new File(instancias);
		if(!salida.exists()){
			salida.mkdirs();
		}
		File file = new File(salida,"moviles_" + anterior + "_" + esc.getIdentificador() + ".txt");
		FileWriter writer = null;
		try{
			writer = new FileWriter(file,false);
			BufferedWriter bufferedWriter = new BufferedWriter(writer);
			String escenario[] = esc.toString().split(":");
			String out = esc.toString();
			bufferedWriter.append(out);
			bufferedWriter.close();
		}finally {
			if (writer != null) writer.close();
		}
}

	public void write(Escenario escenario, int i, int j) throws IOException{
		File salida = new File("instancias");
		if(!salida.exists()){
			salida.mkdirs();
		}
		File file = new File(salida,"moviles_" + i + "_" + j + ".txt");
		FileWriter writer = null;
		try{
			writer = new FileWriter(file,false);
			BufferedWriter bufferedWriter = new BufferedWriter(writer);
			String esce[] = escenario.toString().split(":");
			String out = escenario.toString();
			bufferedWriter.append(out);
			bufferedWriter.close();
		}finally {
			if (writer != null) writer.close();
		}
	}

	/**
	 * Escribe en un archivo la salida del algoritmo
	 * @param codigo
	 * @throws IOException
	 */
	public static void write(StringBuilder codigo,String out) throws IOException {
		File file = new File(out);
		FileWriter writer = null;
		try {
			writer = new FileWriter(file,false);
			BufferedWriter bufferedWriter = new BufferedWriter(writer);
			bufferedWriter.append(codigo + "\n");
			bufferedWriter.close();
		} finally {
			if (writer != null) writer.close();
		}	
	}

	public static void copyFolder(File source, File destination) throws IOException {

		 if (source.isDirectory())
		{
			String files[] = source.list();

			for (String file : files)
			{
				File srcFile = new File(source, file);
				File destFile = new File(destination, file);
				

				copyFolder(srcFile, destFile);
			}
		} else
    {
        InputStream in = null;
        OutputStream out = null;

        try
        {
            in = new FileInputStream(source);
            out = new FileOutputStream(destination);

            byte[] buffer = new byte[1024];

            int length;
            while ((length = in.read(buffer)) > 0)
            {
                out.write(buffer, 0, length);
            }
        }
        catch (Exception e)
        {
            try
            {
                in.close();
            }
            catch (IOException e1)
            {
                e1.printStackTrace();
            }

            try
            {
                out.close();
            }
            catch (IOException e1)
            {
                e1.printStackTrace();
            }
        }
    }
	}
}
    

