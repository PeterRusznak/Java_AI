package decsison_trees;

import java.util.Arrays;
import java.util.List;
import java.util.stream.IntStream;

public class DataSet {
    private String[][] data = null;
    private double entropy = 0;
    private List<Feature> featureList = null;

    public DataSet(String[][] data){
        this.data = data;
        new Feature(data, data[0].length- 1).getValues().stream().forEach(featureValue ->
        entropy += minusPlog2((double)featureValue.getOccurences() / (data.length - 1)));
    }

    public double minusPlog2(double d) {
        double ret = 0;
        if (d != 0) {
            ret = (-1) * d * Math.log(d) / Math.log(2);
        }
        return ret;
    }

    @Override
    public String toString() {
        StringBuffer stringBuffer = new StringBuffer();
        for(int row = 0; row < data.length; row++){
            for(int col = 0; col < data[row].length; col++){
                stringBuffer.append(data[row][col]);
                IntStream.range(0, 24 - data[row][col].length()).forEach(i -> stringBuffer.append(""));
            }
            stringBuffer.append("\n");
            if(row == 0){
                IntStream.range(0, 108).forEach(i -> stringBuffer.append("-"));
                stringBuffer.append("\n");
            }
        }
        return stringBuffer.toString();
    }



    public String[][] getData() {
        return data;
    }

    public double getEntropy() {
        return entropy;
    }

    public List<Feature> getFeatureList() {
        return featureList;
    }
}
