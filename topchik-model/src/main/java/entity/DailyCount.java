package entity;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import java.time.LocalDate;
import java.util.Objects;

/**
 * Класс сущности "Daily Count" (Действия пользователя за день)
 * */
@Entity
@Table(name = "daily_count", schema = "public", catalog = "postgres")
public class DailyCount {
  private long dailyCountId;
  private LocalDate date;
  private int category;
  private int counter;
  private Account accountByAccountId;
  private Repository repositoryByRepoId;

  public DailyCount() {
  }

  public DailyCount(long dailyCountId, LocalDate date, int category, int counter, Account accountByAccountId, Repository repositoryByRepoId) {
    this.dailyCountId = dailyCountId;
    this.date = date;
    this.category = category;
    this.counter = counter;
    this.accountByAccountId = accountByAccountId;
    this.repositoryByRepoId = repositoryByRepoId;
  }

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "daily_count_id", nullable = false)
  public long getDailyCountId() {
    return dailyCountId;
  }

  public void setDailyCountId(long dailyCountId) {
    this.dailyCountId = dailyCountId;
  }

  @Basic
  @Column(name = "date", nullable = false)
  public LocalDate getDate() {
    return date;
  }

  public void setDate(LocalDate date) {
    this.date = date;
  }

  @Basic
  @Column(name = "category", nullable = false)
  public int getCategory() {
    return category;
  }

  public void setCategory(int category) {
    this.category = category;
  }

  @Basic
  @Column(name = "counter", nullable = false)
  public int getCounter() {
    return counter;
  }

  public void setCounter(int counter) {
    this.counter = counter;
  }

  @ManyToOne
  @JoinColumn(name = "account_id", referencedColumnName = "account_id", nullable = false, insertable = false, updatable = false)
  public Account getAccountByAccountId() {
    return accountByAccountId;
  }

  public void setAccountByAccountId(Account accountByAccountId) {
    this.accountByAccountId = accountByAccountId;
  }

  @ManyToOne
  @JoinColumn(name = "repo_id", referencedColumnName = "repo_id", nullable = false)
  public Repository getRepositoryByRepoId() {
    return repositoryByRepoId;
  }

  public void setRepositoryByRepoId(Repository repositoryByRepoId) {
    this.repositoryByRepoId = repositoryByRepoId;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    DailyCount dailyCount = (DailyCount) o;
    return dailyCountId == dailyCount.dailyCountId;
  }

  @Override
  public int hashCode() {
    return Objects.hash(date, category, getAccountByAccountId().getAccountId());
  }
}
