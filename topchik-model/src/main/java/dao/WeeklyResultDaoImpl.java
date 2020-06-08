package dao;

import entity.WeeklyResult;
import org.hibernate.Session;
import org.hibernate.Transaction;
import util.HibernateUtil;

import javax.inject.Singleton;
import java.time.LocalDate;
import java.util.List;

/**
 * Имплементация DAO для сущности WeeklyResult
 * */
@Singleton
public class WeeklyResultDaoImpl extends DaoImpl<WeeklyResult> {
  /**
   * Метод подсчёта общего количества очков по категории
   *
   * @param categoryId - id категории
   * @param accountId - id пользователя
   *
   * @return Long - искомая сумма очков
   * */
  public Long getCategoryPointsSum(int categoryId, Long accountId) {
    Transaction transaction;
    Long pointsSum = null;
    try (Session session = HibernateUtil.getSessionFactory().openSession()) {
      transaction = session.beginTransaction();
      pointsSum = (Long) session.createQuery("SELECT SUM(wr.points) FROM WeeklyResult wr " +
          "WHERE wr.category = :category AND wr.accountByAccountId.accountId = :accountId")
          .setParameter("category", categoryId)
          .setParameter("accountId", accountId)
          .getSingleResult();
      transaction.commit();
    } catch (Exception e) {
      e.printStackTrace();
    }
    return pointsSum;
  }

  /**
   * Метод подсчёта общего количества очков по категории
   *
   * @param categoryId - id категории
   * @param accountId - id пользователя
   * @param repoId - id репозитория
   *
   * @return Long - искомая сумма очков
   * */
  public Long getCategoryPointsSum(int categoryId, Long accountId, Long repoId) {
    Transaction transaction;
    Long pointsSum = null;
    try (Session session = HibernateUtil.getSessionFactory().openSession()) {
      transaction = session.beginTransaction();
      pointsSum = (Long) session.createQuery("SELECT SUM(wr.points) FROM WeeklyResult wr " +
          "WHERE wr.category = :category AND wr.accountByAccountId.accountId = :accountId " +
          "AND wr.repositoryByRepoId.repoId = :repoId")
          .setParameter("category", categoryId)
          .setParameter("accountId", accountId)
          .setParameter("repoId", repoId)
          .getSingleResult();
      transaction.commit();
    } catch (Exception e) {
      e.printStackTrace();
    }
    return pointsSum;
  }

  /**
   * Метод подсчёта общего количества определенной медали, полученной пользователей в данном репозитории
   *
   * @param medal - id категории
   * @param accountId - id пользователя
   *
   * @return Long - искомая сумма очков
   * */
  public Long getMedalSum(int medal, Long accountId) {
    Transaction transaction;
    Long medalSum = null;
    try (Session session = HibernateUtil.getSessionFactory().openSession()) {
      transaction = session.beginTransaction();
      medalSum = (Long) session.createQuery("SELECT COUNT(wr.medal) FROM WeeklyResult wr " +
          "WHERE wr.medal = :medal AND wr.accountByAccountId.accountId = :accountId")
          .setParameter("medal", medal)
          .setParameter("accountId", accountId)
          .getSingleResult();
      transaction.commit();
    } catch (Exception e) {
      e.printStackTrace();
    }
    return medalSum;
  }

  /**
   * Метод подсчёта общего количества определенной медали, полученной пользователей в данном репозитории
   *
   * @param medal - id категории
   * @param accountId - id пользователя
   * @param repoId - id репозитория
   *
   * @return Long - искомая сумма очков
   * */
  public Long getMedalSum(int medal, Long accountId, Long repoId) {
    Transaction transaction;
    Long medalSum = null;
    try (Session session = HibernateUtil.getSessionFactory().openSession()) {
      transaction = session.beginTransaction();
      medalSum = (Long) session.createQuery("SELECT COUNT(wr.medal) FROM WeeklyResult wr " +
          "WHERE wr.medal = :medal AND wr.accountByAccountId.accountId = :accountId " +
          "AND wr.repositoryByRepoId.repoId = :repoId")
          .setParameter("medal", medal)
          .setParameter("accountId", accountId)
          .setParameter("repoId", repoId)
          .getSingleResult();
      transaction.commit();
    } catch (Exception e) {
      e.printStackTrace();
    }
    return medalSum;
  }

  /**
   * Метод возврата списка уникальных дат, за которые есть недельные результаты пользователя в данной категории
   *
   * @param categoryId - id категории
   * @param accountId - id пользователя
   *
   * @return List<Date> - список уникальных дат
   * */
  public List<LocalDate> getDistinctWeekDates(int categoryId, Long accountId) {
    Transaction transaction;
    List<LocalDate> weekDates = null;
    try (Session session = HibernateUtil.getSessionFactory().openSession()) {
      transaction = session.beginTransaction();
      weekDates = session.createQuery("SELECT DISTINCT wr.weekDate FROM WeeklyResult wr " +
          "WHERE wr.category = :category AND wr.accountByAccountId.accountId = :accountId")
          .setParameter("category", categoryId)
          .setParameter("accountId", accountId)
          .getResultList();
      transaction.commit();
    } catch (Exception e) {
      e.printStackTrace();
    }
    return weekDates;
  }

  /**
   * Метод возврата списка уникальных дат, за которые есть недельные результаты пользователя в данной категории
   *
   * @param categoryId - id категории
   * @param accountId - id пользователя
   * @param repoId - id репозитория
   *
   * @return List<Date> - список уникальных дат
   * */
  public List<LocalDate> getDistinctWeekDates(int categoryId, Long accountId, Long repoId) {
    Transaction transaction;
    List<LocalDate> weekDates = null;
    try (Session session = HibernateUtil.getSessionFactory().openSession()) {
      transaction = session.beginTransaction();
      weekDates = session.createQuery("SELECT DISTINCT wr.weekDate FROM WeeklyResult wr " +
          "WHERE wr.category = :category AND wr.accountByAccountId.accountId = :accountId " +
          "AND wr.repositoryByRepoId.repoId = :repoId")
          .setParameter("category", categoryId)
          .setParameter("accountId", accountId)
          .setParameter("repoId", repoId)
          .getResultList();
      transaction.commit();
    } catch (Exception e) {
      e.printStackTrace();
    }
    return weekDates;
  }

  /**
   * Метод возврата медали пользователя в указанную неделю по данной категории
   *
   * @param weekDate - дата подведения итогов той недели
   * @param categoryId - id категории
   * @param accountId - id пользователя
   *
   * @return Long - искомая медаль
   * */
  public int getAccountMedalByWeekDate(LocalDate weekDate, int categoryId, Long accountId) {
    Transaction transaction;
    int medal = 0;
    try (Session session = HibernateUtil.getSessionFactory().openSession()) {
      transaction = session.beginTransaction();
      medal = (int) session.createQuery("SELECT wr.medal FROM WeeklyResult wr " +
          "WHERE wr.weekDate = :weekDate " +
          "AND wr.category = :category " +
          "AND wr.accountByAccountId.accountId = :accountId")
          .setParameter("weekDate", weekDate)
          .setParameter("category", categoryId)
          .setParameter("accountId", accountId)
          .getSingleResult();
      transaction.commit();
    } catch (Exception e) {
      e.printStackTrace();
    }
    return medal;
  }

  /**
   * Метод возврата медали пользователя в указанную неделю по данной категории
   *
   * @param weekDate - дата подведения итогов той недели
   * @param categoryId - id категории
   * @param accountId - id пользователя
   * @param repoId - id репозитория
   *
   * @return Long - искомая медаль
   * */
  public int getAccountMedalByWeekDate(LocalDate weekDate, int categoryId, Long accountId, Long repoId) {
    Transaction transaction;
    int medal = 0;
    try (Session session = HibernateUtil.getSessionFactory().openSession()) {
      transaction = session.beginTransaction();
      medal = (int) session.createQuery("SELECT wr.medal FROM WeeklyResult wr " +
          "WHERE wr.weekDate = :weekDate " +
          "AND wr.category = :category " +
          "AND wr.accountByAccountId.accountId = :accountId " +
          "AND wr.repositoryByRepoId.repoId = :repoId")
          .setParameter("weekDate", weekDate)
          .setParameter("category", categoryId)
          .setParameter("accountId", accountId)
          .setParameter("repoId", repoId)
          .getSingleResult();
      transaction.commit();
    } catch (Exception e) {
      e.printStackTrace();
    }
    return medal;
  }
}
