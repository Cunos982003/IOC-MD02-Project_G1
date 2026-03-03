package ra.coursemanagement.dao;

import java.util.List;

public interface IBaseDAO<T, K> {
    List<T> findAll();
    T findById(K id);
    boolean update(T t);
    boolean delete(K id);
}
