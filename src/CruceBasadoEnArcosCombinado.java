import java.util.ArrayList;
import java.util.Collections;

public class CruceBasadoEnArcosCombinado extends CruceBasadoEnArcos {

  
    
    public CruceBasadoEnArcosCombinado(Double probCruce) {
        super(probCruce);
    }


    @Override
    public Individuo[] cruzar(Individuo ind1, Individuo ind2) {
        Individuo sol[] = new Individuo[2];
        Double prob = Math.random();
        if(prob > this.probCruce){
            sol[0] = new Individuo(ind1);
            sol[1] = new Individuo(ind2);
        }    
        else{
            ArrayList<ArrayList<Integer>> listaAdyacencia = generarListaAdyacencia(ind1,ind2);
            sol[0] = cruce(ind1,ind2,listaAdyacencia);
            Cruce cruceComb = new CruceDeOrden(this.probCruce);
            sol[1] = cruceComb.cruzar(ind1, ind2)[0];
        }
        return sol;
    }

    @Override
    public String getName() {
        return "Cruce basado en arcos combinado";
    }

   
}
