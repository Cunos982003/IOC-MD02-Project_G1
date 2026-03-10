package ra.coursemanagement.dao;

import ra.coursemanagement.model.Enrollment;

import java.util.List;

public interface IEnrollmentDAO extends IBaseDAO<Enrollment, Integer> {

    List<Enrollment> findByStudentId(int studentId);

    boolean existsEnrollment(int studentId, int courseId);

    void save(Enrollment enrollment);
}