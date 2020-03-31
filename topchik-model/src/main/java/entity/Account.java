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

@Entity
public class Account {
  private long accountId;
  private String login;
  private String email;
  private String name;
  private Collection<Approve> approvesByAccountId;
  private Collection<Comment> commentsByAccountId;
  private Collection<Commit> commitsByAccountId;
  private Collection<PullRequest> pullRequestsByAccountId;

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
  public Collection<Approve> getApprovesByAccountId() {
    return approvesByAccountId;
  }

  public void setApprovesByAccountId(final Collection<Approve> approvesByAccountId) {
    this.approvesByAccountId = approvesByAccountId;
  }

  @OneToMany(mappedBy = "accountByAuthorId")
  public Collection<Comment> getCommentsByAccountId() {
    return commentsByAccountId;
  }

  public void setCommentsByAccountId(final Collection<Comment> commentsByAccountId) {
    this.commentsByAccountId = commentsByAccountId;
  }

  @OneToMany(mappedBy = "accountByAuthorId")
  public Collection<Commit> getCommitsByAccountId() {
    return commitsByAccountId;
  }

  public void setCommitsByAccountId(final Collection<Commit> commitsByAccountId) {
    this.commitsByAccountId = commitsByAccountId;
  }

  @OneToMany(mappedBy = "accountByAuthorId")
  public Collection<PullRequest> getPullRequestsByAccountId() {
    return pullRequestsByAccountId;
  }

  public void setPullRequestsByAccountId(Collection<PullRequest> pullRequestsByAccountId) {
    this.pullRequestsByAccountId = pullRequestsByAccountId;
  }
}
