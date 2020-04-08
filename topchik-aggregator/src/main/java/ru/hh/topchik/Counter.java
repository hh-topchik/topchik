package ru.hh.topchik;

import entity.Achievement;
import entity.Action;
import entity.PullRequest;
import enums.Category;
import enums.Medal;

import java.sql.Timestamp;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * Класс подсчёта статистики для действий и достижений
 * */
public class Counter {

  private static final int DAYS_IN_A_WEEK = 7;

  private List<Action> actions = new ArrayList<>();
  private List<Achievement> achievements = new ArrayList<>();

  /**
   * Метод подсчёта замёрдженных PR за день для каждого пользователя
   *
   * @param mergedPullRequests - список замёрдженных PR, полученных из БД
   *
   * */
  public void countActions(List<PullRequest> mergedPullRequests) {
    long i = 0;
    for (PullRequest pr : mergedPullRequests) {
      Timestamp timestamp = pr.getLastUpdateTime();
      LocalDate date = timestamp.toLocalDateTime().toLocalDate();
      Action action = new Action();
      action.setActionId(i++);
      action.setDate(date);
      action.setCategory(Category.SPRINTERS.getId());
      action.setCounter(1);
      action.setAccountByDeveloperId(pr.getAccountByAuthorId());
      action.setRepositoryByRepoId(pr.getRepositoryByRepoId());
      Optional<Action> duplicate = actions.stream().filter(act -> act.hashCode() == action.hashCode()).findFirst();
      if (duplicate.isPresent()) {
        duplicate.get().setCounter(duplicate.get().getCounter() + 1);
        break;
      } else {
        actions.add(action);
      }
    }
  }

  /**
   * Метод подсчёта достижений каждого пользователя за неделю
   *
   * */
  public void countAchievements() {
    long i = 0L;
    for (Action action : actions) {
      Achievement achievement = new Achievement();
      achievement.setAchievementId(i++);
      achievement.setWeekDate(getWeekEndDate(action.getDate()));
      achievement.setCategory(Category.SPRINTERS.getId());
      achievement.setPoints(action.getCounter());
      achievement.setMedal(Medal.NONE.getId());
      achievement.setAccountByDeveloperId(action.getAccountByDeveloperId());
      achievement.setRepositoryByRepoId(action.getRepositoryByRepoId());
      Optional<Achievement> duplicate = achievements.stream()
          .filter(achieve -> achieve.hashCode() == achievement.hashCode()).findFirst();
      if (duplicate.isPresent()) {
        duplicate.get().setPoints(duplicate.get().getPoints() + 1);
      } else {
        achievements.add(achievement);
      }
    }
  }

  public List<Action> getActions() {
    return actions;
  }

  public List<Achievement> getAchievements() {
    return achievements;
  }

  private LocalDate getWeekEndDate(LocalDate date) {
    long daysToAdd = DAYS_IN_A_WEEK - date.getDayOfWeek().getValue();
    return date.plusDays(daysToAdd);
  }
}
