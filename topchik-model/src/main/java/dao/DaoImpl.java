package dao;

import util.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.lang.reflect.ParameterizedType;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

/**
 * Имплементация DAO для сущностей
 * */
public class DaoImpl<T> implements Dao<T> {
  @SuppressWarnings("unchecked")
  private final Class<T> typeOfT = (Class<T>) ((ParameterizedType)getClass().getGenericSuperclass()).getActualTypeArguments()[0];

  @Override
  public T findById(long id) {
    T t = null;
    Transaction transaction;
    try(Session session = HibernateUtil.getSessionFactory().openSession()) {
      transaction = session.beginTransaction();
      t = session.get(typeOfT, id);
      transaction.commit();
    } catch (Exception e) {
      e.printStackTrace();
    }
    return t;
  }

  @Override
  public List<T> findAll() {
    Transaction transaction;
    List<T> t = Collections.emptyList();
    try(Session session = HibernateUtil.getSessionFactory().openSession()) {
      transaction = session.beginTransaction();
      t = session.createQuery("FROM " + typeOfT.getSimpleName(), typeOfT).list();
      transaction.commit();
    } catch (Exception e) {
      e.printStackTrace();
    }
    return t;
  }

  @Override
  public void save(T t) {
    Transaction transaction;
    try(Session session = HibernateUtil.getSessionFactory().openSession()) {
      transaction = session.beginTransaction();
      session.save(t);
      transaction.commit();
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  @Override
  public void saveOrUpdate(T t) {
    Transaction transaction;
    try(Session session = HibernateUtil.getSessionFactory().openSession()) {
      transaction = session.beginTransaction();
      session.saveOrUpdate(t);
      transaction.commit();
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  public void saveOrUpdateAll(Collection<T> collection) {
    Transaction transaction;
    try(Session session = HibernateUtil.getSessionFactory().openSession()) {
      transaction = session.beginTransaction();
      for (T t : collection) {
        session.saveOrUpdate(t);
      }
      transaction.commit();
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  @Override
  public void update(T t) {
    Transaction transaction;
    try(Session session = HibernateUtil.getSessionFactory().openSession()) {
      transaction = session.beginTransaction();
      session.update(t);
      transaction.commit();
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  @Override
  public void delete(T t) {
    Transaction transaction;
    try(Session session = HibernateUtil.getSessionFactory().openSession()) {
      transaction = session.beginTransaction();
      session.delete(t);
      transaction.commit();
    } catch (Exception e) {
      e.printStackTrace();
    }
  }
}
