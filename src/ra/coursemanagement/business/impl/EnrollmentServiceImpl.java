package ra.coursemanagement.business.impl;

import ra.coursemanagement.business.IEnrollmentService;
import ra.coursemanagement.dao.IEnrollmentDAO;
import ra.coursemanagement.dao.impl.EnrollmentDAOImpl;
import ra.coursemanagement.exception.MyCheckedException;
import ra.coursemanagement.exception.MyUncheckedException;
import ra.coursemanagement.model.Enrollment;
import ra.coursemanagement.model.EnrollmentStatus;

import java.util.List;

public class EnrollmentServiceImpl implements IEnrollmentService {

    private final IEnrollmentDAO enrollmentDAO = new EnrollmentDAOImpl();

    @Override
    public List<Enrollment> findAll() {

        try {
            return enrollmentDAO.findAll();
        } catch (MyCheckedException e) {
            throw new MyUncheckedException("Không thể lấy danh sách enrollment", e);
        }
    }

    @Override
    public Enrollment findById(Integer id) {

        try {
            return enrollmentDAO.findById(id);
        } catch (MyCheckedException e) {
            throw new MyUncheckedException("Không tìm thấy enrollment", e);
        }
    }

    @Override
    public boolean update(Enrollment e) {

        try {
            Enrollment old = enrollmentDAO.findById(e.getId());
            if (old == null) {
                throw new MyUncheckedException("Enrollment không tồn tại!");
            }
            if (old.getStatus() != EnrollmentStatus.WAITING) {
                throw new MyUncheckedException("Chỉ được cập nhật enrollment có trạng thái WAITING!");
            }
            return enrollmentDAO.update(e);

        } catch (MyCheckedException ex) {
            throw new MyUncheckedException("Cập nhật enrollment thất bại", ex);
        }
    }

    @Override
    public boolean delete(Integer id) {
        try {
            Enrollment e = enrollmentDAO.findById(id);
            if (e == null) {
                throw new MyUncheckedException("Enrollment không tồn tại!");
            }
            if (e.getStatus() == EnrollmentStatus.CONFIRM) {
                throw new MyUncheckedException("Không thể hủy khóa đã CONFIRM!");
            }

            return enrollmentDAO.delete(id);

        } catch (MyCheckedException ex) {
            throw new MyUncheckedException("Xóa enrollment thất bại", ex);
        }
    }

    @Override
    public List<Enrollment> findByStudentId(int studentId) {
        try {
            return enrollmentDAO.findByStudentId(studentId);
        } catch (MyCheckedException e) {
            throw new MyUncheckedException("Không thể lấy khóa học của student", e);
        }
    }

    @Override
    public boolean existsEnrollment(int studentId, int courseId) {

        try {
            return enrollmentDAO.existsEnrollment(studentId, courseId);
        } catch (MyCheckedException e) {
            throw new MyUncheckedException("Lỗi kiểm tra enrollment", e);
        }
    }

    @Override
    public void register(Enrollment e) {

        try {
            if (enrollmentDAO.existsEnrollment(e.getStudentId(), e.getCourseId())) {
                throw new MyUncheckedException("Bạn đã đăng ký khóa học này!");
            }
            enrollmentDAO.save(e);
        } catch (MyCheckedException ex) {
            throw new MyUncheckedException("Đăng ký khóa học thất bại", ex);
        }
    }
    @Override
    public List<Enrollment> findAllAndPaging(int currentPage, int pageSize) {

        try {
            return enrollmentDAO.findAllAndPaging(currentPage, pageSize);
        } catch (MyCheckedException e) {
            throw new MyUncheckedException("Paging enrollment lỗi", e);
        }
    }
    @Override
    public List<Enrollment> findByCourseId(int courseId) {

        try {
            return enrollmentDAO.findByCourseId(courseId);
        } catch (MyCheckedException e) {
            throw new MyUncheckedException("Không lấy được enrollment", e);
        }
    }

    @Override
    public List<Enrollment> findByCourseIdAndPaging(int courseId, int currentPage, int pageSize) {
        try {
            return enrollmentDAO.findByCourseIdAndPaging(courseId, currentPage, pageSize);
        } catch (MyCheckedException e) {
            throw new MyUncheckedException("Không lấy được enrollment theo course (paging)", e);
        }
    }

    @Override
    public int countByCourseId(int courseId) {
        try {
            return enrollmentDAO.countByCourseId(courseId);
        } catch (MyCheckedException e) {
            throw new MyUncheckedException("Không thể đếm enrollment theo course", e);
        }
    }

    @Override
    public List<Enrollment> findWaitingAndPaging(int currentPage, int pageSize) {
        try {
            return enrollmentDAO.findWaitingAndPaging(currentPage, pageSize);
        } catch (MyCheckedException e) {
            throw new MyUncheckedException("Không thể lấy danh sách enrollment WAITING (paging)", e);
        }
    }

    @Override
    public int countWaiting() {
        try {
            return enrollmentDAO.countWaiting();
        } catch (MyCheckedException e) {
            throw new MyUncheckedException("Không thể đếm enrollment WAITING", e);
        }
    }
}