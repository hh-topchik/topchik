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

public class Counter {

  List<Action> actions = new ArrayList<>();
  List<Achievement> achievements = new ArrayList<>();
  int duplicateActionIndex = -1;
  int duplicateAchievementIndex = -1;

  public void countStatsForSprinters(List<PullRequest> mergedPullRequests) {
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
      if (checkActionDuplicate(actions, action)) {
        actions.get(duplicateActionIndex)
            .setCounter(actions.get(duplicateActionIndex).getCounter() + 1);
        break;
      } else {
        actions.add(action);
      }
    }

    i = 0L;
    for (Action action : actions) {
      Achievement achievement = new Achievement();
      achievement.setAchievementId(i++);
      long daysToSubtract = action.getDate().getDayOfWeek().getValue() - 1;
      achievement.setWeekDate(action.getDate().minusDays(daysToSubtract));
      achievement.setCategory(Category.SPRINTERS.getId());
      achievement.setPoints(action.getCounter());
      achievement.setMedal(Medal.NONE.getId());
      achievement.setAccountByDeveloperId(action.getAccountByDeveloperId());
      achievement.setRepositoryByRepoId(action.getRepositoryByRepoId());
      if (checkAchievementDuplicate(achievements, achievement)) {
        achievements.get(duplicateAchievementIndex)
            .setPoints(achievements.get(duplicateAchievementIndex).getPoints() + 1);
      } else {
        achievements.add(achievement);
      }
    }
  }

  private boolean checkActionDuplicate(List<Action> actions, Action action) {
    boolean isDuplicate = false;
    for (Action element : actions) {
      if ((element.getDate().isEqual(action.getDate())) &&
          (element.getCategory() == action.getCategory()) &&
          (element.getAccountByDeveloperId().getAccountId() == action.getAccountByDeveloperId().getAccountId())) {
        duplicateActionIndex = actions.indexOf(element);
        isDuplicate = true;
        break;
      }
    }
    return isDuplicate;
  }

  private boolean checkAchievementDuplicate(List<Achievement> achievements, Achievement achievement) {
    boolean isDuplicate = false;
    for (Achievement element : achievements) {
      if ((element.getWeekDate().isEqual(achievement.getWeekDate())) &&
          (element.getCategory() == achievement.getCategory()) &&
          (element.getAccountByDeveloperId().getAccountId() == achievement.getAccountByDeveloperId().getAccountId())) {
        duplicateAchievementIndex = achievements.indexOf(element);
        isDuplicate = true;
        break;
      }
    }
    return isDuplicate;
  }

  public List<Action> getActions() {
    return actions;
  }

  public List<Achievement> getAchievements() {
    return achievements;
  }
}
