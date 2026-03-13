package ra.coursemanagement.business.impl;

import org.mindrot.jbcrypt.BCrypt;
import ra.coursemanagement.business.IStudentService;
import ra.coursemanagement.dao.IStudentDAO;
import ra.coursemanagement.dao.impl.StudentDAOImpl;
import ra.coursemanagement.exception.MyCheckedException;
import ra.coursemanagement.exception.MyUncheckedException;
import ra.coursemanagement.model.Student;

import java.util.List;

public class StudentServiceImpl implements IStudentService {

    private static final IStudentDAO studentDao = new StudentDAOImpl();

    @Override
    public List<Student> findAll() {

        try {
            return studentDao.findAll();
        } catch (MyCheckedException e) {
            throw new MyUncheckedException("Không thể lấy danh sách student", e);
        }
    }

    @Override
    public Student findById(Integer id) {

        try {
            return studentDao.findById(id);
        } catch (MyCheckedException e) {
            throw new MyUncheckedException("Không tìm thấy student", e);
        }
    }

    @Override
    public boolean update(Student student) {

        try {
            return studentDao.update(student);
        } catch (MyCheckedException e) {
            throw new MyUncheckedException("Cập nhật student thất bại", e);
        }
    }

    @Override
    public boolean delete(Integer id) {

        try {
            return studentDao.delete(id);
        } catch (MyCheckedException e) {
            throw new MyUncheckedException("Xóa student thất bại", e);
        }
    }

    @Override
    public void register(Student student) {

        try {
            if (studentDao.findByEmail(student.getEmail()) != null) {
                throw new MyUncheckedException("Email đã tồn tại!");
            }

            student.setPassword(
                    BCrypt.hashpw(student.getPassword(), BCrypt.gensalt(12))
            );

            studentDao.saveStudent(student);

        } catch (MyCheckedException e) {
            throw new MyUncheckedException("Đăng ký thất bại", e);
        }
    }

    @Override
    public Student login(String email, String pass) {

        try {

            Student s = studentDao.findByEmail(email);

            if (s != null && BCrypt.checkpw(pass, s.getPassword())) {
                return s;
            }

            return null;

        } catch (MyCheckedException e) {
            throw new MyUncheckedException("Lỗi đăng nhập", e);
        }
    }

    @Override
    public Student findByEmail(String email) {

        try {
            return studentDao.findByEmail(email);
        } catch (MyCheckedException e) {
            throw new MyUncheckedException("Không tìm thấy email", e);
        }
    }

    @Override
    public List<Student> findAllAndPaging(int currentPage, int pageSize) {

        try {
            return studentDao.findAllAndPaging(currentPage, pageSize);
        } catch (MyCheckedException e) {
            throw new MyUncheckedException("Lỗi phân trang student", e);
        }
    }

    @Override
    public int count() {

        try {
            return studentDao.count();
        } catch (MyCheckedException e) {
            throw new MyUncheckedException("Không thể đếm student", e);
        }
    }

}