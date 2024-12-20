import java.time.LocalDate;

public class Task {
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
