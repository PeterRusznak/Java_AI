package decsison_trees;

import java.util.*;
import java.util.stream.IntStream;

public class Main {

    static String[][] WEATHER = {
            {"outlook", "temperature", "humidity", "windy", "play"},
            {"sunny", "hot", "high", "false", "no"},
            {"sunny", "hot", "high", "true", "no"},
            {"overcast", "hot", "high", "false", "yes"},
            {"rainy", "mild", "high", "false", "no"},
            {"rainy", "cool", "high", "false", "no"},
            {"rainy", "cool", "normal", "true", "no"},
            {"overcast", "cool", "normal", "true", "yes"},
            {"rainy", "mild", "high", "false", "no"},
            {"sunny", "cool", "high", "false", "yes"},
            {"rainy", "cool", "high", "true", "no"},
            {"rainy", "mild", "normal", "false", "no"},
            {"rainy", "mild", "high", "true", "no"},
            {"sunny", "mild", "high", "false", "yes"}
    };

    public static void main(String[] args) {
        Main main = new Main();
        Map<String, String[][]> datas = new HashMap<String, String[][]>();
        datas.put("WEATHER", WEATHER);
        datas.keySet().forEach(data ->{
           Map<Feature, Double> featuresInfoGain = new HashMap<Feature, Double>();
           DataSet dataset = new DataSet(datas.get(data));
           IntStream.range(0, datas.get(data)[0].length - 1).forEach(column ->{
               Feature feature = new Feature(datas.get(data), column);
               List<DataSet> dataSets = new ArrayList<DataSet>();
               feature.getValues().stream().forEach(featureValue -> {
                   dataSets.add(main.createDataSet(featureValue, column, datas.get(data)));
               });
               double sums = 0;
               for(int i = 0; i < dataSets.size(); i++){
                   sums += ((double) (dataSets.get(i).getData().length - 1) / (datas.get(data).length - 1))*dataSets.get(i).getEntropy();
               }
               featuresInfoGain.put(feature, dataset.getEntropy() - sums);
           });
            System.out.println("<"+data+" DATASET>:\n"+dataset);
            System.out.println(main.generateInfoGainDisplayTable(featuresInfoGain));
            System.out.println("Best feature to split on is "+ main.determineSplitOnFeature(featuresInfoGain)+"\n");
            System.out.println("\n\n");
        });
    }

    Feature determineSplitOnFeature(Map<Feature, Double> featuresInfoGain){
        Feature splitOnFeature = null;
        Iterator<Feature> iterator = featuresInfoGain.keySet().iterator();
        while(iterator.hasNext()){
            Feature feature = iterator.next();
            if(splitOnFeature == null){
                splitOnFeature = feature;
            }
            if(featuresInfoGain.get(splitOnFeature) < featuresInfoGain.get(feature)){
                splitOnFeature = feature;
            }
        }
        return splitOnFeature;
    }

    StringBuffer generateInfoGainDisplayTable(Map<Feature, Double> featuresInfoGain){
        StringBuffer ret = new StringBuffer();
        ret.append("Feature                      Information Gain\n");
        IntStream.range(0, 38).forEach(i -> ret.append(" - "));
        ret.append("\n");
        Iterator<Feature>iterator = featuresInfoGain.keySet().iterator();
        while (iterator.hasNext()){
            Feature feature = iterator.next();
            ret.append(feature);
            IntStream.range(0, 21 - feature.getName().length()).forEach(i ->ret.append(" "));
            ret.append(String.format("%.8f", featuresInfoGain.get(feature))+"\n");
        }
        return ret;
    }


    private DataSet createDataSet(FeatureValue featureValue, int column, String[][] data){
        String[][] returnData= new String[featureValue.getOccurences() + 1][data[0].length];
        returnData[0] = data[0];
        int counter = 1;
        for(int row = 1; row < data.length; row++ ){
            if(data[row][column] == featureValue.getName()){
                returnData[counter++] = data[row];
            }
        }
        return new DataSet(deleteColumn(returnData, column));
    }

    private String[][]deleteColumn(String[][] data, int deleteColumn){
        String [][] ret = new String[data.length][data[0].length - 1];
        for(int row = 0; row < data.length; row++){
            int columnCoounter = 0;
            for(int col = 0; col < data[0].length; col++){
                if(col != deleteColumn){
                    ret[row][columnCoounter++] = data[row][col];
                }
            }
        }
        return ret;
    }
}
