package genetic_algorithms.domain;

import java.util.List;

public class Course {

    private String number;
    private String name;
    private int maxNumberOfStudents;
    private List<Instructor> instructors;

    public Course(String name, String number, List<Instructor> instructors, int maxNumber){
        this.number = number;
        this.name = name;
        this.maxNumberOfStudents = maxNumber;
        this.instructors = instructors;
    }

    public String getNumber() {
        return number;
    }

    public String getName() {
        return name;
    }

    public int getMaxNumberOfStudents() {
        return maxNumberOfStudents;
    }

    public List<Instructor> getInstructors() {
        return instructors;
    }

    @Override
    public String toString() {
        return this.name;
    }
}
