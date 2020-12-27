package neural_networks.and_data;

import java.util.Random;

public class Perceptron {

    public static final int[][][] andData = {
            {{0, 0}, {0}},
            {{0, 1}, {0}},
            {{1, 0}, {0}},
            {{1, 1}, {1}},
    };
    public static final double LEARNING_RATE = 0.05;
    public static final double[] INITIAL_WEIGHTS = {Math.random(), Math.random()};

    public double calculateWeightSum(int [] data, double[] weights){
        double ret = 0;
        for(int i = 0; i < data.length; i++){
            ret += data[i] * weights[i];
        }
        return ret;
    }

    public int applyActivationFunction(double weightedSum){
        int ret = 0;
        if(weightedSum > 1){
            ret = 1;
        }
        return ret;
    }

    public double[] adjustWeights(int [] data, double[] weights, double error){
        double [] ret = new double[weights.length];
        for(int i = 0; i < weights.length; i++){
            ret[i] = LEARNING_RATE * error * data[i] + weights[i];
        }
        return ret;
    }
}
