package ru.hh.topchik;

import entity.DailyCount;
import entity.WeeklyResult;
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

  private List<DailyCount> dailyCounts = new ArrayList<>();
  private List<WeeklyResult> weeklyResults = new ArrayList<>();

  /**
   * Метод подсчёта замёрдженных PR за день для каждого пользователя
   *
   * @param mergedPullRequests - список замёрдженных PR, полученных из БД
   *
   * */
  public void getDailyCount(List<PullRequest> mergedPullRequests) {
    long i = 0;
    for (PullRequest pr : mergedPullRequests) {
      Timestamp timestamp = pr.getLastUpdateTime();
      LocalDate date = timestamp.toLocalDateTime().toLocalDate();
      DailyCount dailyCount = new DailyCount();
      dailyCount.setDailyCountId(i++);
      dailyCount.setDate(date);
      dailyCount.setCategory(Category.SPRINTERS.getId());
      dailyCount.setCounter(1);
      dailyCount.setAccountByAccountId(pr.getAccountByAuthorId());
      dailyCount.setRepositoryByRepoId(pr.getRepositoryByRepoId());
      Optional<DailyCount> duplicate = dailyCounts.stream().filter(act -> act.hashCode() == dailyCount.hashCode()).findFirst();
      if (duplicate.isPresent()) {
        duplicate.get().setCounter(duplicate.get().getCounter() + 1);
        break;
      } else {
        dailyCounts.add(dailyCount);
      }
    }
  }

  /**
   * Метод подсчёта достижений каждого пользователя за неделю
   *
   * */
  public void getWeeklyResult() {
    long i = 0L;
    for (DailyCount dailyCount : dailyCounts) {
      WeeklyResult weeklyResult = new WeeklyResult();
      weeklyResult.setWeeklyResultId(i++);
      weeklyResult.setWeekDate(getWeekEndDate(dailyCount.getDate()));
      weeklyResult.setCategory(Category.SPRINTERS.getId());
      weeklyResult.setPoints(dailyCount.getCounter());
      weeklyResult.setMedal(Medal.NONE.getId());
      weeklyResult.setAccountByAuthorId(dailyCount.getAccountByAccountId());
      weeklyResult.setRepositoryByRepoId(dailyCount.getRepositoryByRepoId());
      Optional<WeeklyResult> duplicate = weeklyResults.stream()
          .filter(achieve -> achieve.hashCode() == weeklyResult.hashCode()).findFirst();
      if (duplicate.isPresent()) {
        duplicate.get().setPoints(duplicate.get().getPoints() + 1);
      } else {
        weeklyResults.add(weeklyResult);
      }
    }
  }

  public List<DailyCount> getDailyCounts() {
    return dailyCounts;
  }

  public List<WeeklyResult> getWeeklyResults() {
    return weeklyResults;
  }

  private LocalDate getWeekEndDate(LocalDate date) {
    long daysToAdd = DAYS_IN_A_WEEK - date.getDayOfWeek().getValue();
    return date.plusDays(daysToAdd);
  }
}
