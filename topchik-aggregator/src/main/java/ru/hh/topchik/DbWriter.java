package ru.hh.topchik;

import entity.DailyCount;
import entity.WeeklyResult;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;

import java.util.List;

/**
 * Класс записи данных в БД агрегатором
 * */
public class DbWriter {
  private static final Logger LOGGER = LogManager.getLogger(DbWriter.class);

  private static Session session;
  private List<DailyCount> dailyCounts;
  private List<WeeklyResult> weeklyResults;

  public DbWriter() {
  }

  /**
   * Метод-приёмник данных для записи в БД
   *
   * @param dailyCounts - список действий
   * @param weeklyResults - список достижений
   *
   * */
  public void dataToRecordReceiver(List<DailyCount> dailyCounts, List<WeeklyResult> weeklyResults) {
    this.dailyCounts = dailyCounts;
    this.weeklyResults = weeklyResults;
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

  /**
   * Метод добавления записей в БД
   * */
  private void addRecords() {
    try {
      LOGGER.info("Добавление записей в БД");
      Transaction transaction = session.beginTransaction();

      for (DailyCount dailyCount : dailyCounts) {
        session.saveOrUpdate(dailyCount);
      }

      for (WeeklyResult weeklyResult : weeklyResults) {
        session.saveOrUpdate(weeklyResult);
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
