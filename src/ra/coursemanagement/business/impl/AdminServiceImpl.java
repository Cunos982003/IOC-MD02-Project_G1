package ra.coursemanagement.business.impl;

import org.mindrot.jbcrypt.BCrypt;
import ra.coursemanagement.business.IAdminService;
import ra.coursemanagement.dao.IAdminDAO;
import ra.coursemanagement.dao.impl.AdminDAOImpl;
import ra.coursemanagement.model.Admin;

import java.util.List;

public class AdminServiceImpl implements IAdminService {

    private final IAdminDAO adminDAO;

    public AdminServiceImpl() {
        this.adminDAO = new AdminDAOImpl();
    }

    @Override
    public List<Admin> findAll() {
        return adminDAO.findAll();
    }

    @Override
    public Admin findById(Integer id) {
        return adminDAO.findById(id);
    }

    @Override
    public boolean update(Admin admin) {
        return adminDAO.update(admin);
    }

    @Override
    public boolean delete(Integer id) {
        return adminDAO.delete(id);
    }

    @Override
    public void register(Admin admin) {
        admin.setPassword(BCrypt.hashpw(admin.getPassword(), BCrypt.gensalt(12)));
        adminDAO.saveAdmin(admin);
    }

    @Override
    public Admin login(String username, String pass) {

        Admin admin = adminDAO.findByUsername(username);

        if (admin != null && BCrypt.checkpw(pass, admin.getPassword())) {
            return admin;
        }

        return null;
    }

    @Override
    public Admin findByUsername(String username) {
        return adminDAO.findByUsername(username);
    }
}