import java.util.ArrayList;
import java.util.Collections;

public class SteadyState implements SeleccionadorSobrevivientes {
   
    /**
     * Elimina los peores individuos segun la cantidad dada
     * @param pob poblacion de individuos
     * @param cantEliminados cantidad de individuos a eliminar
     * @return lista de individuos no eliminados
     */
    private ArrayList<Individuo> eliminarPeores(Poblacion pob, int cantEliminados) {
        ArrayList<Double> fitness = pob.evaluarIndividuos();
        ArrayList<Individuo> individuos = pob.getIndividuos();

        for (int i = 0; i < cantEliminados; i++){
            double max = Collections.max(fitness);
            int index = fitness.indexOf(max);
            fitness.remove(index);
            individuos.remove(index);
        }

        return individuos;
    }
   @Override
    public Poblacion seleccionar(Poblacion padres, Poblacion hijos) {
        int cantEliminadosPadres = (int) (padres.getIndividuos().size() * Optimizador.porcentajeDeRemplazo);
        int cantEliminadosHijos = hijos.getIndividuos().size() - cantEliminadosPadres;
        ArrayList<Individuo> padresSobrevivientes = eliminarPeores(padres,cantEliminadosPadres);
        ArrayList<Individuo> hijosSobrevivientes = eliminarPeores(hijos,cantEliminadosHijos);
        ArrayList<Individuo> nuevaPoblacion = new ArrayList<>(padresSobrevivientes);
        nuevaPoblacion.addAll(hijosSobrevivientes);

        return new Poblacion(nuevaPoblacion);
    }

}
