package ra.coursemanagement.business.impl;

import ra.coursemanagement.business.IEnrollmentService;
import ra.coursemanagement.dao.IEnrollmentDAO;
import ra.coursemanagement.dao.impl.EnrollmentDAOImpl;
import ra.coursemanagement.model.Enrollment;

import java.util.List;

public class EnrollmentServiceImpl implements IEnrollmentService {

    private final IEnrollmentDAO enrollmentDAO = new EnrollmentDAOImpl();

    @Override
    public List<Enrollment> findAll() {
        return enrollmentDAO.findAll();
    }

    @Override
    public Enrollment findById(Integer id) {
        return enrollmentDAO.findById(id);
    }

    @Override
    public boolean update(Enrollment e) {
        return enrollmentDAO.update(e);
    }

    @Override
    public boolean delete(Integer id) {
        return enrollmentDAO.delete(id);
    }


    @Override
    public List<Enrollment> findByStudentId(int studentId) {
        return enrollmentDAO.findByStudentId(studentId);
    }

    @Override
    public boolean existsEnrollment(int studentId, int courseId) {
        return enrollmentDAO.existsEnrollment(studentId,courseId);
    }

    @Override
    public void register(Enrollment e) {
        enrollmentDAO.save(e);
    }
}