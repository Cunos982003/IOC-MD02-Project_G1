package ra.coursemanagement.dao.impl;

import ra.coursemanagement.dao.IStudentDAO;
import ra.coursemanagement.exception.MyCheckedException;
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
    public List<Student> findAll() throws MyCheckedException {
        List<Student> list = new ArrayList<>();
        String sql = "SELECT * FROM student";

        try (Connection conn = DBUtil.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                list.add(mapResultSet(rs));
            }

        } catch (SQLException e) {
            throw new MyCheckedException("Lỗi lấy danh sách student", e);
        }

        return list;
    }

    @Override
    public Student findById(Integer id) throws MyCheckedException {
        String sql = "SELECT * FROM student WHERE id = ?";

        try (Connection conn = DBUtil.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return mapResultSet(rs);
                }
            }

        } catch (SQLException e) {
            throw new MyCheckedException("Lỗi tìm student theo ID", e);
        }

        return null;
    }

    @Override
    public void saveStudent(Student student) throws MyCheckedException {
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
            throw new MyCheckedException("Lỗi thêm student", e);
        }
    }


    @Override
    public boolean update(Student student) throws MyCheckedException {
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
            throw new MyCheckedException("Lỗi cập nhật student", e);
        }
    }

    @Override
    public boolean delete(Integer id) throws MyCheckedException {
        String sql = "DELETE FROM student WHERE id = ?";

        try (Connection conn = DBUtil.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, id);
            return ps.executeUpdate() > 0;

        } catch (SQLException e) {
            throw new MyCheckedException("Lỗi xóa student", e);
        }
    }

    @Override
    public Student findByEmail(String email) throws MyCheckedException {
        String sql = "SELECT * FROM student WHERE email = ?";

        try (Connection conn = DBUtil.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, email);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return mapResultSet(rs);
                }
            }

        } catch (SQLException e) {
            throw new MyCheckedException("Lỗi tìm student theo email", e);
        }

        return null;
    }

    @Override
    public int count() throws MyCheckedException {

        String sql = "SELECT COUNT(*) FROM student";

        try (Connection conn = DBUtil.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                return rs.getInt(1);
            }

        } catch (Exception e) {
            throw new MyCheckedException("Lỗi đếm student", e);
        }

        return 0;
    }

    @Override
    public List<Student> findAllAndPaging(int currentPage, int pageSize) throws MyCheckedException {

        List<Student> list = new ArrayList<>();

        String sql = "SELECT * FROM student ORDER BY id LIMIT ? OFFSET ?";

        try (Connection conn = DBUtil.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            int offset = (currentPage - 1) * pageSize;

            ps.setInt(1, pageSize);
            ps.setInt(2, offset);

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    Student s = new Student();
                    s.setId(rs.getInt("id"));
                    s.setName(rs.getString("name"));
                    s.setDob(rs.getDate("dob").toLocalDate());
                    s.setEmail(rs.getString("email"));
                    s.setSex(rs.getBoolean("sex"));
                    s.setPhone(rs.getString("phone"));
                    list.add(s);
                }
            }

        } catch (Exception e) {
            throw new MyCheckedException("Lỗi paging student", e);
        }

        return list;
    }

}
