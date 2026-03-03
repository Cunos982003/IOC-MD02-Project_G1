package ra.coursemanagement.presentation;

import java.util.Scanner;

public class StatisticView {
    private final Scanner scanner = new Scanner(System.in);

    public void showStatisticMenu() {
        while (true) {
            System.out.println("\n===== THỐNG KÊ =====");
            System.out.println("1. Tổng số khóa học và học viên");
            System.out.println("2. Học viên từng khóa");
            System.out.println("3. Top 5 khóa học đông nhất");
            System.out.println("4. Liệt kê khóa học > 10 học viên");
            System.out.println("5. Quay lại");
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
                    break;
                case "5":
                    return;
                default:
                    System.out.println("Lựa chọn không hợp lệ!");
            }
        }
    }
}
