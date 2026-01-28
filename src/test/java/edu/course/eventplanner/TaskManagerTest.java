package edu.course.eventplanner;

import edu.course.eventplanner.model.Task;
import edu.course.eventplanner.service.TaskManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class TaskManagerTest {

    TaskManager taskManager;

    @BeforeEach
    public void setUp(){
        taskManager = new TaskManager();
        Task task1 = new Task("Add Task1");
        Task task2 =  new Task("Add Task2");
        taskManager.addTask(task1);
        taskManager.addTask(task2);
    }

    @Test
    public void testAddTask(){
        taskManager.addTask(new Task("Add Task3"));
        assertEquals(3, taskManager.remainingTaskCount());
    }

    @Test
    public void testExecuteNextTask(){
        taskManager.executeNextTask();
        assertEquals(1, taskManager.remainingTaskCount());
    }

    @Test
    public void testUndoLastTask(){
        taskManager.executeNextTask();
        taskManager.undoLastTask();
        assertEquals(2, taskManager.remainingTaskCount());
    }

    @Test
    public void testUndoNextTaskOnEmptyList(){
        assertNull(taskManager.undoLastTask());
    }

    @Test
    public void testThatQueueWorksAsExpected(){
        taskManager.addTask(new Task("Add Task3"));
        assertEquals("Add Task1", taskManager.executeNextTask().getDescription());
        assertEquals("Add Task2", taskManager.executeNextTask().getDescription());
        assertEquals("Add Task3", taskManager.executeNextTask().getDescription());
    }

    @Test
    public void testThatStackWorksAsExpected(){
        taskManager.addTask(new Task("Add Task3"));
        taskManager.addTask(new Task("Add Task4"));
        taskManager.executeNextTask();
        taskManager.executeNextTask();
        assertEquals("Add Task2", taskManager.undoLastTask().getDescription());
    }

    @Test
    public void testTasksQueueMaintainsFIFOOrder() {
        TaskManager manager = new TaskManager();
        Task t1 = new Task("First");
        Task t2 = new Task("Second");
        Task t3 = new Task("Third");

        manager.addTask(t1);
        manager.addTask(t2);
        manager.addTask(t3);

        assertEquals("First", manager.executeNextTask().getDescription());
        assertEquals("Second", manager.executeNextTask().getDescription());
        assertEquals("Third", manager.executeNextTask().getDescription());
        assertEquals(0, manager.remainingTaskCount());
    }

    @Test
    public void testCompletedTasksStackMaintainsLIFOOrder() {
        TaskManager manager = new TaskManager();
        manager.addTask(new Task("Task1"));
        manager.addTask(new Task("Task2"));
        manager.addTask(new Task("Task3"));

        manager.executeNextTask();
        manager.executeNextTask();
        manager.executeNextTask();

        assertEquals("Task3", manager.undoLastTask().getDescription());
        assertEquals("Task2", manager.undoLastTask().getDescription());
        assertEquals("Task1", manager.undoLastTask().getDescription());
    }

    @Test
    public void testUndoMovesTaskBackToQueue() {
        TaskManager manager = new TaskManager();
        manager.addTask(new Task("Original Task"));

        manager.executeNextTask();
        assertEquals(0, manager.remainingTaskCount());

        manager.undoLastTask();
        assertEquals(1, manager.remainingTaskCount());

        assertEquals("Original Task", manager.executeNextTask().getDescription());
    }

    @Test
    public void testMultipleUndoAndRedo() {
        TaskManager manager = new TaskManager();
        manager.addTask(new Task("Task1"));
        manager.addTask(new Task("Task2"));

        manager.executeNextTask();
        manager.executeNextTask();
        assertEquals(0, manager.remainingTaskCount());

        manager.undoLastTask();
        assertEquals(1, manager.remainingTaskCount());

        manager.undoLastTask();
        assertEquals(2, manager.remainingTaskCount());

        assertEquals("Task2", manager.executeNextTask().getDescription());
        assertEquals("Task1", manager.executeNextTask().getDescription());
    }

    @Test
    public void testExecuteOnEmptyQueue() {
        TaskManager manager = new TaskManager();
        assertNull(manager.executeNextTask());
        assertEquals(0, manager.remainingTaskCount());
    }

    @Test
    public void testRemainingTaskCountAfterOperations() {
        TaskManager manager = new TaskManager();
        assertEquals(0, manager.remainingTaskCount());

        manager.addTask(new Task("Task1"));
        assertEquals(1, manager.remainingTaskCount());

        manager.addTask(new Task("Task2"));
        manager.addTask(new Task("Task3"));
        assertEquals(3, manager.remainingTaskCount());

        manager.executeNextTask();
        assertEquals(2, manager.remainingTaskCount());

        manager.undoLastTask();
        assertEquals(3, manager.remainingTaskCount());
    }

    @Test
    public void testUndoWithoutExecuteDoesNothing() {
        TaskManager manager = new TaskManager();
        manager.addTask(new Task("Task1"));

        assertNull(manager.undoLastTask());
        assertEquals(1, manager.remainingTaskCount());
    }

}