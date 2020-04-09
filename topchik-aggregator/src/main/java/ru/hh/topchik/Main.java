package ru.hh.topchik;

import entity.DailyCount;
import entity.WeeklyResult;
import entity.PullRequest;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;

/**
 * Класс запуска агрегатора
 * */
public class Main {
  private static final Logger LOGGER = LogManager.getLogger(Main.class);

  private static final DbReader reader = new DbReader();
  private static final Counter counter = new Counter();
  private static final DbWriter writer = new DbWriter();

  /**
   * Метод запуска агрегатора
   * */
  public static void main(String[] args) {
    LOGGER.info("Получение данных о замёрдженных PR из БД");
    List<PullRequest> mergedPullRequests = reader.readMergedPullRequests();

    LOGGER.info("Подсчет действий и достижений Counter'ом");
    counter.countDailyCount(mergedPullRequests);
    counter.countWeeklyResult();

    List<DailyCount> dailyCounts = counter.getDailyCounts();
    List<WeeklyResult> weeklyResults = counter.getWeeklyResults();
    writer.dataToRecordReceiver(dailyCounts, weeklyResults);

    System.exit(0);
  }
}
