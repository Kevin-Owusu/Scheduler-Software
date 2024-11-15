import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SchedulerWithPriorityMap {
    private Map<Integer, List<Task>> priorityMap = new HashMap<>(); // Map for priority management

    // Method to add a task to the priority map
    public void addTaskToPriorityMap(Task task) {
        priorityMap.putIfAbsent(task.getPriority(), new ArrayList<>());
        priorityMap.get(task.getPriority()).add(task);
    }

    // Method to retrieve tasks by priority from the map
    public List<Task> getTasksByPriority(int priority) {
        return priorityMap.getOrDefault(priority, new ArrayList<>());
    }
}
