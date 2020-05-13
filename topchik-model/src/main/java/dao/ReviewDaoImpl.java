package dao;

import entity.Review;
import enums.ReviewStatus;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import pojo.CommonCountPojo;
import util.HibernateUtil;

import javax.inject.Singleton;
import java.util.ArrayList;
import java.util.List;

/**
 * Имплементация DAO для сущности Review
 * */
@Singleton
public class ReviewDaoImpl extends DaoImpl<Review> {
  /**
   * Метод подсчёта комментариев на ревью в PR других людей за каждый день,
   * отсортированных по репозиторию, дате и количеству
   *
   * @return List<CommonCountPojo> - желаемый агрегированный список количества комментариев на ревью в PR других людей за каждый день
   * */
  public List<CommonCountPojo> getAggregatedDailyComments() {
    final String dailyCommentsQuery = "SELECT new pojo.CommonCountPojo(" +
        "date_trunc('day', r.time) as count_date, r.accountByAuthorId, r.pullRequestByPullRequestId.repositoryByRepoId, COUNT(r) as counter) " +
        "FROM Review r WHERE r.status = :status AND r.accountByAuthorId != r.pullRequestByPullRequestId.accountByAuthorId " +
        "AND r.accountByAuthorId.login NOT LIKE '%[bot]' " +
        "GROUP BY count_date, r.accountByAuthorId, r.pullRequestByPullRequestId.repositoryByRepoId " +
        "ORDER BY r.pullRequestByPullRequestId.repositoryByRepoId, count_date, counter DESC";
    return getAggregatedReviewData(dailyCommentsQuery, ReviewStatus.COMMENTED);
  }

  /**
   * Метод подсчёта комментариев на ревью в PR других людей, агрегированных понедельно для каждого аккаунта и
   * отсортированный по репозиторию, дате и количеству
   *
   * @return List<CommonCountPojo> - желаемый агрегированный список количества комментариев на ревью в PR других людей за неделю
   * */
  public List<CommonCountPojo> getAggregatedWeeklyComments() {
    final String weeklyCommentsQuery = "SELECT new pojo.CommonCountPojo(" +
        "date_trunc('week', r.time) as count_date, r.accountByAuthorId, r.pullRequestByPullRequestId.repositoryByRepoId, COUNT(r) as counter) " +
        "FROM Review r WHERE r.status = :status AND r.accountByAuthorId != r.pullRequestByPullRequestId.accountByAuthorId " +
        "AND r.accountByAuthorId.login NOT LIKE '%[bot]' AND date_trunc('week', r.time) != date_trunc('week', current_date()) " +
        "GROUP BY count_date, r.accountByAuthorId, r.pullRequestByPullRequestId.repositoryByRepoId " +
        "ORDER BY r.pullRequestByPullRequestId.repositoryByRepoId, count_date, counter DESC";
    return getAggregatedReviewData(weeklyCommentsQuery, ReviewStatus.COMMENTED);
  }

  /**
   * Метод подсчёта PR, в которых оставили комментарии, за каждый день,
   * отсортированных по репозиторию, дате и количеству
   *
   * @return List<CommonCountPojo> - желаемый агрегированный список пулл реквестов, в которых оставили комментарии, за каждый день
   * */
  public List<CommonCountPojo> getAggregatedDailyCommentedPullRequests() {
    final String dailyCommentsQuery = "SELECT new pojo.CommonCountPojo(" +
        "date_trunc('day', r.time) as count_date, r.accountByAuthorId, r.pullRequestByPullRequestId.repositoryByRepoId, " +
        "COUNT(DISTINCT r.pullRequestByPullRequestId) as counter) " +
        "FROM Review r WHERE r.status = :status AND r.accountByAuthorId != r.pullRequestByPullRequestId.accountByAuthorId " +
        "AND r.accountByAuthorId.login NOT LIKE '%[bot]' " +
        "GROUP BY count_date, r.accountByAuthorId, r.pullRequestByPullRequestId.repositoryByRepoId " +
        "ORDER BY r.pullRequestByPullRequestId.repositoryByRepoId, count_date, counter DESC";
    return getAggregatedReviewData(dailyCommentsQuery, ReviewStatus.COMMENTED);
  }

  /**
   * Метод подсчёта PR, в которых оставили комментарии, агрегированных понедельно для каждого аккаунта и
   * отсортированных по репозиторию, дате и количеству
   *
   * @return List<CommonCountPojo> - желаемый агрегированный список пулл реквестов, в которых оставили комментарии, за неделю
   * */
  public List<CommonCountPojo> getAggregatedWeeklyCommentedPullRequests() {
    final String weeklyCommentsQuery = "SELECT new pojo.CommonCountPojo(" +
        "date_trunc('week', r.time) as count_date, r.accountByAuthorId, r.pullRequestByPullRequestId.repositoryByRepoId, " +
        "COUNT(DISTINCT r.pullRequestByPullRequestId) as counter) " +
        "FROM Review r WHERE r.status = :status AND r.accountByAuthorId != r.pullRequestByPullRequestId.accountByAuthorId " +
        "AND r.accountByAuthorId.login NOT LIKE '%[bot]' AND date_trunc('week', r.time) != date_trunc('week', current_date()) " +
        "GROUP BY count_date, r.accountByAuthorId, r.pullRequestByPullRequestId.repositoryByRepoId " +
        "ORDER BY r.pullRequestByPullRequestId.repositoryByRepoId, count_date, counter DESC";
    return getAggregatedReviewData(weeklyCommentsQuery, ReviewStatus.COMMENTED);
  }

  /**
   * Метод подсчёта апрувнутых PR за каждый день, отсортированных по репозиторию, дате и количеству
   *
   * @return List<CommonCountPojo> - желаемый агрегированный список апрувнутых пулл реквестов за каждый день
   * */
  public List<CommonCountPojo> getAggregatedDailyApprovedPullRequests() {
    final String dailyCommentsQuery = "SELECT new pojo.CommonCountPojo(" +
        "date_trunc('day', r.time) as count_date, r.accountByAuthorId, r.pullRequestByPullRequestId.repositoryByRepoId, " +
        "COUNT(DISTINCT r.pullRequestByPullRequestId) as counter) " +
        "FROM Review r WHERE r.status = :status AND r.accountByAuthorId != r.pullRequestByPullRequestId.accountByAuthorId " +
        "AND r.accountByAuthorId.login NOT LIKE '%[bot]' " +
        "GROUP BY count_date, r.accountByAuthorId, r.pullRequestByPullRequestId.repositoryByRepoId " +
        "ORDER BY r.pullRequestByPullRequestId.repositoryByRepoId, count_date, counter DESC";
    return getAggregatedReviewData(dailyCommentsQuery, ReviewStatus.APPROVED);
  }

  /**
   * Метод подсчёта апрувнутых PR, агрегированных понедельно для каждого аккаунта и
   * отсортированных по репозиторию, дате и количеству
   *
   * @return List<CommonCountPojo> - желаемый агрегированный список апрувнутых пулл реквестов за неделю
   * */
  public List<CommonCountPojo> getAggregatedWeeklyApprovedPullRequests() {
    final String weeklyCommentsQuery = "SELECT new pojo.CommonCountPojo(" +
        "date_trunc('week', r.time) as count_date, r.accountByAuthorId, r.pullRequestByPullRequestId.repositoryByRepoId, " +
        "COUNT(DISTINCT r.pullRequestByPullRequestId) as counter) " +
        "FROM Review r WHERE r.status = :status AND r.accountByAuthorId != r.pullRequestByPullRequestId.accountByAuthorId " +
        "AND r.accountByAuthorId.login NOT LIKE '%[bot]' AND date_trunc('week', r.time) != date_trunc('week', current_date()) " +
        "GROUP BY count_date, r.accountByAuthorId, r.pullRequestByPullRequestId.repositoryByRepoId " +
        "ORDER BY r.pullRequestByPullRequestId.repositoryByRepoId, count_date, counter DESC";
    return getAggregatedReviewData(weeklyCommentsQuery, ReviewStatus.APPROVED);
  }

  /**
   * Метод подсчёта апрувнутых PR, отсортированных по времени апрува
   *
   * @return List<CommonCountPojo> - желаемый агрегированный список апрувнутых PR, отсортированных по времени апрува, за каждый день
   * */
  public List<CommonCountPojo> getAggregatedDailyTimedApproves() {
    final String dailyCommentsQuery = "SELECT new pojo.CommonCountPojo(" +
        "date_trunc('day', r.time) as count_date, r.accountByAuthorId, r.pullRequestByPullRequestId.repositoryByRepoId, " +
        "CAST(EXTRACT(SECOND FROM (r.time - r.pullRequestByPullRequestId.creationTime)) as long) as counter) " +
        "FROM Review r WHERE r.status = :status AND r.accountByAuthorId != r.pullRequestByPullRequestId.accountByAuthorId " +
        "AND r.accountByAuthorId.login NOT LIKE '%[bot]' " +
        "GROUP BY count_date, r.accountByAuthorId, r.pullRequestByPullRequestId.repositoryByRepoId, " +
        "r.time, r.pullRequestByPullRequestId.creationTime " +
        "ORDER BY r.pullRequestByPullRequestId.repositoryByRepoId, count_date, counter";
    return getAggregatedReviewData(dailyCommentsQuery, ReviewStatus.APPROVED);
  }

  /**
   * Метод подсчёта апрувнутых PR, отсортированных по времени апрува
   *
   * @return List<CommonCountPojo> - желаемый агрегированный список апрувнутых PR, отсортированных по времени апрува, за неделю
   * */
  public List<CommonCountPojo> getAggregatedWeeklyTimedApproves() {
    final String weeklyCommentsQuery = "SELECT new pojo.CommonCountPojo(" +
        "date_trunc('week', r.time) as count_date, r.accountByAuthorId, r.pullRequestByPullRequestId.repositoryByRepoId, " +
        "CAST(EXTRACT(SECOND FROM (r.time - r.pullRequestByPullRequestId.creationTime)) as long) as counter) " +
        "FROM Review r WHERE r.status = :status AND r.accountByAuthorId != r.pullRequestByPullRequestId.accountByAuthorId " +
        "AND r.accountByAuthorId.login NOT LIKE '%[bot]' AND date_trunc('week', r.time) != date_trunc('week', current_date()) " +
        "GROUP BY count_date, r.accountByAuthorId, r.pullRequestByPullRequestId.repositoryByRepoId, " +
        "r.time, r.pullRequestByPullRequestId.creationTime " +
        "ORDER BY r.pullRequestByPullRequestId.repositoryByRepoId, count_date, counter";
    return getAggregatedReviewData(weeklyCommentsQuery, ReviewStatus.APPROVED);
  }

  /**
   * Общий метод для подсчёта данных по количеству комментариев на ревью в PR других людей
   *
   * @param hqlQuery - запрос на языке HQL, который надо выполнить, чтобы забрать из PullRequest агрегированные данные
   *
   * @return  List<CommonCountPojo> - желаемый агрегированный список
   * */
  private List<CommonCountPojo> getAggregatedReviewData(String hqlQuery, ReviewStatus reviewStatus) {
    Transaction transaction;
    List<CommonCountPojo> CommonCountPojos = new ArrayList<>();
    try (Session session = HibernateUtil.getSessionFactory().openSession()) {
      transaction = session.beginTransaction();
      Query<CommonCountPojo> query = session.createQuery(hqlQuery, CommonCountPojo.class)
          .setParameter("status", reviewStatus.getId());
      CommonCountPojos = query.getResultList();
      transaction.commit();
    } catch (Exception e) {
      e.printStackTrace();
    }
    return CommonCountPojos;
  }
}
