package ra.coursemanagement.business.impl;

import ra.coursemanagement.business.IStatisticService;
import ra.coursemanagement.dao.IStatisticDAO;
import ra.coursemanagement.dao.impl.StatisticDAOImpl;
import ra.coursemanagement.exception.MyCheckedException;
import ra.coursemanagement.exception.MyUncheckedException;

import java.util.List;
import java.util.Map;

public class StatisticServiceImpl implements IStatisticService {

    private final IStatisticDAO statisticDAO = new StatisticDAOImpl();

    @Override
    public int countCourse() {
        try {
            return statisticDAO.countCourse();
        } catch (MyCheckedException e) {
            throw new MyUncheckedException("Không lấy được tổng khóa học", e);
        }
    }

    @Override
    public int countStudent() {
        try {
            return statisticDAO.countStudent();
        } catch (MyCheckedException e) {
            throw new MyUncheckedException("Không lấy được tổng học viên", e);
        }
    }

    @Override
    public Map<String, Integer> studentCountByCourse() {
        try {
            return statisticDAO.studentCountByCourse();
        } catch (MyCheckedException e) {
            throw new MyUncheckedException("Không lấy được thống kê", e);
        }
    }

    @Override
    public List<String> top5Course() {
        try {
            return statisticDAO.top5Course();
        } catch (MyCheckedException e) {
            throw new MyUncheckedException("Không lấy được top 5", e);
        }
    }

    @Override
    public List<String> courseMoreThan10() {
        try {
            return statisticDAO.courseMoreThan10();
        } catch (MyCheckedException e) {
            throw new MyUncheckedException("Không lấy được khóa học >10", e);
        }
    }
}
