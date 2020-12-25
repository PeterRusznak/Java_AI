package decsison_trees;

import java.util.Objects;

public class FeatureValue {
    private String name;
    private int occurences;


    public FeatureValue(String name) {
        this.name = name;
    }

    @Override
    public boolean equals(Object o) {
        boolean ret = true;
        if ((o == null) || getClass() != o.getClass()) {
            ret = false;
        }
        if (name == null) {
            if (((FeatureValue) o).name != null) {
                ret = false;
            }
        }else if(!name.equals(((FeatureValue)o).name)){
            ret = false;
        }
        return ret;
    }

    @Override
    public int hashCode() {
        return name.hashCode();
    }

    public int getOccurences() {
        return occurences;
    }

    public void setOccurences(int occurences) {
        this.occurences = occurences;
    }


    public String getName() {
        return name;
    }


}
