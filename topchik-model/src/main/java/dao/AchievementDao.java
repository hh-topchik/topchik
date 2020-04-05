package dao;

import entity.Achievement;
import org.hibernate.SessionFactory;

import javax.inject.Inject;
import javax.inject.Singleton;
import java.util.List;

/**
 * Data Access Object (DAO) для сущности Achievement
 * */
@Singleton
public class AchievementDao {
  private final SessionFactory sessionFactory;

  @Inject
  public AchievementDao(SessionFactory sessionFactory) {
    this.sessionFactory = sessionFactory;
  }

  /**
   * Метод получения списка достижений за неделю
   * */
  public List<Achievement> getWeekResults() {
    return sessionFactory
        .getCurrentSession()
        .createQuery("SELECT ach FROM Achievement ach " +
            "WHERE EXTRACT(week FROM ach.weekDate) = EXTRACT(week FROM current_date()) " +
            "ORDER BY ach.points DESC", Achievement.class)
        .setMaxResults(10)
        .getResultList();
  }

  /**
   * Метод получения списка достижений за квартал
   * */
  public List<Achievement> getQuarterResults() {
    return sessionFactory
        .getCurrentSession()
        .createQuery("SELECT ach FROM Achievement ach " +
            "WHERE (((MONTH(ach.weekDate) - 1) / 3) + 1) = (((MONTH(current_date()) - 1) / 3) + 1) " +
            "ORDER BY ach.points DESC", Achievement.class)
        .setMaxResults(10)
        .getResultList();
  }

  /**
   * Метод получения списка достижений за год
   * */
  public List<Achievement> getYearResults() {
    return sessionFactory
        .getCurrentSession()
        .createQuery("SELECT ach FROM Achievement ach " +
            "WHERE year(ach.weekDate) = year(current_date()) " +
            "ORDER BY ach.points DESC", Achievement.class)
        .setMaxResults(10)
        .getResultList();
  }

  /**
   * Метод получения списка достижений за всё время
   * */
  public List<Achievement> getAllTimeResults() {
    return sessionFactory
        .getCurrentSession()
        .createQuery("SELECT ach FROM Achievement ach " +
            "ORDER BY ach.points DESC", Achievement.class)
        .setMaxResults(10)
        .getResultList();
  }
}
