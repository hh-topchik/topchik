package ru.hh.topchik;

import entity.Achievement;
import entity.Action;
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
    counter.countActions(mergedPullRequests);
    counter.countAchievements();

    List<Action> actions = counter.getActions();
    List<Achievement> achievements = counter.getAchievements();
    writer.dataToRecordReceiver(actions, achievements);

    System.exit(0);
  }
}
