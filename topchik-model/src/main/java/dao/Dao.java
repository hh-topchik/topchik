package dao;

import java.util.List;

/**
 * Data Access Object (DAO) для всех сущностей
 * */
public interface Dao<T> {

  T findById(long id);

  List<T> findAll();

  void save(T t);

  void saveOrUpdate(T t);

  void update(T t);

  void delete(T t);

}
