public abstract class Cruce {
    protected static Double probCruce;

    public Cruce(Double probCruce) {
        this.probCruce = probCruce;
    }

    /**
     * Cruza dos individuos
     * @param ind1 Individuo 1
     * @param ind2 INdividuo 2
     * @return Arreglo con los dos individuos generados en el cruce
     */
    public abstract Individuo[] cruzar(Individuo ind1, Individuo ind2);

    public abstract String getName();
    
}
