package genetic_algorithms.class_scheduling.main;

import genetic_algorithms.class_scheduling.domain.*;



import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Data {
    private List<Room> rooms;
    private List<Instructor> instructors;
    private List<Course> courses;
    private List<Department> depts;
    private List<Time> times;

    private int numberOfClasses = 0;


    public Data() {
        init();
    }

    private Data init() {
        //normally this Data coming from DB!

        Room room1 = new Room("R1", 25);
        Room room2 = new Room("R2", 45);
        Room room3 = new Room("R3", 35);
        this.rooms = new ArrayList<Room>(Arrays.asList(room1, room2, room3));

        Time time1 = new Time("MT1", "MWF 09:00 - 10:00");
        Time time2 = new Time("MT2", "MWF 10:00 - 11:00");
        Time time3 = new Time("MT3", "TTH 09:00 - 10:30");
        Time time4 = new Time("MT4", "TTH 10:30 - 12:00");
        this.times = new ArrayList<Time>(Arrays.asList(time1, time2, time3, time4));

        Instructor instructor1 = new Instructor("I1", "Mr Fedor Emelianenko");
        Instructor instructor2 = new Instructor("I2", "Mr Mike Tyson");
        Instructor instructor3 = new Instructor("I3", "Mr Carlo Pedersoli");
        Instructor instructor4 = new Instructor("I4", "Mr Helio Gracie");
        this.instructors = new ArrayList<Instructor>(Arrays.asList(instructor1, instructor2, instructor3, instructor4));

        Course course1 = new Course("C1", "404K", new ArrayList<Instructor>(Arrays.asList(instructor1, instructor2)), 25);
        Course course2 = new Course("C2", "319J", new ArrayList<Instructor>(Arrays.asList(instructor1, instructor2, instructor3)), 35);
        Course course3 = new Course("C3", "488K", new ArrayList<Instructor>(Arrays.asList(instructor1, instructor2)), 25);
        Course course4 = new Course("C4", "465U", new ArrayList<Instructor>(Arrays.asList(instructor3, instructor4)), 30);
        Course course5 = new Course("C5", "878X", new ArrayList<Instructor>(Arrays.asList(instructor4)), 35);
        Course course6 = new Course("C6", "676Y", new ArrayList<Instructor>(Arrays.asList(instructor1, instructor3)), 45);
        Course course7 = new Course("C7", "363L", new ArrayList<Instructor>(Arrays.asList(instructor2, instructor4)), 45);
        courses = new ArrayList<Course>(Arrays.asList(course1, course2, course3,  course4, course5, course6, course7));

        Department dept1 = new Department("dep:1", new ArrayList<Course>(Arrays.asList(course1, course3)));
        Department dept2 = new Department("dep:2", new ArrayList<Course>(Arrays.asList(course2, course4, course5)));
        Department dept3 = new Department("dep:3", new ArrayList<Course>(Arrays.asList(course6, course7)));
        this.depts = new ArrayList<Department>(Arrays.asList(dept1, dept2, dept3));
        depts.forEach(i ->{
            numberOfClasses += i.getCourses().size();
        });


        return this;
    }

    public List<Room> getRooms() {
        return rooms;
    }

    public List<Instructor> getInstructors() {
        return instructors;
    }

    public List<Course> getCourses() {
        return courses;
    }

    public List<Department> getDepts() {
        return depts;
    }

    public List<Time> getMeetingTimes() {
        return times;
    }

    public int getNumberOfClasses() {
        return numberOfClasses;
    }
}
