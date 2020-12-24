package genetic_algorithms.hello_world;

import java.util.Arrays;

public class Main {

    public static final int POPULATION_SIZE = 10;
    public static final int[] TARGET = {1, 0, 1, 0, 1, 0, 1, 0, 1, 0,1,0,1,0,1,0};
    private static final int NUMBER_OF_TOP_TO_SURVIVE = 2;
    private static final int TOURNAMENT_SELECTION_SIZE = 4;
    private static final double MUTATION_RATE = 0.125;

    public Population updatePopulation(Population population) {
        return doMutate(doCrossover(population));
    }

    private Population doCrossover(Population population) {
        Population crossoverPopulation = new Population(population.getChromosomes().length);
        for (int i = 0; i < population.getChromosomes().length; i++) {
            if (i < NUMBER_OF_TOP_TO_SURVIVE) {
                //Survives
                crossoverPopulation.getChromosomes()[i] = population.getChromosomes()[i];
            } else {
                //Gets Replaced
                Chromosome father = selectTournamentPopulation(population).getChromosomes()[0];
                Chromosome mother = selectTournamentPopulation(population).getChromosomes()[0];
                crossoverPopulation.getChromosomes()[i] = crossoverChromosome(father, mother);
            }
        }
        return crossoverPopulation;
    }

    private Population doMutate(Population population) {
        Population mutatePopulation = new Population(population.getChromosomes().length);
        for (int i = 0; i < population.getChromosomes().length; i++) {
            if (i < NUMBER_OF_TOP_TO_SURVIVE) {
                mutatePopulation.getChromosomes()[i] = population.getChromosomes()[i];
            } else {
                mutatePopulation.getChromosomes()[i] = mutateChromosome(population.getChromosomes()[i]);
            }
        }
        return mutatePopulation;
    }

    private Chromosome crossoverChromosome(Chromosome father, Chromosome mother) {
        Chromosome child = new Chromosome(TARGET.length);
        for (int i = 0; i < father.getGenes().length; i++) {
            if (Math.random() < 0.5) {
                child.getGenes()[i] = father.getGenes()[i];
            } else {
                child.getGenes()[i] = mother.getGenes()[i];
            }
        }
        return child;
    }

    private Population selectTournamentPopulation(Population population) {
        Population ret = new Population(TOURNAMENT_SELECTION_SIZE);
        for (int i = 0; i < TOURNAMENT_SELECTION_SIZE; i++) {
            ret.getChromosomes()[i] =
                    population.getChromosomes()[(int) (Math.random() * population.getChromosomes().length)];
        }
        ret.sortChromosomesByFitness();
        return ret;
    }

    private Chromosome mutateChromosome(Chromosome chrom) {
        Chromosome ret = new Chromosome(TARGET.length);
        for (int i = 0; i < chrom.getGenes().length; i++) {
            if (Math.random() < MUTATION_RATE) {
                if (Math.random() < 0.5) {
                    ret.getGenes()[i] = 1;
                } else {
                    ret.getGenes()[i] = 0;
                }
            } else {
                ret.getGenes()[i] = chrom.getGenes()[i];
            }
        }

        return ret;
    }

    public static void main(String[] args) {
        Main main = new Main();
        Population population = new Population(POPULATION_SIZE).initializePopulation();

        System.out.println("----------------------------");
        System.out.println("Generation # 0"+" | Fittest chromosome Fitness: "
                + population.getChromosomes()[0].getFitness());
        printPopulation(population, "TARGET CHROMOSOME: "+ Arrays.toString(TARGET));

        int generationNumber = 0;

        while(population.getChromosomes()[0].getFitness() < TARGET.length){
            generationNumber++;
            System.out.println("\n----------------------------");
            population = main.updatePopulation(population);

            population.sortChromosomesByFitness();

            System.out.println("Generation # "+generationNumber+" | Best Fitness: "
                    + population.getChromosomes()[0].getFitness());
            printPopulation(population, "TARGET: "+ Arrays.toString(TARGET));
        }
    }

    public static void printPopulation(Population pop, String title){
        System.out.println(title);
        System.out.println("---------------------------");
        for(int i = 0; i < pop.getChromosomes().length; i++){
            System.out.println("Chromosome # "+ i +" : "+ Arrays.toString(pop.getChromosomes()[i].getGenes())
                    +" | Fitness: "+ pop.getChromosomes()[i].getFitness());
        }
    }
}
