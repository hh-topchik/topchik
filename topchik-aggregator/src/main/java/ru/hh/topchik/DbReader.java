package ru.hh.topchik;

import entity.DailyCount;
import entity.WeeklyResult;
import enums.Category;
import enums.Medal;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import pojo.CommitPojo;
import pojo.PullRequestPojo;

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

  private final DaoFactory daoFactory = new DaoFactory();
  private List<DailyCount> dailyCounts = new ArrayList<>();
  private List<WeeklyResult> weeklyResults = new ArrayList<>();

  public DbReader() {
  }


  public void readAggregatedDataFromDb() {
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

  private void countDailyMergedPullRequests(List<PullRequestPojo> pullRequestPojos) {
    long id = dailyCounts.size();
    for (PullRequestPojo mpr : pullRequestPojos) {
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

  private List<PullRequestPojo> readDailyMergedPullRequests() {
    return daoFactory.getPullRequestDao().getAggregatedDailyMergedPullRequests();
  }

  private void countWeeklyMergedPullRequests(List<PullRequestPojo> pullRequestPojos) {
    int currentPointsToAdd = MAX_WEEKLY_POINTS;
    int currentMedalToAdd = Medal.GOLD.getId();

    // Добавление первого (самого старого и максимального на той неделе) недельного результата по замёрдженным PR
    PullRequestPojo referencePullRequestPojo = pullRequestPojos.get(0);
    WeeklyResult firstWeeklyResult = new WeeklyResult();
    firstWeeklyResult.setWeeklyResultId(weeklyResults.size() + 1);
    firstWeeklyResult.setWeekDate(referencePullRequestPojo.getDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDate()
        .plusDays(GAP_BETWEEN_MONDAY_AND_SUNDAY));
    firstWeeklyResult.setCategory(Category.SPRINTERS.getId());
    firstWeeklyResult.setPoints(currentPointsToAdd);
    firstWeeklyResult.setMedal(currentMedalToAdd);
    firstWeeklyResult.setAccountByAccountId(referencePullRequestPojo.getAccountByAuthorId());
    firstWeeklyResult.setRepositoryByRepoId(referencePullRequestPojo.getRepositoryByRepoId());
    weeklyResults.add(firstWeeklyResult);

    for (int i = 1; i < pullRequestPojos.size(); i++) {
      if (referencePullRequestPojo.getDate().equals(pullRequestPojos.get(i).getDate()) &&
      referencePullRequestPojo.getRepositoryByRepoId().getRepoId() == pullRequestPojos.get(i).getRepositoryByRepoId().getRepoId()) {
        WeeklyResult weeklyResult = new WeeklyResult();
        weeklyResult.setWeeklyResultId(weeklyResults.size() + 1);
        weeklyResult.setWeekDate((pullRequestPojos.get(i).getDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDate()
            .plusDays(GAP_BETWEEN_MONDAY_AND_SUNDAY)));
        weeklyResult.setCategory(Category.SPRINTERS.getId());
        weeklyResult.setAccountByAccountId(pullRequestPojos.get(i).getAccountByAuthorId());
        weeklyResult.setRepositoryByRepoId(pullRequestPojos.get(i).getRepositoryByRepoId());

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
        referencePullRequestPojo = pullRequestPojos.get(i);
        currentPointsToAdd = MAX_WEEKLY_POINTS;
        currentMedalToAdd = Medal.GOLD.getId();

        WeeklyResult weeklyResult = new WeeklyResult();
        weeklyResult.setWeeklyResultId(weeklyResults.size() + 1);
        weeklyResult.setWeekDate(referencePullRequestPojo.getDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDate()
            .plusDays(GAP_BETWEEN_MONDAY_AND_SUNDAY));
        weeklyResult.setCategory(Category.SPRINTERS.getId());
        weeklyResult.setPoints(currentPointsToAdd);
        weeklyResult.setMedal(currentMedalToAdd);
        weeklyResult.setAccountByAccountId(referencePullRequestPojo.getAccountByAuthorId());
        weeklyResult.setRepositoryByRepoId(referencePullRequestPojo.getRepositoryByRepoId());
        weeklyResults.add(weeklyResult);
      }
    }
  }

  private List<PullRequestPojo> readWeeklyMergedPullRequests() {
    return daoFactory.getPullRequestDao().getAggregatedWeeklyMergedPullRequests();
  }

  private void countDailyAddedLines(List<CommitPojo> commitPojos) {
    long id = dailyCounts.size();
    for (CommitPojo line : commitPojos) {
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

  private List<CommitPojo> readDailyAddedLines() {
    return daoFactory.getCommitDao().getAggregatedDailyAddedLines();
  }

  private void countWeeklyAddedLines(List<CommitPojo> commitPojos) {
    int currentPointsToAdd = MAX_WEEKLY_POINTS;
    int currentMedalToAdd = Medal.GOLD.getId();

    // Добавление первого (самого старого и максимального на той неделе) недельного результата по добавленным строкам
    CommitPojo referenceCommitPojo = commitPojos.get(0);
    WeeklyResult firstWeeklyResult = new WeeklyResult();
    firstWeeklyResult.setWeeklyResultId(weeklyResults.size() + 1);
    firstWeeklyResult.setWeekDate(referenceCommitPojo.getDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDate()
        .plusDays(GAP_BETWEEN_MONDAY_AND_SUNDAY));
    firstWeeklyResult.setCategory(Category.MIND_GIGANTS.getId());
    firstWeeklyResult.setPoints(currentPointsToAdd);
    firstWeeklyResult.setMedal(currentMedalToAdd);
    firstWeeklyResult.setAccountByAccountId(referenceCommitPojo.getAccountByAuthorId());
    firstWeeklyResult.setRepositoryByRepoId(referenceCommitPojo.getRepositoryByRepoId());
    weeklyResults.add(firstWeeklyResult);

    for (int i = 1; i < commitPojos.size(); i++) {
      if (referenceCommitPojo.getDate().equals(commitPojos.get(i).getDate()) &&
          referenceCommitPojo.getRepositoryByRepoId().getRepoId() == commitPojos.get(i).getRepositoryByRepoId().getRepoId()) {
        WeeklyResult weeklyResult = new WeeklyResult();
        weeklyResult.setWeeklyResultId(weeklyResults.size() + 1);
        weeklyResult.setWeekDate((commitPojos.get(i).getDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDate()
            .plusDays(GAP_BETWEEN_MONDAY_AND_SUNDAY)));
        weeklyResult.setCategory(Category.MIND_GIGANTS.getId());
        weeklyResult.setAccountByAccountId(commitPojos.get(i).getAccountByAuthorId());
        weeklyResult.setRepositoryByRepoId(commitPojos.get(i).getRepositoryByRepoId());

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
        referenceCommitPojo = commitPojos.get(i);
        currentPointsToAdd = MAX_WEEKLY_POINTS;
        currentMedalToAdd = Medal.GOLD.getId();

        WeeklyResult weeklyResult = new WeeklyResult();
        weeklyResult.setWeeklyResultId(weeklyResults.size() + 1);
        weeklyResult.setWeekDate(referenceCommitPojo.getDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDate()
            .plusDays(GAP_BETWEEN_MONDAY_AND_SUNDAY));
        weeklyResult.setCategory(Category.MIND_GIGANTS.getId());
        weeklyResult.setPoints(currentPointsToAdd);
        weeklyResult.setMedal(currentMedalToAdd);
        weeklyResult.setAccountByAccountId(referenceCommitPojo.getAccountByAuthorId());
        weeklyResult.setRepositoryByRepoId(referenceCommitPojo.getRepositoryByRepoId());
        weeklyResults.add(weeklyResult);
      }
    }
  }

  private List<CommitPojo> readWeeklyAddedLines() {
    return daoFactory.getCommitDao().getAggregatedWeeklyAddedLines();
  }

  private void countDailyDeletedLines(List<CommitPojo> commitPojos) {
    long id = dailyCounts.size();
    for (CommitPojo line : commitPojos) {
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

  private List<CommitPojo> readDailyDeletedLines() {
    return daoFactory.getCommitDao().getAggregatedDailyDeletedLines();
  }

  private void countWeeklyDeletedLines(List<CommitPojo> commitPojos) {
    int currentPointsToAdd = MAX_WEEKLY_POINTS;
    int currentMedalToAdd = Medal.GOLD.getId();

    // Добавление первого (самого старого и максимального на той неделе) недельного результата по удаленным строкам
    CommitPojo referenceCommitPojo = commitPojos.get(0);
    WeeklyResult firstWeeklyResult = new WeeklyResult();
    firstWeeklyResult.setWeeklyResultId(weeklyResults.size() + 1);
    firstWeeklyResult.setWeekDate(referenceCommitPojo.getDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDate()
        .plusDays(GAP_BETWEEN_MONDAY_AND_SUNDAY));
    firstWeeklyResult.setCategory(Category.RENOVATORS.getId());
    firstWeeklyResult.setPoints(currentPointsToAdd);
    firstWeeklyResult.setMedal(currentMedalToAdd);
    firstWeeklyResult.setAccountByAccountId(referenceCommitPojo.getAccountByAuthorId());
    firstWeeklyResult.setRepositoryByRepoId(referenceCommitPojo.getRepositoryByRepoId());
    weeklyResults.add(firstWeeklyResult);

    for (int i = 1; i < commitPojos.size(); i++) {
      if (referenceCommitPojo.getDate().equals(commitPojos.get(i).getDate()) &&
          referenceCommitPojo.getRepositoryByRepoId().getRepoId() == commitPojos.get(i).getRepositoryByRepoId().getRepoId()) {
        WeeklyResult weeklyResult = new WeeklyResult();
        weeklyResult.setWeeklyResultId(weeklyResults.size() + 1);
        weeklyResult.setWeekDate((commitPojos.get(i).getDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDate()
            .plusDays(GAP_BETWEEN_MONDAY_AND_SUNDAY)));
        weeklyResult.setCategory(Category.RENOVATORS.getId());
        weeklyResult.setAccountByAccountId(commitPojos.get(i).getAccountByAuthorId());
        weeklyResult.setRepositoryByRepoId(commitPojos.get(i).getRepositoryByRepoId());

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
        referenceCommitPojo = commitPojos.get(i);
        currentPointsToAdd = MAX_WEEKLY_POINTS;
        currentMedalToAdd = Medal.GOLD.getId();

        WeeklyResult weeklyResult = new WeeklyResult();
        weeklyResult.setWeeklyResultId(weeklyResults.size() + 1);
        weeklyResult.setWeekDate(referenceCommitPojo.getDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDate()
            .plusDays(GAP_BETWEEN_MONDAY_AND_SUNDAY));
        weeklyResult.setCategory(Category.RENOVATORS.getId());
        weeklyResult.setPoints(currentPointsToAdd);
        weeklyResult.setMedal(currentMedalToAdd);
        weeklyResult.setAccountByAccountId(referenceCommitPojo.getAccountByAuthorId());
        weeklyResult.setRepositoryByRepoId(referenceCommitPojo.getRepositoryByRepoId());
        weeklyResults.add(weeklyResult);
      }
    }
  }

  private List<CommitPojo> readWeeklyDeletedLines() {
    return daoFactory.getCommitDao().getAggregatedWeeklyDeletedLines();
  }

  public List<DailyCount> getDailyCounts() {
    return dailyCounts;
  }

  public List<WeeklyResult> getWeeklyResults() {
    return weeklyResults;
  }

}
