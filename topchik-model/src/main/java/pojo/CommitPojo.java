package pojo;

import entity.Account;
import entity.Repository;

import java.util.Date;

/**
 * POJO для получения нужных полей из БД по запросу добавленных или удалённых строк
 * */
public class CommitPojo {
  private Date date;
  private Account accountByAuthorId;
  private Repository repositoryByRepoId;
  private long sumLines;

  public CommitPojo(Date date, Account accountByAuthorId, Repository repositoryByRepoId, long sumLines) {
    this.date = date;
    this.accountByAuthorId = accountByAuthorId;
    this.repositoryByRepoId = repositoryByRepoId;
    this.sumLines = sumLines;
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

  public long getSumLines() {
    return sumLines;
  }

  public void setSumLines(long sumLines) {
    this.sumLines = sumLines;
  }
}
