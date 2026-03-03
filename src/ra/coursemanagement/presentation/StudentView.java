package ra.coursemanagement.presentation;

import org.mindrot.jbcrypt.BCrypt;
import ra.coursemanagement.business.IStudentService;
import ra.coursemanagement.business.impl.StudentServiceImpl;
import ra.coursemanagement.model.Student;

import java.time.LocalDate;
import java.util.Scanner;

public class StudentView {

    public static Student userLogin = null;
    private static final IStudentService studentService = new StudentServiceImpl();
    public static void showMenuLogin(Scanner sc) {
        while (true) {
            System.out.println("===== ĐĂNG NHẬP HỌC VIÊN =====");

            System.out.print("Nhập email: ");
            String email = sc.nextLine().trim();

            System.out.print("Nhập mật khẩu: ");
            String password = sc.nextLine().trim();

            if (email.isEmpty() || password.isEmpty()) {
                System.out.println("❌ Không được để trống!");
                continue;
            }

            Student student = studentService.findByEmail(email);

            if (student == null) {
                System.out.println("❌ Email không tồn tại!");
                continue;
            }

            // So sánh password đã hash
            if (!BCrypt.checkpw(password, student.getPassword())) {
                System.out.println("❌ Sai mật khẩu!");
                continue;
            }

            System.out.println("✅ Đăng nhập thành công!");
            userLogin = student;
            showStudentMenu(sc);
            break;
        }
    }

    public static void showMenuRegister(Scanner sc) {

        System.out.println("===== ĐĂNG KÝ HỌC VIÊN =====");

        System.out.print("Nhập tên sinh viên: ");
        String name = sc.nextLine().trim();

        System.out.print("Nhập ngày sinh (yyyy-MM-dd): ");
        LocalDate dob = LocalDate.parse(sc.nextLine());

        System.out.print("Nhập email: ");
        String email = sc.nextLine().trim();

        if (studentService.findByEmail(email) != null) {
            System.out.println("❌ Email đã tồn tại!");
            return;
        }

        boolean sex;
        while (true) {
            System.out.print("Chọn giới tính (1 - Nam / 0 - Nữ): ");
            String input = sc.nextLine();

            if (input.equals("1")) {
                sex = true;
                break;
            } else if (input.equals("0")) {
                sex = false;
                break;
            } else {
                System.out.println("❌ Vui lòng nhập 1 hoặc 0!");
            }
        }

        System.out.print("Nhập Phone: ");
        String phone = sc.nextLine();

        System.out.print("Nhập mật khẩu: ");
        String pass = sc.nextLine();

        if (name.isEmpty() || email.isEmpty() || phone.isEmpty() || pass.isEmpty()) {
            System.out.println("❌ Không được để trống!");
            return;
        }

        // Hash password
        String hashedPassword = BCrypt.hashpw(pass, BCrypt.gensalt(10));

        Student s = new Student(name, dob, email, sex, phone, hashedPassword);

        studentService.register(s);

        System.out.println("✅ Đăng ký thành công! Vui lòng đăng nhập.");
    }

    private static void showStudentMenu(Scanner sc) {
        while (true) {
            System.out.println("\n===== MENU HỌC VIÊN =====");
            System.out.println("1. Xem danh sách khóa học");
            System.out.println("2. Đăng kí khóa học");
            System.out.println("3. Xem khóa học đã đăng kí");
            System.out.println("4. Hủy đăng kí (nếu chưa bắt đầu)");
            System.out.println("5. Đổi mật khẩu");
            System.out.println("6. Đăng xuất");
            System.out.print("Chọn: ");

            String choice = sc.nextLine();

            switch (choice) {
                case "1":
                    System.out.println("→ Hiển thị danh sách khóa học");
                    break;
                case "2":
                    System.out.println("→ Đăng kí khóa học");
                    break;
                case "3":
                    System.out.println("→ Xem khóa học đã đăng kí");
                    break;
                case "4":
                    System.out.println("Hủy đăng kí...");
                    break;
                case "5":
                    System.out.println("Đổi mật khẩu...");
                    break;
                case "6":
                    System.out.println("Đăng xuất...");
                    break;
                default:
                    System.out.println("❌ Lựa chọn không hợp lệ!");
            }
        }
    }
}