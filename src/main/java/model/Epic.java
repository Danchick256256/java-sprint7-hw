package model;

import util.Status;
import util.TaskType;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.ArrayList;


public class Epic extends Task {
    private ArrayList<Subtask> subTasks;

    private LocalDateTime endTime;

    public Epic(String name, String description) {
        super(name, description);

        subTasks = new ArrayList<>();
    }

    public Epic(Integer id, String name, Status status, String description, LocalDateTime localDateTime, Duration duration) {
        super(id, name, status, description, localDateTime, duration);
    }

    public Epic(Integer id, String name, Status status, String description) {
        super(id, name, status, description);
    }

    public void addSubTask(Subtask subTask){
        subTasks.add(subTask);
        refreshDates();
    }

    public Status getStatus(){
        Status result;
        int statusSum = Status.NEW.ordinal();

        if (subTasks.size() == 0){
            result = Status.NEW;
        } else {
            for (Subtask subTask : subTasks)
                statusSum += subTask.getStatus().ordinal();

            if (statusSum == Status.NEW.ordinal()){
                result = Status.NEW;
            } else if (statusSum == (Status.DONE.ordinal() * subTasks.size())){
                result = Status.DONE;
            } else {
                result = Status.IN_PROGRESS;
            }
        }

        return result;
    }

    public void refreshDates(){
        Duration sumDuration = null;
        LocalDateTime firstDate = null;
        LocalDateTime lastDate = null;

        if (subTasks != null){
            for (Subtask subTask : subTasks){
                if (subTask.getDuration()!=null && subTask.getStartTime()!=null){
                    if (firstDate == null || firstDate.isAfter(subTask.getStartTime()))
                        firstDate = subTask.getStartTime();
                    if(lastDate == null || lastDate.isBefore(subTask.getEndTime()))
                        lastDate = subTask.getEndTime();
                    if (sumDuration == null)
                        sumDuration = subTask.getDuration();
                    else
                        sumDuration = sumDuration.plus(subTask.getDuration());
                }
            }
        } else {
            duration = Duration.ZERO;
            startTime = LocalDateTime.now();
            endTime = LocalDateTime.now();
        }
        duration = sumDuration;
        startTime = firstDate;
        endTime = lastDate;
    }

    public LocalDateTime getEndTime() {
        return endTime;
    }

    public ArrayList<Subtask> getSubTasks() {
        return subTasks;
    }

    @Override
    public void setStatus(Status status) {
        throw new UnsupportedOperationException("You can not set status of Epic");
    }

    @Override
    public String toString() {
        return getId() +
                "," + TaskType.EPIC +
                "," + getName() +
                "," + getStatus() +
                "," + getDescription() +
                "," + null +
                "," + startTime +
                "," + duration;
    }
}