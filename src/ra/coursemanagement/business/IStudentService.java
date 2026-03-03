package ra.coursemanagement.business;

import ra.coursemanagement.model.Student;

public interface IStudentService extends IBaseService<Student,Integer> {
Student findByEmail(String email);
Student login(String email, String pass);

}
