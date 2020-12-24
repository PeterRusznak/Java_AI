package genetic_algorithms.knapsack;

import java.util.Arrays;

/*
Representing the items to be collected. Each item has value, weight and size.
* */
public class Item {

    private int value;
    private int weight;
    private int[] size;

    public Item(int value, int weight, int[] size) {
        this.value = value;
        this.weight = weight;
        this.size = size;
    }

    @Override
    public String toString() {
        return "Item{" +
                "value=" + value +
                ", weight=" + weight +
                ", size=" + Arrays.toString(size) +
                ", size in cm3 = "+(size[0] * size[1] * size[2])+
                '}';
    }

    public int getValue() {
        return value;
    }

    public int getWeight() {
        return weight;
    }

    public int[] getSize() {
        return size;
    }
}
