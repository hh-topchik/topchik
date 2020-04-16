package dao;

import entity.WeeklyResult;
import org.hibernate.Session;
import org.hibernate.Transaction;
import util.HibernateUtil;

import javax.inject.Singleton;
import java.util.ArrayList;
import java.util.List;

/**
 * Имплементация DAO для сущности WeeklyResult
 * */
@Singleton
public class WeeklyResultDaoImpl extends DaoImpl<WeeklyResult> {

  /**
   * Метод получения списка достижений за неделю
   * */
  public List<WeeklyResult> getWeekResults() {
    Transaction transaction;
    List<WeeklyResult> weeklyResults = new ArrayList<>();
    try (Session session = HibernateUtil.getSessionFactory().openSession()) {
      transaction = session.beginTransaction();
      weeklyResults = session.createQuery("SELECT wr FROM WeeklyResult wr " +
          "WHERE EXTRACT(week FROM wr.weekDate) = EXTRACT(week FROM current_date()) " +
          "ORDER BY wr.points DESC", WeeklyResult.class)
          .setMaxResults(10)
          .getResultList();
      transaction.commit();
    } catch (Exception e) {
      e.printStackTrace();
    }
    return weeklyResults;
  }

  /**
   * Метод получения списка достижений за квартал
   * */
  public List<WeeklyResult> getQuarterResults() {
    Transaction transaction;
    List<WeeklyResult> weeklyResults = new ArrayList<>();
    try (Session session = HibernateUtil.getSessionFactory().openSession()) {
      transaction = session.beginTransaction();
      weeklyResults = session.createQuery("SELECT wr FROM WeeklyResult wr " +
          "WHERE (((MONTH(wr.weekDate) - 1) / 3) + 1) = (((MONTH(current_date()) - 1) / 3) + 1) " +
          "ORDER BY wr.points DESC", WeeklyResult.class)
          .setMaxResults(10)
          .getResultList();
      transaction.commit();
    } catch (Exception e) {
      e.printStackTrace();
    }
    return weeklyResults;
  }

  /**
   * Метод получения списка достижений за год
   * */
  public List<WeeklyResult> getYearResults() {
    Transaction transaction;
    List<WeeklyResult> weeklyResults = new ArrayList<>();
    try (Session session = HibernateUtil.getSessionFactory().openSession()) {
      transaction = session.beginTransaction();
      weeklyResults = session.createQuery("SELECT wr FROM WeeklyResult wr " +
          "WHERE YEAR(wr.weekDate) = YEAR(current_date()) " +
          "ORDER BY wr.points DESC", WeeklyResult.class)
          .setMaxResults(10)
          .getResultList();
      transaction.commit();
    } catch (Exception e) {
      e.printStackTrace();
    }
    return weeklyResults;
  }

  /**
   * Метод получения списка достижений за всё время
   * */
  public List<WeeklyResult> getAllTimeResults() {
    Transaction transaction;
    List<WeeklyResult> weeklyResults = new ArrayList<>();
    try (Session session = HibernateUtil.getSessionFactory().openSession()) {
      transaction = session.beginTransaction();
      weeklyResults = session.createQuery("SELECT wr FROM WeeklyResult wr " +
          "ORDER BY wr.points DESC", WeeklyResult.class)
          .setMaxResults(10)
          .getResultList();
      transaction.commit();
    } catch (Exception e) {
      e.printStackTrace();
    }
    return weeklyResults;
  }
}
