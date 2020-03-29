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
public class Action {
  private long actionId;
  private LocalDate date;
  private int category;
  private int counter;
  private Account accountByDeveloperId;
  private Repository repositoryByRepoId;

  public Action() {
  }

  public Action(long actionId, LocalDate date, int category, int counter, Account accountByDeveloperId, Repository repositoryByRepoId) {
    this.actionId = actionId;
    this.date = date;
    this.category = category;
    this.counter = counter;
    this.accountByDeveloperId = accountByDeveloperId;
    this.repositoryByRepoId = repositoryByRepoId;
  }

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "action_id", nullable = false)
  public long getActionId() {
    return actionId;
  }

  public void setActionId(long actionId) {
    this.actionId = actionId;
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
    Action action = (Action) o;
    return actionId == action.actionId;
  }

  @Override
  public int hashCode() {
    return Objects.hash(actionId, date, category, counter);
  }
}
