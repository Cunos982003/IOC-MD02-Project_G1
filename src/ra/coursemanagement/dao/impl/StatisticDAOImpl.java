package ra.coursemanagement.dao.impl;

import ra.coursemanagement.dao.IStatisticDAO;
import ra.coursemanagement.exception.MyCheckedException;
import ra.coursemanagement.utils.DBUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.*;

public class StatisticDAOImpl implements IStatisticDAO {

    @Override
    public int countCourse() throws MyCheckedException {

        String sql = "SELECT COUNT(*) FROM course";

        try (Connection conn = DBUtil.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            if (rs.next()) {
                return rs.getInt(1);
            }

        } catch (Exception e) {
            throw new MyCheckedException("Lỗi đếm khóa học", e);
        }

        return 0;
    }

    @Override
    public int countStudent() throws MyCheckedException {

        String sql = "SELECT COUNT(*) FROM student";

        try (Connection conn = DBUtil.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            if (rs.next()) {
                return rs.getInt(1);
            }

        } catch (Exception e) {
            throw new MyCheckedException("Lỗi đếm học viên", e);
        }

        return 0;
    }

    @Override
    public Map<String, Integer> studentCountByCourse() throws MyCheckedException {

        Map<String, Integer> map = new LinkedHashMap<>();

        String sql = """
                SELECT c.name, COUNT(e.student_id) total
                FROM course c
                LEFT JOIN enrollment e ON c.id = e.course_id
                GROUP BY c.name
                """;

        try (Connection conn = DBUtil.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {

                map.put(
                        rs.getString("name"),
                        rs.getInt("total")
                );
            }

        } catch (Exception e) {
            throw new MyCheckedException("Lỗi thống kê học viên theo khóa", e);
        }

        return map;
    }

    @Override
    public List<String> top5Course() throws MyCheckedException {

        List<String> list = new ArrayList<>();

        String sql = """
                SELECT c.name, COUNT(e.student_id) total
                FROM course c
                JOIN enrollment e ON c.id = e.course_id
                GROUP BY c.name
                ORDER BY total DESC
                LIMIT 5
                """;

        try (Connection conn = DBUtil.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {

                String row =
                        rs.getString("name")
                                + " - "
                                + rs.getInt("total")
                                + " học viên";

                list.add(row);
            }

        } catch (Exception e) {
            throw new MyCheckedException("Lỗi top 5 khóa học", e);
        }

        return list;
    }

    @Override
    public List<String> courseMoreThan10() throws MyCheckedException {

        List<String> list = new ArrayList<>();

        String sql = """
                SELECT c.name, COUNT(e.student_id) total
                FROM course c
                JOIN enrollment e ON c.id = e.course_id
                GROUP BY c.name
                HAVING COUNT(e.student_id) > 10
                """;

        try (Connection conn = DBUtil.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {

                String row =
                        rs.getString("name")
                                + " - "
                                + rs.getInt("total")
                                + " học viên";

                list.add(row);
            }

        } catch (Exception e) {
            throw new MyCheckedException("Lỗi khóa học >10 học viên", e);
        }

        return list;
    }
}