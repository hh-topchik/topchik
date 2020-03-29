package ru.hh.topchik;

import entity.Achievement;
import entity.Action;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;

import java.util.List;

public class DbWriter {
  private static final Logger LOGGER = LogManager.getLogger(DbWriter.class);

  private static Session session;
  private List<Action> actions;
  private List<Achievement> achievements;

  public DbWriter(List<Action> actions, List<Achievement> achievements) {
    this.actions = actions;
    this.achievements = achievements;
    LOGGER.info("Создание сессии");
    session = createHibernateSession();
    if (session != null) {
      addRecords();
      if (session.isOpen()) {
        LOGGER.info("Закрытие сессии");
        session.close();
      }
    }
  }

  private void addRecords() {
    try {
      LOGGER.info("Добавление записей в БД");
      Transaction transaction = session.beginTransaction();

      for (Action action : actions) {
        session.saveOrUpdate(action);
      }

      for (Achievement achievement : achievements) {
        session.saveOrUpdate(achievement);
      }

      transaction.commit();
      LOGGER.info("Записи добавлены");
    } catch (Exception e) {
      LOGGER.error(e.getMessage());
      e.printStackTrace();
    }
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
