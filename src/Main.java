import org.mindrot.jbcrypt.BCrypt;
import ra.coursemanagement.exception.MyCheckedException;
import ra.coursemanagement.model.Student;
import ra.coursemanagement.presentation.StudentView;

import java.time.LocalDate;
import java.util.Scanner;

import static ra.coursemanagement.presentation.StudentView.studentService;

public class Main {
    public static void main(String[] args) throws MyCheckedException {
        Scanner sc = new Scanner(System.in);
        while (true){
            System.out.println("""
                    ------------------------MENU--------------------------
                    1. Đăng ký sinh viên
                    2. Đăng nhập sinh viên
                    3. Thoát
                    """);
            System.out.println("Nhap lua chon : ");
            int choice = Integer.parseInt(sc.nextLine());

            switch (choice){
                case 1:
                    showMenuRegister(sc);
                    break;
                case 2:
                    StudentView.showMenuLogin(sc);
                    break;
                case 3:
                    System.out.println("Thoat chuong trình");
                    sc.close();
                    return;
                default:

            }
        }
    }

    public static void showMenuRegister(Scanner sc) throws MyCheckedException {

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

        String hashedPassword = BCrypt.hashpw(pass, BCrypt.gensalt(10));
        Student s = new Student(name, dob, email, sex, phone, hashedPassword);
        studentService.register(s);
        System.out.println("✅ Đăng ký thành công! Vui lòng đăng nhập.");
    }
}