package ra.coursemanagement.business.impl;

import ra.coursemanagement.business.ICourseService;
import ra.coursemanagement.dao.ICourseDAO;
import ra.coursemanagement.dao.impl.CourseDAOImpl;
import ra.coursemanagement.exception.MyCheckedException;
import ra.coursemanagement.exception.MyUncheckedException;
import ra.coursemanagement.model.Course;

import java.util.List;
import java.util.stream.Collectors;

public class CourseServiceImpl implements ICourseService {

    private final ICourseDAO courseDAO = new CourseDAOImpl();

    @Override
    public int count() {

        try {
            return courseDAO.count();
        } catch (MyCheckedException e) {
            throw new MyUncheckedException("Lỗi đếm course: " + e.getMessage());
        }

    }

    @Override
    public List<Course> findAll() {
        try {
            return courseDAO.findAll();
        } catch (MyCheckedException e) {
            throw new MyUncheckedException("Không thể lấy danh sách course: " + e.getMessage());
        }
    }

    @Override
    public List<Course> findAllAndPaging(int currentPage, int pageSize) {

        if (currentPage <= 0 || pageSize <= 0) {
            throw new MyUncheckedException("Page không hợp lệ");
        }

        try {
            return courseDAO.findAllAndPaging(currentPage, pageSize);
        } catch (MyCheckedException e) {
            throw new MyUncheckedException("Lỗi phân trang course: " + e.getMessage());
        }
    }

    @Override
    public Course findById(Integer id) {
        try {
            return courseDAO.findById(id);
        } catch (MyCheckedException e) {
            throw new MyUncheckedException("Không tìm thấy course: " + e.getMessage());
        }
    }

    @Override
    public boolean update(Course course) {
        try {
            return courseDAO.update(course);
        } catch (MyCheckedException e) {
            throw new MyUncheckedException("Cập nhật course thất bại: " + e.getMessage());
        }
    }

    @Override
    public boolean delete(Integer id) {
        try {
            return courseDAO.delete(id);
        } catch (MyCheckedException e) {
            throw new MyUncheckedException("Xóa course thất bại: " + e.getMessage());
        }
    }

    @Override
    public void register(Course course) {

        try {

            if (courseDAO.findByName(course.getName()) != null) {
                throw new MyUncheckedException("Course đã tồn tại!");
            }

            courseDAO.saveCourse(course);

        } catch (MyCheckedException e) {
            throw new MyUncheckedException("Thêm course thất bại: " + e.getMessage());
        }
    }

    @Override
    public Course findByName(String name) {
        try {
            return courseDAO.findByName(name);
        } catch (MyCheckedException e) {
            throw new MyUncheckedException("Không tìm thấy course: " + e.getMessage());
        }
    }

    @Override
    public List<Course> searchByName(String keyword) {

        try {

            return courseDAO.findAll()
                    .stream()
                    .filter(c -> c.getName()
                            .toLowerCase()
                            .contains(keyword.toLowerCase()))
                    .collect(Collectors.toList());

        } catch (MyCheckedException e) {
            throw new MyUncheckedException("Lỗi tìm kiếm course: " + e.getMessage());
        }
    }
}