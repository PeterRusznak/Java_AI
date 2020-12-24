package genetic_algorithms.tsp;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class Chromosome {
    private final List<Gene> chromosome;

    Chromosome(final List<Gene> genes) {
        this.chromosome = Collections.unmodifiableList(genes);
    }

    static Chromosome create(Gene[] points) {
        List<Gene> genes = Arrays.asList(Arrays.copyOf(points, points.length));
        Collections.shuffle(genes);
        return new Chromosome(genes);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (Gene gene : this.chromosome) {
            sb.append(gene.toString()).append((":"));
        }
        return sb.toString();
    }

    public List<Gene> getChromosome() {
        return this.chromosome;
    }

    public double calculateDistance() {
        double total = 0.0f;
        for (int i = 0; i < this.chromosome.size() - 1; i++) {
            total += this.chromosome.get(i).distance(this.chromosome.get(i + 1));
        }
        return total;
    }

    public Chromosome[] crossover(Chromosome other) {
        List<Gene>[] myDNA = TravellingUtils.split(this.chromosome);
        List<Gene>[] otherDNA = TravellingUtils.split(other.getChromosome());
        List<Gene> firstCrossover = new ArrayList<>(myDNA[0]);
        for(Gene gene: otherDNA[0]){
            if(!firstCrossover.contains(gene)){
                firstCrossover.add(gene);
            }
        }
        for(Gene gene: otherDNA[1]){
            if(!firstCrossover.contains(gene)){
                firstCrossover.add(gene);
            }
        }

        List<Gene> secondCrossover = new ArrayList<>(otherDNA[1]);

        for(Gene gene: myDNA[0]){
            if(!secondCrossover.contains(gene)){
                secondCrossover.add(gene);
            }
        }
        for(Gene gene: myDNA[1]){
            if(!secondCrossover.contains(gene)){
                secondCrossover.add(gene);
            }
        }

        if(firstCrossover.size() != TravellingUtils.CITIES.length
                || secondCrossover.size() != TravellingUtils.CITIES.length ){
            throw new RuntimeException("Inconsistent size");
        }

        return new Chromosome[]{
                new Chromosome(firstCrossover),
                new Chromosome(secondCrossover)
        };
    }

    public Chromosome mutate(){
        List<Gene> copy = new ArrayList<>(this.chromosome);
        int indexA = TravellingUtils.randomIndex(copy.size());
        int indexB = TravellingUtils.randomIndex(copy.size());
        while (indexA == indexB){
            indexA = TravellingUtils.randomIndex(copy.size());
            indexB = TravellingUtils.randomIndex(copy.size());
        }
        Collections.swap(copy, indexA, indexB);
        return  new Chromosome(copy);
    }

}
