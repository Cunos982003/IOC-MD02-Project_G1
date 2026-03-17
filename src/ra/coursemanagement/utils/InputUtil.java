package ra.coursemanagement.utils;

import java.util.Scanner;

public class InputUtil {

    private InputUtil() {
    }

    public static int readInt(Scanner sc, String prompt) {
        while (true) {
            System.out.print(prompt);
            String raw = sc.nextLine().trim();
            try {
                return Integer.parseInt(raw);
            } catch (NumberFormatException e) {
                System.out.println(" Vui lòng nhập số hợp lệ!");
            }
        }
    }
}


