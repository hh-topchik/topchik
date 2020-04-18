package entity;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import java.sql.Timestamp;
import java.util.Objects;

/**
 * Класс сущности "Commit" (Коммит на GitHub)
 * */
@Entity
public class Commit {
  private String sha;
  private Timestamp creationTime;
  private int addedLines;
  private int deletedLines;
  private Account accountByAuthorId;
  private PullRequest pullRequestByPullRequestId;
  private Repository repositoryByRepoId;

  public Commit(final String sha, final Timestamp creationTime, final int addedLines, final int deletedLines,
                final Account accountByAuthorId, final Repository repositoryByRepoId) {
    this.sha = sha;
    this.creationTime = creationTime;
    this.addedLines = addedLines;
    this.deletedLines = deletedLines;
    this.accountByAuthorId = accountByAuthorId;
    this.repositoryByRepoId = repositoryByRepoId;
  }

  public Commit() {
  }

  @Id
  @Column(name = "sha", nullable = false, length = 40)
  public String getSha() {
    return sha;
  }

  public void setSha(final String sha) {
    this.sha = sha;
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
  @Column(name = "added_lines", nullable = false)
  public int getAddedLines() {
    return addedLines;
  }

  public void setAddedLines(final int addedLines) {
    this.addedLines = addedLines;
  }

  @Basic
  @Column(name = "deleted_lines", nullable = false)
  public int getDeletedLines() {
    return deletedLines;
  }

  public void setDeletedLines(final int deletedLines) {
    this.deletedLines = deletedLines;
  }

  @Override
  public boolean equals(final Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    final Commit commit = (Commit) o;
    return Objects.equals(sha, commit.sha);
  }

  @Override
  public int hashCode() {
    return Objects.hash(sha, creationTime, addedLines, deletedLines);
  }

  @ManyToOne
  @JoinColumn(name = "author_id", referencedColumnName = "account_id", nullable = false)
  public Account getAccountByAuthorId() {
    return accountByAuthorId;
  }

  public void setAccountByAuthorId(final Account accountByAuthorId) {
    this.accountByAuthorId = accountByAuthorId;
  }

  @ManyToOne
  @JoinColumn(name = "pull_request_id", referencedColumnName = "pull_request_id", nullable = false)
  public PullRequest getPullRequestByPullRequestId() {
    return pullRequestByPullRequestId;
  }

  public void setPullRequestByPullRequestId(final PullRequest pullRequestByPullRequestId) {
    this.pullRequestByPullRequestId = pullRequestByPullRequestId;
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
