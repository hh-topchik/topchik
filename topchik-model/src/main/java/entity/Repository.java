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
 * Класс сущности "Repository" (Репозиторий на GitHub)
 * */
@Entity
public class Repository {
  private long repoId;
  private String path;
  private Collection<Commit> commitsByRepoId;
  private Collection<PullRequest> pullRequestsByRepoId;

  public Repository(final long repoId, final String path) {
    this.repoId = repoId;
    this.path = path;
  }

  public Repository() {
  }

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "repo_id", nullable = false)
  public long getRepoId() {
    return repoId;
  }

  public void setRepoId(final long repoId) {
    this.repoId = repoId;
  }

  @Basic
  @Column(name = "path", nullable = false, length = 255)
  public String getPath() {
    return path;
  }

  public void setPath(final String path) {
    this.path = path;
  }

  @Override
  public boolean equals(final Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    final Repository that = (Repository) o;
    return repoId == that.repoId;
  }

  @Override
  public int hashCode() {
    return Objects.hash(repoId);
  }

  @OneToMany(mappedBy = "repositoryByRepoId")
  public Collection<Commit> getCommitsByRepoId() {
    return commitsByRepoId;
  }

  public void setCommitsByRepoId(final Collection<Commit> commitsByRepoId) {
    this.commitsByRepoId = commitsByRepoId;
  }

  @OneToMany(mappedBy = "repositoryByRepoId")
  public Collection<PullRequest> getPullRequestsByRepoId() {
    return pullRequestsByRepoId;
  }

  public void setPullRequestsByRepoId(final Collection<PullRequest> pullRequestsByRepoId) {
    this.pullRequestsByRepoId = pullRequestsByRepoId;
  }

}
