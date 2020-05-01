package pojo;

import entity.Account;
import entity.Repository;

import java.util.Date;

/**
 * POJO для получения нужных полей (+ count) из БД по запросу замёрдженных PR
 * */
public class PullRequestPojo {
  private Date date;
  private Account accountByAuthorId;
  private Repository repositoryByRepoId;
  private long count;

  public PullRequestPojo(Date date, Account accountByAuthorId, Repository repositoryByRepoId, long count) {
    this.date = date;
    this.accountByAuthorId = accountByAuthorId;
    this.repositoryByRepoId = repositoryByRepoId;
    this.count = count;
  }

  public Date getDate() {
    return date;
  }

  public void setDate(Date date) {
    this.date = date;
  }

  public Account getAccountByAuthorId() {
    return accountByAuthorId;
  }

  public void setAccountByAuthorId(Account accountByAuthorId) {
    this.accountByAuthorId = accountByAuthorId;
  }

  public Repository getRepositoryByRepoId() {
    return repositoryByRepoId;
  }

  public void setRepositoryByRepoId(Repository repositoryByRepoId) {
    this.repositoryByRepoId = repositoryByRepoId;
  }

  public long getCount() {
    return count;
  }

  public void setCount(long count) {
    this.count = count;
  }
}
