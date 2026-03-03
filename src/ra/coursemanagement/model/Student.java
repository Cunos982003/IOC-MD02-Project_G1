package ra.coursemanagement.model;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class Student {

    private Integer id;
    private String name;
    private LocalDate dob;
    private String email;
    private boolean sex;
    private String phone;
    private String password;
    private LocalDateTime createdAt;

    public Student() {
    }

    public Student(int id, String name, LocalDate dob, String email,
                   boolean sex, String phone, String password,
                   LocalDateTime createdAt) {
        this.id = id;
        this.name = name;
        this.dob = dob;
        this.email = email;
        this.sex = sex;
        this.phone = phone;
        this.password = password;
        this.createdAt = createdAt;
    }

    public Student(String name, LocalDate dob, String email,
                   boolean sex, String phone, String password) {
        this.name = name;
        this.dob = dob;
        this.email = email;
        this.sex = sex;
        this.phone = phone;
        this.password = password;
    }

    // Getter & Setter

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public LocalDate getDob() { return dob; }
    public void setDob(LocalDate dob) { this.dob = dob; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public boolean isSex() { return sex; }
    public void setSex(boolean sex) { this.sex = sex; }

    public String getPhone() { return phone; }
    public void setPhone(String phone) { this.phone = phone; }

    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }

    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }

    @Override
    public String toString() {
        return String.format("%-5d | %-20s | %-15s | %-25s | %-5s",
                id, name, dob, email, (sex ? "Nam" : "Nữ"));
    }
}
