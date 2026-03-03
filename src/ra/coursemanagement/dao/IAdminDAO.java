package ra.coursemanagement.dao;


import ra.coursemanagement.model.Admin;

public interface IAdminDAO extends IBaseDAO<Admin, Integer> {
    Admin findByUsername(String username);
    Admin login(String username, String password);
    void saveAdmin(Admin admin);
    Admin findByEmail(String email);
}