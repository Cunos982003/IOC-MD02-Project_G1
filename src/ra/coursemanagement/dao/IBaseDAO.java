package ra.coursemanagement.dao;

import ra.coursemanagement.exception.MyCheckedException;

import java.util.List;

public interface IBaseDAO<T, K> {
    List<T> findAll() throws MyCheckedException;
    T findById(K id) throws MyCheckedException;
    boolean update(T t) throws MyCheckedException;
    boolean delete(K id) throws MyCheckedException;;
}
