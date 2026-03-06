package ra.coursemanagement.business.impl;

import org.mindrot.jbcrypt.BCrypt;
import ra.coursemanagement.business.IStudentService;
import ra.coursemanagement.dao.IStudentDAO;
import ra.coursemanagement.dao.impl.StudentDAOImpl;
import ra.coursemanagement.model.Student;

import java.util.List;

public class StudentServiceImpl implements IStudentService {

    private static final IStudentDAO studentDao = new StudentDAOImpl();

    @Override
    public List<Student> findAll() {
        return studentDao.findAll();
    }

    @Override
    public Student findById(Integer id) {
        return studentDao.findById(id);
    }

    @Override
    public boolean update(Student student) {
        return studentDao.update(student);
    }

    @Override
    public boolean delete(Integer id) {
        return studentDao.delete(id);
    }

    @Override
    public void register(Student student) {
        student.setPassword(
                BCrypt.hashpw(student.getPassword(), BCrypt.gensalt(12))
        );
        studentDao.saveStudent(student);
    }

    @Override
    public Student login(String email, String pass) {
        Student s = studentDao.findByEmail(email);
        if (s != null && BCrypt.checkpw(pass, s.getPassword())) {
            return s;
        }
        return null;
    }

    @Override
    public Student findByEmail(String email) {
        return studentDao.findByEmail(email);
    }
}