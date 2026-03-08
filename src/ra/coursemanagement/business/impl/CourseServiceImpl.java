package ra.coursemanagement.business.impl;

import ra.coursemanagement.business.ICourseService;
import ra.coursemanagement.dao.ICourseDAO;
import ra.coursemanagement.dao.impl.CourseDAOImpl;
import ra.coursemanagement.model.Course;

import java.util.List;
import java.util.stream.Collectors;

public class CourseServiceImpl implements ICourseService {

    private final ICourseDAO courseDAO = new CourseDAOImpl();

    @Override
    public List<Course> findAll() {
        return courseDAO.findAll();
    }

    @Override
    public Course findById(Integer id) {
        return courseDAO.findById(id);
    }

    @Override
    public boolean update(Course course) {
        return courseDAO.update(course);
    }

    @Override
    public boolean delete(Integer id) {
        return courseDAO.delete(id);
    }

    @Override
    public void register(Course course) {
        courseDAO.save(course);
    }

    @Override
    public Course findByName(String name) {
        return courseDAO.findByName(name);
    }

    @Override
    public List<Course> searchByName(String keyword) {

        return courseDAO.findAll()
                .stream()
                .filter(c -> c.getName().toLowerCase().contains(keyword.toLowerCase()))
                .collect(Collectors.toList());
    }
}