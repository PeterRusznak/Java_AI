package genetic_algorithms.class_scheduling.domain;

public class Room {
    private String number;
    private int seatingCapacity;
    public Room(String number, int seats) {
        this.number = number;
        this.seatingCapacity = seats;
    }

    public String getNumber() {
        return number;
    }

    public int getSeatingCapacity() {
        return seatingCapacity;
    }
}
