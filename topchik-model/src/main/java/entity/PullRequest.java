package entity;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.sql.Timestamp;
import java.util.Collection;
import java.util.Objects;

/**
 * Класс сущности "Pull Request" (Пулл реквест на GitHub)
 * */
@Entity
@Table(name = "pull_request")
public class PullRequest {
  private long pullRequestId;
  private Timestamp creationTime;
  private Timestamp lastUpdateTime;
  private int status;
  private Collection<Review> reviewsByPullRequestId;
  private Collection<Commit> commitsByPullRequestId;
  private Account accountByAuthorId;
  private Repository repositoryByRepoId;

  public PullRequest(final long pullRequestId, final Account accountByAuthorId, final Timestamp creationTime,
                     final Timestamp lastUpdateTime, final int status, final Repository repositoryByRepoId) {
    this.pullRequestId = pullRequestId;
    this.creationTime = creationTime;
    this.lastUpdateTime = lastUpdateTime;
    this.status = status;
    this.accountByAuthorId = accountByAuthorId;
    this.repositoryByRepoId = repositoryByRepoId;
  }

  public PullRequest() {
  }

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "pull_request_id", nullable = false)
  public long getPullRequestId() {
    return pullRequestId;
  }

  public void setPullRequestId(final long pullRequestId) {
    this.pullRequestId = pullRequestId;
  }

  @Basic
  @Column(name = "creation_time", nullable = false)
  public Timestamp getCreationTime() {
    return creationTime;
  }

  public void setCreationTime(final Timestamp creationTime) {
    this.creationTime = creationTime;
  }

  @Basic
  @Column(name = "last_update_time", nullable = false)
  public Timestamp getLastUpdateTime() {
    return lastUpdateTime;
  }

  public void setLastUpdateTime(final Timestamp lastUpdateTime) {
    this.lastUpdateTime = lastUpdateTime;
  }

  @Basic
  @Column(name = "status", nullable = false)
  public int getStatus() {
    return status;
  }

  public void setStatus(final int status) {
    this.status = status;
  }

  @Override
  public boolean equals(final Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    final PullRequest that = (PullRequest) o;
    return pullRequestId == that.pullRequestId;
  }

  @Override
  public int hashCode() {
    return Objects.hash(pullRequestId, creationTime, lastUpdateTime, status);
  }

  @OneToMany(mappedBy = "pullRequestByPullRequestId")
  public Collection<Review> getReviewsByPullRequestId() {
    return reviewsByPullRequestId;
  }

  public void setReviewsByPullRequestId(final Collection<Review> reviewsByPullRequestId) {
    this.reviewsByPullRequestId = reviewsByPullRequestId;
  }

  @OneToMany(mappedBy = "pullRequestByPullRequestId")
  public Collection<Commit> getCommitsByPullRequestId() {
    return commitsByPullRequestId;
  }

  public void setCommitsByPullRequestId(final Collection<Commit> commitsByPullRequestId) {
    this.commitsByPullRequestId = commitsByPullRequestId;
  }

  @ManyToOne
  @JoinColumn(name = "author_id", referencedColumnName = "account_id", nullable = false, insertable = false, updatable = false)
  public Account getAccountByAuthorId() {
    return accountByAuthorId;
  }

  public void setAccountByAuthorId(final Account accountByAuthorId) {
    this.accountByAuthorId = accountByAuthorId;
  }

  @ManyToOne
  @JoinColumn(name = "repo_id", referencedColumnName = "repo_id", nullable = false)
  public Repository getRepositoryByRepoId() {
    return repositoryByRepoId;
  }

  public void setRepositoryByRepoId(final Repository repositoryByRepoId) {
    this.repositoryByRepoId = repositoryByRepoId;
  }
}
