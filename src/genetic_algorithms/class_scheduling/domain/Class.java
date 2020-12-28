package genetic_algorithms.class_scheduling.domain;

public class Class {
    private static final String COMMA = ",  ";

    private int id;
    private Department dept;
    private Course course;
    private Instructor instructor;
    private Time time;
    private Room room;


    public Class(int id, Department dept, Course course) {
        this.id = id;
        this.dept = dept;
        this.course = course;
    }

    public void setInstructor(Instructor instructor) {
        this.instructor = instructor;
    }

    public void setMeetingTime(Time time) {
        this.time = time;
    }

    public void setRoom(Room room) {
        this.room = room;
    }

    public int getId() {
        return id;
    }

    public Department getDept() {
        return dept;
    }

    public Course getCourse() {
        return course;
    }

    public Instructor getInstructor() {
        return instructor;
    }

    public Time getMeetingTime() {
        return time;
    }

    public Room getRoom() {
        return room;
    }

    @Override
    public String toString() {
        return "Class [" + dept.getName() + COMMA + course.getNumber() + COMMA
                + room.getNumber() + COMMA + instructor.getId()
                + COMMA + time.getId() + "]";
    }
}
