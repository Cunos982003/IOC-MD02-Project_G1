package ra.coursemanagement.presentation;

import ra.coursemanagement.business.IAdminService;
import ra.coursemanagement.business.impl.AdminServiceImpl;
import ra.coursemanagement.model.Admin;

import java.util.Scanner;

public class AdminView {

    private static final IAdminService adminService = new AdminServiceImpl();

    public static void showAdminLogin(Scanner sc) {

        while (true) {
            System.out.println("===== ĐĂNG NHẬP QUẢN TRỊ VIÊN =====");

            System.out.print("Username: ");
            String username = sc.nextLine().trim();

            System.out.print("Password: ");
            String password = sc.nextLine().trim();

            if (username.isEmpty() || password.isEmpty()) {
                System.out.println("❌ Không được để trống!");
                continue;
            }

            Admin admin = adminService.login(username, password);

            if (admin == null) {
                System.out.println("❌ Sai email hoặc mật khẩu!");
            } else {
                System.out.println("✅ Đăng nhập thành công!");
                showAdminMenu(sc);
                break;
            }
        }
    }

    private static void showAdminMenu(Scanner sc) {
        while (true) {
            System.out.println("\n=========== MENU ADMIN ===========");
            System.out.println("1. Quản lý khóa học");
            System.out.println("2. Quản lý học viên");
            System.out.println("3. Quản lý đăng ký học");
            System.out.println("4. Thống kê học viên theo khóa học");
            System.out.println("5. Đăng xuất");
            System.out.print("Chọn: ");

            String choice = sc.nextLine();

            switch (choice) {
                case "1":
                    System.out.println("→ Quản lý khóa học");
                    break;
                case "2":
                    System.out.println("→ Quản lý học viên");
                    break;
                case "3":
                    System.out.println("→ Quản lý đăng ký học");
                    break;
                case "4":
                    System.out.println("→ Thống kê học viên theo khóa học");
                    break;
                case "5":
                    System.out.println("Đăng xuất...");
                    return;
                default:
                    System.out.println("❌ Lựa chọn không hợp lệ!");
            }
        }
    }
}