package genetic_algorithms.class_scheduling.main;

import java.util.ArrayList;
import java.util.List;


public class Population {
    private List<Schedule> schedules;

    public Population(int size, Data data){
        schedules = new ArrayList<Schedule>(size);
        for(int i = 0; i < size; i++){
            schedules.add(new Schedule(data).init());
        }
    }

    public Population sortByFitness(){
        schedules.sort((firstSchedule, secondSchedule) ->{
            int ret = 0;
            if(firstSchedule.getFitness() > secondSchedule.getFitness()){
                ret = -1;
            }else if(firstSchedule.getFitness() < secondSchedule.getFitness()){
                ret = 1;
            }
            return ret;
        });
        return this;
    }

    public List<Schedule> getSchedules() {
        return schedules;
    }
}
