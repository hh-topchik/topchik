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
 * Класс сущности "Weekly Result" (Достижения пользователя за неделю)
 * */
@Entity
@Table(name = "weekly_result")
public class WeeklyResult {
  private long weeklyResultId;
  private LocalDate weekDate;
  private int category;
  private int points;
  private int medal;
  private Account accountByAuthorId;
  private Repository repositoryByRepoId;

  public WeeklyResult() {
  }

  public WeeklyResult(long weeklyResultId, LocalDate weekDate, int category, int points,
                      int medal, Account accountByAuthorId, Repository repositoryByRepoId) {
    this.weeklyResultId = weeklyResultId;
    this.weekDate = weekDate;
    this.category = category;
    this.points = points;
    this.medal = medal;
    this.accountByAuthorId = accountByAuthorId;
    this.repositoryByRepoId = repositoryByRepoId;
  }

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "weekly_result_id", nullable = false)
  public long getWeeklyResultId() {
    return weeklyResultId;
  }

  public void setWeeklyResultId(long weeklyResultId) {
    this.weeklyResultId = weeklyResultId;
  }

  @Basic
  @Column(name = "week_date", nullable = false)
  public LocalDate getWeekDate() {
    return weekDate;
  }

  public void setWeekDate(LocalDate weekDate) {
    this.weekDate = weekDate;
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
  @Column(name = "points", nullable = false)
  public int getPoints() {
    return points;
  }

  public void setPoints(int points) {
    this.points = points;
  }

  @Basic
  @Column(name = "medal")
  public int getMedal() {
    return medal;
  }

  public void setMedal(int medal) {
    this.medal = medal;
  }

  @ManyToOne
  @JoinColumn(name = "author_id", referencedColumnName = "account_id", nullable = false, insertable = false, updatable = false)
  public Account getAccountByAuthorId() {
    return accountByAuthorId;
  }

  public void setAccountByAuthorId(Account accountByAuthorId) {
    this.accountByAuthorId = accountByAuthorId;
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
    WeeklyResult that = (WeeklyResult) o;
    return weeklyResultId == that.weeklyResultId;
  }

  @Override
  public int hashCode() {
    return Objects.hash(weekDate, category, getAccountByAuthorId().getAccountId());
  }
}
