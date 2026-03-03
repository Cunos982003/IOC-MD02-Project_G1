package ra.coursemanagement.presentation;

import java.util.Scanner;

public class CourseView {
    private final Scanner scanner = new Scanner(System.in);

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
                    // gọi service.findAll()
                    break;
                case "2":
                    // thêm
                    break;
                case "3":
                    // update
                    break;
                case "4":
                    // delete
                    break;
                case "5":
                    // search
                    break;
                case "6":
                    // sort
                    break;
                case "7":
                    return;
                default:
                    System.out.println("Lựa chọn không hợp lệ!");
            }
        }
    }
}
