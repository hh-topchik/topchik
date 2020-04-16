package ru.hh.topchik;

import entity.DailyCount;
import entity.WeeklyResult;
import enums.Category;
import enums.Medal;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import pojo.CommitCountPojo;
import pojo.PullRequestCountPojo;

import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;

/**
 * Класс чтения данных из БД агрегатором
 * */
public class DbReader {
  private static final Logger LOGGER = LogManager.getLogger(DbReader.class);
  private static final int GAP_BETWEEN_MONDAY_AND_SUNDAY = 6;
  private static final int MAX_WEEKLY_POINTS = 10;
  private static final int MIN_WEEKLY_POINTS = 1;

  private SessionFactory sessionFactory = new SessionFactory();
  private List<DailyCount> dailyCounts = new ArrayList<>();
  private List<WeeklyResult> weeklyResults = new ArrayList<>();

  public DbReader() {
  }


  public void readDataFromDb() {
    try {
      LOGGER.info("Получение списка замёрдженных PR");
      countDailyMergedPullRequests(readDailyMergedPullRequests());
      countWeeklyMergedPullRequests(readWeeklyMergedPullRequests());
      countDailyAddedLines(readDailyAddedLines());
      countWeeklyAddedLines(readWeeklyAddedLines());
      countDailyDeletedLines(readDailyDeletedLines());
      countWeeklyDeletedLines(readWeeklyDeletedLines());
    } catch (Exception e) {
      LOGGER.error(e.getMessage());
    }
  }

  private void countDailyMergedPullRequests(List<PullRequestCountPojo> pullRequestCountPojos) {
    long id = dailyCounts.size();
    for (PullRequestCountPojo mpr : pullRequestCountPojos) {
      DailyCount dailyCount = new DailyCount();
      dailyCount.setDailyCountId(id++);
      dailyCount.setDate(mpr.getDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDate());
      dailyCount.setCategory(Category.SPRINTERS.getId());
      dailyCount.setCounter(mpr.getCount());
      dailyCount.setAccountByAccountId(mpr.getAccountByAuthorId());
      dailyCount.setRepositoryByRepoId(mpr.getRepositoryByRepoId());
      dailyCounts.add(dailyCount);
    }
  }

  private List<PullRequestCountPojo> readDailyMergedPullRequests() {
    return sessionFactory.getPullRequestDao().getAggregatedDailyMergedPullRequests();
  }

  private void countWeeklyMergedPullRequests(List<PullRequestCountPojo> pullRequestCountPojos) {
    int currentPointsToAdd = MAX_WEEKLY_POINTS;
    int currentMedalToAdd = Medal.GOLD.getId();

    // Добавление первого (самого старого и максимального на той неделе) недельного результата по замёрдженным PR
    PullRequestCountPojo referencePullRequestCountPojo = pullRequestCountPojos.get(0);
    WeeklyResult firstWeeklyResult = new WeeklyResult();
    firstWeeklyResult.setWeeklyResultId(weeklyResults.size() + 1);
    firstWeeklyResult.setWeekDate(referencePullRequestCountPojo.getDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDate()
        .plusDays(GAP_BETWEEN_MONDAY_AND_SUNDAY));
    firstWeeklyResult.setCategory(Category.SPRINTERS.getId());
    firstWeeklyResult.setPoints(currentPointsToAdd);
    firstWeeklyResult.setMedal(currentMedalToAdd);
    firstWeeklyResult.setAccountByAuthorId(referencePullRequestCountPojo.getAccountByAuthorId());
    firstWeeklyResult.setRepositoryByRepoId(referencePullRequestCountPojo.getRepositoryByRepoId());
    weeklyResults.add(firstWeeklyResult);

    for (int i = 1; i < pullRequestCountPojos.size(); i++) {
      if (referencePullRequestCountPojo.getDate().equals(pullRequestCountPojos.get(i).getDate()) &&
      referencePullRequestCountPojo.getRepositoryByRepoId().getRepoId() == pullRequestCountPojos.get(i).getRepositoryByRepoId().getRepoId()) {
        WeeklyResult weeklyResult = new WeeklyResult();
        weeklyResult.setWeeklyResultId(weeklyResults.size() + 1);
        weeklyResult.setWeekDate((pullRequestCountPojos.get(i).getDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDate()
            .plusDays(GAP_BETWEEN_MONDAY_AND_SUNDAY)));
        weeklyResult.setCategory(Category.SPRINTERS.getId());
        weeklyResult.setAccountByAuthorId(pullRequestCountPojos.get(i).getAccountByAuthorId());
        weeklyResult.setRepositoryByRepoId(pullRequestCountPojos.get(i).getRepositoryByRepoId());

        // Определение сколько давать очков
        if (currentPointsToAdd < MIN_WEEKLY_POINTS) {
          weeklyResult.setPoints(currentPointsToAdd);
        } else {
          currentPointsToAdd--;
          weeklyResult.setPoints(currentPointsToAdd);
        }

        // Определение какую давать медаль (и давать ли)
        if (currentMedalToAdd >= Medal.BRONZE.getId()) {
          weeklyResult.setMedal(Medal.NONE.getId());
        } else {
          currentMedalToAdd++;
          weeklyResult.setMedal(currentMedalToAdd);
        }
        weeklyResults.add(weeklyResult);
      } else {
        referencePullRequestCountPojo = pullRequestCountPojos.get(i);
        currentPointsToAdd = MAX_WEEKLY_POINTS;
        currentMedalToAdd = Medal.GOLD.getId();

        WeeklyResult weeklyResult = new WeeklyResult();
        weeklyResult.setWeeklyResultId(weeklyResults.size() + 1);
        weeklyResult.setWeekDate(referencePullRequestCountPojo.getDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDate()
            .plusDays(GAP_BETWEEN_MONDAY_AND_SUNDAY));
        weeklyResult.setCategory(Category.SPRINTERS.getId());
        weeklyResult.setPoints(currentPointsToAdd);
        weeklyResult.setMedal(currentMedalToAdd);
        weeklyResult.setAccountByAuthorId(referencePullRequestCountPojo.getAccountByAuthorId());
        weeklyResult.setRepositoryByRepoId(referencePullRequestCountPojo.getRepositoryByRepoId());
        weeklyResults.add(weeklyResult);
      }
    }
  }

  private List<PullRequestCountPojo> readWeeklyMergedPullRequests() {
    return sessionFactory.getPullRequestDao().getAggregatedWeeklyMergedPullRequests();
  }

  private void countDailyAddedLines(List<CommitCountPojo> commitCountPojos) {
    long id = dailyCounts.size();
    for (CommitCountPojo line : commitCountPojos) {
      DailyCount dailyCount = new DailyCount();
      dailyCount.setDailyCountId(id++);
      dailyCount.setDate(line.getDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDate());
      dailyCount.setCategory(Category.MIND_GIGANTS.getId());
      dailyCount.setCounter(line.getSumLines());
      dailyCount.setAccountByAccountId(line.getAccountByAuthorId());
      dailyCount.setRepositoryByRepoId(line.getRepositoryByRepoId());
      dailyCounts.add(dailyCount);
    }
  }

  private List<CommitCountPojo> readDailyAddedLines() {
    return sessionFactory.getCommitDao().getAggregatedDailyAddedLines();
  }

  private void countWeeklyAddedLines(List<CommitCountPojo> commitCountPojos) {
    int currentPointsToAdd = MAX_WEEKLY_POINTS;
    int currentMedalToAdd = Medal.GOLD.getId();

    // Добавление первого (самого старого и максимального на той неделе) недельного результата по добавленным строкам
    CommitCountPojo referenceCommitCountPojo = commitCountPojos.get(0);
    WeeklyResult firstWeeklyResult = new WeeklyResult();
    firstWeeklyResult.setWeeklyResultId(weeklyResults.size() + 1);
    firstWeeklyResult.setWeekDate(referenceCommitCountPojo.getDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDate()
        .plusDays(GAP_BETWEEN_MONDAY_AND_SUNDAY));
    firstWeeklyResult.setCategory(Category.MIND_GIGANTS.getId());
    firstWeeklyResult.setPoints(currentPointsToAdd);
    firstWeeklyResult.setMedal(currentMedalToAdd);
    firstWeeklyResult.setAccountByAuthorId(referenceCommitCountPojo.getAccountByAuthorId());
    firstWeeklyResult.setRepositoryByRepoId(referenceCommitCountPojo.getRepositoryByRepoId());
    weeklyResults.add(firstWeeklyResult);

    for (int i = 1; i < commitCountPojos.size(); i++) {
      if (referenceCommitCountPojo.getDate().equals(commitCountPojos.get(i).getDate()) &&
          referenceCommitCountPojo.getRepositoryByRepoId().getRepoId() == commitCountPojos.get(i).getRepositoryByRepoId().getRepoId()) {
        WeeklyResult weeklyResult = new WeeklyResult();
        weeklyResult.setWeeklyResultId(weeklyResults.size() + 1);
        weeklyResult.setWeekDate((commitCountPojos.get(i).getDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDate()
            .plusDays(GAP_BETWEEN_MONDAY_AND_SUNDAY)));
        weeklyResult.setCategory(Category.MIND_GIGANTS.getId());
        weeklyResult.setAccountByAuthorId(commitCountPojos.get(i).getAccountByAuthorId());
        weeklyResult.setRepositoryByRepoId(commitCountPojos.get(i).getRepositoryByRepoId());

        // Определение сколько давать очков
        if (currentPointsToAdd < MIN_WEEKLY_POINTS) {
          weeklyResult.setPoints(currentPointsToAdd);
        } else {
          currentPointsToAdd--;
          weeklyResult.setPoints(currentPointsToAdd);
        }

        // Определение какую давать медаль (и давать ли)
        if (currentMedalToAdd >= Medal.BRONZE.getId()) {
          weeklyResult.setMedal(Medal.NONE.getId());
        } else {
          currentMedalToAdd++;
          weeklyResult.setMedal(currentMedalToAdd);
        }
        weeklyResults.add(weeklyResult);
      } else {
        referenceCommitCountPojo = commitCountPojos.get(i);
        currentPointsToAdd = MAX_WEEKLY_POINTS;
        currentMedalToAdd = Medal.GOLD.getId();

        WeeklyResult weeklyResult = new WeeklyResult();
        weeklyResult.setWeeklyResultId(weeklyResults.size() + 1);
        weeklyResult.setWeekDate(referenceCommitCountPojo.getDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDate()
            .plusDays(GAP_BETWEEN_MONDAY_AND_SUNDAY));
        weeklyResult.setCategory(Category.MIND_GIGANTS.getId());
        weeklyResult.setPoints(currentPointsToAdd);
        weeklyResult.setMedal(currentMedalToAdd);
        weeklyResult.setAccountByAuthorId(referenceCommitCountPojo.getAccountByAuthorId());
        weeklyResult.setRepositoryByRepoId(referenceCommitCountPojo.getRepositoryByRepoId());
        weeklyResults.add(weeklyResult);
      }
    }
  }

  private List<CommitCountPojo> readWeeklyAddedLines() {
    return sessionFactory.getCommitDao().getAggregatedWeeklyAddedLines();
  }

  private void countDailyDeletedLines(List<CommitCountPojo> commitCountPojos) {
    long id = dailyCounts.size();
    for (CommitCountPojo line : commitCountPojos) {
      DailyCount dailyCount = new DailyCount();
      dailyCount.setDailyCountId(id++);
      dailyCount.setDate(line.getDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDate());
      dailyCount.setCategory(Category.RENOVATORS.getId());
      dailyCount.setCounter(line.getSumLines());
      dailyCount.setAccountByAccountId(line.getAccountByAuthorId());
      dailyCount.setRepositoryByRepoId(line.getRepositoryByRepoId());
      dailyCounts.add(dailyCount);
    }
  }

  private List<CommitCountPojo> readDailyDeletedLines() {
    return sessionFactory.getCommitDao().getAggregatedDailyDeletedLines();
  }

  private void countWeeklyDeletedLines(List<CommitCountPojo> commitCountPojos) {
    int currentPointsToAdd = MAX_WEEKLY_POINTS;
    int currentMedalToAdd = Medal.GOLD.getId();

    // Добавление первого (самого старого и максимального на той неделе) недельного результата по удаленным строкам
    CommitCountPojo referenceCommitCountPojo = commitCountPojos.get(0);
    WeeklyResult firstWeeklyResult = new WeeklyResult();
    firstWeeklyResult.setWeeklyResultId(weeklyResults.size() + 1);
    firstWeeklyResult.setWeekDate(referenceCommitCountPojo.getDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDate()
        .plusDays(GAP_BETWEEN_MONDAY_AND_SUNDAY));
    firstWeeklyResult.setCategory(Category.RENOVATORS.getId());
    firstWeeklyResult.setPoints(currentPointsToAdd);
    firstWeeklyResult.setMedal(currentMedalToAdd);
    firstWeeklyResult.setAccountByAuthorId(referenceCommitCountPojo.getAccountByAuthorId());
    firstWeeklyResult.setRepositoryByRepoId(referenceCommitCountPojo.getRepositoryByRepoId());
    weeklyResults.add(firstWeeklyResult);

    for (int i = 1; i < commitCountPojos.size(); i++) {
      if (referenceCommitCountPojo.getDate().equals(commitCountPojos.get(i).getDate()) &&
          referenceCommitCountPojo.getRepositoryByRepoId().getRepoId() == commitCountPojos.get(i).getRepositoryByRepoId().getRepoId()) {
        WeeklyResult weeklyResult = new WeeklyResult();
        weeklyResult.setWeeklyResultId(weeklyResults.size() + 1);
        weeklyResult.setWeekDate((commitCountPojos.get(i).getDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDate()
            .plusDays(GAP_BETWEEN_MONDAY_AND_SUNDAY)));
        weeklyResult.setCategory(Category.RENOVATORS.getId());
        weeklyResult.setAccountByAuthorId(commitCountPojos.get(i).getAccountByAuthorId());
        weeklyResult.setRepositoryByRepoId(commitCountPojos.get(i).getRepositoryByRepoId());

        // Определение сколько давать очков
        if (currentPointsToAdd < MIN_WEEKLY_POINTS) {
          weeklyResult.setPoints(currentPointsToAdd);
        } else {
          currentPointsToAdd--;
          weeklyResult.setPoints(currentPointsToAdd);
        }

        // Определение какую давать медаль (и давать ли)
        if (currentMedalToAdd >= Medal.BRONZE.getId()) {
          weeklyResult.setMedal(Medal.NONE.getId());
        } else {
          currentMedalToAdd++;
          weeklyResult.setMedal(currentMedalToAdd);
        }
        weeklyResults.add(weeklyResult);
      } else {
        referenceCommitCountPojo = commitCountPojos.get(i);
        currentPointsToAdd = MAX_WEEKLY_POINTS;
        currentMedalToAdd = Medal.GOLD.getId();

        WeeklyResult weeklyResult = new WeeklyResult();
        weeklyResult.setWeeklyResultId(weeklyResults.size() + 1);
        weeklyResult.setWeekDate(referenceCommitCountPojo.getDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDate()
            .plusDays(GAP_BETWEEN_MONDAY_AND_SUNDAY));
        weeklyResult.setCategory(Category.RENOVATORS.getId());
        weeklyResult.setPoints(currentPointsToAdd);
        weeklyResult.setMedal(currentMedalToAdd);
        weeklyResult.setAccountByAuthorId(referenceCommitCountPojo.getAccountByAuthorId());
        weeklyResult.setRepositoryByRepoId(referenceCommitCountPojo.getRepositoryByRepoId());
        weeklyResults.add(weeklyResult);
      }
    }
  }

  private List<CommitCountPojo> readWeeklyDeletedLines() {
    return sessionFactory.getCommitDao().getAggregatedWeeklyDeletedLines();
  }

  public List<DailyCount> getDailyCounts() {
    return dailyCounts;
  }

  public List<WeeklyResult> getWeeklyResults() {
    return weeklyResults;
  }

}
