package genetic_algorithms.class_scheduling.domain;

public class Time {
    private String id;
    private String time;
    public Time(String id, String time){
        this.id = id;
        this.time = time;
    }

    public String getId() {
        return id;
    }

    public String getTime() {
        return time;
    }
}
