package ru.hh.topchik;

import dao.AccountDaoImpl;
import dao.CommentDaoImpl;
import dao.CommitDaoImpl;
import dao.DailyCountDaoImpl;
import dao.PullRequestDaoImpl;
import dao.RepositoryDaoImpl;
import dao.ReviewDaoImpl;
import dao.WeeklyResultDaoImpl;

public class DaoFactory {
  private AccountDaoImpl accountDao;
  private RepositoryDaoImpl repositoryDao;
  private PullRequestDaoImpl pullRequestDao;
  private CommentDaoImpl commentDao;
  private CommitDaoImpl commitDao;
  private ReviewDaoImpl reviewDao;
  private DailyCountDaoImpl dailyCountDao;
  private WeeklyResultDaoImpl weeklyResultDao;

  AccountDaoImpl getAccountDao() {
    if (accountDao == null) {
      accountDao = new AccountDaoImpl();
    }
    return accountDao;
  }

  RepositoryDaoImpl getRepositoryDao() {
    if (repositoryDao == null) {
      repositoryDao = new RepositoryDaoImpl();
    }
    return repositoryDao;
  }

  PullRequestDaoImpl getPullRequestDao() {
    if (pullRequestDao == null) {
      pullRequestDao = new PullRequestDaoImpl();
    }
    return pullRequestDao;
  }

  CommentDaoImpl getCommentDao() {
    if (commentDao == null) {
      commentDao = new CommentDaoImpl();
    }
    return commentDao;
  }

  CommitDaoImpl getCommitDao() {
    if (commitDao == null) {
      commitDao = new CommitDaoImpl();
    }
    return commitDao;
  }

  ReviewDaoImpl getReviewDao() {
    if (reviewDao == null) {
      reviewDao = new ReviewDaoImpl();
    }
    return reviewDao;
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
