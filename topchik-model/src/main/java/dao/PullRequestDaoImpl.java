package dao;

import entity.PullRequest;
import enums.PullRequestStatus;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import pojo.PullRequestPojo;
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
   * Метод получения списка замёрдженных пулл реквестов за каждый день,
   * отсортированных по репозиторию, дате и количеству
   *
   * @return List<PullRequestPojo> - желаемый агрегированный список количества замёрдженных пулл реквестов за каждый день
   * */
  public List<PullRequestPojo> getAggregatedDailyMergedPullRequests() {
    final String dailyMergedPullRequestsQuery = "SELECT new pojo.PullRequestPojo(" +
        "date_trunc('day', pr.lastUpdateTime) as count_date, pr.accountByAuthorId, pr.repositoryByRepoId, COUNT(pr) as counter) " +
        "FROM entity.PullRequest pr WHERE pr.status = :status AND pr.accountByAuthorId.login NOT LIKE '%[bot]' " +
        "GROUP BY count_date, pr.accountByAuthorId, pr.repositoryByRepoId " +
        "ORDER BY pr.repositoryByRepoId, count_date, counter DESC";
    return getAggregatedPullRequestData(dailyMergedPullRequestsQuery);
  }
  
  /**
   * Метод получения списка замёрдженных пулл реквестов, агрегированный понедельно для каждого аккаунта и
   * отсортированный по репозиторию, дате и количеству
   *
   * @return List<PullRequestPojo> - желаемый агрегированный список количества замёрдженных пулл реквестов за каждый день
   * */
  public List<PullRequestPojo> getAggregatedWeeklyMergedPullRequests() {
    final String weeklyMergedPullRequestsQuery = "SELECT new pojo.PullRequestPojo(" +
        "date_trunc('week', pr.lastUpdateTime) as week_date, pr.accountByAuthorId, pr.repositoryByRepoId, COUNT(pr) as counter) " +
        "FROM entity.PullRequest pr WHERE pr.status = :status AND pr.accountByAuthorId.login NOT LIKE '%[bot]' " +
        "AND date_trunc('week', pr.lastUpdateTime) != date_trunc('week', current_date()) " +
        "GROUP BY week_date, pr.accountByAuthorId, pr.repositoryByRepoId " +
        "ORDER BY pr.repositoryByRepoId, week_date, counter DESC";
    return getAggregatedPullRequestData(weeklyMergedPullRequestsQuery);
  }

  /**
   * Общий метод для получения агрегированных данных по количеству замёрдженных PR
   *
   * @param hqlQuery - запрос на языке HQL, который надо выполнить, чтобы забрать из PullRequest агрегированные данные
   *
   * @return  List<PullRequestPojo> - желаемый агрегированный список
   * */
  private List<PullRequestPojo> getAggregatedPullRequestData(String hqlQuery) {
    Transaction transaction;
    List<PullRequestPojo> pullRequestPojos = new ArrayList<>();
    try (Session session = HibernateUtil.getSessionFactory().openSession()) {
      transaction = session.beginTransaction();
      Query<PullRequestPojo> query = session.createQuery(hqlQuery, PullRequestPojo.class)
          .setParameter("status", PullRequestStatus.MERGED.getId());
      pullRequestPojos = query.getResultList();
      transaction.commit();
    } catch (Exception e) {
      e.printStackTrace();
    }
    return pullRequestPojos;
  }
}
