package ra.coursemanagement.presentation;

import ra.coursemanagement.business.IEnrollmentService;
import ra.coursemanagement.business.ICourseService;
import ra.coursemanagement.business.IStudentService;
import ra.coursemanagement.business.impl.EnrollmentServiceImpl;
import ra.coursemanagement.business.impl.CourseServiceImpl;
import ra.coursemanagement.business.impl.StudentServiceImpl;
import ra.coursemanagement.exception.MyUncheckedException;
import ra.coursemanagement.model.*;
import ra.coursemanagement.utils.InputUtil;
import ra.coursemanagement.utils.PaginationUtil;

import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Scanner;

public class EnrollmentView {

    private static final Scanner sc = new Scanner(System.in);

    private static final IEnrollmentService enrollmentService =
            new EnrollmentServiceImpl();

    private static final ICourseService courseService =
            new CourseServiceImpl();

    private static final IStudentService studentService =
            new StudentServiceImpl();

    private static final int PAGE_SIZE = 5;

    public static void showEnrollmentMenu() {

        while (true) {

            System.out.println("\n===== QUẢN LÝ ĐĂNG KÝ KHÓA HỌC =====");
            System.out.println("1. Hiển thị sinh viên theo khóa học");
            System.out.println("2. Duyệt đăng ký");
            System.out.println("3. Xóa đăng ký");
            System.out.println("0. Quay lại");

            System.out.print("Chọn: ");
            String choice = sc.nextLine();

            switch (choice) {

                case "1":
                    showStudentByCourse();
                    break;

                case "2":
                    approveEnrollment();
                    break;

                case "3":
                    deleteEnrollment(sc);
                    break;

                case "0":
                    return;

                default:
                    System.out.println(" Không hợp lệ!");
            }
        }
    }

    private static void showStudentByCourse() {

        int courseId = InputUtil.readInt(sc, "Nhập ID khóa học: ");

        int currentPage = 1;
        int total = 0;
        int totalPage = 0;

        DateTimeFormatter formatter =
                DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");

        while (true) {

            try {
                total = enrollmentService.countByCourseId(courseId);
            } catch (Exception e) {
                System.out.println(" Lỗi đếm enrollment: " + e.getMessage());
                return;
            }

            if (total == 0) {
                System.out.println(" Không có sinh viên đăng ký!");
                return;
            }

            totalPage = PaginationUtil.totalPages(total, PAGE_SIZE);
            currentPage = PaginationUtil.clampPage(currentPage, totalPage);

            List<Enrollment> pageList =
                    enrollmentService.findByCourseIdAndPaging(courseId, currentPage, PAGE_SIZE);

            System.out.printf("\n%-5s %-20s %-20s %-12s\n",
                    "ID", "Student", "Registered", "Status");

            for (Enrollment e : pageList) {

                Student s = studentService.findById(e.getStudentId());

                System.out.printf("%-5d %-20s %-20s %-12s\n",
                        e.getId(),
                        s.getName(),
                        e.getRegisteredAt().format(formatter),
                        e.getStatus());
            }

            System.out.println("\nTrang: " + currentPage + "/" + totalPage);
            System.out.println("1. Previous");
            System.out.println("2. Next");
            System.out.println("0. Thoát");

            String nav = sc.nextLine();

            switch (nav) {

                case "1":
                    if (currentPage > 1) currentPage--;
                    else System.out.println(" Trang đầu!");
                    break;

                case "2":
                    if (currentPage < totalPage) currentPage++;
                    else System.out.println(" Trang cuối!");
                    break;

                case "0":
                    return;
            }
        }
    }

    private static void approveEnrollment() {
        int currentPage = 1;
        int total = 0;
        int totalPage = 0;

        DateTimeFormatter formatter =
                DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");

        while (true) {
            try {
                total = enrollmentService.countWaiting();
                if (total == 0) {
                    System.out.println(" Không có enrollment nào đang WAITING!");
                    return;
                }
                totalPage = PaginationUtil.totalPages(total, PAGE_SIZE);
                currentPage = PaginationUtil.clampPage(currentPage, totalPage);

                List<Enrollment> list =
                        enrollmentService.findWaitingAndPaging(currentPage, PAGE_SIZE);

                System.out.printf("\n%-5s %-20s %-20s %-20s %-12s\n",
                        "ID", "Student", "Course", "Registered", "Status");

                for (Enrollment e : list) {
                    Student s = studentService.findById(e.getStudentId());
                    Course c = courseService.findById(e.getCourseId());

                    System.out.printf("%-5d %-20s %-20s %-20s %-12s\n",
                            e.getId(),
                            s.getName(),
                            c.getName(),
                            e.getRegisteredAt().format(formatter),
                            e.getStatus());
                }

                System.out.println("\nTrang: " + currentPage + "/" + totalPage);
                System.out.println("1. Previous");
                System.out.println("2. Next");
                System.out.println("3. Duyệt theo ID");
                System.out.println("0. Thoát");
                System.out.print("Chọn: ");

                String nav = sc.nextLine();

                switch (nav) {
                    case "1":
                        if (currentPage > 1) currentPage--;
                        else System.out.println(" Trang đầu!");
                        break;
                    case "2":
                        if (currentPage < totalPage) currentPage++;
                        else System.out.println(" Trang cuối!");
                        break;
                    case "3":
                        int id = InputUtil.readInt(sc, "Nhập ID enrollment (WAITING): ");

                        Enrollment e = enrollmentService.findById(id);
                        if (e == null) {
                            System.out.println(" Không tồn tại!");
                            break;
                        }
                        if (e.getStatus() != EnrollmentStatus.WAITING) {
                            System.out.println(" Chỉ được duyệt enrollment có status WAITING!");
                            break;
                        }

                        System.out.println("1. Confirm");
                        System.out.println("2. Denied");
                        System.out.print("Chọn: ");

                        String choice = sc.nextLine();
                        switch (choice) {
                            case "1":
                                e.setStatus(EnrollmentStatus.CONFIRM);
                                break;
                            case "2":
                                e.setStatus(EnrollmentStatus.DENIED);
                                break;
                            default:
                                System.out.println(" Không hợp lệ!");
                                break;
                        }

                        try {
                            if (enrollmentService.update(e)) {
                                System.out.println(" Cập nhật thành công!");
                            } else {
                                System.out.println(" Thất bại!");
                            }
                        } catch (MyUncheckedException ex) {
                            System.out.println(" " + ex.getMessage());
                        }
                        break;
                    case "0":
                        return;
                    default:
                        System.out.println(" Không hợp lệ!");
                }
            } catch (Exception ex) {
                System.out.println(" Lỗi: " + ex.getMessage());
            }
        }
    }

    private static void deleteEnrollment(Scanner sc) {

        int id = InputUtil.readInt(sc, "Nhập ID enrollment: ");

        System.out.print("Bạn chắc chắn xóa? (y/n): ");

        if (sc.nextLine().equalsIgnoreCase("y")) {

            try {

                if (enrollmentService.delete(id)) {
                    System.out.println(" Đã xóa!");
                } else {
                    System.out.println(" Xóa thất bại!");
                }

            } catch (MyUncheckedException e) {
                System.out.println(" " + e.getMessage());
            }

        }
    }
}