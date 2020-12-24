package genetic_algorithms.knapsack;

import java.util.Arrays;
import java.util.Comparator;
import java.util.Random;

/**
 * Solving the Knapsack Problem by Genetic Algorithms
 */
public class Knapsack {

    private static final Random RANDOM = new Random();
    public static final Item[] TREASURE = createTreasureItems();
    public static final int NUMBERS_OF_ITEMS = 100;
    private static final int POPULATION_SIZE = 400;
    private static final int BEST = POPULATION_SIZE / 4;
    private static final int ITEM_LIMIT = 25;
    private static final int MAX_EPOCHS = 50;


    public static void main(String[] args) {
        Knapsack solution = new Knapsack();
        solution.run();
    }

    private void run() {
        Selection[] generation = createGeneration();
        Arrays.sort(generation, Comparator.comparingInt(Selection::getFitness).reversed());
        for (int ep = 0; ep < MAX_EPOCHS; ep++) {
            for (int i = 0; i < POPULATION_SIZE; i++) {
                if (i <= BEST) {
                    // the top 25% individuals (i.e. the fittest ones) in a generations survives
                    //the epoch automatically
                    continue;
                }
                //the rest 75% to be replaced by the offspring of the fittest 25%.
                generation[i] = doCrossover(generation);
                doMutate(generation[i]);
            }
            Arrays.sort(generation, Comparator.comparingInt(Selection::getFitness).reversed());

            Selection top = generation[0];
            System.out.println("EPOCH: " + ep);
            System.out.println(" Total values = " + top.getFitness());
            System.out.println(" Total weight = " + top.getTotalWeight());
            System.out.println(" Total size = " + top.getTotalSize());
            System.out.println(" Number of selected items = " + top.getNumberOfItemsSelected());

            System.out.println();
            System.out.println("-------------");
        }
    }

    private Selection doMutate(Selection selection) {
        if (Math.random() * 100 <= 1) {
            flipValue(selection);
        }
        return selection;
    }

    private Selection flipValue(Selection selection) {
        int index = RANDOM.nextInt(NUMBERS_OF_ITEMS);
        if (selection.getSequence()[index] == 1) {
            selection.getSequence()[index] = 0;
        } else {
            selection.getSequence()[index] = 1;
        }
        return selection;
    }

    private Selection doCrossover(Selection[] generation) {
        Selection father = generation[RANDOM.nextInt(BEST)];
        Selection mother = generation[RANDOM.nextInt(BEST)];
        int split = RANDOM.nextInt(NUMBERS_OF_ITEMS);

        Selection child = new Selection(father, mother, split);
        return child;
    }

    /**
     * create first generation by randomly selecting Items
     *
     * @return an array of byte arrays where each 1 and 0 indicates
     * whether the corresponding item was selected or not.
     */
    private Selection[] createGeneration() {
        Selection[] ret = new Selection[POPULATION_SIZE];
        for (int i = 0; i < POPULATION_SIZE; i++) {
            ret[i] = new Selection();
        }
        return ret;
    }


    /**
     * create the array of items with randomly generated values, weights and sizes.
     * Values, weights and sizes can not be 0.
     *
     * @return array of items.
     */
    private static Item[] createTreasureItems() {
        Item[] ret = new Item[NUMBERS_OF_ITEMS];
        for (int i = 0; i < NUMBERS_OF_ITEMS; i++) {
            int[] size = {RANDOM.nextInt(ITEM_LIMIT) + 1, RANDOM.nextInt(ITEM_LIMIT) + 1, RANDOM.nextInt(ITEM_LIMIT) + 1};
            ret[i] = new Item(RANDOM.nextInt(ITEM_LIMIT * 3) + 1, RANDOM.nextInt(ITEM_LIMIT) + 1, size);
        }
        return ret;
    }
}
