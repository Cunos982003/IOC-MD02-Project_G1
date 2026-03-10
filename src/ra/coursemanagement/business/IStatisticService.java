package ra.coursemanagement.business;

import java.util.Map;
import java.util.List;

public interface IStatisticService {

    int countCourse();

    int countStudent();

    Map<String, Integer> studentCountByCourse();

    List<String> top5Course();

    List<String> courseMoreThan10();
}