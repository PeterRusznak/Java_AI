package genetic_algorithms.class_scheduling.main;

import genetic_algorithms.domain.Class;

import java.util.List;

public class Main {
    private static final String PH = "  |  ";
    private static final int POPULATION_SIZE = 9;
    private static final double MUTATION_RATE = 0.1;

    private static final int TOURNAMENT_SELECTION_SIZE = 3;
    private static final int NUMBER_OF_SURVIVORS = 2;
    private int scheduleNumber = 0;
    private int classNumber = 1;

    private Data data;

    public Population evolve(Population pop) {
        return mutatePopulation(doCrossoverPopulation(pop));
    }

    private Population doCrossoverPopulation(Population population) {
        Population ret = new Population(population.getSchedules().size(), this.data);
        for (int i = 0; i < ret.getSchedules().size(); i++) {
            if (i < NUMBER_OF_SURVIVORS) {
                ret.getSchedules().set(i, population.getSchedules().get(i));
            } else {
                Schedule first = selectTournamentPopulation(population).sortByFitness().getSchedules().get(0);
                Schedule sec = selectTournamentPopulation(population).sortByFitness().getSchedules().get(0);
                ret.getSchedules().set(i, crossoverSchedule(first, sec));
            }
        }
        return ret;
    }

    private Schedule crossoverSchedule(Schedule first, Schedule sec) {
        Schedule ret = new Schedule(data).init();
        for (int i = 0; i < ret.getClasses().size(); i++) {
            if (Math.random() > 0.5) {
                ret.getClasses().set(i, first.getClasses().get(i));
            } else {
                ret.getClasses().set(i, sec.getClasses().get(i));
            }
        }
        return ret;
    }

    private Population mutatePopulation(Population pop) {
        Population ret = new Population(pop.getSchedules().size(), data);
        List<Schedule> scheduleList = ret.getSchedules();
        for (int i = 0; i < pop.getSchedules().size(); i++) {
            if (i > NUMBER_OF_SURVIVORS) {
                scheduleList.set(i, pop.getSchedules().get(i));
            } else {
                scheduleList.set(i, mutateSchedule(pop.getSchedules().get(i)));
            }
        }
        return ret;
    }

    private Schedule mutateSchedule(Schedule mutateSchedule) {
        Schedule schedule = new Schedule(data).init();
        for (int i = 0; i < mutateSchedule.getClasses().size(); i++) {
            if (MUTATION_RATE > Math.random()) {
                mutateSchedule.getClasses().set(i, schedule.getClasses().get(i));
            }
        }
        return mutateSchedule;
    }

    private Population selectTournamentPopulation(Population pop) {
        Population ret = new Population(TOURNAMENT_SELECTION_SIZE, this.data);
        for (int i = 0; i < TOURNAMENT_SELECTION_SIZE; i++) {
            ret.getSchedules().set(i, pop.getSchedules().get((int) (Math.random() * pop.getSchedules().size())));
        }
        return ret;
    }

    public static void main(String[] args) {
        Main main = new Main();
        main.data = new Data();
        main.printHeader();

        int epochNumber = 0;
        Population population = new Population(POPULATION_SIZE, main.data).sortByFitness();
        main.classNumber = 1;
        while (population.getSchedules().get(0).getFitness() != 1.0) {
            population = main.evolve(population).sortByFitness();
            main.scheduleNumber = 0;
            main.printEpochTable(epochNumber++, population);
            main.printClassTable(population.getSchedules().get(0), epochNumber);
            main.classNumber = 1;
        }
    }

    private void printEpochTable(int epochNumber, Population copulation) {
        System.out.println();
        System.out.println("*************************************************************************************************************");
        System.out.println("Number of Epochs " + epochNumber);
        System.out.println("Conflicts  |  Fitness | Sched.# | Classes [department, class, room, instructor, time]");
        for (Schedule schedule : copulation.getSchedules()) {
            //copulation.getSchedules().forEach(schedule -> {
            System.out.println("        " + schedule.getNumberOfConflicts() + PH + String.format("%.5f", schedule.getFitness()) + PH
                    + scheduleNumber++ + "   |  " + schedule);
        }
    }


    private void printClassTable(Schedule schedule, int generation) {
        List<Class> classes = schedule.getClasses();
        System.out.print("\n        ");
        System.out.println("Class # |  Dept  |  Course (number, max # of Students)  | Room (Capacity) | Instructor (Id)              | Time(Id)");
        System.out.println("--------------------------------------------------------------------------------------------------------------------------------------");
        classes.forEach(x -> {
            int majorIndex = data.getDepts().indexOf(x.getDept());
            int courseIndex = data.getCourses().indexOf(x.getCourse());
            int roomIndex = data.getRooms().indexOf(x.getRoom());
            int instructorIndex = data.getInstructors().indexOf(x.getInstructor());
            int meetingTimeIndex = data.getMeetingTimes().indexOf(x.getMeetingTime());
            System.out.print("         ");
            System.out.print(String.format(" %1$02d  ", classNumber) + PH);
            System.out.print(String.format(" %1$4s", data.getDepts().get(majorIndex).getName()) + PH);
            System.out.print(String.format(" %1$31s", data.getCourses().get(courseIndex).getName() +
                    " (" + data.getCourses().get(courseIndex).getNumber() +
                    ", " + data.getCourses().get(courseIndex).getMaxNumberOfStudents()) + ")" + PH);
            System.out.print(String.format(" %1$10s", data.getRooms().get(roomIndex).getNumber() +
                    " (" + data.getRooms().get(roomIndex).getSeatingCapacity()) + ")" + PH);
            System.out.print(String.format(" %1$25s", data.getInstructors().get(instructorIndex).getName() +
                    " (" + data.getInstructors().get(instructorIndex).getId()) + ")" + PH);
            System.out.println(data.getMeetingTimes().get(meetingTimeIndex).getTime() +
                    " (" + data.getMeetingTimes().get(meetingTimeIndex).getId() + ")");
            classNumber++;
        });
        if (schedule.getFitness() == 1) {
            System.out.println("" +
                    "Solution found in " + (generation + 1) + " generations");
        }

    }


    private void printHeader() {
        System.out.println("Departments -->");
        data.getDepts().forEach(x -> {
            System.out.println("name :" + x.getName() + ", courses: " + x.getCourses());
        });
        System.out.println();


        System.out.println("Courses -->");
        data.getCourses().forEach(x -> {
            System.out.println("course #: " + x.getNumber() + ", name: " + x.getName() + ", max students: "
                    + x.getMaxNumberOfStudents() + ", instructors: " + x.getInstructors());
        });
        System.out.println();


        System.out.println("Rooms -->");
        data.getRooms().forEach(x -> {
            System.out.println("Room Number :" + x.getNumber() + ", max student capacity : " + x.getSeatingCapacity());
        });
        System.out.println();


        System.out.println("Instructors -->");
        data.getInstructors().forEach(x -> {
            System.out.println("id :" + x.getId() + ", name : " + x.getName());
        });
        System.out.println();


        System.out.println("Time -->");
        data.getMeetingTimes().forEach(x -> {
            System.out.println("id :" + x.getId() + ",  Time : " + x.getTime());
        });
    }
}
