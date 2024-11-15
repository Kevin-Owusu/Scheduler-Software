import java.util.LinkedList;
import java.util.Queue;

public class SchedulerWithQueue {
    private Queue<Task> taskQueue = new LinkedList<>(); // Queue for task processing

    // Method to add a task to the queue
    public void addTaskToQueue(Task task) {
        taskQueue.offer(task);
    }

    // Method to get the next task from the queue
    public Task getNextTaskFromQueue() {
        return taskQueue.poll();
    }
}
