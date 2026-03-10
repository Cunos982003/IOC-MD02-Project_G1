import ra.coursemanagement.exception.MyCheckedException;
import ra.coursemanagement.presentation.StudentView;

import java.util.Scanner;
public class Main {
    public static void main(String[] args) throws MyCheckedException {
        Scanner sc = new Scanner(System.in);
        while (true){
            System.out.println("""
                    ------------------------MENU--------------------------
                    1. Đăng ký sinh viên
                    2. Đăng nhập sinh viên
                    3. Thoát
                    """);
            System.out.println("Nhap lua chon : ");
            int choice = Integer.parseInt(sc.nextLine());

            switch (choice){
                case 1:
                    StudentView.showMenuRegister(sc);
                    break;
                case 2:
                    StudentView.showMenuLogin(sc);
                    break;
                case 3:
                    System.out.println("Thoat chuong trình");
                    sc.close();
                    return;
                default:

            }
        }
    }
}