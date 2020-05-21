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
 * Класс сущности "Comment" (Комментарий на GitHub)
 * */
@Entity
public class Comment {
  private long commentId;
  private Timestamp creationTime;
  private Review reviewByReviewId;

  public Comment(final long commentId, final Timestamp creationTime, final Review reviewByReviewId) {
    this.commentId = commentId;
    this.creationTime = creationTime;
    this.reviewByReviewId = reviewByReviewId;
  }

  public Comment() {
  }

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
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

  @ManyToOne
  @JoinColumn(name = "review_id", referencedColumnName = "review_id", nullable = false)
  public Review getReviewByReviewId() {
    return reviewByReviewId;
  }

  public void setReviewByReviewId(final Review reviewByReviewId) {
    this.reviewByReviewId = reviewByReviewId;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (!(o instanceof Comment)) {
      return false;
    }
    Comment comment = (Comment) o;
    return getCommentId() == comment.getCommentId() &&
        Objects.equals(getCreationTime(), comment.getCreationTime()) &&
        Objects.equals(reviewByReviewId, comment.reviewByReviewId);
  }

  @Override
  public int hashCode() {
    return Objects.hash(getCommentId(), getCreationTime(), reviewByReviewId);
  }
}
