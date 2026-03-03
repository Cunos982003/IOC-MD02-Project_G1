package ra.coursemanagement.business.impl;

import org.mindrot.jbcrypt.BCrypt;
import ra.coursemanagement.business.IStudentService;
import ra.coursemanagement.dao.IStudentDAO;
import ra.coursemanagement.dao.impl.StudentDAOImpl;
import ra.coursemanagement.model.Student;

import java.util.List;

public class StudentServiceImpl implements IStudentService {
    @Override
    public List<Student> findAll() {
        return List.of();
    }

    @Override
    public Student findById(Integer id) {
        return null;
    }

    @Override
    public boolean update(Student student) {
        return false;
    }

    @Override
    public boolean delete(Integer id) {
        return false;
    }

    @Override
    public void register(Student student) {
        student.setPassword(BCrypt.hashpw(student.getPassword(),BCrypt.gensalt(12)));
        studentDao.saveStudent(student);
    }

    private static final IStudentDAO studentDao = new StudentDAOImpl();

    @Override
    public Student login(String email, String pass) {
        Student s = studentDao.findByEmail(email);
        if (s!=null && BCrypt.checkpw(pass,s.getPassword())){
            return s;
        }
        return null;
    }

    public Student findByEmail(String email) {
        return studentDao.findByEmail(email);
    }
}
