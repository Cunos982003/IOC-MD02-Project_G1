package ra.coursemanagement.dao.impl;

import ra.coursemanagement.dao.IStudentDAO;
import ra.coursemanagement.model.Student;
import ra.coursemanagement.utils.DBUtil;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class StudentDAOImpl implements IStudentDAO {

    private Student mapResultSet(ResultSet rs) throws SQLException {
        return new Student(
                rs.getInt("id"),
                rs.getString("name"),
                rs.getDate("dob").toLocalDate(),
                rs.getString("email"),
                rs.getBoolean("sex"),
                rs.getString("phone"),
                rs.getString("password"),
                rs.getTimestamp("created_at").toLocalDateTime()
        );
    }

    @Override
    public List<Student> findAll() {
        List<Student> list = new ArrayList<>();
        String sql = "SELECT * FROM student";

        try (Connection conn = DBUtil.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                list.add(mapResultSet(rs));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return list;
    }

    @Override
    public Student findById(Integer id) {
        String sql = "SELECT * FROM student WHERE id = ?";

        try (Connection conn = DBUtil.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                return mapResultSet(rs);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }

    @Override
    public void saveStudent(Student student) {
        String sql = "INSERT INTO STUDENT(name, dob, email, sex, phone, password)" +
                " values(?,?,?,?,?,?)";
        try (
                Connection conn = DBUtil.getConnection();
                PreparedStatement pre = conn.prepareStatement(sql);
        ) {
            pre.setString(1, student.getName());
            pre.setDate(2, Date.valueOf(student.getDob()));
            pre.setString(3, student.getEmail());
            pre.setBoolean(4, student.isSex());
            pre.setString(5, student.getPhone());
            pre.setString(6, student.getPassword());
            pre.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    @Override
    public boolean update(Student student) {
        String sql = """
                UPDATE student
                SET name=?, dob=?, email=?, sex=?, phone=?, password=?
                WHERE id=?
                """;

        try (Connection conn = DBUtil.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, student.getName());
            ps.setDate(2, Date.valueOf(student.getDob()));
            ps.setString(3, student.getEmail());
            ps.setBoolean(4, student.isSex());
            ps.setString(5, student.getPhone());
            ps.setString(6, student.getPassword());
            ps.setInt(7, student.getId());

            return ps.executeUpdate() > 0;

        } catch (SQLException e) {
            System.out.println("Lỗi cập nhật student: " + e.getMessage());
        }

        return false;
    }

    @Override
    public boolean delete(Integer id) {
        String sql = "DELETE FROM student WHERE id = ?";

        try (Connection conn = DBUtil.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, id);
            return ps.executeUpdate() > 0;

        } catch (SQLException e) {
            System.out.println("Lỗi xóa student: " + e.getMessage());
        }

        return false;
    }

    @Override
    public Student findByEmail(String email) {
        String sql = "SELECT * FROM student WHERE email = ?";

        try (Connection conn = DBUtil.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, email);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                return mapResultSet(rs);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }

    @Override
    public Student login(String email, String password) {
        String sql = "SELECT * FROM student WHERE email = ? AND password = ?";

        try (Connection conn = DBUtil.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, email);
            ps.setString(2, password);

            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                return mapResultSet(rs);
            }

        } catch (SQLException e) {
            System.out.println("Lỗi login student: " + e.getMessage());
        }

        return null;
    }

    @Override
    public List<Student> searchByName(String keyword) {
        List<Student> list = new ArrayList<>();
        String sql = "SELECT * FROM student WHERE name ILIKE ?";

        try (Connection conn = DBUtil.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, "%" + keyword + "%");
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                list.add(mapResultSet(rs));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return list;
    }

    @Override
    public List<Student> sort(String field, String direction) {

        List<Student> list = new ArrayList<>();

        String sql = "SELECT * FROM student ORDER BY " + field + " " + direction;
        if (!field.equals("name") && !field.equals("id")) {
            field = "id";
        }

        if (!direction.equalsIgnoreCase("ASC") &&
                !direction.equalsIgnoreCase("DESC")) {
            direction = "ASC";
        }

        try (Connection conn = DBUtil.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                list.add(mapResultSet(rs));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return list;
    }

}
