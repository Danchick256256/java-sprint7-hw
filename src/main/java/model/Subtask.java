package model;

import util.Status;
import util.TaskType;

import java.time.Duration;
import java.time.LocalDateTime;

public class Subtask extends Task {
    private Integer epicId = 0;

    public Subtask(String name, String description, Integer epicId) {
        super(name, description);

        this.epicId = epicId;
    }

    public Subtask(Integer id, String name, Status status, String description, Integer epicId, LocalDateTime localDateTime, Duration duration) {
        super(id, name, status, description, localDateTime, duration);

        this.epicId = epicId;
    }

    public Subtask(String name, String description, Integer epicId, LocalDateTime startTime, Duration duration) {
        super(name, description, startTime, duration);

        this.epicId = epicId;
    }

    public int getEpicId(){
        return epicId;
    }

    @Override
    public String toString() {
        return getId() +
                "," + TaskType.SUBTASK +
                "," + getName() +
                "," + getStatus() +
                "," + getDescription() +
                "," + getEpicId() +
                "," + startTime +
                "," + duration;
    }
}