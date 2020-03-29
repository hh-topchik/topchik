package entity;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import java.time.LocalDate;
import java.util.Objects;

@Entity
public class Achievement {
  private long achievementId;
  private LocalDate weekDate;
  private int category;
  private int points;
  private int medal;
  private Account accountByDeveloperId;
  private Repository repositoryByRepoId;

  public Achievement() {
  }

  public Achievement(long achievementId, LocalDate weekDate, int category, int points,
                     int medal, Account accountByDeveloperId, Repository repositoryByRepoId) {
    this.achievementId = achievementId;
    this.weekDate = weekDate;
    this.category = category;
    this.points = points;
    this.medal = medal;
    this.accountByDeveloperId = accountByDeveloperId;
    this.repositoryByRepoId = repositoryByRepoId;
  }

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "achievement_id", nullable = false)
  public long getAchievementId() {
    return achievementId;
  }

  public void setAchievementId(long achievementId) {
    this.achievementId = achievementId;
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
  @JoinColumn(name = "developer_id", referencedColumnName = "account_id", nullable = false, insertable = false, updatable = false)
  public Account getAccountByDeveloperId() {
    return accountByDeveloperId;
  }

  public void setAccountByDeveloperId(Account accountByDeveloperId) {
    this.accountByDeveloperId = accountByDeveloperId;
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
    Achievement that = (Achievement) o;
    return achievementId == that.achievementId;
  }

  @Override
  public int hashCode() {
    return Objects.hash(achievementId, weekDate, category, points, medal);
  }
}
