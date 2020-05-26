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
   * Метод подсчёта времени апрува PR (с момента создания PR до момента апрува) 
   * за каждый день, отсортированных по репозиторию, дате и количеству
   *
   * @return List<CommonCountPojo> - желаемый агрегированный список апрувнутых PR, отсортированных по времени апрува, за каждый день
   * */
  public List<CommonCountPojo> getAggregatedDailyTimedApproves() {
    final String dailyCommentsQuery = "SELECT new pojo.CommonCountPojo(" +
        "date_trunc('day', r.time) as count_date, r.accountByAuthorId, r.pullRequestByPullRequestId.repositoryByRepoId, " +
        "SUM(CAST(((year(r.time) - year(r.pullRequestByPullRequestId.creationTime)) * 31536000 + " +
        "(month(r.time) - month(r.pullRequestByPullRequestId.creationTime)) * 2628000 + " +
        "(day(r.time) - day(r.pullRequestByPullRequestId.creationTime)) * 86400 + " +
        "(hour(r.time) - hour(r.pullRequestByPullRequestId.creationTime)) * 3600 + " +
        "(minute(r.time) - minute(r.pullRequestByPullRequestId.creationTime)) * 60 + " +
        "(second(r.time) - second(r.pullRequestByPullRequestId.creationTime))) as long)) as counter) " +
        "FROM Review r WHERE r.status = :status AND r.accountByAuthorId != r.pullRequestByPullRequestId.accountByAuthorId " +
        "AND r.accountByAuthorId.login NOT LIKE '%[bot]' " +
        "GROUP BY count_date, r.accountByAuthorId, r.pullRequestByPullRequestId.repositoryByRepoId " +
        "ORDER BY r.pullRequestByPullRequestId.repositoryByRepoId, count_date, counter";
    return getAggregatedReviewData(dailyCommentsQuery, ReviewStatus.APPROVED);
  }

  /**
   * Метод подсчёта времени апрува PR (с момента создания PR до момента апрува), агрегированных понедельно для каждого аккаунта и
   * отсортированных по репозиторию, дате и количеству
   *
   * @return List<CommonCountPojo> - желаемый агрегированный список апрувнутых PR, отсортированных по времени апрува, за неделю
   * */
  public List<CommonCountPojo> getAggregatedWeeklyTimedApproves() {
    final String weeklyCommentsQuery = "SELECT new pojo.CommonCountPojo(" +
        "date_trunc('week', r.time) as count_date, r.accountByAuthorId, r.pullRequestByPullRequestId.repositoryByRepoId, " +
        "SUM(CAST(((year(r.time) - year(r.pullRequestByPullRequestId.creationTime)) * 31536000 + " +
        "(month(r.time) - month(r.pullRequestByPullRequestId.creationTime)) * 2628000 + " +
        "(day(r.time) - day(r.pullRequestByPullRequestId.creationTime)) * 86400 + " +
        "(hour(r.time) - hour(r.pullRequestByPullRequestId.creationTime)) * 3600 + " +
        "(minute(r.time) - minute(r.pullRequestByPullRequestId.creationTime)) * 60 + " +
        "(second(r.time) - second(r.pullRequestByPullRequestId.creationTime))) as long)) as counter) " +
        "FROM Review r WHERE r.status = :status AND r.accountByAuthorId != r.pullRequestByPullRequestId.accountByAuthorId " +
        "AND r.accountByAuthorId.login NOT LIKE '%[bot]' AND date_trunc('week', r.time) != date_trunc('week', current_date()) " +
        "GROUP BY count_date, r.accountByAuthorId, r.pullRequestByPullRequestId.repositoryByRepoId " +
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
    List<CommonCountPojo> commonCountPojos = new ArrayList<>();
    try (Session session = HibernateUtil.getSessionFactory().openSession()) {
      transaction = session.beginTransaction();
      Query<CommonCountPojo> query = session.createQuery(hqlQuery, CommonCountPojo.class)
          .setParameter("status", reviewStatus.getId());
      commonCountPojos = query.getResultList();
      transaction.commit();
    } catch (Exception e) {
      e.printStackTrace();
    }
    return commonCountPojos;
  }
}
