import java.util.LinkedList;
import java.util.List;

public class SchedulerWithLinkedList {
    private LinkedList<Task> tasks = new LinkedList<>(); // Linked list for task storage

    // Method to add a task to the linked list
    public void addTask(Task task) {
        tasks.add(task);
    }

    // Method to get all tasks from the linked list
    public List<Task> getTasks() {
        return tasks;
    }
}
