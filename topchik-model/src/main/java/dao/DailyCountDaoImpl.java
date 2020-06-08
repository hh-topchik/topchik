package dao;

import entity.DailyCount;
import org.hibernate.Session;
import org.hibernate.Transaction;
import util.HibernateUtil;

import javax.inject.Singleton;
import java.time.LocalDate;

/**
 * Имплементация DAO для сущности DailyCount
 * */
@Singleton
public class DailyCountDaoImpl extends DaoImpl<DailyCount> {
  /**
   * Метод подсчёта общего количества релевантных действий по категории
   *
   * @param categoryId - id категории
   * @param accountId - id пользователя
   *
   * @return Long - искомая сумма релевантных действий
   * */
  public Long getCategoryCountSum(int categoryId, Long accountId) {
    Transaction transaction;
    Long countSum = null;
    try (Session session = HibernateUtil.getSessionFactory().openSession()) {
      transaction = session.beginTransaction();
      countSum = (Long) session.createQuery("SELECT SUM(dc.counter) FROM DailyCount dc " +
          "WHERE dc.category = :category AND dc.accountByAccountId.accountId = :accountId")
          .setParameter("category", categoryId)
          .setParameter("accountId", accountId)
          .getSingleResult();
      transaction.commit();
    } catch (Exception e) {
      e.printStackTrace();
    }
    return countSum;
  }

  /**
   * Метод подсчёта общего количества релевантных действий по категории
   *
   * @param categoryId - id категории
   * @param accountId - id пользователя
   * @param repoId - id репозитория
   *
   * @return Long - искомая сумма релевантных действий
   * */
  public Long getCategoryCountSum(int categoryId, Long accountId, Long repoId) {
    Transaction transaction;
    Long countSum = null;
    try (Session session = HibernateUtil.getSessionFactory().openSession()) {
      transaction = session.beginTransaction();
      countSum = (Long) session.createQuery("SELECT SUM(dc.counter) FROM DailyCount dc " +
          "WHERE dc.category = :category AND dc.accountByAccountId.accountId = :accountId " +
          "AND dc.repositoryByRepoId.repoId = :repoId")
          .setParameter("category", categoryId)
          .setParameter("accountId", accountId)
          .setParameter("repoId", repoId)
          .getSingleResult();
      transaction.commit();
    } catch (Exception e) {
      e.printStackTrace();
    }
    return countSum;
  }

  /**
   * Метод подсчёта общего количества релевантных действий по категории
   *
   * @param weekDate - неделя, за которую надо подсчитать релевантные действия
   * @param categoryId - id категории
   * @param accountId - id пользователя
   *
   * @return Long - искомая сумма релевантных действий
   * */
  public Long getWeekDateCountSum(LocalDate weekDate, int categoryId, Long accountId) {
    Transaction transaction;
    Long countSum = null;
    try (Session session = HibernateUtil.getSessionFactory().openSession()) {
      transaction = session.beginTransaction();
      countSum = (Long) session.createQuery("SELECT SUM(dc.counter) FROM DailyCount dc " +
          "WHERE date_trunc('week', dc.date) = date_trunc('week', CAST(:weekDate AS date)) " +
          "AND dc.category = :category AND dc.accountByAccountId.accountId = :accountId")
          .setParameter("weekDate", weekDate)
          .setParameter("category", categoryId)
          .setParameter("accountId", accountId)
          .getSingleResult();
      transaction.commit();
    } catch (Exception e) {
      e.printStackTrace();
    }
    return countSum;
  }

  /**
   * Метод подсчёта общего количества релевантных действий по категории
   *
   * @param weekDate - неделя, за которую надо подсчитать релевантные действия
   * @param categoryId - id категории
   * @param accountId - id пользователя
   * @param repoId - id репозитория
   *
   * @return Long - искомая сумма релевантных действий
   * */
  public Long getWeekDateCountSum(LocalDate weekDate, int categoryId, Long accountId, Long repoId) {
    Transaction transaction;
    Long countSum = null;
    try (Session session = HibernateUtil.getSessionFactory().openSession()) {
      transaction = session.beginTransaction();
      countSum = (Long) session.createQuery("SELECT SUM(dc.counter) FROM DailyCount dc " +
          "WHERE date_trunc('week', dc.date) = date_trunc('week', CAST(:weekDate AS date)) " +
          "AND dc.category = :category AND dc.accountByAccountId.accountId = :accountId " +
          "AND dc.repositoryByRepoId.repoId = :repoId")
          .setParameter("weekDate", weekDate)
          .setParameter("category", categoryId)
          .setParameter("accountId", accountId)
          .setParameter("repoId", repoId)
          .getSingleResult();
      transaction.commit();
    } catch (Exception e) {
      e.printStackTrace();
    }
    return countSum;
  }
}
