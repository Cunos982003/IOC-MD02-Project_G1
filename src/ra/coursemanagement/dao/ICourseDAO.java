package ra.coursemanagement.dao;

import ra.coursemanagement.exception.MyCheckedException;
import ra.coursemanagement.model.Course;

import java.util.List;

public interface ICourseDAO extends IBaseDAO<Course, Integer> {
    boolean saveCourse(Course course) throws MyCheckedException;
    Course findByName(String name) throws MyCheckedException;
    List<Course> findAllAndPaging(int currentPage, int pageSize) throws MyCheckedException;
    int count() throws MyCheckedException;
}
