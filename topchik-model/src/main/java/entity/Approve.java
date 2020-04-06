package entity;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import java.sql.Timestamp;
import java.util.Objects;

/**
 * Класс сущности "Approve" (Статус ревью пулл реквеста)
 * */
@Entity
public class Approve {
  private long approveId;
  private int status;
  private Timestamp time;
  private Account accountByAuthorId;
  private PullRequest pullRequestByPullRequestId;

  public Approve(final long approveId, final Account accountByAuthorId, final PullRequest pullRequestByPullRequestId,
                 final int status, final Timestamp time) {
    this.approveId = approveId;
    this.status = status;
    this.time = time;
    this.accountByAuthorId = accountByAuthorId;
    this.pullRequestByPullRequestId = pullRequestByPullRequestId;
  }

  public Approve() {
  }

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "approve_id", nullable = false)
  public long getApproveId() {
    return approveId;
  }

  public void setApproveId(final long approveId) {
    this.approveId = approveId;
  }

  @Basic
  @Column(name = "status", nullable = false)
  public int getStatus() {
    return status;
  }

  public void setStatus(final int status) {
    this.status = status;
  }

  @Basic
  @Column(name = "time", nullable = false)
  public Timestamp getTime() {
    return time;
  }

  public void setTime(final Timestamp time) {
    this.time = time;
  }

  @Override
  public boolean equals(final Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    final Approve approve = (Approve) o;
    return approveId == approve.approveId;
  }

  @Override
  public int hashCode() {
    return Objects.hash(approveId);
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
}
