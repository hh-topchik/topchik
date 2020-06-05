package dao;

import entity.Comment;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import pojo.CommonCountPojo;
import util.HibernateUtil;

import javax.inject.Singleton;
import java.util.List;

/**
 * Имплементация DAO для сущности Comment
 * */
@Singleton
public class CommentDaoImpl extends DaoImpl<Comment> {
  /**
   * Метод подсчёта комментариев на ревью в PR других людей за каждый день,
   * отсортированных по репозиторию, дате и количеству
   *
   * @return List<CommonCountPojo> - желаемый агрегированный список количества комментариев на ревью в PR других людей за каждый день
   * */
  public List<CommonCountPojo> getAggregatedDailyComments() {
    final String dailyCommentsQuery = "SELECT new pojo.CommonCountPojo(" +
        "date_trunc('day', c.creationTime) as count_date, c.reviewByReviewId.accountByAuthorId, " +
        "c.reviewByReviewId.pullRequestByPullRequestId.repositoryByRepoId, COUNT(c) as counter) " +
        "FROM Comment c WHERE c.reviewByReviewId.accountByAuthorId != c.reviewByReviewId.pullRequestByPullRequestId.accountByAuthorId " +
        "AND c.reviewByReviewId.accountByAuthorId.login NOT LIKE '%[bot]' " +
        "AND c.reviewByReviewId.accountByAuthorId.login NOT LIKE 'testUser%' " +
        "GROUP BY count_date, c.reviewByReviewId.accountByAuthorId, c.reviewByReviewId.pullRequestByPullRequestId.repositoryByRepoId " +
        "ORDER BY c.reviewByReviewId.pullRequestByPullRequestId.repositoryByRepoId, count_date, counter DESC";
    return getAggregatedCommentData(dailyCommentsQuery);
  }

  /**
   * Метод подсчёта комментариев на ревью в PR других людей, агрегированных понедельно для каждого аккаунта и
   * отсортированный по репозиторию, дате и количеству
   *
   * @return List<CommonCountPojo> - желаемый агрегированный список количества комментариев на ревью в PR других людей за неделю
   * */
  public List<CommonCountPojo> getAggregatedWeeklyComments() {
    final String weeklyCommentsQuery = "SELECT new pojo.CommonCountPojo(" +
        "date_trunc('week', c.creationTime) as count_date, c.reviewByReviewId.accountByAuthorId, " +
        "c.reviewByReviewId.pullRequestByPullRequestId.repositoryByRepoId, COUNT(c) as counter) " +
        "FROM Comment c WHERE c.reviewByReviewId.accountByAuthorId != c.reviewByReviewId.pullRequestByPullRequestId.accountByAuthorId " +
        "AND c.reviewByReviewId.accountByAuthorId.login NOT LIKE '%[bot]' " +
        "AND c.reviewByReviewId.accountByAuthorId.login NOT LIKE 'testUser%' " +
        "AND date_trunc('week', c.creationTime) != date_trunc('week', current_date()) " +
        "GROUP BY count_date, c.reviewByReviewId.accountByAuthorId, c.reviewByReviewId.pullRequestByPullRequestId.repositoryByRepoId " +
        "ORDER BY c.reviewByReviewId.pullRequestByPullRequestId.repositoryByRepoId, count_date, counter DESC";
    return getAggregatedCommentData(weeklyCommentsQuery);
  }

  /**
   * Метод подсчёта PR, в которых оставили комментарии, за каждый день,
   * отсортированных по репозиторию, дате и количеству
   *
   * @return List<CommonCountPojo> - желаемый агрегированный список пулл реквестов, в которых оставили комментарии, за каждый день
   * */
  public List<CommonCountPojo> getAggregatedDailyCommentedPullRequests() {
    final String dailyCommentsQuery = "SELECT new pojo.CommonCountPojo(" +
        "date_trunc('day', c.creationTime) as count_date, c.reviewByReviewId.accountByAuthorId, " +
        "c.reviewByReviewId.pullRequestByPullRequestId.repositoryByRepoId, " +
        "COUNT(DISTINCT c.reviewByReviewId.pullRequestByPullRequestId.pullRequestId) as counter) " +
        "FROM Comment c " +
        "WHERE c.reviewByReviewId.accountByAuthorId != c.reviewByReviewId.pullRequestByPullRequestId.accountByAuthorId " +
        "AND c.reviewByReviewId.accountByAuthorId.login NOT LIKE '%[bot]' " +
        "AND c.reviewByReviewId.accountByAuthorId.login NOT LIKE 'testUser%' " +
        "GROUP BY count_date, c.reviewByReviewId.accountByAuthorId, " +
        "c.reviewByReviewId.pullRequestByPullRequestId.repositoryByRepoId " +
        "ORDER BY c.reviewByReviewId.pullRequestByPullRequestId.repositoryByRepoId, count_date, counter DESC";
    return getAggregatedCommentData(dailyCommentsQuery);
  }

  /**
   * Метод подсчёта PR, в которых оставили комментарии, агрегированных понедельно для каждого аккаунта и
   * отсортированных по репозиторию, дате и количеству
   *
   * @return List<CommonCountPojo> - желаемый агрегированный список пулл реквестов, в которых оставили комментарии, за неделю
   * */
  public List<CommonCountPojo> getAggregatedWeeklyCommentedPullRequests() {
    final String weeklyCommentsQuery = "SELECT new pojo.CommonCountPojo(" +
        "date_trunc('week', c.creationTime) as count_date, c.reviewByReviewId.accountByAuthorId, " +
        "c.reviewByReviewId.pullRequestByPullRequestId.repositoryByRepoId, " +
        "COUNT(DISTINCT c.reviewByReviewId.pullRequestByPullRequestId.pullRequestId) as counter) " +
        "FROM Comment c " +
        "WHERE c.reviewByReviewId.accountByAuthorId != c.reviewByReviewId.pullRequestByPullRequestId.accountByAuthorId " +
        "AND c.reviewByReviewId.accountByAuthorId.login NOT LIKE '%[bot]' " +
        "AND c.reviewByReviewId.accountByAuthorId.login NOT LIKE 'testUser%' " +
        "AND date_trunc('week', c.creationTime) != date_trunc('week', current_date()) " +
        "GROUP BY count_date, c.reviewByReviewId.accountByAuthorId, " +
        "c.reviewByReviewId.pullRequestByPullRequestId.repositoryByRepoId " +
        "ORDER BY c.reviewByReviewId.pullRequestByPullRequestId.repositoryByRepoId, count_date, counter DESC";
    return getAggregatedCommentData(weeklyCommentsQuery);
  }

  /**
   * Общий метод для подсчёта данных по количеству комментариев на ревью в PR других людей
   *
   * @param hqlQuery - запрос на языке HQL, который надо выполнить, чтобы забрать из PullRequest агрегированные данные
   *
   * @return  List<CommonCountPojo> - желаемый агрегированный список
   * */
  private List<CommonCountPojo> getAggregatedCommentData(String hqlQuery) {
    Transaction transaction;
    List<CommonCountPojo> commonCountPojos = null;
    try (Session session = HibernateUtil.getSessionFactory().openSession()) {
      transaction = session.beginTransaction();
      Query<CommonCountPojo> query = session.createQuery(hqlQuery, CommonCountPojo.class);
      commonCountPojos = query.getResultList();
      transaction.commit();
    } catch (Exception e) {
      e.printStackTrace();
      System.out.println(e.toString());
    }
    return commonCountPojos;
  }
}
