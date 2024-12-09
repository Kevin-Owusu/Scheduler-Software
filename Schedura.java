import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.*;

public class Schedura extends Application {
    private Scheduler scheduler = new Scheduler();
    private ListView<String> taskListView = new ListView<>();

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Task Scheduler");

        // Set up fonts and styles
        Font labelFont = Font.font("Arial", 14);
        Font buttonFont = Font.font("Arial", 16);

        // Title Field
        TextField titleField = new TextField();
        titleField.setPromptText("Task Title");
        titleField.setStyle("-fx-border-color: #ccc; -fx-background-color: #f9f9f9;");
        titleField.setPadding(new Insets(5));

        // Due Date Picker
        DatePicker dueDatePicker = new DatePicker();
        dueDatePicker.setPromptText("Due Date");
        dueDatePicker.setStyle("-fx-border-color: #ccc; -fx-background-color: #f9f9f9;");
        dueDatePicker.setPadding(new Insets(5));

        // Priority ComboBox
        ComboBox<String> priorityComboBox = new ComboBox<>();
        priorityComboBox.getItems().addAll("Low", "Medium", "High");
        priorityComboBox.setPromptText("Priority");
        priorityComboBox.setStyle("-fx-border-color: #ccc; -fx-background-color: #f9f9f9;");
        priorityComboBox.setPadding(new Insets(5));

        // Category ComboBox
        ComboBox<String> categoryComboBox = new ComboBox<>();
        categoryComboBox.getItems().addAll("Work", "Personal", "School", "Other");
        categoryComboBox.setPromptText("Category");
        categoryComboBox.setStyle("-fx-border-color: #ccc; -fx-background-color: #f9f9f9;");
        categoryComboBox.setPadding(new Insets(5));

        // Tags Field
        TextField tagField = new TextField();
        tagField.setPromptText("Tags (comma separated)");
        tagField.setStyle("-fx-border-color: #ccc; -fx-background-color: #f9f9f9;");
        tagField.setPadding(new Insets(5));

        // Recurring Checkbox
        CheckBox recurringCheckBox = new CheckBox("Recurring");

        // Progress Slider
        Slider progressSlider = new Slider(0, 100, 0);
        progressSlider.setBlockIncrement(10);
        progressSlider.setShowTickMarks(true);
        progressSlider.setShowTickLabels(true);
        progressSlider.setStyle("-fx-color: #42a5f5;");
        progressSlider.setPadding(new Insets(5));

        // Add Button
        Button addButton = new Button("Add Task");
        addButton.setFont(buttonFont);
        addButton.setStyle("-fx-background-color: #4CAF50; -fx-text-fill: white;");
        addButton.setPadding(new Insets(10));
        addButton.setOnAction(e -> {
            String title = titleField.getText();
            LocalDate dueDate = dueDatePicker.getValue();
            String category = categoryComboBox.getValue();
            String tags = tagField.getText();
            String priority = priorityComboBox.getValue();
            boolean isRecurring = recurringCheckBox.isSelected();
            double progress = progressSlider.getValue();

            Task task = new Task(title, dueDate, priority, category, tags, progress, isRecurring);
            scheduler.addTask(task);
            updateTaskListView();

            // Clear fields
            titleField.clear();
            dueDatePicker.setValue(null);
            priorityComboBox.setValue(null);
            categoryComboBox.setValue(null);
            tagField.clear();
            recurringCheckBox.setSelected(false);
            progressSlider.setValue(0);
        });

        // Edit and Delete Buttons
        Button editButton = new Button("Edit Task");
        Button deleteButton = new Button("Delete Task");
        editButton.setFont(buttonFont);
        deleteButton.setFont(buttonFont);
        editButton.setStyle("-fx-background-color: #FFA500; -fx-text-fill: white;");
        deleteButton.setStyle("-fx-background-color: #F44336; -fx-text-fill: white;");
        editButton.setPadding(new Insets(10));
        deleteButton.setPadding(new Insets(10));

        // Button actions
        editButton.setOnAction(e -> {
            Task selectedTask = getSelectedTask();
            if (selectedTask != null) {
                // Implement task editing logic
                showAlert("Edit Task", "Editing task: " + selectedTask.getTitle());
            }
        });

        deleteButton.setOnAction(e -> {
            Task selectedTask = getSelectedTask();
            if (selectedTask != null) {
                scheduler.removeTask(selectedTask);
                updateTaskListView();
            }
        });

        // Task List Section
        taskListView.setStyle("-fx-border-color: #ccc;");
        taskListView.setPrefHeight(200);

        // Layout setup (using GridPane for better structure)
        GridPane layout = new GridPane();
        layout.setVgap(10);
        layout.setHgap(10);
        layout.setPadding(new Insets(15));
        layout.add(new Text("Task Scheduler"), 0, 0, 2, 1);
        layout.add(new Label("Task Title:"), 0, 1);
        layout.add(titleField, 1, 1);
        layout.add(new Label("Due Date:"), 0, 2);
        layout.add(dueDatePicker, 1, 2);
        layout.add(new Label("Priority:"), 0, 3);
        layout.add(priorityComboBox, 1, 3);
        layout.add(new Label("Category:"), 0, 4);
        layout.add(categoryComboBox, 1, 4);
        layout.add(new Label("Tags:"), 0, 5);
        layout.add(tagField, 1, 5);
        layout.add(recurringCheckBox, 0, 6, 2, 1);
        layout.add(new Label("Progress:"), 0, 7);
        layout.add(progressSlider, 1, 7);
        layout.add(addButton, 0, 8, 2, 1);
        layout.add(editButton, 0, 9);
        layout.add(deleteButton, 1, 9);
        layout.add(taskListView, 0, 10, 2, 1);

        // Set up scene and show stage
        Scene scene = new Scene(layout, 500, 600);
        scene.setFill(Color.LIGHTGRAY);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private void updateTaskListView() {
        taskListView.getItems().clear();
        for (Task task : scheduler.getTasks()) {
            taskListView.getItems().add(task.toString());
            checkTaskDueDate(task);
        }
    }

    private Task getSelectedTask() {
        String selectedTaskString = taskListView.getSelectionModel().getSelectedItem();
        if (selectedTaskString != null) {
            return scheduler.getTaskByTitle(selectedTaskString.split(" ")[0]);
        }
        return null;
    }

    private void checkTaskDueDate(Task task) {
        if (task.getDueDate() != null) {
            long daysUntilDue = ChronoUnit.DAYS.between(LocalDate.now(), task.getDueDate());
            if (daysUntilDue == 1) {
                showAlert("Reminder", "Task '" + task.getTitle() + "' is due tomorrow!");
            } else if (daysUntilDue < 0) {
                showAlert("Overdue", "Task '" + task.getTitle() + "' is overdue!");
            }
        }
    }

    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION, message);
        alert.setTitle(title);
        alert.showAndWait();
    }
}

// Task class with added fields for category, tags, progress, and recurring
class Task {
    private String title;
    private LocalDate dueDate;
    private String priority;
    private String category;
    private String tags;
    private double progress;
    private boolean isRecurring;

    public Task(String title, LocalDate dueDate, String priority, String category, String tags, double progress, boolean isRecurring) {
        this.title = title;
        this.dueDate = dueDate;
        this.priority = priority;
        this.category = category;
        this.tags = tags;
        this.progress = progress;
        this.isRecurring = isRecurring;
    }

    public String getTitle() {
        return title;
    }

    public LocalDate getDueDate() {
        return dueDate;
    }

    public String getPriority() {
        return priority;
    }

    public String getCategory() {
        return category;
    }

    public String getTags() {
        return tags;
    }

    public double getProgress() {
        return progress;
    }

    public boolean isRecurring() {
        return isRecurring;
    }

    @Override
    public String toString() {
        return title + " (Due: " + dueDate + ", Priority: " + priority + ", Category: " + category + ", Progress: " + progress + "%, Tags: " + tags + ", Recurring: " + isRecurring + ")";
    }
}

// Scheduler class to manage tasks
class Scheduler {
    private List<Task> tasks = new ArrayList<>();

    public void addTask(Task task) {
        tasks.add(task);
    }

    public void removeTask(Task task) {
        tasks.remove(task);
    }

    public List<Task> getTasks() {
        return tasks;
    }

    public Task getTaskByTitle(String title) {
        for (Task task : tasks) {
            if (task.getTitle().equals(title)) {
                return task;
            }
        }
        return null;
    }
}
