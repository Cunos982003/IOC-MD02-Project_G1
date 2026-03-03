package ra.coursemanagement.model;

import java.time.LocalDateTime;

public class Course {

    private int id;
    private String name;
    private int duration;
    private String instructor;
    private LocalDateTime createdAt;

    public Course() {
    }

    public Course(int id, String name, int duration, String instructor, LocalDateTime createdAt) {
        this.id = id;
        this.name = name;
        this.duration = duration;
        this.instructor = instructor;
        this.createdAt = createdAt;
    }

    public Course(String name, int duration, String instructor) {
        this.name = name;
        this.duration = duration;
        this.instructor = instructor;
    }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public int getDuration() { return duration; }
    public void setDuration(int duration) { this.duration = duration; }

    public String getInstructor() { return instructor; }
    public void setInstructor(String instructor) { this.instructor = instructor; }

    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }

    @Override
    public String toString() {
        return String.format("%-5d | %-25s | %-5d giờ | %-20s",
                id, name, duration, instructor);
    }
}
