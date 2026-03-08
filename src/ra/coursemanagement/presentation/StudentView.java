package ra.coursemanagement.presentation;

import org.mindrot.jbcrypt.BCrypt;
import ra.coursemanagement.business.ICourseService;
import ra.coursemanagement.business.IStudentService;
import ra.coursemanagement.business.impl.CourseServiceImpl;
import ra.coursemanagement.business.impl.StudentServiceImpl;
import ra.coursemanagement.model.Course;
import ra.coursemanagement.model.Student;

import java.time.LocalDate;
import java.util.List;
import java.util.Scanner;

public class StudentView {

    public static Student userLogin = null;
    private static final IStudentService studentService = new StudentServiceImpl();
    private static final ICourseService courseService = new CourseServiceImpl();
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
            System.out.println("2. Tìm kiếm khóa học");
            System.out.println("3. Đăng ký khóa học");
            System.out.println("4. Xem khóa học đã đăng ký");
            System.out.println("5. Hủy đăng ký");
            System.out.println("6. Đổi mật khẩu");
            System.out.println("7. Đăng xuất");
            System.out.print("Chọn: ");

            String choice = sc.nextLine();

            switch (choice) {
                case "1":
                    System.out.println("→ Hiển thị danh sách khóa học");
                    showCourseList();
                    break;
                case "2":
                    System.out.println("→ Tìm kiếm khóa học");
                    searchCourse(sc);
                    break;
                case "3":
                    System.out.println("→ Xem khóa học đã đăng kí");
                    registerCourse(sc);
                    break;
                case "4":
                    System.out.println("Hủy đăng kí...");
                    break;
                case "5":
                    System.out.println("Đổi mật khẩu...");
                    break;
                case "6":
                    changePassword(sc);
                    break;
                case "7":
                    System.out.println("Đăng xuất...");
                    return;
                default:
                    System.out.println("❌ Lựa chọn không hợp lệ!");
            }
        }
    }
    private static void showCourseList() {

        List<Course> list = courseService.findAll();

        if (list.isEmpty()) {
            System.out.println("Không có khóa học nào!");
            return;
        }

        System.out.printf("%-5s %-20s %-10s %-20s\n",
                "ID", "Tên khóa học", "Duration", "Instructor");

        for (Course c : list) {
            System.out.printf("%-5d %-20s %-10d %-20s\n",
                    c.getId(),
                    c.getName(),
                    c.getDuration(),
                    c.getInstructor());
        }
    }

    private static void searchCourse(Scanner sc) {

        System.out.print("Nhập tên khóa học: ");
        String keyword = sc.nextLine();

        List<Course> list = courseService.searchByName(keyword);

        if (list.isEmpty()) {
            System.out.println("❌ Không tìm thấy!");
            return;
        }

        System.out.printf("%-5s %-20s %-10s %-20s\n",
                "ID", "Tên khóa học", "Duration", "Instructor");

        for (Course c : list) {
            System.out.printf("%-5d %-20s %-10d %-20s\n",
                    c.getId(),
                    c.getName(),
                    c.getDuration(),
                    c.getInstructor());
        }
    }

    private static void registerCourse(Scanner sc) {

        showCourseList();

        System.out.print("Nhập ID khóa học muốn đăng ký: ");
        int id = Integer.parseInt(sc.nextLine());

        Course course = courseService.findById(id);

        if (course == null) {
            System.out.println("❌ Khóa học không tồn tại!");
            return;
        }

        System.out.println("✅ Đăng ký khóa học thành công!");
    }

    private static void changePassword(Scanner sc) {

        System.out.print("Nhập mật khẩu cũ: ");
        String oldPass = sc.nextLine();

        if (!BCrypt.checkpw(oldPass, userLogin.getPassword())) {
            System.out.println("❌ Mật khẩu cũ không đúng!");
            return;
        }

        System.out.print("Nhập mật khẩu mới: ");
        boolean result = studentService.update(userLogin);
        String newPass = sc.nextLine();

        String hash = BCrypt.hashpw(newPass, BCrypt.gensalt(10));

        userLogin.setPassword(hash);

        studentService.update(userLogin);

        if (result) {
            System.out.println("✅ Đổi mật khẩu thành công!");
        } else {
            System.out.println("❌ Đổi mật khẩu thất bại!");
        }

    }

}