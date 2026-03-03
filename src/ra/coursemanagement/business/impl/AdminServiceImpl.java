package ra.coursemanagement.business.impl;

import org.mindrot.jbcrypt.BCrypt;
import ra.coursemanagement.dao.IAdminDAO;
import ra.coursemanagement.dao.impl.AdminDAOImpl;
import ra.coursemanagement.model.Admin;
import ra.coursemanagement.business.IAdminService;

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
        admin.setPassword(BCrypt.hashpw(admin.getPassword(),BCrypt.gensalt(12)));
        adminDao.saveAdmin(admin);
    }

    private static final IAdminDAO adminDao = new AdminDAOImpl();

    @Override
    public Admin login(String username, String pass) {
        Admin a = adminDao.findByUsername(username);
        if (a!=null && BCrypt.checkpw(pass,a.getPassword())){
            return a;
        }
        return null;
    }

    @Override
    public Admin findByUsername(String username) {
        return null;
    }
}
