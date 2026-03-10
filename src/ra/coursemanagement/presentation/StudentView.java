package ra.coursemanagement.presentation;

import org.mindrot.jbcrypt.BCrypt;
import ra.coursemanagement.business.ICourseService;
import ra.coursemanagement.business.IEnrollmentService;
import ra.coursemanagement.business.IStudentService;
import ra.coursemanagement.business.impl.CourseServiceImpl;
import ra.coursemanagement.business.impl.EnrollmentServiceImpl;
import ra.coursemanagement.business.impl.StudentServiceImpl;
import ra.coursemanagement.model.Course;
import ra.coursemanagement.model.Enrollment;
import ra.coursemanagement.model.EnrollmentStatus;
import ra.coursemanagement.model.Student;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Comparator;
import java.util.List;
import java.util.Scanner;

public class StudentView {

    public static Student userLogin = null;
    private static final IStudentService studentService = new StudentServiceImpl();
    private static final ICourseService courseService = new CourseServiceImpl();
    private static final IEnrollmentService enrollmentService = new EnrollmentServiceImpl();
    private static DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
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
                    System.out.println("→ Đăng ký khóa học");
                    registerCourse(sc);
                    break;
                case "4":
                    System.out.println("→ Xem khóa học đã đăng kí");
                    viewRegisteredCourses(sc);
                    break;
                case "5":
                    System.out.println("→ Hủy đăng kí");
                    cancelEnrollment(sc);
                    break;
                case "6":
                    changePassword(sc);
                    break;
                case "7":
                    System.out.println("Đăng xuất...");
                    userLogin = null;
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

        System.out.print("Nhập ID khóa học: ");
        int courseId = Integer.parseInt(sc.nextLine());

        if (enrollmentService.existsEnrollment(userLogin.getId(), courseId)) {
            System.out.println("❌ Bạn đã đăng ký khóa học này!");
            return;
        }

        Enrollment e = new Enrollment();

        e.setStudentId(userLogin.getId());
        e.setCourseId(courseId);
        e.setRegisteredAt(LocalDateTime.now());
        e.setStatus(EnrollmentStatus.WAITING);

        enrollmentService.register(e);

        System.out.println("✅ Đăng ký thành công! (Chờ duyệt)");
    }

    private static void changePassword(Scanner sc) {

        System.out.print("Nhập email hoặc số điện thoại: ");
        String verify = sc.nextLine().trim();

        if (!verify.equals(userLogin.getEmail()) &&
                !verify.equals(userLogin.getPhone())) {

            System.out.println("❌ Xác thực thất bại!");
            return;
        }

        System.out.print("Nhập mật khẩu cũ: ");
        String oldPass = sc.nextLine();

        if (!BCrypt.checkpw(oldPass, userLogin.getPassword())) {
            System.out.println("❌ Mật khẩu cũ không đúng!");
            return;
        }

        System.out.print("Nhập mật khẩu mới: ");
        String newPass = sc.nextLine();

        if (newPass.length() < 6) {
            System.out.println("❌ Mật khẩu phải ≥ 6 ký tự!");
            return;
        }

        String hash = BCrypt.hashpw(newPass, BCrypt.gensalt(10));

        userLogin.setPassword(hash);

        boolean result = studentService.update(userLogin);

        if (result) {
            System.out.println("✅ Đổi mật khẩu thành công!");
        } else {
            System.out.println("❌ Đổi mật khẩu thất bại!");
        }
    }

    private static void viewRegisteredCourses(Scanner sc) {

        List<Enrollment> list =
                enrollmentService.findByStudentId(userLogin.getId());

        if (list.isEmpty()) {
            System.out.println("❌ Bạn chưa đăng ký khóa học nào!");
            return;
        }

        System.out.println("Chọn kiểu sắp xếp:");
        System.out.println("1. Tên khóa học tăng dần");
        System.out.println("2. Tên khóa học giảm dần");
        System.out.println("3. Ngày đăng ký tăng dần");
        System.out.println("4. Ngày đăng ký giảm dần");

        String choice = sc.nextLine();

        switch (choice) {

            case "1":
                list.sort(Comparator.comparing(e ->
                        courseService.findById(e.getCourseId()).getName()));
                break;

            case "2":
                list.sort(Comparator.comparing(
                        (Enrollment e) ->
                                courseService.findById(e.getCourseId()).getName()
                ).reversed());
                break;

            case "3":
                list.sort(Comparator.comparing(Enrollment::getRegisteredAt));
                break;

            case "4":
                list.sort(Comparator.comparing(
                        Enrollment::getRegisteredAt).reversed());
                break;
        }

        System.out.printf("%-5s %-20s %-20s %-10s\n",
                "ID", "Course", "Registered At", "Status");

        for (Enrollment e : list) {

            Course c = courseService.findById(e.getCourseId());

            System.out.printf("%-5d %-20s %-20s %-10s\n",
                    c.getId(),
                    c.getName(),
                    e.getRegisteredAt().format(formatter),
                    e.getStatus());
        }
    }

    private static void cancelEnrollment(Scanner sc) {

        List<Enrollment> list =
                enrollmentService.findByStudentId(userLogin.getId());

        if (list.isEmpty()) {
            System.out.println("❌ Bạn chưa đăng ký khóa học nào!");
            return;
        }

        System.out.print("Nhập ID khóa học muốn hủy: ");
        int courseId = Integer.parseInt(sc.nextLine());

        for (Enrollment e : list) {

            if (e.getCourseId() == courseId) {

                if (e.getStatus() != EnrollmentStatus.WAITING) {
                    System.out.println("❌ Không thể hủy vì khóa học đã được xác nhận!");
                    return;
                }

                e.setStatus(EnrollmentStatus.CANCEL);

                if (enrollmentService.update(e)) {
                    System.out.println("✅ Hủy đăng ký thành công!");
                } else {
                    System.out.println("❌ Hủy thất bại!");
                }

                return;
            }
        }

        System.out.println("❌ Không tìm thấy khóa học đã đăng ký!");
    }

}