package genetic_algorithms.class_scheduling.main;

import genetic_algorithms.class_scheduling.domain.Course;
import genetic_algorithms.class_scheduling.domain.Department;
import genetic_algorithms.class_scheduling.domain.Class;

import java.util.ArrayList;
import java.util.List;

public class Schedule {

    private List<Class> classes;
    private Data data;
    private int classCounter = 0;
    private int numberOfConflicts = 0;

    private boolean isFitnessChanged = true;
    private double fitness = -1;


    public Schedule(Data data) {
        this.data = data;
        this.classes = new ArrayList<Class>(data.getNumberOfClasses());
    }

    public Schedule init() {
        for (Department dep : data.getDepts()) {
            for (Course course : dep.getCourses()) {
                Class newClass = new Class(classCounter++, dep, course);
                newClass.setMeetingTime(data.getMeetingTimes().get((int) (data.getMeetingTimes().size() * Math.random())));
                newClass.setRoom(data.getRooms().get((int) (data.getRooms().size() * Math.random())));
                newClass.setInstructor(course.getInstructors().get((int) (course.getInstructors().size() * Math.random())));
                classes.add(newClass);
            }
        }
        return this;
    }

    private double calculateFitness() {
        numberOfConflicts = 0;
        for (Class xClass : classes) {
            if (xClass.getRoom().getSeatingCapacity() < xClass.getCourse().getMaxNumberOfStudents()) {
                numberOfConflicts++;
            }
            for (Class yClass : classes) {
                if ((classes.indexOf(yClass) >= classes.indexOf(xClass))) {
                    if (xClass.getMeetingTime() == yClass.getMeetingTime() && xClass.getId() != yClass.getId()) {
                        if (xClass.getRoom() == yClass.getRoom()) {
                            numberOfConflicts++;
                        }
                        if (xClass.getInstructor() == yClass.getInstructor()) {
                            numberOfConflicts++;
                        }
                    }
                }
            }
        }
        return 1 / (double) (numberOfConflicts + 1);
    }

    // Double checking
    private double doubleCheckCalculate() {
        numberOfConflicts = 0;
        classes.forEach(xClass -> {
            if (xClass.getRoom().getSeatingCapacity() < xClass.getCourse().getMaxNumberOfStudents()) {
                numberOfConflicts++;
            }
            classes.stream().filter(yClass -> classes.indexOf(yClass) >= classes.indexOf(xClass)).forEach(yClass -> {
                if (xClass.getMeetingTime() == yClass.getMeetingTime() && xClass.getId() != yClass.getId()) {
                    if (xClass.getRoom() == yClass.getRoom()) {
                        numberOfConflicts++;
                    }
                    if (xClass.getInstructor() == yClass.getInstructor()) {
                        numberOfConflicts++;
                    }
                }
            });
        });
        return 1 / (double) (numberOfConflicts + 1);
    }

    public double getFitness() {
        if (isFitnessChanged) {
            fitness = calculateFitness();
            double sajat = doubleCheckCalculate();
            if (fitness != sajat) {
                System.out.println("régi" + fitness);
                System.out.println("enyém " + sajat);
                throw new RuntimeException();
            }
            isFitnessChanged = false;
        }
        return fitness;
    }

    public List<Class> getClasses() {
        isFitnessChanged = true;
        return classes;
    }

    public Data getData() {
        return data;
    }

    public int getNumberOfConflicts() {
        return numberOfConflicts;
    }

    @Override
    public String toString() {
        String ret = new String();
        for (int i = 0; i < classes.size() - 1; i++) {
            ret += classes.get(i) + ",";
        }
        ret += classes.get(classes.size() - 1);
        return ret;
    }
}
