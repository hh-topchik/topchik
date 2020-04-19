package ru.hh.topchik;

import entity.DailyCount;
import entity.WeeklyResult;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;

/**
 * Класс записи данных в БД агрегатором
 * */
public class DbWriter {
  private static final Logger LOGGER = LogManager.getLogger(DbWriter.class);

  private final DaoFactory daoFactory = new DaoFactory();
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
  public void receiveDataToRecord(List<DailyCount> dailyCounts, List<WeeklyResult> weeklyResults) {
    this.dailyCounts = dailyCounts;
    this.weeklyResults = weeklyResults;
    addRecords();
  }

  /**
   * Метод добавления записей в БД
   * */
  private void addRecords() {
    try {
      LOGGER.info("Добавление ежедневной статистики в БД");
      daoFactory.getDailyCountDao().saveOrUpdateAll(dailyCounts);

      LOGGER.info("Добавление еженедельной статистики в БД");
      daoFactory.getWeeklyResultDao().saveOrUpdateAll(weeklyResults);

      LOGGER.info("Записи добавлены");
    } catch (Exception e) {
      LOGGER.error(e.getMessage());
      e.printStackTrace();
    }
  }
}
