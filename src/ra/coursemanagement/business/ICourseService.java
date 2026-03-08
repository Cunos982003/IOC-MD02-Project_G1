package ra.coursemanagement.business;

import ra.coursemanagement.model.Course;

import java.util.List;

public interface ICourseService extends IBaseService<Course, Integer> {
    Course findByName(String name);
    List<Course> searchByName(String keyword);
}