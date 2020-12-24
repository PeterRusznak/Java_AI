package genetic_algorithms.knapsack;

/**
 * Representing random selection of items.
 *
 */
public class Selection {

    private static final int WEIGHT_CAPACITY = 1000;
    private static final int SIZE_CAPACITY = 50 * 50 * 50;
    private byte[] sequence = new byte[Knapsack.NUMBERS_OF_ITEMS];
    private int fitness = 0;
    private int numberOfItemsSelected = 0;
    private int totalWeight = 0;
    private int totalSize = 0;


    public Selection(){
        this.sequence = selectItems();
        this.fitness = calculateFitness();
    }

    public Selection(Selection father, Selection mother, int split){
        for(int i = 0; i < this.sequence.length; i++){
            if(i <= split){
                this.sequence[i] = father.getSequence()[i];
            }else{
                this.sequence[i] = mother.getSequence()[i];
            }
        }
        this.fitness = calculateFitness();
    }

    private int calculateFitness(){
        if(this.sequence.length != Knapsack.NUMBERS_OF_ITEMS){
            throw new RuntimeException("Inconsistent length of SELECTION!!!");
        }
        int totalWeight = 0;
        int totalSize = 0;
        int totalValue = 0;

        for(int i = 0; i < Knapsack.NUMBERS_OF_ITEMS; i++){
            //only if our selection contains this Item
            if(this.sequence[i] == 1){
                this.numberOfItemsSelected++;
              totalValue += Knapsack.TREASURE[i].getValue();
              totalWeight += Knapsack.TREASURE[i].getWeight();
              if(totalWeight > WEIGHT_CAPACITY){
                  // useless selection, to heavy.
                  return -1;
              }
              totalSize += calculateSizeInCM3(Knapsack.TREASURE[i].getSize());
              if(totalSize > SIZE_CAPACITY){
                  //useless selection, too large.
                  return -1;
              }
            }
        }
        this.totalSize = totalSize;
        this.totalWeight = totalWeight;
        return  totalValue;
    }

    private int calculateSizeInCM3(int[] size){
        return size[0]* size[1]*size[2];
    }

    /**
     * selecting Items from 'treasure'.
     * @return a byte array where 1  meaning that the item is selected, 0 meaning it is not.
     */
    private byte[] selectItems(){
        byte [] ret = new byte[Knapsack.NUMBERS_OF_ITEMS];
        for(int i = 0; i <Knapsack.NUMBERS_OF_ITEMS; i++){
            if(Math.random()*100 < 50){
                ret[i] = 1; // item selected
            }else{
                ret[i] = 0; // item NOT selected.
            }
        }
        return ret;
    }

    public int getNumberOfItemsSelected() {
        return numberOfItemsSelected;
    }

    public int getTotalWeight() {
        return totalWeight;
    }

    public int getTotalSize() {
        return totalSize;
    }

    public byte[] getSequence() {
        return sequence;
    }

    public int getFitness() {
        return fitness;
    }
}
