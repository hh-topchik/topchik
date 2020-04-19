package ru.hh.topchik;

import dao.CommitDaoImpl;
import dao.DailyCountDaoImpl;
import dao.PullRequestDaoImpl;
import dao.WeeklyResultDaoImpl;

public class DaoFactory {
  private PullRequestDaoImpl pullRequestDao;
  private CommitDaoImpl commitDao;
  private DailyCountDaoImpl dailyCountDao;
  private WeeklyResultDaoImpl weeklyResultDao;

  PullRequestDaoImpl getPullRequestDao() {
    if (pullRequestDao == null) {
      pullRequestDao = new PullRequestDaoImpl();
    }
    return pullRequestDao;
  }

  CommitDaoImpl getCommitDao() {
    if (commitDao == null) {
      commitDao = new CommitDaoImpl();
    }
    return commitDao;
  }

  DailyCountDaoImpl getDailyCountDao() {
    if (dailyCountDao == null) {
      dailyCountDao = new DailyCountDaoImpl();
    }
    return dailyCountDao;
  }

  WeeklyResultDaoImpl getWeeklyResultDao() {
    if (weeklyResultDao == null) {
      weeklyResultDao = new WeeklyResultDaoImpl();
    }
    return weeklyResultDao;
  }
}
