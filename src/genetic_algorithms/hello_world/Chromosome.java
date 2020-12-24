package genetic_algorithms.hello_world;

/*
 * Chromosome class represents a possible solution.
 * The survival chance of this particular mutation is determined by its fitness value.
 *
 */
public class Chromosome {

    private int[] genes;

    private int fitness = 0;

    public Chromosome(int length) {
        this.genes = new int[length];
    }

    public Chromosome initializeChromosome() {
        for (int i = 0; i < genes.length; i++) {
            if (Math.random() >= 0.5) {
                genes[i] = 1;
            } else {
                genes[i] = 0;
            }
        }
        calculateFitness();
        return this;
    }

    public void calculateFitness() {
        int fi = 0;
        for (int i = 0; i < this.genes.length; i++) {
            if (genes[i] == Main.TARGET[i]) {
                fi++;
            }
        }
        this.fitness= fi;
    }

    public int[] getGenes() {
        return genes;
    }

    public int getFitness() {
        return fitness;
    }


}
