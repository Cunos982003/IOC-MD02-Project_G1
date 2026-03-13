package ra.coursemanagement.business;

import ra.coursemanagement.model.Enrollment;

import java.util.List;

public interface IEnrollmentService extends IBaseService<Enrollment,Integer>{
    List<Enrollment> findByStudentId(int studentId);
    List<Enrollment> findByCourseId(int courseId);
    List<Enrollment> findByCourseIdAndPaging(int courseId, int currentPage, int pageSize);
    int countByCourseId(int courseId);
    boolean existsEnrollment(int studentId,int courseId);
    void register(Enrollment enrollment);
    List<Enrollment> findAllAndPaging(int currentPage, int pageSize);
    List<Enrollment> findWaitingAndPaging(int currentPage, int pageSize);
    int countWaiting();
}