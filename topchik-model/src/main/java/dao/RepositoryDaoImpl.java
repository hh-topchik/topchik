package dao;

import entity.Repository;
import org.hibernate.Session;
import org.hibernate.Transaction;
import util.HibernateUtil;

import javax.inject.Singleton;

/**
 * Имплементация DAO для сущности Repository
 * */
@Singleton
public class RepositoryDaoImpl extends DaoImpl<Repository> {

  public String getRepoPathByRepo(Repository repo) {
    Transaction transaction;
    String path = "";
    try (Session session = HibernateUtil.getSessionFactory().openSession()) {
      transaction = session.beginTransaction();
      session.update(repo); // required for reattaching detached Repository entity to an active Hibernate Session to access Repository fields
      path = repo.getPath();
      transaction.commit();
    } catch (Exception e) {
      e.printStackTrace();
    }
    return path;
  }
}
