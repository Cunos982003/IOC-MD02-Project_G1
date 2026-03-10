package ra.coursemanagement.model;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Scanner;

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

    public Integer getId() { return id; }
    public void setId(Integer id) { this.id = id; }

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

    public void inputData(Scanner sc) {

        System.out.print("Nhập tên: ");
        while (true) {
            name = sc.nextLine().trim();
            if (!name.isEmpty()) break;
            System.out.print("❌ Không được để trống. Nhập lại: ");
        }

        while (true) {
            try {
                System.out.print("Nhập ngày sinh (yyyy-MM-dd): ");
                dob = LocalDate.parse(sc.nextLine());
                break;
            } catch (Exception e) {
                System.out.println("❌ Sai định dạng!");
            }
        }

        System.out.print("Nhập email: ");
        while (true) {
            email = sc.nextLine().trim();
            if (!email.isEmpty() && email.contains("@")) break;
            System.out.print("❌ Email không hợp lệ. Nhập lại: ");
        }

        while (true) {
            System.out.print("Giới tính (1-Nam | 0-Nữ): ");
            String input = sc.nextLine();
            if (input.equals("1") || input.equals("0")) {
                sex = input.equals("1");
                break;
            }
            System.out.println("❌ Chỉ nhập 1 hoặc 0");
        }

        System.out.print("Nhập số điện thoại: ");
        phone = sc.nextLine();

        while (true) {
            System.out.print("Nhập mật khẩu: ");
            password = sc.nextLine();
            if (password.length() >= 6) break;
            System.out.println("❌ Mật khẩu >= 6 ký tự");
        }
    }
    @Override
    public String toString() {
        return String.format("%-5d %-20s %-12s %-25s %-6s %-15s %-20s",
                id,
                name,
                dob,
                email,
                (sex ? "Nam" : "Nữ"),
                phone == null ? "" : phone,
                createdAt == null ? "" :
                        createdAt.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"))
        );
    }
}
