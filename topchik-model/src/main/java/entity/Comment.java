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

@Entity
public class Comment {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private long commentId;
  private Timestamp creationTime;
  private Account accountByAuthorId;
  private Commit commitByCommitHash;

  public Comment(final long commentId, final Timestamp creationTime, final Account accountByAuthorId,
                 final Commit commitByCommitHash) {
    this.commentId = commentId;
    this.creationTime = creationTime;
    this.accountByAuthorId = accountByAuthorId;
    this.commitByCommitHash = commitByCommitHash;
  }

  public Comment() {
  }

  @Column(name = "comment_id", nullable = false)
  public long getCommentId() {
    return commentId;
  }

  public void setCommentId(final long commentId) {
    this.commentId = commentId;
  }

  @Basic
  @Column(name = "creation_time", nullable = false)
  public Timestamp getCreationTime() {
    return creationTime;
  }

  public void setCreationTime(final Timestamp creationTime) {
    this.creationTime = creationTime;
  }

  @Override
  public boolean equals(final Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    final Comment comment = (Comment) o;
    return commentId == comment.commentId;
  }

  @Override
  public int hashCode() {
    return Objects.hash(commentId, creationTime);
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
  @JoinColumn(name = "commit_hash", referencedColumnName = "sha", nullable = false)
  public Commit getCommitByCommitHash() {
    return commitByCommitHash;
  }

  public void setCommitByCommitHash(final Commit commitByCommitHash) {
    this.commitByCommitHash = commitByCommitHash;
  }
}
