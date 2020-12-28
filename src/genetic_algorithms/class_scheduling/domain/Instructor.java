package genetic_algorithms.class_scheduling.domain;

public class Instructor {
    private String id;
    private String name;
    public Instructor(String id, String name){
        this.id = id;
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        return this.name;
    }
}
