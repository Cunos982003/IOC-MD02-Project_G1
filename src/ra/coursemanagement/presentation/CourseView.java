package ra.coursemanagement.presentation;

import ra.coursemanagement.business.ICourseService;
import ra.coursemanagement.business.impl.CourseServiceImpl;
import ra.coursemanagement.exception.MyCheckedException;
import ra.coursemanagement.model.Course;

import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.List;
import java.util.Scanner;

public class CourseView {
    private final Scanner scanner = new Scanner(System.in);
    private final ICourseService courseService = new CourseServiceImpl();
    private int currentPage = 1;
    private final int pageSize = 5;

    public void showCourseMenu() {
        while (true) {
            System.out.println("\n===== QUẢN LÝ KHÓA HỌC =====");
            System.out.println("1. Hiển thị danh sách");
            System.out.println("2. Thêm mới");
            System.out.println("3. Chỉnh sửa");
            System.out.println("4. Xóa");
            System.out.println("5. Tìm kiếm");
            System.out.println("6. Sắp xếp");
            System.out.println("7. Quay lại");
            System.out.print("Chọn: ");

            String choice = scanner.nextLine();

            switch (choice) {
                case "1":
                    showList();
                    break;

                case "2":
                    addCourse();
                    break;

                case "3":
                    updateCourse();
                    break;

                case "4":
                    deleteCourse();
                    break;

                case "5":
                    searchCourse();
                    break;

                case "6":
                    sortCourse();
                    break;

                case "7":
                    return;

                default:
                    System.out.println("❌ Lựa chọn không hợp lệ!");
            }
        }
    }

    private void showList() {

        while (true) {

            try {

                List<Course> list = courseService.findAllAndPaging(currentPage, pageSize);

                if (list.isEmpty()) {
                    System.out.println("Danh sách khóa học trống!");
                    return;
                }

                printTable(list);

            } catch (MyCheckedException e) {
                System.out.println("❌ Lỗi: " + e.getMessage());
                return;
            }

            System.out.println();
            System.out.println("1. Previous page");
            System.out.println("2. Back");
            System.out.println("3. Next page");

            System.out.print("Enter choice: ");

            String choice = scanner.nextLine();

            switch (choice) {

                case "1":
                    if (currentPage > 1) {
                        currentPage--;
                    } else {
                        System.out.println("❌ Đang ở trang đầu!");
                    }
                    break;

                case "2":
                    currentPage = 1;
                    return;

                case "3":
                    currentPage++;
                    break;

                default:
                    System.out.println("❌ Lựa chọn không hợp lệ!");
            }
        }
    }

    private void printPageNumber() throws MyCheckedException {

        int total = courseService.count();

        int totalPage = (int) Math.ceil((double) total / pageSize);

        for (int i = 1; i <= totalPage; i++) {

            if (i == currentPage) {
                System.out.print("[" + i + "] ");
            } else {
                System.out.print(i + " ");
            }
        }

        System.out.println();
    }

    private void printTable(List<Course> list) throws MyCheckedException {

        System.out.println("\n===== DANH SÁCH KHÓA HỌC =====");

        System.out.printf("%-5s %-20s %-10s %-20s %-20s\n",
                "ID", "Tên", "Duration", "Instructor", "Created At");

        System.out.println("--------------------------------------------------------------------");

        for (Course c : list) {
            System.out.printf("%-5d %-20s %-10d %-20s %-20s\n",
                    c.getId(),
                    c.getName(),
                    c.getDuration(),
                    c.getInstructor(),
                    c.getCreatedAt());
        }

        printPageNumber();
    }

    private void addCourse() {

        System.out.println("\n===== THÊM KHÓA HỌC =====");

        String name;

        while (true) {
            System.out.print("Tên khóa học: ");
            name = scanner.nextLine().trim();

            if (name.isEmpty()) {
                System.out.println("❌ Tên không được để trống!");
            } else if (name.length() < 3) {
                System.out.println("❌ Tên phải ≥ 3 ký tự!");
            } else if (courseService.findByName(name) != null) {
                System.out.println("❌ Khóa học đã tồn tại!");
            } else {
                break;
            }
        }

        int duration;

        while (true) {
            try {
                System.out.print("Thời lượng: ");
                duration = Integer.parseInt(scanner.nextLine());

                if (duration <= 0) {
                    System.out.println("❌ Duration phải > 0!");
                } else {
                    break;
                }

            } catch (NumberFormatException e) {
                System.out.println("❌ Phải nhập số!");
            }
        }

        String instructor;

        while (true) {
            System.out.print("Giảng viên: ");
            instructor = scanner.nextLine().trim();

            if (instructor.isEmpty()) {
                System.out.println("❌ Instructor không được để trống!");
            } else {
                break;
            }
        }

        Course c = new Course();
        c.setName(name);
        c.setDuration(duration);
        c.setInstructor(instructor);
        c.setCreatedAt(LocalDateTime.now());

        courseService.register(c);

        System.out.println("✅ Thêm thành công!");
    }

    private void updateCourse() {

        int id;

        try {
            System.out.print("Nhập ID cần sửa: ");
            id = Integer.parseInt(scanner.nextLine());
        } catch (NumberFormatException e) {
            System.out.println("❌ ID phải là số!");
            return;
        }

        Course c = courseService.findById(id);

        if (c == null) {
            System.out.println("❌ Không tìm thấy khóa học!");
            return;
        }

        while (true) {

            System.out.println("\n1. Sửa tên");
            System.out.println("2. Sửa duration");
            System.out.println("3. Sửa instructor");
            System.out.println("0. Quay lại");
            System.out.print("Chọn: ");

            String choice = scanner.nextLine();

            switch (choice) {

                case "1":
                    while (true) {
                        System.out.print("Tên mới: ");
                        String name = scanner.nextLine().trim();

                        if (name.isEmpty()) {
                            System.out.println("❌ Không được để trống!");
                        } else {
                            c.setName(name);
                            break;
                        }
                    }
                    break;

                case "2":
                    while (true) {
                        try {
                            System.out.print("Duration mới: ");
                            int duration = Integer.parseInt(scanner.nextLine());

                            if (duration <= 0) {
                                System.out.println("❌ Duration phải > 0!");
                            } else {
                                c.setDuration(duration);
                                break;
                            }

                        } catch (NumberFormatException e) {
                            System.out.println("❌ Phải nhập số!");
                        }
                    }
                    break;

                case "3":
                    while (true) {
                        System.out.print("Instructor mới: ");
                        String ins = scanner.nextLine().trim();

                        if (ins.isEmpty()) {
                            System.out.println("❌ Không được để trống!");
                        } else {
                            c.setInstructor(ins);
                            break;
                        }
                    }
                    break;

                case "0":
                    return;

                default:
                    System.out.println("❌ Không hợp lệ!");
                    continue;
            }

            courseService.update(c);
            System.out.println("✅ Cập nhật thành công!");
        }
    }

    private void deleteCourse() {

        int id;

        try {
            System.out.print("Nhập ID cần xóa: ");
            id = Integer.parseInt(scanner.nextLine());
        } catch (NumberFormatException e) {
            System.out.println("❌ ID phải là số!");
            return;
        }

        Course c = courseService.findById(id);

        if (c == null) {
            System.out.println("❌ Không tồn tại!");
            return;
        }

        System.out.print("Bạn có chắc chắn xóa? (y/n): ");

        if (scanner.nextLine().equalsIgnoreCase("y")) {

            courseService.delete(id);

            System.out.println("✅ Đã xóa!");
        }
    }

    private void searchCourse() {

        System.out.print("Nhập tên khóa học: ");
        String keyword = scanner.nextLine().trim();

        if (keyword.isEmpty()) {
            System.out.println("❌ Không được để trống!");
            return;
        }

        List<Course> list = courseService.searchByName(keyword);

        if (list.isEmpty()) {
            System.out.println("❌ Không tìm thấy khóa học!");
        } else {
            try {
                printTable(list);
            } catch (MyCheckedException e) {
                System.out.println("❌ Lỗi: " + e.getMessage());
            }
        }
    }

    private void sortCourse() {

        List<Course> list = courseService.findAll();

        System.out.println("\n1. Tên tăng");
        System.out.println("2. Tên giảm");
        System.out.println("3. ID tăng");
        System.out.println("4. ID giảm");
        System.out.print("Chọn: ");

        String choice = scanner.nextLine();

        switch (choice) {

            case "1":
                list.sort(Comparator.comparing(Course::getName));
                break;

            case "2":
                list.sort(Comparator.comparing(Course::getName).reversed());
                break;

            case "3":
                list.sort(Comparator.comparing(Course::getId));
                break;

            case "4":
                list.sort(Comparator.comparing(Course::getId).reversed());
                break;

            default:
                System.out.println("Không hợp lệ!");
                return;
        }

        try {
            printTable(list);
        } catch (MyCheckedException e) {
            System.out.println("❌ Lỗi: " + e.getMessage());
        }
    }


}
