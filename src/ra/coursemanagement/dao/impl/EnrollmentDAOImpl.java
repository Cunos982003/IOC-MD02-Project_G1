package ra.coursemanagement.dao.impl;

import ra.coursemanagement.dao.IEnrollmentDAO;
import ra.coursemanagement.exception.MyCheckedException;
import ra.coursemanagement.model.Enrollment;
import ra.coursemanagement.model.EnrollmentStatus;
import ra.coursemanagement.utils.DBUtil;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class EnrollmentDAOImpl implements IEnrollmentDAO {
    private Enrollment mapResultSet(ResultSet rs) throws SQLException {

        Enrollment e = new Enrollment();

        e.setId(rs.getInt("id"));
        e.setStudentId(rs.getInt("student_id"));
        e.setCourseId(rs.getInt("course_id"));
        e.setRegisteredAt(rs.getTimestamp("registered_at").toLocalDateTime());
        e.setStatus(EnrollmentStatus.valueOf(rs.getString("status")));

        return e;
    }

    @Override
    public List<Enrollment> findAll() throws MyCheckedException {

        List<Enrollment> list = new ArrayList<>();

        String sql = "SELECT * FROM enrollment";

        try (Connection conn = DBUtil.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                list.add(mapResultSet(rs));
            }

        } catch (SQLException e) {
            throw new MyCheckedException("Lỗi lấy danh sách enrollment", e);
        }

        return list;
    }

    @Override
    public Enrollment findById(Integer id) throws MyCheckedException {

        String sql = "SELECT * FROM enrollment WHERE id=?";

        try (Connection conn = DBUtil.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, id);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return mapResultSet(rs);
                }
            }

        } catch (SQLException e) {
            throw new MyCheckedException("Lỗi tìm enrollment theo ID", e);
        }

        return null;
    }

    @Override
    public boolean update(Enrollment e) throws MyCheckedException {

        String sql = "UPDATE enrollment SET status=?::enrollment_status WHERE id=?";

        try (Connection conn = DBUtil.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, e.getStatus().name());
            ps.setInt(2, e.getId());

            return ps.executeUpdate() > 0;

        } catch (SQLException ex) {
            throw new MyCheckedException("Lỗi cập nhật enrollment", ex);
        }
    }

    @Override
    public boolean delete(Integer id) throws MyCheckedException {

        String sql = "DELETE FROM enrollment WHERE id=?";

        try (Connection conn = DBUtil.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, id);

            return ps.executeUpdate() > 0;

        } catch (SQLException e) {
            throw new MyCheckedException("Lỗi xóa enrollment", e);
        }
    }

    @Override
    public List<Enrollment> findByStudentId(int studentId) throws MyCheckedException {

        List<Enrollment> list = new ArrayList<>();

        String sql = "SELECT * FROM enrollment WHERE student_id = ?";

        try (Connection conn = DBUtil.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, studentId);

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    list.add(mapResultSet(rs));
                }
            }

        } catch (SQLException e) {
            throw new MyCheckedException("Lỗi tìm enrollment theo student", e);
        }

        return list;
    }

    @Override
    public boolean existsEnrollment(int studentId, int courseId) throws MyCheckedException {

        String sql = "SELECT 1 FROM enrollment WHERE student_id=? AND course_id=?";

        try (Connection conn = DBUtil.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, studentId);
            ps.setInt(2, courseId);

            try (ResultSet rs = ps.executeQuery()) {
                return rs.next();
            }

        } catch (SQLException e) {
            throw new MyCheckedException("Lỗi kiểm tra enrollment", e);
        }
    }

    @Override
    public void save(Enrollment e) throws MyCheckedException {

        String sql = """
            INSERT INTO enrollment(student_id, course_id, registered_at, status)
            VALUES (?,?,?,?::enrollment_status)
            """;

        try (Connection conn = DBUtil.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, e.getStudentId());
            ps.setInt(2, e.getCourseId());
            ps.setTimestamp(3, Timestamp.valueOf(e.getRegisteredAt()));
            ps.setString(4, e.getStatus().name());

            ps.executeUpdate();

        } catch (SQLException ex) {
            throw new MyCheckedException("Lỗi đăng ký khóa học", ex);
        }
    }
    @Override
    public List<Enrollment> findAllAndPaging(int currentPage, int pageSize)
            throws MyCheckedException {

        List<Enrollment> list = new ArrayList<>();

        String sql = """
                SELECT * FROM enrollment
                ORDER BY id
                LIMIT ? OFFSET ?
                """;

        int offset = (currentPage - 1) * pageSize;

        try (Connection conn = DBUtil.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, pageSize);
            ps.setInt(2, offset);

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    list.add(mapResultSet(rs));
                }
            }

        } catch (SQLException e) {
            throw new MyCheckedException("Lỗi paging enrollment", e);
        }

        return list;
    }
    @Override
    public List<Enrollment> findByCourseId(int courseId) throws MyCheckedException {

        List<Enrollment> list = new ArrayList<>();

        String sql = "SELECT * FROM enrollment WHERE course_id = ?";

        try (Connection conn = DBUtil.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, courseId);

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    list.add(mapResultSet(rs));
                }
            }

        } catch (SQLException e) {
            throw new MyCheckedException("Lỗi lấy enrollment theo course", e);
        }

        return list;
    }

    @Override
    public List<Enrollment> findByCourseIdAndPaging(int courseId, int currentPage, int pageSize) throws MyCheckedException {

        List<Enrollment> list = new ArrayList<>();

        String sql = """
                SELECT * FROM enrollment
                WHERE course_id = ?
                ORDER BY id
                LIMIT ? OFFSET ?
                """;

        int offset = (currentPage - 1) * pageSize;

        try (Connection conn = DBUtil.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, courseId);
            ps.setInt(2, pageSize);
            ps.setInt(3, offset);

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    list.add(mapResultSet(rs));
                }
            }

        } catch (SQLException e) {
            throw new MyCheckedException("Lỗi paging enrollment theo course", e);
        }

        return list;
    }

    @Override
    public int countByCourseId(int courseId) throws MyCheckedException {

        String sql = "SELECT COUNT(*) FROM enrollment WHERE course_id = ?";

        try (Connection conn = DBUtil.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, courseId);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) return rs.getInt(1);
            }

        } catch (SQLException e) {
            throw new MyCheckedException("Lỗi đếm enrollment theo course", e);
        }

        return 0;
    }

    @Override
    public List<Enrollment> findWaitingAndPaging(int currentPage, int pageSize) throws MyCheckedException {

        List<Enrollment> list = new ArrayList<>();

        String sql = """
                SELECT * FROM enrollment
                WHERE status = 'WAITING'
                ORDER BY id
                LIMIT ? OFFSET ?
                """;

        int offset = (currentPage - 1) * pageSize;

        try (Connection conn = DBUtil.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, pageSize);
            ps.setInt(2, offset);

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    list.add(mapResultSet(rs));
                }
            }

        } catch (SQLException e) {
            throw new MyCheckedException("Lỗi paging enrollment WAITING", e);
        }

        return list;
    }

    @Override
    public int countWaiting() throws MyCheckedException {

        String sql = "SELECT COUNT(*) FROM enrollment WHERE status = 'WAITING'";

        try (Connection conn = DBUtil.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            if (rs.next()) return rs.getInt(1);

        } catch (SQLException e) {
            throw new MyCheckedException("Lỗi đếm enrollment WAITING", e);
        }

        return 0;
    }
}