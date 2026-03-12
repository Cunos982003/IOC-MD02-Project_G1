package ra.coursemanagement.presentation;

import ra.coursemanagement.business.IStatisticService;
import ra.coursemanagement.business.impl.StatisticServiceImpl;

import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class StatisticView {

    private static final Scanner scanner = new Scanner(System.in);
    private static final IStatisticService statisticService = new StatisticServiceImpl();

    public static void showStatisticMenu() {

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

                    int courseTotal = statisticService.countCourse();
                    int studentTotal = statisticService.countStudent();

                    System.out.println("Tổng khóa học: " + courseTotal);
                    System.out.println("Tổng học viên: " + studentTotal);
                    break;

                case "2":

                    Map<String, Integer> map =
                            statisticService.studentCountByCourse();

                    map.forEach((course, count) ->
                            System.out.println(course + " : " + count));

                    break;

                case "3":

                    List<String> top5 =
                            statisticService.top5Course();

                    top5.forEach(System.out::println);

                    break;

                case "4":

                    List<String> list =
                            statisticService.courseMoreThan10();

                    list.forEach(System.out::println);

                    break;

                case "5":
                    return;

                default:
                    System.out.println(" Lựa chọn không hợp lệ!");
            }
        }
    }
}
