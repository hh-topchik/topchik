package ru.hh.topchik;

import entity.DailyCount;
import entity.WeeklyResult;
import enums.Category;
import enums.Medal;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import pojo.CommonCountPojo;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
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
  private final Notifier notifier = new Notifier();
  private List<DailyCount> dailyCounts = new ArrayList<>();
  private List<WeeklyResult> weeklyResults = new ArrayList<>();

  public DbReader() {
  }

  public void readAggregatedDataFromDb() {
    try {
      LOGGER.info("Получение агрегированного списка замёрдженных PR");
      countDailyMergedPullRequests(readDailyMergedPullRequests());
      countWeeklyMergedPullRequests(readWeeklyMergedPullRequests());
      LOGGER.info("Получение агрегированного списка добавленных строк кода");
      countDailyAddedLines(readDailyAddedLines());
      countWeeklyAddedLines(readWeeklyAddedLines());
      LOGGER.info("Получение агрегированного списка удаленных строк кода");
      countDailyDeletedLines(readDailyDeletedLines());
      countWeeklyDeletedLines(readWeeklyDeletedLines());
      LOGGER.info("Получение агрегированного списка комментариев на ревью в PR других людей");
      countDailyComments(readDailyComments());
      countWeeklyComments(readWeeklyComments());
      LOGGER.info("Получение агрегированного списка PR, в которых оставлялись комментарии");
      countDailyCommentedPullRequests(readDailyCommentedPullRequests());
      countWeeklyCommentedPullRequests(readWeeklyCommentedPullRequests());
      LOGGER.info("Получение агрегированного списка апрувнутых PR");
      countDailyApprovedPullRequests(readDailyApprovedPullRequests());
      countWeeklyApprovedPullRequests(readWeeklyApprovedPullRequests());
      LOGGER.info("Получение агрегированного списка апрувнутых PR по времени апрува");
      countDailyTimedApproves(readDailyTimedApproves());
      countWeeklyTimedApproves(readWeeklyTimedApproves());
    } catch (Exception e) {
      LOGGER.error(e.getMessage());
    }
  }

  private void countDailyMergedPullRequests(List<CommonCountPojo> commonCountPojos) {
    countDailyData(commonCountPojos, Category.SPRINTERS);
  }

  private List<CommonCountPojo> readDailyMergedPullRequests() {
    return daoFactory.getPullRequestDao().getAggregatedDailyMergedPullRequests();
  }

  private void countWeeklyMergedPullRequests(List<CommonCountPojo> commonCountPojos) {
    countWeeklyData(commonCountPojos, Category.SPRINTERS);
  }

  private List<CommonCountPojo> readWeeklyMergedPullRequests() {
    return daoFactory.getPullRequestDao().getAggregatedWeeklyMergedPullRequests();
  }

  private void countDailyAddedLines(List<CommonCountPojo> commonCountPojos) {
    countDailyData(commonCountPojos, Category.MIND_GIGANTS);
  }

  private List<CommonCountPojo> readDailyAddedLines() {
    return daoFactory.getCommitDao().getAggregatedDailyAddedLines();
  }

  private void countWeeklyAddedLines(List<CommonCountPojo> commonCountPojos) {
    countWeeklyData(commonCountPojos, Category.MIND_GIGANTS);
  }

  private List<CommonCountPojo> readWeeklyAddedLines() {
    return daoFactory.getCommitDao().getAggregatedWeeklyAddedLines();
  }

  private void countDailyDeletedLines(List<CommonCountPojo> commonCountPojos) {
    countDailyData(commonCountPojos, Category.RENOVATORS);
  }

  private List<CommonCountPojo> readDailyDeletedLines() {
    return daoFactory.getCommitDao().getAggregatedDailyDeletedLines();
  }

  private void countWeeklyDeletedLines(List<CommonCountPojo> commonCountPojos) {
    countWeeklyData(commonCountPojos, Category.RENOVATORS);
  }

  private List<CommonCountPojo> readWeeklyDeletedLines() {
    return daoFactory.getCommitDao().getAggregatedWeeklyDeletedLines();
  }

  private void countDailyComments(List<CommonCountPojo> commonCountPojos) {
    countDailyData(commonCountPojos, Category.MASTERS);
  }

  private List<CommonCountPojo> readDailyComments() {
    return daoFactory.getCommentDao().getAggregatedDailyComments();
  }

  private void countWeeklyComments(List<CommonCountPojo> commonCountPojos) {
    countWeeklyData(commonCountPojos, Category.MASTERS);
  }

  private List<CommonCountPojo> readWeeklyComments() {
    return daoFactory.getCommentDao().getAggregatedWeeklyComments();
  }

  private void countDailyCommentedPullRequests(List<CommonCountPojo> commonCountPojos) {
    countDailyData(commonCountPojos, Category.OVERSEERS);
  }

  private List<CommonCountPojo> readDailyCommentedPullRequests() {
    return daoFactory.getCommentDao().getAggregatedDailyCommentedPullRequests();
  }

  private void countWeeklyCommentedPullRequests(List<CommonCountPojo> commonCountPojos) {
    countWeeklyData(commonCountPojos, Category.OVERSEERS);
  }

  private List<CommonCountPojo> readWeeklyCommentedPullRequests() {
    return daoFactory.getCommentDao().getAggregatedWeeklyCommentedPullRequests();
  }

  private void countDailyApprovedPullRequests(List<CommonCountPojo> commonCountPojos) {
    countDailyData(commonCountPojos, Category.PATRONS);
  }

  private List<CommonCountPojo> readDailyApprovedPullRequests() {
    return daoFactory.getReviewDao().getAggregatedDailyApprovedPullRequests();
  }

  private void countWeeklyApprovedPullRequests(List<CommonCountPojo> commonCountPojos) {
    countWeeklyData(commonCountPojos, Category.PATRONS);
  }

  private List<CommonCountPojo> readWeeklyApprovedPullRequests() {
    return daoFactory.getReviewDao().getAggregatedWeeklyApprovedPullRequests();
  }

  private void countDailyTimedApproves(List<CommonCountPojo> commonCountPojos) {
    countDailyData(commonCountPojos, Category.KIND_MEN);
  }

  private List<CommonCountPojo> readDailyTimedApproves() {
    return daoFactory.getReviewDao().getAggregatedDailyTimedApproves();
  }

  private void countWeeklyTimedApproves(List<CommonCountPojo> commonCountPojos) {
    countWeeklyData(commonCountPojos, Category.KIND_MEN);
  }

  private List<CommonCountPojo> readWeeklyTimedApproves() {
    return daoFactory.getReviewDao().getAggregatedWeeklyTimedApproves();
  }

  private void countDailyData(List<CommonCountPojo> commonCountPojos, Category category) {
    long id = dailyCounts.size();
    for (CommonCountPojo apr : commonCountPojos) {
      DailyCount dailyCount = new DailyCount();
      dailyCount.setDailyCountId(id++);
      dailyCount.setDate(apr.getDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDate());
      dailyCount.setCategory(category.getId());
      dailyCount.setCounter(apr.getCount());
      dailyCount.setAccountByAccountId(apr.getAccountByAuthorId());
      dailyCount.setRepositoryByRepoId(apr.getRepositoryByRepoId());
      dailyCounts.add(dailyCount);
    }
  }

  private void countWeeklyData(List<CommonCountPojo> commonCountPojos, Category category) {
    int currentPointsToAdd = MAX_WEEKLY_POINTS;
    int currentMedalToAdd = Medal.GOLD.getId();

    // Добавление первого (самого старого и максимального на той неделе) недельного результата
    CommonCountPojo referenceCommonCountPojo = commonCountPojos.get(0);
    WeeklyResult firstWeeklyResult = new WeeklyResult();
    firstWeeklyResult.setWeeklyResultId(weeklyResults.size() + 1);
    firstWeeklyResult.setWeekDate(referenceCommonCountPojo.getDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDate()
        .plusDays(GAP_BETWEEN_MONDAY_AND_SUNDAY));
    firstWeeklyResult.setCategory(category.getId());
    firstWeeklyResult.setPoints(currentPointsToAdd);
    firstWeeklyResult.setMedal(currentMedalToAdd);
    firstWeeklyResult.setAccountByAccountId(referenceCommonCountPojo.getAccountByAuthorId());
    firstWeeklyResult.setRepositoryByRepoId(referenceCommonCountPojo.getRepositoryByRepoId());
    weeklyResults.add(firstWeeklyResult);

    checkAndSendNotification(firstWeeklyResult, referenceCommonCountPojo, currentPointsToAdd);

    for (int i = 1; i < commonCountPojos.size(); i++) {
      if (referenceCommonCountPojo.getDate().equals(commonCountPojos.get(i).getDate()) &&
          referenceCommonCountPojo.getRepositoryByRepoId().getRepoId() == commonCountPojos.get(i).getRepositoryByRepoId().getRepoId()) {
        WeeklyResult weeklyResult = new WeeklyResult();
        weeklyResult.setWeeklyResultId(weeklyResults.size() + 1);
        weeklyResult.setWeekDate((commonCountPojos.get(i).getDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDate()
            .plusDays(GAP_BETWEEN_MONDAY_AND_SUNDAY)));
        weeklyResult.setCategory(category.getId());
        weeklyResult.setAccountByAccountId(commonCountPojos.get(i).getAccountByAuthorId());
        weeklyResult.setRepositoryByRepoId(commonCountPojos.get(i).getRepositoryByRepoId());

        // Определение сколько давать очков
        if (currentPointsToAdd < MIN_WEEKLY_POINTS) {
          weeklyResult.setPoints(currentPointsToAdd);
        } else {
          currentPointsToAdd--;
          weeklyResult.setPoints(currentPointsToAdd);
          checkAndSendNotification(weeklyResult, referenceCommonCountPojo, currentPointsToAdd);
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
        referenceCommonCountPojo = commonCountPojos.get(i);
        currentPointsToAdd = MAX_WEEKLY_POINTS;
        currentMedalToAdd = Medal.GOLD.getId();

        WeeklyResult weeklyResult = new WeeklyResult();
        weeklyResult.setWeeklyResultId(weeklyResults.size() + 1);
        weeklyResult.setWeekDate(referenceCommonCountPojo.getDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDate()
            .plusDays(GAP_BETWEEN_MONDAY_AND_SUNDAY));
        weeklyResult.setCategory(category.getId());
        weeklyResult.setPoints(currentPointsToAdd);
        weeklyResult.setMedal(currentMedalToAdd);
        weeklyResult.setAccountByAccountId(referenceCommonCountPojo.getAccountByAuthorId());
        weeklyResult.setRepositoryByRepoId(referenceCommonCountPojo.getRepositoryByRepoId());
        weeklyResults.add(weeklyResult);

        checkAndSendNotification(weeklyResult, referenceCommonCountPojo, currentPointsToAdd);
      }
    }
  }

  private void checkAndSendNotification(WeeklyResult weeklyResult, CommonCountPojo referenceCommonCountPojo, int currentPointsToAdd) {
    if (isNotificationDateToday() && isItLastWeekResult(weeklyResult.getWeekDate())) {
      notifier.sendNotification(daoFactory, weeklyResult.getAccountByAccountId(),
          weeklyResult.getRepositoryByRepoId(),
          getPlace(currentPointsToAdd),
          weeklyResult.getCategory(),
          referenceCommonCountPojo.getCount());
    }
  }

  private int getPlace(int currentPoints) {
    return (MAX_WEEKLY_POINTS - currentPoints) + MIN_WEEKLY_POINTS;
  }

  private boolean isNotificationDateToday() {
    return LocalDate.now().getDayOfWeek() == DayOfWeek.MONDAY;
  }

  private boolean isItLastWeekResult(LocalDate weekDate) {
    return Math.abs(ChronoUnit.DAYS.between(LocalDate.now(), weekDate)) == LocalDate.now().getDayOfWeek().getValue();
  }

  public List<DailyCount> getDailyCounts() {
    return dailyCounts;
  }

  public List<WeeklyResult> getWeeklyResults() {
    return weeklyResults;
  }
}
