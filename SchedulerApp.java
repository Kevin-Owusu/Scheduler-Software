import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import java.time.LocalDate;
import java.util.*;

public class SchedulerApp extends Application {
    private Scheduler scheduler = new Scheduler();
    private ListView<String> taskListView = new ListView<>();

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Task Scheduler");

        TextField titleField = new TextField();
        titleField.setPromptText("Task Title");

        DatePicker dueDatePicker = new DatePicker();
        dueDatePicker.setPromptText("Due Date");

        TextField priorityField = new TextField();
        priorityField.setPromptText("Priority");

        Button addButton = new Button("Add Task");
        addButton.setOnAction(e -> {
            String title = titleField.getText();
            LocalDate dueDate = dueDatePicker.getValue();
            try {
                int priority = Integer.parseInt(priorityField.getText());

                Task task = new Task(title, dueDate, priority);
                scheduler.addTask(task);
                updateTaskListView();
                
                titleField.clear();
                dueDatePicker.setValue(null);
                priorityField.clear();
            } catch (NumberFormatException ex) {
                Alert alert = new Alert(Alert.AlertType.ERROR, "Please enter a valid priority number.");
                alert.showAndWait();
            }
        });

        VBox layout = new VBox(10);
        layout.getChildren().addAll(titleField, dueDatePicker, priorityField, addButton, taskListView);

        Scene scene = new Scene(layout, 400, 300);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private void updateTaskListView() {
        taskListView.getItems().clear();
        for (Task task : scheduler.getTasks()) {
            taskListView.getItems().add(task.toString());
        }
    }
}

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

    public int getPriority() {
        return priority;
    }

    @Override
    public String toString() {
        return title + " (Due: " + dueDate + ", Priority: " + priority + ")";
    }
}

// Custom Linked List Implementation in Scheduler
class Scheduler {
    private Queue<Task> taskQueue = new LinkedList<>(); // Queue for task processing
    private LinkedList<Task> tasks = new LinkedList<>(); // Linked list for task storage
    private Map<Integer, List<Task>> priorityMap = new HashMap<>(); // Map for priority management

    public void addTask(Task task) {
        tasks.add(task);  // Add to linked list
        taskQueue.offer(task); // Add to the queue
        
        // Add to priority map
        priorityMap.putIfAbsent(task.getPriority(), new ArrayList<>());
        priorityMap.get(task.getPriority()).add(task);
    }

    public Task getNextTask() {
        return taskQueue.poll();  // Get the next task from the queue
    }

    public List<Task> getTasks() {
        return tasks;
    }

    public List<Task> getTasksByPriority(int priority) {
        return priorityMap.getOrDefault(priority, new ArrayList<>()); // Get tasks by priority
    }
}
