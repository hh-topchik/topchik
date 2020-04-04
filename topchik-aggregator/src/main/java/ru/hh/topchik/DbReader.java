package ru.hh.topchik;

import entity.PullRequest;
import enums.PullRequestStatus;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;

import javax.persistence.TypedQuery;
import java.util.ArrayList;
import java.util.List;

/**
 * Класс чтения данных из БД агрегатором
 * */
public class DbReader {
  private static final Logger LOGGER = LogManager.getLogger(DbReader.class);

  private static Session session;
  private static List<PullRequest> mergedPullRequests = new ArrayList<>();

  public DbReader() {
  }

  /**
   * Метод открытия сессии для соединения с БД
   * */
  private void openSession() {
    LOGGER.info("Создание сессии");
    session = createHibernateSession();
  }

  /**
   * Метод закрытия сессии для соединения с БД
   * */
  private void closeSession() {
    LOGGER.info("Закрытие сессии");
    session.close();
  }

  /**
   * Чтение данных из БД для категории "Спринтеры"
   *
   * @return - Список замёрдженных Pull Request'ов
   */
  public List<PullRequest> readMergedPullRequests() {
    try {
      openSession();
      LOGGER.info("Получение списка замёрдженных PR");
      Transaction transaction = session.beginTransaction();
      TypedQuery<PullRequest> query = session.createQuery("FROM PullRequest WHERE status = :status",
          PullRequest.class).setParameter("status", PullRequestStatus.MERGED.getId());
      mergedPullRequests = query.getResultList();
      transaction.commit();
      closeSession();
    } catch (Exception e) {
      LOGGER.error(e.getMessage());
    }
    return mergedPullRequests;
  }

  /**
   * Процедура создания сессии
   *
   * @return org.hibernate.Session
   */
  private Session createHibernateSession() {
    final SessionFactory sessionFactory;
    try {
      try {
        Configuration cfg = new Configuration().addResource("hibernate.cfg.xml").configure();
        sessionFactory = cfg.buildSessionFactory();
      } catch (Throwable e) {
        System.err.println("Failed to create sessionFactory object." + e);
        throw new ExceptionInInitializerError(e);
      }
      session = sessionFactory.openSession();
    } catch (Exception e) {
      LOGGER.info(e.getMessage());
    }
    return session;
  }

}
