package entity;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import java.util.Collection;
import java.util.Objects;

/**
 * Класс сущности "Account" (Пользователь на GitHub)
 * */
@Entity
public class Account {
  private long accountId;
  private String login;
  private String email;
  private String name;
  private Collection<Review> reviewsByAuthorId;
  private Collection<Comment> commentsByAuthorId;
  private Collection<Commit> commitsByAuthorId;
  private Collection<PullRequest> pullRequestsByAuthorId;

  public Account(final long accountId, final String login, final String email, final String name) {
    this.accountId = accountId;
    this.login = login;
    this.email = email;
    this.name = name;
  }

  public Account() {
  }

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "account_id", nullable = false, insertable = false, updatable = false)
  public long getAccountId() {
    return accountId;
  }

  public void setAccountId(final long accountId) {
    this.accountId = accountId;
  }

  @Basic
  @Column(name = "login", nullable = false, length = 255)
  public String getLogin() {
    return login;
  }

  public void setLogin(final String login) {
    this.login = login;
  }

  @Basic
  @Column(name = "email", nullable = true, length = 255)
  public String getEmail() {
    return email;
  }

  public void setEmail(final String email) {
    this.email = email;
  }

  @Basic
  @Column(name = "name", nullable = true, length = 255)
  public String getName() {
    return name;
  }

  public void setName(final String name) {
    this.name = name;
  }

  @Override
  public boolean equals(final Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    final Account account = (Account) o;
    return accountId == account.accountId;
  }

  @Override
  public int hashCode() {
    return Objects.hash(accountId);
  }

  @OneToMany(mappedBy = "accountByAuthorId")
  public Collection<Review> getReviewsByAuthorId() {
    return reviewsByAuthorId;
  }

  public void setReviewsByAuthorId(final Collection<Review> reviewsByAuthorId) {
    this.reviewsByAuthorId = reviewsByAuthorId;
  }

  @OneToMany(mappedBy = "accountByAuthorId")
  public Collection<Comment> getCommentsByAuthorId() {
    return commentsByAuthorId;
  }

  public void setCommentsByAuthorId(final Collection<Comment> commentsByAuthorId) {
    this.commentsByAuthorId = commentsByAuthorId;
  }

  @OneToMany(mappedBy = "accountByAuthorId")
  public Collection<Commit> getCommitsByAuthorId() {
    return commitsByAuthorId;
  }

  public void setCommitsByAuthorId(final Collection<Commit> commitsByAuthorId) {
    this.commitsByAuthorId = commitsByAuthorId;
  }

  @OneToMany(mappedBy = "accountByAuthorId")
  public Collection<PullRequest> getPullRequestsByAuthorId() {
    return pullRequestsByAuthorId;
  }

  public void setPullRequestsByAuthorId(Collection<PullRequest> pullRequestsByAuthorId) {
    this.pullRequestsByAuthorId = pullRequestsByAuthorId;
  }
}
