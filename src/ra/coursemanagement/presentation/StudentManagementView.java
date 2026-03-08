package ra.coursemanagement.presentation;

import ra.coursemanagement.business.IStudentService;
import ra.coursemanagement.business.impl.StudentServiceImpl;
import ra.coursemanagement.model.Student;

import java.util.Comparator;
import java.util.List;
import java.util.Scanner;

public class StudentManagementView {

    private static final IStudentService studentService = new StudentServiceImpl();

    public static void showStudentMenu(Scanner sc) {
        while (true) {
            System.out.println("\n====== QUẢN LÝ HỌC VIÊN ======");
            System.out.println("1. Hiển thị danh sách");
            System.out.println("2. Thêm mới học viên");
            System.out.println("3. Chỉnh sửa học viên");
            System.out.println("4. Xóa học viên");
            System.out.println("5. Tìm kiếm");
            System.out.println("6. Sắp xếp");
            System.out.println("0. Quay lại");
            System.out.print("Chọn: ");

            String choice = sc.nextLine();

            switch (choice) {
                case "1":
                    showList();
                    break;
                case "2":
                    addStudent(sc);
                    break;
                case "3":
                    updateStudent(sc);
                    break;
                case "4":
                    deleteStudent(sc);
                    break;
                case "5":
                    searchStudent(sc);
                    break;
                case "6":
                    sortStudent(sc);
                    break;
                case "0":
                    return;
                default:
                    System.out.println("❌ Không hợp lệ!");
            }
        }
    }

    private static void showList() {
        List<Student> list = studentService.findAll();
        printStudentTable(list);
    }

    private static void printStudentTable(List<Student> list) {

        if (list.isEmpty()) {
            System.out.println("Danh sách học viên trống!");
            return;
        }

        System.out.println("\n===== DANH SÁCH HỌC VIÊN =====");

        System.out.printf("%-5s %-20s %-12s %-25s %-6s %-15s\n",
                "ID", "Tên", "Ngày sinh", "Email", "Giới", "SĐT");

        System.out.println("--------------------------------------------------------------------------");

        for (Student s : list) {
            System.out.printf("%-5d %-20s %-12s %-25s %-6s %-15s\n",
                    s.getId(),
                    s.getName(),
                    s.getDob(),
                    s.getEmail(),
                    s.isSex() ? "Nam" : "Nữ",
                    s.getPhone());
        }
    }

    private static void addStudent(Scanner sc) {
        System.out.println("\n===== THÊM HỌC VIÊN =====");

        Student s = new Student();
        s.inputData(sc);

        if (studentService.findByEmail(s.getEmail()) != null) {
            System.out.println("❌ Email đã tồn tại!");
            return;
        }

        studentService.register(s);
        System.out.println("✅ Thêm thành công!");
    }

    private static void updateStudent(Scanner sc) {

        System.out.print("Nhập ID cần sửa: ");
        int id = Integer.parseInt(sc.nextLine());

        Student s = studentService.findById(id);

        if (s == null) {
            System.out.println("❌ Không tìm thấy!");
            return;
        }

        while (true) {

            System.out.println("\n1. Sửa tên");
            System.out.println("2. Sửa email");
            System.out.println("3. Sửa SĐT");
            System.out.println("0. Quay lại");

            String choice = sc.nextLine();

            switch (choice) {

                case "1":
                    System.out.print("Tên mới: ");
                    s.setName(sc.nextLine());
                    break;

                case "2":
                    System.out.print("Email mới: ");
                    s.setEmail(sc.nextLine());
                    break;

                case "3":
                    System.out.print("SĐT mới: ");
                    s.setPhone(sc.nextLine());
                    break;

                case "0":
                    return;

                default:
                    System.out.println("Không hợp lệ!");
                    continue;
            }

            studentService.update(s);
            System.out.println("✅ Cập nhật thành công!");
        }
    }

    private static void deleteStudent(Scanner sc) {

        System.out.print("Nhập ID cần xóa: ");
        int id = Integer.parseInt(sc.nextLine());

        Student s = studentService.findById(id);

        if (s == null) {
            System.out.println("❌ Không tồn tại!");
            return;
        }

        System.out.print("Bạn có chắc chắn xóa? (y/n): ");

        if (sc.nextLine().equalsIgnoreCase("y")) {
            studentService.delete(id);
            System.out.println("✅ Đã xóa!");
        }
    }

    private static void searchStudent(Scanner sc) {

        System.out.print("Nhập từ khóa: ");
        String keyword = sc.nextLine().toLowerCase();

        List<Student> list = studentService.findAll()
                .stream()
                .filter(s ->
                        s.getName().toLowerCase().contains(keyword)
                                || s.getEmail().toLowerCase().contains(keyword)
                                || String.valueOf(s.getId()).contains(keyword))
                .toList();

        printStudentTable(list);
    }

    private static void sortStudent(Scanner sc) {

        List<Student> list = studentService.findAll();

        if (list.isEmpty()) {
            System.out.println("Danh sách học viên trống!");
            return;
        }

        System.out.println("\n===== SẮP XẾP HỌC VIÊN =====");
        System.out.println("1. Theo tên tăng");
        System.out.println("2. Theo tên giảm");
        System.out.println("3. Theo ID tăng");
        System.out.println("4. Theo ID giảm");

        System.out.print("Chọn: ");
        String choice = sc.nextLine();

        switch (choice) {

            case "1":
                list.sort(Comparator.comparing(Student::getName));
                break;

            case "2":
                list.sort(Comparator.comparing(Student::getName).reversed());
                break;

            case "3":
                list.sort(Comparator.comparing(Student::getId));
                break;

            case "4":
                list.sort(Comparator.comparing(Student::getId).reversed());
                break;

            default:
                System.out.println("❌ Không hợp lệ!");
                return;
        }

        printStudentTable(list);
    }
}