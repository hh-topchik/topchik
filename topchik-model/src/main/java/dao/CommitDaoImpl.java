package dao;

import entity.Commit;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import pojo.CommitCountPojo;
import util.HibernateUtil;

import javax.inject.Singleton;
import java.util.ArrayList;
import java.util.List;

/**
 * Имплементация DAO для сущности Commit
 * */
@Singleton
public class CommitDaoImpl extends DaoImpl<Commit> {
  public List<CommitCountPojo> getAggregatedDailyAddedLines() {
    Transaction transaction;
    List<CommitCountPojo> commitCountPojos = new ArrayList<>();
    try (Session session = HibernateUtil.getSessionFactory().openSession()) {
      transaction = session.beginTransaction();
      Query<CommitCountPojo> query = session.createQuery("SELECT new pojo.CommitCountPojo(" +
          "date_trunc('day', c.creationTime) as count_date, c.accountByAuthorId, c.repositoryByRepoId, SUM(c.addedLines) as lines) " +
          "FROM entity.Commit c WHERE c.addedLines != 0 AND c.accountByAuthorId.login NOT LIKE '%[bot]' " +
          "GROUP BY count_date, c.accountByAuthorId, c.repositoryByRepoId " +
          "ORDER BY c.repositoryByRepoId, count_date, lines DESC", CommitCountPojo.class);
      commitCountPojos = query.getResultList();
      transaction.commit();
    } catch (Exception e) {
      e.printStackTrace();
    }
    return commitCountPojos;
  }

  public List<CommitCountPojo> getAggregatedWeeklyAddedLines() {
    Transaction transaction;
    List<CommitCountPojo> commitCountPojos = new ArrayList<>();
    try (Session session = HibernateUtil.getSessionFactory().openSession()) {
      transaction = session.beginTransaction();
      Query<CommitCountPojo> query = session.createQuery("SELECT new pojo.CommitCountPojo(" +
          "date_trunc('week', c.creationTime) as count_date, c.accountByAuthorId, c.repositoryByRepoId, SUM(c.addedLines) as lines) " +
          "FROM entity.Commit c WHERE c.addedLines != 0 AND c.accountByAuthorId.login NOT LIKE '%[bot]' " +
          "GROUP BY count_date, c.accountByAuthorId, c.repositoryByRepoId " +
          "ORDER BY c.repositoryByRepoId, count_date, lines DESC", CommitCountPojo.class);
      commitCountPojos = query.getResultList();
      transaction.commit();
    } catch (Exception e) {
      e.printStackTrace();
    }
    return commitCountPojos;
  }

  public List<CommitCountPojo> getAggregatedDailyDeletedLines() {
    Transaction transaction;
    List<CommitCountPojo> commitCountPojos = new ArrayList<>();
    try (Session session = HibernateUtil.getSessionFactory().openSession()) {
      transaction = session.beginTransaction();
      Query<CommitCountPojo> query = session.createQuery("SELECT new pojo.CommitCountPojo(" +
          "date_trunc('day', c.creationTime) as count_date, c.accountByAuthorId, c.repositoryByRepoId, SUM(c.deletedLines) as lines) " +
          "FROM entity.Commit c WHERE c.deletedLines != 0 AND c.accountByAuthorId.login NOT LIKE '%[bot]' " +
          "GROUP BY count_date, c.accountByAuthorId, c.repositoryByRepoId " +
          "ORDER BY c.repositoryByRepoId, count_date, lines DESC", CommitCountPojo.class);
      commitCountPojos = query.getResultList();
      transaction.commit();
    } catch (Exception e) {
      e.printStackTrace();
    }
    return commitCountPojos;
  }

  public List<CommitCountPojo> getAggregatedWeeklyDeletedLines() {
    Transaction transaction;
    List<CommitCountPojo> commitCountPojos = new ArrayList<>();
    try (Session session = HibernateUtil.getSessionFactory().openSession()) {
      transaction = session.beginTransaction();
      Query<CommitCountPojo> query = session.createQuery("SELECT new pojo.CommitCountPojo(" +
          "date_trunc('week', c.creationTime) as count_date, c.accountByAuthorId, c.repositoryByRepoId, SUM(c.deletedLines) as lines) " +
          "FROM entity.Commit c WHERE c.deletedLines != 0 AND c.accountByAuthorId.login NOT LIKE '%[bot]' " +
          "GROUP BY count_date, c.accountByAuthorId, c.repositoryByRepoId " +
          "ORDER BY c.repositoryByRepoId, count_date, lines DESC", CommitCountPojo.class);
      commitCountPojos = query.getResultList();
      transaction.commit();
    } catch (Exception e) {
      e.printStackTrace();
    }
    return commitCountPojos;
  }
}
