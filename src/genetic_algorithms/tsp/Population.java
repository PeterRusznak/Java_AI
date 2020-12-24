package genetic_algorithms.tsp;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class Population {
    private List<Chromosome> population;
    private int initialSize;
    public Population(Gene[] points, int initialSize){
        this.population = init(points, initialSize);
        this.initialSize = initialSize;
    }
    public Chromosome getAlpha(){
        return this.population.get(0);
    }

    private List<Chromosome> init(Gene[] points, int initialSize) {
        List<Chromosome> ret = new ArrayList<>();
        for(int i = 0; i<initialSize; i++){
            Chromosome chromosome = Chromosome.create(points);
            ret.add(chromosome);
        }
        return  ret;
    }

    public void update(){
        doCrossover();
        doMutation();
        doSpawn();
        doSelection();
    }

    private void doSelection(){
        this.population.sort(Comparator.comparingDouble(Chromosome::calculateDistance));
        this. population = this.population.stream().limit(this.initialSize).collect(Collectors.toList());
    }

    private  void  doSpawn(){
        IntStream.range(0,1000).forEach(e->this.population.add(Chromosome.create(TravellingUtils.CITIES)));
    }


    private void doMutation(){
        List<Chromosome> newPopulation = new ArrayList<>();
        for(int i = 0; i < this.population.size()/10; i++){
            Chromosome mutation = this.population.get(TravellingUtils.randomIndex(this.population.size())).mutate();
            newPopulation.add(mutation);
        }
        this.population.addAll(newPopulation);
    }

    private void doCrossover(){
      List<Chromosome>newPopulation = new ArrayList<>();
      for(Chromosome chromosome: this.population){
          Chromosome partner = getCrossOverPartner(chromosome);
          newPopulation.addAll(Arrays.asList(chromosome.crossover(partner)));
      }
      this.population.addAll(newPopulation);
    }


    private Chromosome getCrossOverPartner(Chromosome chromosome){
        Chromosome partner = this.population.get(TravellingUtils.randomIndex(this.population.size()));
        while (chromosome == partner){
            partner = this.population.get(TravellingUtils.randomIndex(this.population.size()));
        }
        return partner;
    }


}
