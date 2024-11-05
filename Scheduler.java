import java.time.LocalDate; // Import for LocalDate
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

// Task class
class Task {
    private String title;
    private LocalDate dueDate;
    private int priority;

    public Task(String title, LocalDate dueDate, int priority) {
        this.title = title;
        this.dueDate = dueDate;
        this.priority = priority;
    }

    public String getTitle() {
        return title;
    }

    public LocalDate getDueDate() {
        return dueDate;
    }

    public int getPriority() {
        return priority;
    }

    @Override
    public String toString() {
        return title + " (Due: " + dueDate + ", Priority: " + priority + ")";
    }
}

// Scheduler class
public class Scheduler {
    private List<Task> tasks;

    public Scheduler() {
        tasks = new ArrayList<>();
    }

    public void addTask(Task task) {
        tasks.add(task);
    }

    public void removeTask(Task task) {
        tasks.remove(task);
    }

    public List<Task> getTasks() {
        return tasks;
    }

    public void sortByPriority() {
        Collections.sort(tasks, Comparator.comparingInt(Task::getPriority));
    }

    public void sortByDueDate() {
        Collections.sort(tasks, Comparator.comparing(Task::getDueDate));
    }

    public void saveTasksToFile(String filename) {
     
    }

    public void loadTasksFromFile(String filename) {
        
    }
}
