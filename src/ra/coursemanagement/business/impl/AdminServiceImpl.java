package ra.coursemanagement.business.impl;

import org.mindrot.jbcrypt.BCrypt;
import ra.coursemanagement.business.IAdminService;
import ra.coursemanagement.dao.IAdminDAO;
import ra.coursemanagement.dao.impl.AdminDAOImpl;
import ra.coursemanagement.exception.MyCheckedException;
import ra.coursemanagement.exception.MyUncheckedException;
import ra.coursemanagement.model.Admin;

import java.util.List;

public class AdminServiceImpl implements IAdminService {

    private final IAdminDAO adminDAO;

    public AdminServiceImpl() {
        this.adminDAO = new AdminDAOImpl();
    }

    @Override
    public List<Admin> findAll() {
        try {
            return adminDAO.findAll();
        } catch (MyCheckedException e) {
            throw new MyUncheckedException("Không thể lấy danh sách admin: " + e.getMessage());
        }
    }

    @Override
    public Admin findById(Integer id) {
        try {
            return adminDAO.findById(id);
        } catch (MyCheckedException e) {
            throw new MyUncheckedException("Không tìm thấy admin: " + e.getMessage());
        }
    }

    @Override
    public boolean update(Admin admin) {
        try {
            return adminDAO.update(admin);
        } catch (MyCheckedException e) {
            throw new MyUncheckedException("Cập nhật admin thất bại: " + e.getMessage());
        }
    }

    @Override
    public boolean delete(Integer id) {
        try {
            return adminDAO.delete(id);
        } catch (MyCheckedException e) {
            throw new MyUncheckedException("Xóa admin thất bại: " + e.getMessage());
        }
    }

    @Override
    public void register(Admin admin) {

        try {

            if (adminDAO.findByUsername(admin.getUsername()) != null) {
                throw new MyUncheckedException("Username đã tồn tại!");
            }

            admin.setPassword(BCrypt.hashpw(admin.getPassword(), BCrypt.gensalt(12)));

            adminDAO.saveAdmin(admin);

        } catch (MyCheckedException e) {
            throw new MyUncheckedException("Đăng ký admin thất bại: " + e.getMessage());
        }
    }

    @Override
    public Admin login(String username, String pass) {

        try {

            Admin admin = adminDAO.findByUsername(username);

            if (admin == null) {
                throw new MyUncheckedException("Username không tồn tại!");
            }

            if (!BCrypt.checkpw(pass, admin.getPassword())) {
                throw new MyUncheckedException("Sai mật khẩu!");
            }

            return admin;

        } catch (MyCheckedException e) {
            throw new MyUncheckedException("Lỗi hệ thống: " + e.getMessage());
        }
    }
}