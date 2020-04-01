
import entity.Account;
import entity.Approve;
import entity.Comment;
import entity.Commit;
import entity.PullRequest;
import entity.Repository;
import init.Init;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;

import java.util.List;

/**
 * Класс запуска Parser'a
 */
public class Start {
  private static final Logger LOGGER = LogManager.getLogger(Start.class);

  private static Init init;
  private static Session session;

  /**
   * Конструктор класса
   */
  private Start() {
    // Создание сессии
    session = createHibernateSession();
    if (session != null) {
      // Добавление записей в таблицу
      recordsAdd();
      if (session.isOpen()) {
        session.close();
      }
    }
  }

  public static void main(String[] args) throws Exception {

    final List<String> repos = List.of(
        "hh-topchik/topchik",
        "foxel93/C"
    );

    for (final String repo : repos) {
      System.out.println("Начало инициализации репозитория " + repo);
      LOGGER.info("Начало инициализации репозитория {}", repo);
      init = new Init(repo);
      System.out.println("Инициализация репозитория " + repo + " прошла успешно");
      LOGGER.info("Инициализация репозитория {} прошла успешно", repo);
      new Start();
    }
    System.exit(0);
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
      System.out.println("Создание сессии");
    } catch (Exception e) {
      System.out.println(e.getMessage());
    }
    return session;
  }

  /**
   * Процедура добавления записей в таблицу
   */
  private void recordsAdd() {
    try {
      System.out.println("Добавление записи в таблицу БД");
      Transaction tx = session.beginTransaction();

      for (final Repository repository : init.getRepositories()) {
        session.saveOrUpdate(repository);
      }
      for (final PullRequest pullRequest : init.getPullRequests()) {
        session.saveOrUpdate(pullRequest);
      }
      for (final Comment comment : init.getComments()) {
        session.saveOrUpdate(comment);
      }
      for (final Approve approve : init.getReviews()) {
        session.saveOrUpdate(approve);
      }
      for (final Account account : init.getAccounts()) {
        session.saveOrUpdate(account);
      }
      for (final Commit commit : init.getCommits()) {
        session.saveOrUpdate(commit);
      }

      tx.commit();
      System.out.println("\tЗаписи добавлены");
    } catch (Exception e) {
      System.out.println(e.getMessage());
    }
  }
}
