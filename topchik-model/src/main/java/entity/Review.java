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
 * Класс сущности "Review" (Статус ревью пулл реквеста)
 * */
@Entity
public class Review {
  private long reviewId;
  private int status;
  private Timestamp time;
  private Account accountByAuthorId;
  private PullRequest pullRequestByPullRequestId;

  public Review(final long reviewId, final Account accountByAuthorId, final PullRequest pullRequestByPullRequestId,
                final int status, final Timestamp time) {
    this.reviewId = reviewId;
    this.status = status;
    this.time = time;
    this.accountByAuthorId = accountByAuthorId;
    this.pullRequestByPullRequestId = pullRequestByPullRequestId;
  }

  public Review() {
  }

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "review_id", nullable = false)
  public long getReviewId() {
    return reviewId;
  }

  public void setReviewId(final long reviewId) {
    this.reviewId = reviewId;
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
    final Review review = (Review) o;
    return reviewId == review.reviewId;
  }

  @Override
  public int hashCode() {
    return Objects.hash(reviewId);
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
