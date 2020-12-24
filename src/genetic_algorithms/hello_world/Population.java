package genetic_algorithms.hello_world;

import java.util.Arrays;

public class Population {
    private Chromosome[] chromosomes;

    public Population(int length){
        chromosomes = new Chromosome[length];
    }

    public Population initializePopulation(){
        for(int i = 0; i < chromosomes.length; i++){
            chromosomes[i] = new Chromosome(Main.TARGET.length).initializeChromosome();
        }
        sortChromosomesByFitness();
        return this;
    }

    public void sortChromosomesByFitness(){
        for(Chromosome chromosome: this.getChromosomes()){
            chromosome.calculateFitness();
        }
        Arrays.sort(chromosomes, (chromosome1,chromosome2) ->{
            int ret = 0;
            if(chromosome1.getFitness() > chromosome2.getFitness()){
                ret = -1;
            }else if(chromosome1.getFitness() < chromosome2.getFitness()){
                ret = 1;
            }
            return  ret;
        });

    }

    public Chromosome[] getChromosomes() {
        return chromosomes;
    }
}
