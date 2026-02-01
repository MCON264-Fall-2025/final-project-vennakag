package edu.course.eventplanner.service;

import edu.course.eventplanner.model.Task;
import java.util.*;

public class TaskManager {
    private final Queue<Task> upcoming = new LinkedList<>();
    private final Stack<Task> completed = new Stack<>();
    public void addTask(Task task) {upcoming.add(task);}
    public Task executeNextTask() {
        if(upcoming.isEmpty()) {
            return null;
        }
        Task t = upcoming.remove();
        completed.push(t);
        return t;}
    public Task undoLastTask() {
        if(completed.isEmpty()) {
            return null;
        }
        Task t = completed.pop();
        upcoming.add(t);
        return t; }
    public int remainingTaskCount() {
        return upcoming.size(); }
}
