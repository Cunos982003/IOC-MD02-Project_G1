package ra.coursemanagement.dao;


import ra.coursemanagement.exception.MyCheckedException;
import ra.coursemanagement.model.Admin;

public interface IAdminDAO extends IBaseDAO<Admin, Integer> {
    Admin findByUsername(String username) throws MyCheckedException;
    boolean saveAdmin(Admin admin) throws MyCheckedException;
}