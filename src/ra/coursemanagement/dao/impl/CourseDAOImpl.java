package ra.coursemanagement.dao.impl;

import ra.coursemanagement.dao.ICourseDAO;
import ra.coursemanagement.exception.MyCheckedException;
import ra.coursemanagement.model.Course;
import ra.coursemanagement.utils.DBUtil;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CourseDAOImpl implements ICourseDAO {

    private Course mapResultSet(ResultSet rs) throws SQLException {
        return new Course(
                rs.getInt("id"),
                rs.getString("name"),
                rs.getInt("duration"),
                rs.getString("instructor"),
                rs.getTimestamp("created_at").toLocalDateTime()
        );
    }

    @Override
    public List<Course> findAll() throws MyCheckedException {

        List<Course> list = new ArrayList<>();

        String sql = "SELECT * FROM course";

        try (
                Connection conn = DBUtil.getConnection();
                PreparedStatement ps = conn.prepareStatement(sql);
                ResultSet rs = ps.executeQuery();
        ) {

            while (rs.next()) {
                list.add(mapResultSet(rs));
            }

        } catch (SQLException e) {
            throw new MyCheckedException("Lỗi khi lưu enrollment", e);
        }

        return list;
    }

    @Override
    public List<Course> findAllAndPaging(int currentPage, int pageSize) throws MyCheckedException {

        List<Course> list = new ArrayList<>();

        String sql = "SELECT * FROM course ORDER BY id LIMIT ? OFFSET ?";

        try (Connection conn = DBUtil.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            int offset = (currentPage - 1) * pageSize;

            ps.setInt(1, pageSize);
            ps.setInt(2, offset);

            ResultSet rs = ps.executeQuery();

            while (rs.next()) {

                Course c = new Course();

                c.setId(rs.getInt("id"));
                c.setName(rs.getString("name"));
                c.setDuration(rs.getInt("duration"));
                c.setInstructor(rs.getString("instructor"));

                list.add(c);
            }

        } catch (Exception e) {
            throw new MyCheckedException("Lỗi paging course", e);
        }

        return list;
    }

    @Override
    public Course findById(Integer id) throws MyCheckedException {

        String sql = "SELECT * FROM course WHERE id = ?";

        try (
                Connection conn = DBUtil.getConnection();
                PreparedStatement ps = conn.prepareStatement(sql);
        ) {

            ps.setInt(1, id);

            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                return mapResultSet(rs);
            }

        } catch (SQLException e) {
            throw new MyCheckedException("Lỗi khi tìm ID Course", e);
        }

        return null;
    }

    @Override
    public boolean saveCourse(Course course) throws MyCheckedException {

        String sql = """
            INSERT INTO course(name, duration, instructor)
            VALUES(?,?,?)
            """;

        try (
                Connection conn = DBUtil.getConnection();
                PreparedStatement ps = conn.prepareStatement(sql);
        ) {

            ps.setString(1, course.getName());
            ps.setInt(2, course.getDuration());
            ps.setString(3, course.getInstructor());

            return ps.executeUpdate() > 0;

        } catch (SQLException e) {
            throw new MyCheckedException("Lỗi thêm course", e);
        }
    }

    @Override
    public boolean update(Course course) throws MyCheckedException {

        String sql = """
            UPDATE course
            SET name=?, duration=?, instructor=?
            WHERE id=?
            """;

        try (
                Connection conn = DBUtil.getConnection();
                PreparedStatement ps = conn.prepareStatement(sql);
        ) {

            ps.setString(1, course.getName());
            ps.setInt(2, course.getDuration());
            ps.setString(3, course.getInstructor());
            ps.setInt(4, course.getId());

            return ps.executeUpdate() > 0;

        } catch (SQLException e) {
            throw new MyCheckedException("Lỗi update course", e);
        }
    }

    @Override
    public boolean delete(Integer id) throws MyCheckedException {

        String sql = "DELETE FROM course WHERE id = ?";

        try (
                Connection conn = DBUtil.getConnection();
                PreparedStatement ps = conn.prepareStatement(sql);
        ) {

            ps.setInt(1, id);

            return ps.executeUpdate() > 0;

        } catch (SQLException e) {
            throw new MyCheckedException("Lỗi xóa course", e);
        }
    }

    @Override
    public Course findByName(String name) throws MyCheckedException {

        String sql = "SELECT * FROM course WHERE name = ?";

        try (
                Connection conn = DBUtil.getConnection();
                PreparedStatement ps = conn.prepareStatement(sql);
        ) {

            ps.setString(1, name);

            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                return mapResultSet(rs);
            }

        } catch (SQLException e) {
            throw new MyCheckedException("Lỗi tìm course theo name", e);
        }

        return null;
    }

    @Override
    public int count() throws MyCheckedException {

        String sql = "SELECT COUNT(*) FROM course";

        try (Connection conn = DBUtil.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            if (rs.next()) {
                return rs.getInt(1);
            }

        } catch (Exception e) {
            throw new MyCheckedException("Lỗi count course", e);
        }

        return 0;
    }
}