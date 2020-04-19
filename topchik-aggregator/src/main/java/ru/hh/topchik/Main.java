package ru.hh.topchik;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * Класс запуска агрегатора
 * */
public class Main {
  private static final Logger LOGGER = LogManager.getLogger(Main.class);

  /**
   * Метод запуска агрегатора
   * */
  public static void main(String[] args) {

    LOGGER.info("Инициализация объектов для чтения и записи из/в БД");
    DbReader reader = new DbReader();
    DbWriter writer = new DbWriter();

    LOGGER.info("Чтение необходимых данных для составления ежедневной и еженедельной статистики из БД");
    reader.readAggregatedDataFromDb();

    LOGGER.info("Запись агрегированных список DailyCount и WeeklyResult в БД");
    writer.receiveDataToRecord(reader.getDailyCounts(), reader.getWeeklyResults());

    System.exit(0);
  }
}
