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

  /**
   * Метод получения списка коммитов, агрегированного по количеству добавленных строк за каждый день,
   * отсортированных по репозиторию, дате и количеству
   *
   * @return List<CommitCountPojo> - желаемый агрегированный список количества добавленных строк за каждый день
   * */
  public List<CommitCountPojo> getAggregatedDailyAddedLines() {
    final String dailyAddedLinesQuery = "SELECT new pojo.CommitCountPojo(" +
        "date_trunc('day', c.creationTime) as count_date, c.accountByAuthorId, c.repositoryByRepoId, SUM(c.addedLines) as lines) " +
        "FROM entity.Commit c WHERE c.addedLines != 0 AND c.accountByAuthorId.login NOT LIKE '%[bot]' " +
        "GROUP BY count_date, c.accountByAuthorId, c.repositoryByRepoId " +
        "ORDER BY c.repositoryByRepoId, count_date, lines DESC";
    return getAggregatedCommitData(dailyAddedLinesQuery);
  }

  /**
   * Метод получения списка коммитов, агрегированного по количеству добавленных строк за всю неделю,
   * отсортированных по репозиторию, дате и количеству
   *
   * @return List<CommitCountPojo> - желаемый агрегированный понедельно список количества добавленных строк
   * */
  public List<CommitCountPojo> getAggregatedWeeklyAddedLines() {
    final String weeklyAddedLinesQuery = "SELECT new pojo.CommitCountPojo(" +
        "date_trunc('week', c.creationTime) as count_date, c.accountByAuthorId, c.repositoryByRepoId, SUM(c.addedLines) as lines) " +
        "FROM entity.Commit c WHERE c.addedLines != 0 AND c.accountByAuthorId.login NOT LIKE '%[bot]' " +
        "GROUP BY count_date, c.accountByAuthorId, c.repositoryByRepoId " +
        "ORDER BY c.repositoryByRepoId, count_date, lines DESC";
    return getAggregatedCommitData(weeklyAddedLinesQuery);
  }

  /**
   * Метод получения списка коммитов, агрегированного по количеству удаленных строк за каждый день,
   * отсортированных по репозиторию, дате и количеству
   *
   * @return List<CommitCountPojo> - желаемый агрегированный список количества удаленных строк за каждый день
   * */
  public List<CommitCountPojo> getAggregatedDailyDeletedLines() {
    final String dailyDeletedLinesQuery = "SELECT new pojo.CommitCountPojo(" +
        "date_trunc('day', c.creationTime) as count_date, c.accountByAuthorId, c.repositoryByRepoId, SUM(c.deletedLines) as lines) " +
        "FROM entity.Commit c WHERE c.deletedLines != 0 AND c.accountByAuthorId.login NOT LIKE '%[bot]' " +
        "GROUP BY count_date, c.accountByAuthorId, c.repositoryByRepoId " +
        "ORDER BY c.repositoryByRepoId, count_date, lines DESC";
    return getAggregatedCommitData(dailyDeletedLinesQuery);
  }

  /**
   * Метод получения списка коммитов, агрегированного по количеству удаленных строк за всю неделю,
   * отсортированных по репозиторию, дате и количеству
   *
   * @return List<CommitCountPojo> - желаемый агрегированный понедельно список количества удаленных строк
   * */
  public List<CommitCountPojo> getAggregatedWeeklyDeletedLines() {
    final String weeklyDeletedLinesQuery = "SELECT new pojo.CommitCountPojo(" +
        "date_trunc('week', c.creationTime) as count_date, c.accountByAuthorId, c.repositoryByRepoId, SUM(c.deletedLines) as lines) " +
        "FROM entity.Commit c WHERE c.deletedLines != 0 AND c.accountByAuthorId.login NOT LIKE '%[bot]' " +
        "GROUP BY count_date, c.accountByAuthorId, c.repositoryByRepoId " +
        "ORDER BY c.repositoryByRepoId, count_date, lines DESC";
    return getAggregatedCommitData(weeklyDeletedLinesQuery);
  }

  /**
   * Общий метод для получения агрегированных данных по количеству добавленных или удаленных строк
   *
   * @param hqlQuery - запрос на языке HQL, который надо выполнить, чтобы забрать из Commit агрегированные данные
   *
   * @return  List<CommitCountPojo> - желаемый агрегированный список
   * */
  private List<CommitCountPojo> getAggregatedCommitData(String hqlQuery) {
    Transaction transaction;
    List<CommitCountPojo> commitCountPojos = new ArrayList<>();
    try (Session session = HibernateUtil.getSessionFactory().openSession()) {
      transaction = session.beginTransaction();
      Query<CommitCountPojo> query = session.createQuery(hqlQuery, CommitCountPojo.class);
      commitCountPojos = query.getResultList();
      transaction.commit();
    } catch (Exception e) {
      e.printStackTrace();
    }
    return commitCountPojos;
  }
}
