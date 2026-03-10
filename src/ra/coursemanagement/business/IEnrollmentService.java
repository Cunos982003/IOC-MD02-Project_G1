package ra.coursemanagement.business;

import ra.coursemanagement.model.Enrollment;

import java.util.List;

public interface IEnrollmentService extends IBaseService<Enrollment,Integer>{

    List<Enrollment> findByStudentId(int studentId);

    boolean existsEnrollment(int studentId,int courseId);

    void register(Enrollment enrollment);

}