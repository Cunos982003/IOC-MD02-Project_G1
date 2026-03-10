package ra.coursemanagement.dao;

import ra.coursemanagement.exception.MyCheckedException;

import java.util.List;
import java.util.Map;

public interface IStatisticDAO {
    int countCourse() throws MyCheckedException;
    int countStudent() throws MyCheckedException;
    Map<String, Integer> studentCountByCourse() throws MyCheckedException;
    List<String> top5Course() throws MyCheckedException;
    List<String> courseMoreThan10() throws MyCheckedException;
}
