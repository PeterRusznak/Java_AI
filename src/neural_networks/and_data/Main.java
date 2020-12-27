package neural_networks.and_data;

public class Main {

    private static final String PLACE_HOLDER = "  |  ";

    public static void main(String[] args) {
        int[][][] data = Perceptron.andData;
        double[] weights = Perceptron.INITIAL_WEIGHTS;
        Main main = new Main();
        Perceptron perceptron = new Perceptron();

        int epochNum = 0;
        double error = 1;
        double[] adjustedWeights = null;

        while (error != 0) {
            error = 0;
            for (int i = 0; i < data.length; i++) {
                double weightedSum = perceptron.calculateWeightSum(data[i][0], weights);
                int result = perceptron.applyActivationFunction(weightedSum);

                error = data[i][1][0] - result;

                adjustedWeights = perceptron.adjustWeights(data[i][0], weights, error);
                main.printVector(epochNum++, data[i], weights, result, error, weightedSum, adjustedWeights);
                weights = adjustedWeights;
            }
        }
    }



    private void printVector(int epochNum, int[][] data, double[] weights, int result, double error, double weightedSum, double[] adjustedWeights) {
        System.out.println("EPOCH # = " + epochNum);
        System.out.println("W1 ="+ String.format("%.2f", weights[0]));
        System.out.println("W2 ="+ String.format("%.2f", weights[1]));
        System.out.println("X1 ="+ data[0][0]);
        System.out.println("X2 ="+ data[0][1]);
        System.out.println("TARGET RESULT ="+ data[1][0]);
        System.out.println("RESULT ="+ result);
        System.out.println("ERROR ="+ error);
        System.out.println("WEIGHTED SUM ="+ String.format("%.2f", weightedSum));
        System.out.println("ADJUSTED_WEIGHT_1 ="+ String.format("%.2f", adjustedWeights[0]));
        System.out.println("ADJUSTED_WEIGHT_2 ="+ String.format("%.2f", adjustedWeights[1]));
        System.out.println("******************************************");
    }
}
