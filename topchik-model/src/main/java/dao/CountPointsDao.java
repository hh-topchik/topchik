package dao;

import org.hibernate.SessionFactory;
import pojo.CountPointsPojo;

import javax.inject.Inject;
import javax.inject.Singleton;
import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * DAO для nab полного результата в релевантных действиях (count) и в очках (points) за временной промежуток
 * */
@Singleton
public class CountPointsDao {
  private final SessionFactory sessionFactory;

  @Inject
  public CountPointsDao(SessionFactory sessionFactory) {
    this.sessionFactory = sessionFactory;
  }

  /**
   * Получение количества очков по любой категории и всем репозиториям
   * */
  @Transactional
  private List<CountPointsPojo> getFullResults(String hqlQuery, int categoryId) {
    return sessionFactory
        .getCurrentSession()
        .createQuery(hqlQuery, CountPointsPojo.class)
        .setParameter("category", categoryId)
        .setMaxResults(10)
        .getResultList();
  }

  /**
   * Получение полных результатов по любой категории и конкретному репозиторию
   * */
  @Transactional
  private List<CountPointsPojo> getFullResults(String hqlQuery, int categoryId, long repoId) {
    return sessionFactory
        .getCurrentSession()
        .createQuery(hqlQuery, CountPointsPojo.class)
        .setParameter("category", categoryId)
        .setParameter("repo", repoId)
        .setMaxResults(10)
        .getResultList();
  }

  /**
   * Получение результатов за неделю (по всем репозиторию)
   * */
  @Transactional
  public List<CountPointsPojo> getWeekResults(int categoryId) {
    String query = "SELECT new pojo.CountPointsPojo(" +
        "acc.avatar, acc.login, SUM(dc.counter), 0L) " +
        "FROM Account acc " +
        "INNER JOIN DailyCount dc ON acc.accountId = dc.accountByAccountId " +
        "WHERE date_trunc('week', dc.date) = date_trunc('week', current_date()) " +
        "AND dc.category = :category " +
        "GROUP BY acc.login, acc.avatar " +
        "ORDER BY SUM(dc.counter) DESC";
    return getFullResults(query, categoryId);
  }

  /**
   * Получение результатов за неделю (по конкретному репозиторию)
   * */
  @Transactional
  public List<CountPointsPojo> getWeekResults(int categoryId, long repoId) {
    String query = "SELECT new pojo.CountPointsPojo(" +
        "acc.avatar, acc.login, SUM(dc.counter), 0L) " +
        "FROM Account acc " +
        "INNER JOIN DailyCount dc ON acc.accountId = dc.accountByAccountId " +
        "WHERE date_trunc('week', dc.date) = date_trunc('week', current_date()) " +
        "AND dc.category = :category AND wr.repositoryByRepoId.repoId = :repo " +
        "GROUP BY acc.login, acc.avatar " +
        "ORDER BY SUM(dc.counter) DESC";
    return getFullResults(query, categoryId, repoId);
  }

  /**
   * Получение результатов за квартал (по всем репозиториям)
   * */
  @Transactional
  public List<CountPointsPojo> getQuarterResults(int categoryId) {
    String query = "SELECT new pojo.CountPointsPojo(" +
        "acc.avatar, acc.login, SUM(dc.counter), SUM(wr.points)) " +
        "FROM Account acc " +
        "INNER JOIN DailyCount dc ON acc.accountId = dc.accountByAccountId " +
        "INNER JOIN WeeklyResult wr ON acc.accountId = wr.accountByAccountId " +
        "WHERE date_trunc('quarter', wr.weekDate) = date_trunc('quarter', current_date()) " +
        "AND dc.category = :category AND wr.category = :category " +
        "GROUP BY acc.login, acc.avatar " +
        "ORDER BY SUM(wr.points) DESC, SUM(dc.counter) DESC";
    return getFullResults(query, categoryId);
  }

  /**
   * Получение результатов за квартал (по конкретному репозиторию)
   * */
  @Transactional
  public List<CountPointsPojo> getQuarterResults(int categoryId, long repoId) {
    String query = "SELECT new pojo.CountPointsPojo(" +
        "acc.avatar, acc.login, SUM(dc.counter), SUM(wr.points)) " +
        "FROM Account acc " +
        "INNER JOIN DailyCount dc ON acc.accountId = dc.accountByAccountId " +
        "INNER JOIN WeeklyResult wr ON acc.accountId = wr.accountByAccountId " +
        "WHERE date_trunc('quarter', wr.weekDate) = date_trunc('quarter', current_date()) " +
        "AND dc.category = :category AND wr.category = :category AND wr.repositoryByRepoId.repoId = :repo " +
        "GROUP BY acc.login, acc.avatar " +
        "ORDER BY SUM(wr.points) DESC, SUM(dc.counter) DESC";
    return getFullResults(query, categoryId, repoId);
  }

  /**
   * Получение результатов за год (по всем репозиториям)
   * */
  @Transactional
  public List<CountPointsPojo> getYearResults(int categoryId) {
    String query = "SELECT new pojo.CountPointsPojo(" +
        "acc.avatar, acc.login, SUM(dc.counter), SUM(wr.points)) " +
        "FROM Account acc " +
        "INNER JOIN DailyCount dc ON acc.accountId = dc.accountByAccountId " +
        "INNER JOIN WeeklyResult wr ON acc.accountId = wr.accountByAccountId " +
        "WHERE date_trunc('year', wr.weekDate) = date_trunc('year', current_date()) " +
        "AND dc.category = :category AND wr.category = :category " +
        "GROUP BY acc.login, acc.avatar " +
        "ORDER BY SUM(wr.points) DESC, SUM(dc.counter) DESC";
    return getFullResults(query, categoryId);
  }

  /**
   * Получение результатов за год (по конкретному репозиторию)
   * */
  @Transactional
  public List<CountPointsPojo> getYearResults(int categoryId, long repoId) {
    String query = "SELECT new pojo.CountPointsPojo(" +
        "acc.avatar, acc.login, SUM(dc.counter), SUM(wr.points)) " +
        "FROM Account acc " +
        "INNER JOIN DailyCount dc ON acc.accountId = dc.accountByAccountId " +
        "INNER JOIN WeeklyResult wr ON acc.accountId = wr.accountByAccountId " +
        "WHERE date_trunc('year', wr.weekDate) = date_trunc('year', current_date()) " +
        "AND dc.category = :category AND wr.category = :category AND wr.repositoryByRepoId.repoId = :repo " +
        "GROUP BY acc.login, acc.avatar " +
        "ORDER BY SUM(wr.points) DESC, SUM(dc.counter) DESC";
    return getFullResults(query, categoryId, repoId);
  }

  /**
   * Получение результатов за все время (по всем репозиторию)
   * */
  @Transactional
  public List<CountPointsPojo> getAllTimeResults(int categoryId) {
    String query = "SELECT new pojo.CountPointsPojo(" +
        "acc.avatar, acc.login, SUM(dc.counter), SUM(wr.points)) " +
        "FROM Account acc " +
        "INNER JOIN DailyCount dc ON acc.accountId = dc.accountByAccountId " +
        "INNER JOIN WeeklyResult wr ON acc.accountId = wr.accountByAccountId " +
        "WHERE dc.category = :category AND wr.category = :category " +
        "GROUP BY acc.login, acc.avatar " +
        "ORDER BY SUM(wr.points) DESC, SUM(dc.counter) DESC";
    return getFullResults(query, categoryId);
  }

  /**
   * Получение результатов за все время (по конкретному репозиторию)
   * */
  @Transactional
  public List<CountPointsPojo> getAllTimeResults(int categoryId, long repoId) {
    String query = "SELECT new pojo.CountPointsPojo(" +
        "acc.avatar, acc.login, SUM(dc.counter), SUM(wr.points)) " +
        "FROM Account acc " +
        "INNER JOIN DailyCount dc ON acc.accountId = dc.accountByAccountId " +
        "INNER JOIN WeeklyResult wr ON acc.accountId = wr.accountByAccountId " +
        "WHERE dc.category = :category AND wr.category = :category AND wr.repositoryByRepoId.repoId = :repo " +
        "GROUP BY acc.login, acc.avatar " +
        "ORDER BY SUM(wr.points) DESC, SUM(dc.counter) DESC";
    return getFullResults(query, categoryId, repoId);
  }

  /**
   * Получение списка id всех имеющихся категорий
   * */
  @Transactional
  public List<Integer> getCategoriesIdList() {
    List categoriesObjects = sessionFactory
        .getCurrentSession()
        .createNativeQuery("SELECT DISTINCT category FROM daily_count")
        .list();
    List<Integer> categoriesList = new ArrayList<>();
    for (Object object : categoriesObjects) {
      categoriesList.add((Integer) object);
    }
    Collections.sort(categoriesList);
    return categoriesList;
  }

}
