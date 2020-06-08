package dao;

import enums.Category;
import org.hibernate.SessionFactory;
import org.hibernate.transform.Transformers;
import org.hibernate.type.StandardBasicTypes;
import pojo.CountPointsPojo;

import javax.inject.Inject;
import javax.inject.Singleton;
import javax.transaction.Transactional;
import java.math.BigInteger;
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
   * Получение полных результатов по любой категории и конкретному репозиторию
   * */
  @Transactional
  @SuppressWarnings("unchecked")
  private List<CountPointsPojo> getFullResults(String query, Integer categoryId, Long repoId) {
    if (repoId == null) {
      return sessionFactory
          .getCurrentSession()
          .createSQLQuery(query)
          .setParameter("category", categoryId)
          .addScalar("avatar", StandardBasicTypes.STRING)
          .addScalar("account", StandardBasicTypes.STRING)
          .addScalar("count", StandardBasicTypes.LONG)
          .addScalar("points", StandardBasicTypes.LONG)
          .setResultTransformer(Transformers.aliasToBean(CountPointsPojo.class))
          .list();
    } else {
      return sessionFactory
          .getCurrentSession()
          .createSQLQuery(query)
          .setParameter("category", categoryId)
          .setParameter("repo", repoId)
          .addScalar("avatar", StandardBasicTypes.STRING)
          .addScalar("account", StandardBasicTypes.STRING)
          .addScalar("count", StandardBasicTypes.LONG)
          .addScalar("points", StandardBasicTypes.LONG)
          .setResultTransformer(Transformers.aliasToBean(CountPointsPojo.class))
          .list();
    }
  }

  /**
   * Получение результатов за неделю
   * */
  @Transactional
  public List<CountPointsPojo> getWeekResults(Integer categoryId, Long repoId) {
    String query;
    if (repoId == null) {
      query = "SELECT acc.avatar AS avatar, acc.login AS account, CAST(SUM(dc.counter) AS bigint) AS count, CAST(0 AS bigint) AS points " +
          "FROM account AS acc " +
          "INNER JOIN daily_count AS dc ON acc.account_id = dc.account_id " +
          "WHERE date_trunc('week', dc.date) = date_trunc('week', current_date) " +
          "AND dc.category = :category " +
          "GROUP BY account, avatar " +
          getOrderSubquery(categoryId, true);
    } else {
      query = "SELECT acc.avatar AS avatar, acc.login AS account, CAST(SUM(dc.counter) AS bigint) AS count, CAST(0 AS bigint) AS points " +
          "FROM account AS acc " +
          "INNER JOIN daily_count AS dc ON acc.account_id = dc.account_id " +
          "WHERE date_trunc('week', dc.date) = date_trunc('week', current_date) " +
          "AND dc.repo_id = :repo AND dc.category = :category " +
          "GROUP BY account, avatar " +
          getOrderSubquery(categoryId, true);
    }
    return getFullResults(query, categoryId, repoId);
  }

  /**
   * Получение результатов за квартал
   * */
  @Transactional
  public List<CountPointsPojo> getQuarterResults(Integer categoryId, Long repoId) {
    String query;
    if (repoId == null) {
      query = "SELECT dc_select.avatar AS avatar, dc_select.login AS account, " +
          "CAST(SUM(dc_select.count) AS bigint) AS count, CAST(SUM(wr_select.points) AS bigint) AS points " +
          "FROM " +
          "(SELECT acc.avatar, acc.login, SUM(dc.counter) AS count " +
          "FROM account AS acc " +
          "INNER JOIN daily_count AS dc ON acc.account_id = dc.account_id " +
          "WHERE date_trunc('quarter', dc.date) = date_trunc('quarter', current_date) " +
          "AND date_trunc('week', dc.date) != date_trunc('week', current_date) " +
          "AND dc.category = :category " +
          "GROUP BY acc.login, acc.avatar " +
          "ORDER BY count DESC) AS dc_select " +
          "LEFT JOIN " +
          "(SELECT acc.avatar, acc.login, SUM(wr.points) AS points " +
          "FROM account AS acc " +
          "INNER JOIN weekly_result AS wr ON acc.account_id = wr.account_id " +
          "WHERE date_trunc('quarter', wr.week_date) = date_trunc('quarter', current_date) " +
          "AND date_trunc('week', wr.week_date) != date_trunc('week', current_date) " +
          "AND wr.category = :category " +
          "GROUP BY acc.login, acc.avatar " +
          "ORDER BY points DESC) AS wr_select " +
          "ON (dc_select.login = wr_select.login) " +
          "GROUP BY dc_select.login, dc_select.avatar " +
          getOrderSubquery(categoryId, false);
    } else {
      query = "SELECT dc_select.avatar AS avatar, dc_select.login AS account, " +
          "CAST(SUM(dc_select.count) AS bigint) AS count, CAST(SUM(wr_select.points) AS bigint) AS points " +
          "FROM " +
          "(SELECT acc.avatar, acc.login, SUM(dc.counter) AS count " +
          "FROM account AS acc " +
          "INNER JOIN daily_count AS dc ON acc.account_id = dc.account_id " +
          "WHERE date_trunc('quarter', dc.date) = date_trunc('quarter', current_date) " +
          "AND date_trunc('week', dc.date) != date_trunc('week', current_date) " +
          "AND dc.repo_id = :repo AND dc.category = :category " +
          "GROUP BY acc.login, acc.avatar " +
          "ORDER BY count DESC) AS dc_select " +
          "LEFT JOIN " +
          "(SELECT acc.avatar, acc.login, SUM(wr.points) AS points " +
          "FROM account AS acc " +
          "INNER JOIN weekly_result AS wr ON acc.account_id = wr.account_id " +
          "WHERE date_trunc('quarter', wr.week_date) = date_trunc('quarter', current_date) " +
          "AND date_trunc('week', wr.week_date) != date_trunc('week', current_date) " +
          "AND wr.repo_id = :repo AND wr.category = :category " +
          "GROUP BY acc.login, acc.avatar " +
          "ORDER BY points DESC) AS wr_select " +
          "ON (dc_select.login = wr_select.login) " +
          "GROUP BY dc_select.login, dc_select.avatar " +
          getOrderSubquery(categoryId, false);
    }
    return getFullResults(query, categoryId, repoId);
  }

  /**
   * Получение результатов за год
   * */
  @Transactional
  public List<CountPointsPojo> getYearResults(Integer categoryId, Long repoId) {
    String query;
    if (repoId == null) {
      query = "SELECT dc_select.avatar AS avatar, dc_select.login AS account, " +
          "CAST(SUM(dc_select.count) AS bigint) AS count, CAST(SUM(wr_select.points) AS bigint) AS points " +
          "FROM " +
          "(SELECT acc.avatar, acc.login, SUM(dc.counter) AS count " +
          "FROM account AS acc " +
          "INNER JOIN daily_count AS dc ON acc.account_id = dc.account_id " +
          "WHERE date_trunc('year', dc.date) = date_trunc('year', current_date) " +
          "AND date_trunc('week', dc.date) != date_trunc('week', current_date) " +
          "AND dc.category = :category " +
          "GROUP BY acc.login, acc.avatar " +
          "ORDER BY count DESC) AS dc_select " +
          "LEFT JOIN " +
          "(SELECT acc.avatar, acc.login, SUM(wr.points) AS points " +
          "FROM account AS acc " +
          "INNER JOIN weekly_result AS wr ON acc.account_id = wr.account_id " +
          "WHERE date_trunc('year', wr.week_date) = date_trunc('year', current_date) " +
          "AND date_trunc('week', wr.week_date) != date_trunc('week', current_date) " +
          "AND wr.category = :category " +
          "GROUP BY acc.login, acc.avatar " +
          "ORDER BY points DESC) AS wr_select " +
          "ON (dc_select.login = wr_select.login) " +
          "GROUP BY dc_select.login, dc_select.avatar " +
          getOrderSubquery(categoryId, false);
    } else {
      query = "SELECT dc_select.avatar AS avatar, dc_select.login AS account, " +
          "CAST(SUM(dc_select.count) AS bigint) AS count, CAST(SUM(wr_select.points) AS bigint) AS points " +
          "FROM " +
          "(SELECT acc.avatar, acc.login, SUM(dc.counter) AS count " +
          "FROM account AS acc " +
          "INNER JOIN daily_count AS dc ON acc.account_id = dc.account_id " +
          "WHERE date_trunc('year', dc.date) = date_trunc('year', current_date) " +
          "AND date_trunc('week', dc.date) != date_trunc('week', current_date) " +
          "AND dc.repo_id = :repo AND dc.category = :category " +
          "GROUP BY acc.login, acc.avatar " +
          "ORDER BY count DESC) AS dc_select " +
          "LEFT JOIN " +
          "(SELECT acc.avatar, acc.login, SUM(wr.points) AS points " +
          "FROM account AS acc " +
          "INNER JOIN weekly_result AS wr ON acc.account_id = wr.account_id " +
          "WHERE date_trunc('year', wr.week_date) = date_trunc('year', current_date) " +
          "AND date_trunc('week', wr.week_date) != date_trunc('week', current_date) " +
          "AND wr.repo_id = :repo AND wr.category = :category " +
          "GROUP BY acc.login, acc.avatar " +
          "ORDER BY points DESC) AS wr_select " +
          "ON (dc_select.login = wr_select.login) " +
          "GROUP BY dc_select.login, dc_select.avatar " +
          getOrderSubquery(categoryId, false);
    }
    return getFullResults(query, categoryId, repoId);
  }

  /**
   * Получение результатов за все время
   * */
  @Transactional
  public List<CountPointsPojo> getAllTimeResults(Integer categoryId, Long repoId) {
    String query;
    if (repoId == null) {
      query = "SELECT dc_select.avatar AS avatar, dc_select.login AS account, " +
          "CAST(SUM(dc_select.count) AS bigint) AS count, CAST(SUM(wr_select.points) AS bigint) AS points " +
          "FROM " +
          "(SELECT acc.avatar, acc.login, SUM(dc.counter) AS count " +
          "FROM account AS acc " +
          "INNER JOIN daily_count AS dc ON acc.account_id = dc.account_id " +
          "WHERE dc.category = :category " +
          "AND date_trunc('week', dc.date) != date_trunc('week', current_date) " +
          "GROUP BY acc.login, acc.avatar " +
          "ORDER BY count DESC) AS dc_select " +
          "LEFT JOIN " +
          "(SELECT acc.avatar, acc.login, SUM(wr.points) AS points " +
          "FROM account AS acc " +
          "INNER JOIN weekly_result AS wr ON acc.account_id = wr.account_id " +
          "WHERE wr.category = :category " +
          "AND date_trunc('week', wr.week_date) != date_trunc('week', current_date) " +
          "GROUP BY acc.login, acc.avatar " +
          "ORDER BY points DESC) AS wr_select " +
          "ON (dc_select.login = wr_select.login) " +
          "GROUP BY dc_select.login, dc_select.avatar " +
          getOrderSubquery(categoryId, false);
    } else {
      query = "SELECT dc_select.avatar AS avatar, dc_select.login AS account, " +
          "CAST(SUM(dc_select.count) AS bigint) AS count, CAST(SUM(wr_select.points) AS bigint) AS points " +
          "FROM " +
          "(SELECT acc.avatar, acc.login, SUM(dc.counter) AS count " +
          "FROM account AS acc " +
          "INNER JOIN daily_count AS dc ON acc.account_id = dc.account_id " +
          "WHERE dc.repo_id = :repo AND dc.category = :category " +
          "AND date_trunc('week', dc.date) != date_trunc('week', current_date) " +
          "GROUP BY acc.login, acc.avatar " +
          "ORDER BY count DESC) AS dc_select " +
          "LEFT JOIN " +
          "(SELECT acc.avatar, acc.login, SUM(wr.points) AS points " +
          "FROM account AS acc " +
          "INNER JOIN weekly_result AS wr ON acc.account_id = wr.account_id " +
          "WHERE wr.repo_id = :repo AND wr.category = :category " +
          "AND date_trunc('week', wr.week_date) != date_trunc('week', current_date) " +
          "GROUP BY acc.login, acc.avatar " +
          "ORDER BY points DESC) AS wr_select " +
          "ON (dc_select.login = wr_select.login) " +
          "GROUP BY dc_select.login, dc_select.avatar " +
          getOrderSubquery(categoryId, false);
    }
    return getFullResults(query, categoryId, repoId);
  }

  /**
   * Метод определения, требуется ли возвращать результат в перевёрнутом (reverse) порядке или нет
   * */
  private String getOrderSubquery(int categoryId, boolean isWeekPeriod) {
    if (categoryId == Category.KIND_MEN.getId()) {
      if (isWeekPeriod) {
        return "ORDER BY count LIMIT 10";
      } else {
        return "ORDER BY points DESC, count LIMIT 10";
      }
    } else {
      if (isWeekPeriod) {
        return "ORDER BY count DESC LIMIT 10";
      } else {
        return "ORDER BY points DESC, count DESC LIMIT 10";
      }
    }
  }

  /**
   * Получение списка id всех имеющихся категорий
   * */
  @Transactional
  @SuppressWarnings("unchecked")
  public List<Integer> getCategoriesIdList(Long accountId) {
    if (accountId == null) {
      return sessionFactory
          .getCurrentSession()
          .createNativeQuery("SELECT DISTINCT category FROM daily_count " +
              "ORDER BY category")
          .list();
    } else {
      return sessionFactory
          .getCurrentSession()
          .createNativeQuery("SELECT DISTINCT category FROM daily_count WHERE account_id =:account_id " +
              "ORDER BY category")
          .setParameter("account_id", accountId)
          .list();
    }
  }

  /**
   * Получение списка id всех пользователей в данном репозитории
   * */
  @Transactional
  @SuppressWarnings("unchecked")
  public List<BigInteger> getReposAccountIdList(Long repoId) {
    if (repoId == null) {
      return sessionFactory
          .getCurrentSession()
          .createNativeQuery("SELECT DISTINCT account_id FROM daily_count " +
              "ORDER BY account_id")
          .list();
    } else {
      return sessionFactory
          .getCurrentSession()
          .createNativeQuery("SELECT DISTINCT account_id FROM daily_count WHERE repo_id = :repo_id " +
              "ORDER BY account_id")
          .setParameter("repo_id", repoId)
          .list();
    }
  }
}
