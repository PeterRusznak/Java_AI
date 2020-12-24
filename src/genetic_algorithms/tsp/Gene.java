package genetic_algorithms.tsp;

import java.util.Objects;

public class Gene {
    private final int x;
    private final int y;


    Gene(final int x, final int y){
        this.x = x;
        this.y = y;
    }

    public double distance(final Gene other){
        return Math.sqrt(Math.pow(this.getX()- other.getX(), 2)  + Math.pow(this.getY() - other.getY(), 2));
    }

    @Override
    public String toString() {
        return "("+this.x + ", "+ this.y+")";
    }

    public int getX() {
        return this.x;
    }

    public int getY() {
        return this.y;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Gene tspGene = (Gene) o;
        return x == tspGene.x && y == tspGene.y;
    }

    @Override
    public int hashCode() {
        return Objects.hash(x, y);
    }
}
