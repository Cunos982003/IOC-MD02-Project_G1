package ra.coursemanagement.dao.impl;

import ra.coursemanagement.dao.IEnrollmentDAO;
import ra.coursemanagement.model.Enrollment;
import ra.coursemanagement.model.EnrollmentStatus;
import ra.coursemanagement.utils.DBUtil;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class EnrollmentDAOImpl implements IEnrollmentDAO {

    @Override
    public List<Enrollment> findAll() {

        List<Enrollment> list = new ArrayList<>();

        String sql = "SELECT * FROM enrollment";

        try(Connection conn = DBUtil.getConnection();
            PreparedStatement ps = conn.prepareStatement(sql);
            ResultSet rs = ps.executeQuery()){

            while(rs.next()){

                Enrollment e = new Enrollment();

                e.setId(rs.getInt("id"));
                e.setStudentId(rs.getInt("student_id"));
                e.setCourseId(rs.getInt("course_id"));
                e.setRegisteredAt(rs.getTimestamp("registered_at").toLocalDateTime());
                e.setStatus(EnrollmentStatus.valueOf(rs.getString("status")));

                list.add(e);
            }

        }catch(Exception e){
            System.out.println("Lỗi findAll enrollment: " + e.getMessage());
        }

        return list;
    }

    @Override
    public Enrollment findById(Integer id) {

        String sql = "SELECT * FROM enrollment WHERE id=?";

        try(Connection conn = DBUtil.getConnection();
            PreparedStatement ps = conn.prepareStatement(sql)){

            ps.setInt(1,id);

            ResultSet rs = ps.executeQuery();

            if(rs.next()){

                Enrollment e = new Enrollment();

                e.setId(rs.getInt("id"));
                e.setStudentId(rs.getInt("student_id"));
                e.setCourseId(rs.getInt("course_id"));
                e.setRegisteredAt(rs.getTimestamp("registered_at").toLocalDateTime());
                e.setStatus(EnrollmentStatus.valueOf(rs.getString("status")));

                return e;
            }

        }catch(Exception e){
            System.out.println("Lỗi findById enrollment: " + e.getMessage());
        }

        return null;
    }

    @Override
    public boolean update(Enrollment e) {

        try (Connection conn = DBUtil.getConnection();
             PreparedStatement ps =
                     conn.prepareStatement(
                             "UPDATE enrollment SET status=?::enrollment_status WHERE id=?")) {

            ps.setString(1, e.getStatus().name());
            ps.setInt(2, e.getId());

            return ps.executeUpdate() > 0;

        } catch (Exception ex) {
            ex.printStackTrace();
        }

        return false;
    }

    @Override
    public boolean delete(Integer id) {

        String sql = "DELETE FROM enrollment WHERE id=?";

        try(Connection conn = DBUtil.getConnection();
            PreparedStatement ps = conn.prepareStatement(sql)){

            ps.setInt(1,id);

            return ps.executeUpdate()>0;

        }catch(Exception e){
            System.out.println("Lỗi delete enrollment: "+e.getMessage());
        }

        return false;
    }

    @Override
    public List<Enrollment> findByStudentId(int studentId) {

        List<Enrollment> list = new ArrayList<>();

        try (Connection conn = DBUtil.getConnection();
             PreparedStatement ps = conn.prepareStatement(
                     "SELECT * FROM enrollment WHERE student_id = ?")) {

            ps.setInt(1, studentId);

            ResultSet rs = ps.executeQuery();

            while (rs.next()) {

                Enrollment e = new Enrollment();

                e.setId(rs.getInt("id"));
                e.setStudentId(rs.getInt("student_id"));
                e.setCourseId(rs.getInt("course_id"));
                e.setRegisteredAt(rs.getTimestamp("registered_at").toLocalDateTime());
                e.setStatus(EnrollmentStatus.valueOf(rs.getString("status")));

                list.add(e);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return list;
    }

    @Override
    public boolean existsEnrollment(int studentId, int courseId) {

        try (Connection conn = DBUtil.getConnection();
             PreparedStatement ps =
                     conn.prepareStatement(
                             "SELECT 1 FROM enrollment WHERE student_id = ? AND course_id = ?")) {

            ps.setInt(1, studentId);
            ps.setInt(2, courseId);

            ResultSet rs = ps.executeQuery();

            return rs.next();

        } catch (Exception e) {
            e.printStackTrace();
        }

        return false;
    }

    @Override
    public void save(Enrollment e) {

        try (Connection conn = DBUtil.getConnection();
             PreparedStatement ps =
                     conn.prepareStatement(
                             "INSERT INTO enrollment(student_id, course_id, registered_at, status) VALUES (?,?,?,?::enrollment_status)")) {

            ps.setInt(1, e.getStudentId());
            ps.setInt(2, e.getCourseId());
            ps.setTimestamp(3, Timestamp.valueOf(e.getRegisteredAt()));
            ps.setString(4, e.getStatus().name());

            ps.executeUpdate();

        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}