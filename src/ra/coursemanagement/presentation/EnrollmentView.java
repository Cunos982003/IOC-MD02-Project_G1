package ra.coursemanagement.presentation;

import java.util.Scanner;

public class EnrollmentView {
    private final Scanner scanner = new Scanner(System.in);

    public void showEnrollmentMenu() {
        while (true) {
            System.out.println("\n===== QUẢN LÝ ĐĂNG KÝ =====");
            System.out.println("1. Hiển thị học viên theo khóa");
            System.out.println("2. Thêm đăng ký");
            System.out.println("3. Xóa đăng ký");
            System.out.println("4. Quay lại");
            System.out.print("Chọn: ");

            String choice = scanner.nextLine();

            switch (choice) {
                case "1":
                    break;
                case "2":
                    break;
                case "3":
                    break;
                case "4":
                    return;
                default:
                    System.out.println("Lựa chọn không hợp lệ!");
            }
        }
    }
}
