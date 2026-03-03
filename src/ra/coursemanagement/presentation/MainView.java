package ra.coursemanagement.presentation;

import org.mindrot.jbcrypt.BCrypt;

import java.util.Scanner;

public class MainView {

    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);
        AdminView adminView = new AdminView();
        StudentView studentView = new StudentView();

        while (true) {
            System.out.println("========== HỆ THỐNG QUẢN LÝ ĐÀO TẠO ==========");
            System.out.println("1. Đăng nhập với tư cách Quản trị viên");
            System.out.println("2. Đăng nhập với tư cách Học viên");
            System.out.println("3. Thoát");
            System.out.println("===============================================");
            System.out.print("Nhập lựa chọn: ");

            String choice = sc.nextLine();

            switch (choice) {
                case "1":
                    adminView.showAdminLogin(sc);
                    break;
                case "2":
                    studentView.showMenuLogin(sc);
                    break;
                case "3":
                    System.out.println("Thoát chương trình...");
                    System.exit(0);
                    break;
                default:
                    System.out.println("❌ Lựa chọn không hợp lệ!\n");
            }
        }
    }
}
