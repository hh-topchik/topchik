package dao;

import entity.WeeklyResult;
import org.hibernate.SessionFactory;

import javax.inject.Inject;
import javax.inject.Singleton;
import java.util.List;

/**
 * Data Access Object (DAO) для сущности WeeklyResult
 * */
@Singleton
public class WeeklyResultDao {
  private final SessionFactory sessionFactory;

  @Inject
  public WeeklyResultDao(SessionFactory sessionFactory) {
    this.sessionFactory = sessionFactory;
  }

  /**
   * Метод получения списка достижений за неделю
   * */
  public List<WeeklyResult> getWeekResults() {
    return sessionFactory
        .getCurrentSession()
        .createQuery("SELECT wr FROM WeeklyResult wr " +
            "WHERE EXTRACT(week FROM wr.weekDate) = EXTRACT(week FROM current_date()) " +
            "ORDER BY wr.points DESC", WeeklyResult.class)
        .setMaxResults(10)
        .getResultList();
  }

  /**
   * Метод получения списка достижений за квартал
   * */
  public List<WeeklyResult> getQuarterResults() {
    return sessionFactory
        .getCurrentSession()
        .createQuery("SELECT wr FROM WeeklyResult wr " +
            "WHERE (((MONTH(wr.weekDate) - 1) / 3) + 1) = (((MONTH(current_date()) - 1) / 3) + 1) " +
            "ORDER BY wr.points DESC", WeeklyResult.class)
        .setMaxResults(10)
        .getResultList();
  }

  /**
   * Метод получения списка достижений за год
   * */
  public List<WeeklyResult> getYearResults() {
    return sessionFactory
        .getCurrentSession()
        .createQuery("SELECT wr FROM WeeklyResult wr " +
            "WHERE YEAR(wr.weekDate) = YEAR(current_date()) " +
            "ORDER BY wr.points DESC", WeeklyResult.class)
        .setMaxResults(10)
        .getResultList();
  }

  /**
   * Метод получения списка достижений за всё время
   * */
  public List<WeeklyResult> getAllTimeResults() {
    return sessionFactory
        .getCurrentSession()
        .createQuery("SELECT wr FROM WeeklyResult wr " +
            "ORDER BY wr.points DESC", WeeklyResult.class)
        .setMaxResults(10)
        .getResultList();
  }
}
