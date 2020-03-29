package ru.hh.topchik;

import entity.Achievement;
import entity.Action;
import entity.PullRequest;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;

public class Main {
  private static final Logger LOGGER = LogManager.getLogger(Main.class);

  private static DbReader reader;
  private static Counter counter;
  private static DbWriter writer;

  private static List<PullRequest> mergedPullRequests;
  private static List<Action> actions;
  private static List<Achievement> achievements;

  public static void main(String[] args) {
    LOGGER.info("Инициализация DbReader");
    reader = new DbReader();
    LOGGER.info("Получение данных о замёрдженных PR из БД");
    mergedPullRequests = reader.readDataForSprinters();
    LOGGER.info("Инициализация Counter");
    counter = new Counter();
    LOGGER.info("Подсчет действий и достижений Counter'ом");
    counter.countStatsForSprinters(mergedPullRequests);
    actions = counter.getActions();
    achievements = counter.getAchievements();
    LOGGER.info("Инициализация DbWriter");
    writer = new DbWriter(actions, achievements);
    System.exit(0);
  }
}
