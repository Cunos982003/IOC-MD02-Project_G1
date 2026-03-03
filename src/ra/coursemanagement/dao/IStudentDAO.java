package ra.coursemanagement.dao;

import ra.coursemanagement.model.Student;

import java.util.List;

public interface IStudentDAO extends IBaseDAO<Student, Integer> {
    void saveStudent(Student student);
    Student findByEmail(String email);
    Student login(String email, String password);
    List<Student> searchByName(String keyword);
}
