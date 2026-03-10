package ra.coursemanagement.business;

import ra.coursemanagement.exception.MyCheckedException;
import ra.coursemanagement.model.Student;
import java.util.List;

public interface IStudentService extends IBaseService<Student, Integer> {

    Student findByEmail(String email) throws MyCheckedException;
    Student login(String email, String password) throws MyCheckedException;
    List<Student> findAllAndPaging(int currentPage, int pageSize);
    int count() throws MyCheckedException;

}