package ra.coursemanagement.business;

import java.util.List;

public interface IBaseService <T,K>{
    List<T> findAll();
    T findById(K id);
    boolean update(T t);
    boolean delete(K id);
    void register(T t);
}
