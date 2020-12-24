package genetic_algorithms.hamlet;

import java.util.Arrays;
import java.util.Comparator;
import java.util.Random;

/*
 * Evolving towards Hamlet's famous line from random Strings.
 * */
public class Hamlet {

    private static final Random RANDOM = new Random();
    private static final String TARGET = "to be or not to be that is the question";
    private static final int TARGET_LENGTH = Hamlet.TARGET.length();
    private static final char[] ALPHABET = " abcdefghijklmnopqrstuvwxyz".toCharArray();
    // this number should be enough for a relatively small target.
    private static final int MAX_EPOCHS = 5000;

    private static final int POPULATION_SIZE = 200;
    // number of fittest individuals in each generation.
    private static final int BEST = POPULATION_SIZE/4;

    public static void main(String[] args) {
        Population[] generation = new Population[POPULATION_SIZE];
        for (int i = 0; i < POPULATION_SIZE; i++) {
            generation[i] = new Population();
        }
        Arrays.sort(generation, Comparator.comparingDouble(Population::getFitness).reversed());

        for (int ep = 0; ep < MAX_EPOCHS; ep++) {
            for (int i = 0; i < POPULATION_SIZE; i++) {
                if (i <= BEST) {
                    // the top 25% individuals (i.e. the fittest ones) in a generations survives
                    //the epoch automatically
                    continue;
                }
                //the rest 75% to be replaced by the offspring of the fittest 25%.
                generation[i] = doCrossover(generation);
                //that's important. Without it the solution will never be found if Population in the starter generation
                //contains the necessary character.
                doMutate(generation[i]);
            }
            Arrays.sort(generation, Comparator.comparingDouble(Population::getFitness).reversed());
            if (generation[0].getString().equals(TARGET)) {
                System.out.println("Solution found in epoch: " + ep);
                System.out.println(generation[0]);
                System.exit(1);
            } else {
                System.out.println(generation[0] + " in epoch: " + ep);
            }
        }
    }

    private static void doMutate(Population population) {
        if (Math.random() * 100 <= 1) {
            population.dna[RANDOM.nextInt(TARGET_LENGTH)] = ALPHABET[RANDOM.nextInt(ALPHABET.length)];
            population.string = new String(population.dna);
            population.fitness = population.calcFitness();
        }
    }

    private static Population doCrossover(Population[] generation) {
        Population father = generation[RANDOM.nextInt(BEST)];
        Population mother = generation[RANDOM.nextInt(BEST)];
        int split = RANDOM.nextInt(TARGET_LENGTH);
        Population child = new Population(father.getString().substring(0, split), mother.getString().substring(split));
        return child;
    }

    static class Population {

        private char[] dna;
        private String string;
        private double fitness;

        public Population() {
            this.dna = new char[TARGET_LENGTH];
            for (int i = 0; i < TARGET_LENGTH; i++) {
                dna[i] = ALPHABET[RANDOM.nextInt(ALPHABET.length)];
            }
            this.string = new String(dna);
            this.fitness = calcFitness();
        }

        public Population(String fromFather, String fromMother) {
            if (fromFather.length() + fromMother.length() != TARGET_LENGTH) {
                throw new RuntimeException("inconsistent length");
            }
            this.string = fromFather.concat(fromMother);
            this.dna = string.toCharArray();
            this.fitness = calcFitness();
        }

        private double calcFitness() {
            double ret = 0.0;
            for (int i = 0; i < TARGET_LENGTH; i++) {
                if (Hamlet.TARGET.charAt(i) == this.dna[i]) {
                    ret++;
                }
            }
            return Math.pow(ret, 1);
        }

        @Override
        public String toString() {
            return this.string + " --> " + this.fitness;
        }

        public String getString() {
            return string;
        }

        private double getFitness() {
            return fitness;
        }
    }
}
