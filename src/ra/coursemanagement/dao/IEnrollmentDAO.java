package ra.coursemanagement.dao;

import ra.coursemanagement.exception.MyCheckedException;
import ra.coursemanagement.model.Enrollment;

import java.util.List;

public interface IEnrollmentDAO extends IBaseDAO<Enrollment, Integer> {
    List<Enrollment> findByStudentId(int studentId) throws MyCheckedException;
    List<Enrollment> findByCourseId(int courseId) throws MyCheckedException;
    boolean existsEnrollment(int studentId, int courseId) throws MyCheckedException;
    void save(Enrollment enrollment) throws MyCheckedException;
    List<Enrollment> findAllAndPaging(int currentPage, int pageSize) throws MyCheckedException;
}