package dao;

import entity.PullRequest;
import enums.PullRequestStatus;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import pojo.CommonCountPojo;
import util.HibernateUtil;

import javax.inject.Singleton;
import java.util.ArrayList;
import java.util.List;

/**
 * Имплементация DAO для сущности PullRequest
 * */
@Singleton
public class PullRequestDaoImpl extends DaoImpl<PullRequest> {
  /**
   * Метод подсчёта замёрдженных пулл реквестов за каждый день,
   * отсортированных по репозиторию, дате и количеству
   *
   * @return List<CommonCountPojo> - желаемый агрегированный список количества замёрдженных пулл реквестов за каждый день
   * */
  public List<CommonCountPojo> getAggregatedDailyMergedPullRequests() {
    final String dailyMergedPullRequestsQuery = "SELECT new pojo.CommonCountPojo(" +
        "date_trunc('day', pr.lastUpdateTime) as count_date, pr.accountByAuthorId, pr.repositoryByRepoId, COUNT(pr) as counter) " +
        "FROM PullRequest pr WHERE pr.status = :status AND pr.accountByAuthorId.login NOT LIKE '%[bot]' " +
        "AND pr.accountByAuthorId.login NOT LIKE 'testUser%' " +
        "GROUP BY count_date, pr.accountByAuthorId, pr.repositoryByRepoId " +
        "ORDER BY pr.repositoryByRepoId, count_date, counter DESC";
    return getAggregatedPullRequestData(dailyMergedPullRequestsQuery);
  }
  
  /**
   * Метод подсчёта замёрдженных пулл реквестов, агрегированных понедельно для каждого аккаунта и
   * отсортированный по репозиторию, дате и количеству
   *
   * @return List<CommonCountPojo> - желаемый агрегированный понедельно список количества замёрдженных пулл реквестов
   * */
  public List<CommonCountPojo> getAggregatedWeeklyMergedPullRequests() {
    final String weeklyMergedPullRequestsQuery = "SELECT new pojo.CommonCountPojo(" +
        "date_trunc('week', pr.lastUpdateTime) as week_date, pr.accountByAuthorId, pr.repositoryByRepoId, COUNT(pr) as counter) " +
        "FROM PullRequest pr WHERE pr.status = :status AND pr.accountByAuthorId.login NOT LIKE '%[bot]' " +
        "AND pr.accountByAuthorId.login NOT LIKE 'testUser%' " +
        "AND date_trunc('week', pr.lastUpdateTime) != date_trunc('week', current_date()) " +
        "GROUP BY week_date, pr.accountByAuthorId, pr.repositoryByRepoId " +
        "ORDER BY pr.repositoryByRepoId, week_date, counter DESC";
    return getAggregatedPullRequestData(weeklyMergedPullRequestsQuery);
  }

  /**
   * Общий метод для подсчёта данных по количеству замёрдженных PR
   *
   * @param hqlQuery - запрос на языке HQL, который надо выполнить, чтобы забрать из PullRequest агрегированные данные
   *
   * @return  List<CommonCountPojo> - желаемый агрегированный список
   * */
  private List<CommonCountPojo> getAggregatedPullRequestData(String hqlQuery) {
    Transaction transaction;
    List<CommonCountPojo> commonCountPojos = new ArrayList<>();
    try (Session session = HibernateUtil.getSessionFactory().openSession()) {
      transaction = session.beginTransaction();
      Query<CommonCountPojo> query = session.createQuery(hqlQuery, CommonCountPojo.class)
          .setParameter("status", PullRequestStatus.MERGED.getId());
      commonCountPojos = query.getResultList();
      transaction.commit();
    } catch (Exception e) {
      e.printStackTrace();
    }
    return commonCountPojos;
  }
}
