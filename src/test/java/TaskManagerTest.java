import edu.course.eventplanner.model.Task;
import edu.course.eventplanner.service.TaskManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class TaskManagerTest {

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
        taskManager.addTask(new Task("Add Task"));
        assertEquals(3,taskManager.remainingTaskCount());

    }

    @Test
    public void testExecuteNextTask(){
        taskManager.executeNextTask();
        assertTrue(taskManager.remainingTaskCount() == 1);
    }

   @Test
    public void testUndoLastTask(){
        taskManager.executeNextTask();
        taskManager.undoLastTask();
        assertTrue(taskManager.remainingTaskCount() == 1);
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
}
