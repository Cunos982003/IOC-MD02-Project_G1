package ra.coursemanagement.dao;

import ra.coursemanagement.exception.MyCheckedException;
import ra.coursemanagement.model.Student;

import java.util.List;

public interface IStudentDAO extends IBaseDAO<Student, Integer> {
    void saveStudent(Student student) throws MyCheckedException;
    Student findByEmail(String email) throws MyCheckedException;
    List<Student> findAllAndPaging(int currentPage, int pageSize) throws MyCheckedException;
    int count() throws MyCheckedException;
}
