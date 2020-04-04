package init;

import entity.Account;
import entity.Approve;
import entity.Comment;
import entity.Commit;
import entity.PullRequest;
import entity.Repository;
import org.kohsuke.github.GHCommit;
import org.kohsuke.github.GHIssueState;
import org.kohsuke.github.GHRepository;
import org.kohsuke.github.GitHub;
import org.kohsuke.github.GitHubBuilder;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Класс инициализации сущностей для внесения в БД
 */
public class Init {
  private Set<Account> accounts = new HashSet<>();
  private List<Commit> commits = new ArrayList<>();
  private List<Comment> comments = new ArrayList<>();
  private List<PullRequest> pullRequests = new ArrayList<>();
  private List<Approve> reviews = new ArrayList<>();
  private List<Repository> repositories = new ArrayList<>();

  /**
   * Инициализация списков сущностей для внесения в БД
   *
   * @param repo - репозиторий (логин создателя + название репозитория)
   * @throws Exception
   */
  public Init(final String repo) throws Exception {
    //Токкен не забудьте прописать свой
    final GitHub github = new GitHubBuilder().withOAuthToken("ВВЕДИ СВОЙ ТОКЕН").build();

    //Списки сущностей для данного репозитория
    final Set<Account> accounts = new HashSet<>();
    final List<Commit> commits = new ArrayList<>();
    final List<Comment> comments = new ArrayList<>();
    final List<Repository> repositories = new ArrayList<>();

    final GHRepository ghRepository = github.getRepository(repo);
    final Repository repository = Converter.convertRepository(ghRepository);

    //Формирование списка репозиториев для внесения в БД
    repositories.add(repository);

    //В списках коммитов находится основная информация
    for (final GHCommit ghCommit : ghRepository.listCommits()) {
      //Формирование аккаунта по данным Github для данного коммита
      final Account account = Converter.convertUser(ghCommit.getAuthor());

      //Формирование коммита по данным Github для данного коммита
      final Commit commit = Converter.convertCommit(ghCommit);

      //Формирование комментариев по данным Github для данного коммита
      final List<Comment> commentList = Converter.listConvertedComment(ghCommit);

      if (account != null) {
        accounts.add(account);
      }
      if (commit != null) {
        commit.setRepositoryByRepoId(repository);
        commits.add(commit);
      }
      comments.addAll(commentList);
    }
    //Формирование списка пулл реквестов по данным Github для данного репозитория
    final List<PullRequest> pullRequests = Converter.listConvertedPullRequest(ghRepository.getPullRequests(GHIssueState.ALL), commits);

    //Формирование списка проверок по данным Github для данного репозитория
    final List<Approve> reviews = Converter.listConvertedApprove(ghRepository.getPullRequests(GHIssueState.ALL));

    //Слияние списков сущностей данного репозитория со всеми остальными
    this.accounts.addAll(accounts);
    this.commits.addAll(commits);
    this.comments.addAll(comments);
    this.pullRequests.addAll(pullRequests);
    this.reviews.addAll(reviews);
    this.repositories.addAll(repositories);

    //В PR есть боты, которые не присутствуют в коммитах, откуда строится первоначальный список accounts
    for (PullRequest pullRequest : this.pullRequests) {
      this.accounts.add(pullRequest.getAccountByAuthorId());
    }
  }

  public Set<Account> getAccounts() {
    return accounts;
  }

  public List<Commit> getCommits() {
    return commits;
  }

  public List<Comment> getComments() {
    return comments;
  }

  public List<PullRequest> getPullRequests() {
    return pullRequests;
  }

  public List<Approve> getReviews() {
    return reviews;
  }

  public List<Repository> getRepositories() {
    return repositories;
  }
}
