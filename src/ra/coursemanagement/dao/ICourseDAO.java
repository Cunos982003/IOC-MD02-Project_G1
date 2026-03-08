package ra.coursemanagement.dao;

import ra.coursemanagement.model.Course;

public interface ICourseDAO extends IBaseDAO<Course, Integer> {
    boolean save(Course course);
    Course findByName(String name);
}
