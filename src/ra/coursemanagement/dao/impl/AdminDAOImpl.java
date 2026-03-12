package ra.coursemanagement.dao.impl;

import ra.coursemanagement.dao.IAdminDAO;
import ra.coursemanagement.exception.MyCheckedException;
import ra.coursemanagement.model.Admin;
import ra.coursemanagement.utils.DBUtil;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class AdminDAOImpl implements IAdminDAO {
    private Admin mapResultSet(ResultSet rs) throws SQLException {
        return new Admin(
                rs.getInt("id"),
                rs.getString("username"),
                rs.getString("password")
        );
    }

    @Override
    public boolean saveAdmin(Admin admin) throws MyCheckedException {

        String sql = "INSERT INTO admin(username, password) VALUES(?,?)";

        try (
                Connection conn = DBUtil.getConnection();
                PreparedStatement ps = conn.prepareStatement(sql);
        ) {

            ps.setString(1, admin.getUsername());
            ps.setString(2, admin.getPassword());

            return ps.executeUpdate() > 0;

        } catch (SQLException e) {
            throw new MyCheckedException("Lỗi khi thêm admin", e);
        }
    }

    @Override
    public List<Admin> findAll() throws MyCheckedException {

        List<Admin> admins = new ArrayList<>();
        String sql = "SELECT * FROM admin";

        try (Connection conn = DBUtil.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                admins.add(mapResultSet(rs));
            }

        } catch (SQLException e) {
            throw new MyCheckedException("Lỗi lấy danh sách admin", e);
        }

        return admins;
    }

    @Override
    public Admin findById(Integer id) throws MyCheckedException {

        String sql = "SELECT * FROM admin WHERE id = ?";

        try (Connection conn = DBUtil.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                return mapResultSet(rs);
            }

        } catch (SQLException e) {
            throw new MyCheckedException("Lỗi tìm admin theo ID", e);
        }

        return null;
    }

    @Override
    public Admin findByUsername(String username) throws MyCheckedException {

        String sql = "SELECT * FROM admin WHERE username = ?";

        try (Connection conn = DBUtil.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, username);

            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                return mapResultSet(rs);
            }

        } catch (SQLException e) {
            throw new MyCheckedException("Lỗi tìm admin theo username", e);
        }

        return null;
    }

    @Override
    public boolean update(Admin admin) throws MyCheckedException {

        String sql = "UPDATE admin SET username = ?, password = ? WHERE id = ?";

        try (Connection conn = DBUtil.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, admin.getUsername());
            ps.setString(2, admin.getPassword());
            ps.setInt(3, admin.getId());

            return ps.executeUpdate() > 0;

        } catch (SQLException e) {
            throw new MyCheckedException("Lỗi cập nhật admin", e);
        }
    }

    @Override
    public boolean delete(Integer id) throws MyCheckedException {

        String sql = "DELETE FROM admin WHERE id = ?";

        try (Connection conn = DBUtil.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, id);

            return ps.executeUpdate() > 0;

        } catch (SQLException e) {
            throw new MyCheckedException("Lỗi xóa admin", e);
        }
    }
}
