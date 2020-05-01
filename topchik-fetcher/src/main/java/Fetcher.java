import entity.Account;
import entity.Commit;
import entity.Review;
import entity.PullRequest;
import entity.Repository;
import enums.ReviewStatus;
import enums.PullRequestStatus;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.kohsuke.github.GHCommit;
import org.kohsuke.github.GHIssueState;
import org.kohsuke.github.GHPullRequest;
import org.kohsuke.github.GHPullRequestCommitDetail;
import org.kohsuke.github.GHPullRequestReview;
import org.kohsuke.github.GHRepository;
import org.kohsuke.github.GHUser;
import org.kohsuke.github.GitHub;

import java.sql.Timestamp;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;

/**
 * Класс создания сущностей по данным из Github'a
 */
class Fetcher {
  private final Set<Account> accounts = new HashSet<>();
  private final Set<Commit> commits = new HashSet<>();
  private final Set<PullRequest> pullRequests = new HashSet<>();
  private final Set<Review> reviews = new HashSet<>();
  private final Repository repository;

  private final GHRepository ghRepository;
  private final List<GHPullRequest> ghPullRequests;

  private static final Logger LOGGER = LogManager.getLogger(Fetcher.class);

  Fetcher(final GitHub github, final String repo) throws Exception {
    ghRepository = github.getRepository(repo);
    ghPullRequests = ghRepository.getPullRequests(GHIssueState.ALL);
    repository = convertRepository();
    convertAll();
  }

  /**
   * Создание сущности "Репозиторий"
   *
   * @return сущность "Репозиторий" для внесения в БД
   */
  private Repository convertRepository() throws Exception {
    try {
      LOGGER.info("Начало конвертации репозитория " + ghRepository);
      final long id = ghRepository.getId();
      final String path = ghRepository.getHtmlUrl().toString();
      final Repository repository = new Repository(id, path);
      LOGGER.info("Конвертация репозитория прошла успешно");
      return repository;
    } catch (Exception e) {
      LOGGER.error("Ошибка конвертации репозитория ", e);
      throw new Exception("Ошибка конвертации репозитория ", e);
    }
  }

  /**
   * Создание сущностей из Пулл Реквеста
   *
   */
  private void convertAll() throws Exception {
    if (ghPullRequests.isEmpty()) {
      LOGGER.info("пулл реквестов в репозитории {} нет", ghRepository.getName());
      return;
    }
    LOGGER.info("Начало конвертации списка пулл реквестов");
    for (final GHPullRequest ghPullRequest : ghPullRequests) {
      final PullRequest pullRequest = convertPullRequest(ghPullRequest);
      if (pullRequest != null) {
        pullRequests.add(pullRequest);
        convertCommits(ghPullRequest, pullRequest);
        convertReview(ghPullRequest, pullRequest);
      }
    }
    LOGGER.info("Конвертация списка пулл реквестов прошла успешно");
  }

  /**
   * Создание сущности "Пулл Реквест"
   *
   * @param ghPullRequest - сущность "Пулл Реквест" из Github
   * @return - сущность "Пулл Реквест" для внесения в БД
   */
  private PullRequest convertPullRequest(final GHPullRequest ghPullRequest) throws Exception {
    if (!ghPullRequest.getBase().getRef().equals("master")) {
      LOGGER.info("Пулл реквест не вливается в master");
      return null;
    }
    try {
      LOGGER.info("Начало конвертации пулл реквеста " + ghPullRequest);
      final long id = ghPullRequest.getId();
      final Account author = convertUser(ghPullRequest.getUser());
      if (author == null) {
        LOGGER.error("Автор пулл реквеста не найден");
        return null;
      }
      accounts.add(author);

      final Timestamp creationDate = Timestamp.from(ghPullRequest.getCreatedAt().toInstant());

      PullRequestStatus status = PullRequestStatus.valueOf(ghPullRequest.getState().name());
      Timestamp lastUpdateDate = null;

      if (status == PullRequestStatus.CLOSED && ghPullRequest.getMergedAt() != null) {
        status = PullRequestStatus.MERGED;
        lastUpdateDate = Timestamp.from(ghPullRequest.getMergedAt().toInstant());
      } else if (ghPullRequest.getClosedAt() != null) {
        lastUpdateDate = Timestamp.from(ghPullRequest.getClosedAt().toInstant());
      } else if (ghPullRequest.getUpdatedAt() != null) {
        lastUpdateDate = Timestamp.from(ghPullRequest.getUpdatedAt().toInstant());
      }
      final PullRequest pullRequest = new PullRequest(id, author, creationDate, lastUpdateDate, status.getId(), repository);
      LOGGER.info("Конвертация пулл реквеста прошла успешно");
      return pullRequest;
    } catch (Exception e) {
      LOGGER.error("Ошибка конвертации пулл реквеста ", e);
      throw new Exception("Ошибка конвертации пулл реквеста ", e);
    }
  }

  /**
   * Создание сущности "Аккаунт"
   *
   * @param ghUser - сущность "Аккаунт" из Github
   * @return - сущность "Аккаунт" для внесения в БД
   */
  private Account convertUser(final GHUser ghUser) throws Exception {
    if (ghUser == null) {
      return null;
    }
    try {
      LOGGER.info("Начало конвертации пользователя " + ghUser);
      final long id = ghUser.getId();
      final String name = ghUser.getName();
      final String email = ghUser.getEmail();
      final String login = ghUser.getLogin();
      final String avatar = ghUser.getAvatarUrl();
      final Account account = new Account(id, login, email, name, avatar);
      LOGGER.info("Конвертация пользователя прошла успешно");
      return account;
    } catch (Exception e) {
      throw new Exception("Ошибка конвертации пользователя ", e);
    }
  }

  /**
   * Создание сущности "Коммит"
   *
   * @param ghCommit - сущность "Коммит" из Github
   * @return - сущность "Коммит" для внесения в БД
   */
  private Commit convertCommit(final GHCommit ghCommit) throws Exception {
    if (ghCommit == null) {
      LOGGER.info("Коммита нет");
      return null;
    }
    try {
      LOGGER.info("Начало конвертации коммита " + ghCommit);
      final String sha = ghCommit.getSHA1();
      final Account author = convertUser(ghCommit.getAuthor());
      if (author == null) {
        LOGGER.error("Автор коммита не найден");
        return null;
      }
      final int linesAdded = ghCommit.getLinesAdded();
      final int linesDeleted = ghCommit.getLinesDeleted();
      final Timestamp creationTime = Timestamp.from(ghCommit.getCommitDate().toInstant());
      final Commit commit = new Commit(sha, creationTime, linesAdded, linesDeleted, author, null);
      LOGGER.info("Конвертация коммита прошла успешно");
      return commit;
    } catch (Exception e) {
      LOGGER.error("Ошибка конвертации комментария ", e);
      throw new Exception("Ошибка конвертации коммита ", e);
    }
  }

  /**
   * Создание списка сущностей "Коммит"
   *
   * @param ghPullRequest - сущность "Пулл запросов" из Github
   * @param pullRequest - сущность "Пулл запросов" для внесения в БД
   */
  private void convertCommits(final GHPullRequest ghPullRequest, final PullRequest pullRequest) throws Exception {
    for (final GHPullRequestCommitDetail commitDetail : ghPullRequest.listCommits()) {
      final GHCommit ghCommit = ghRepository.getCommit(commitDetail.getSha());
      final Commit commit = convertCommit(ghCommit);
      if (commit != null) {
        commit.setPullRequestByPullRequestId(pullRequest);
        commit.setRepositoryByRepoId(pullRequest.getRepositoryByRepoId());
        commits.add(commit);
        accounts.add(commit.getAccountByAuthorId());
      }
    }
  }

  /**
   * Создание списка сущносте "Проверка" для данного ПР
   *
   * @param ghPullRequest - сущность "Пулл запросов" из Github
   * @param pullRequest - сущность "Пулл запросов" для внесения в БД
   */
  private void convertReview(final GHPullRequest ghPullRequest, final PullRequest pullRequest) throws Exception {
    try {
      LOGGER.info("Начало конвертации списка ревью для пулл реквеста " + ghPullRequest);
      for (final GHPullRequestReview ghReview : ghPullRequest.listReviews()) {
        LOGGER.info("Начало конвертации ревью для пулл реквеста " + ghReview);
        final long approveId = ghReview.getId();
        final Account author = convertUser(ghReview.getUser());
        if (author == null) {
          LOGGER.error("Автор ревью для пулл реквеста не найден");
          continue;
        }
        accounts.add(author);
        final Timestamp creationDate = Timestamp.from(ghReview.getCreatedAt().toInstant());
        final ReviewStatus status = ReviewStatus.valueOf(Objects.requireNonNull(ghReview.getState()).name());
        final Review review = new Review(approveId, author, pullRequest, status.getId(), creationDate);
        LOGGER.info("Конвертация ревью для пулл реквеста прошла успешно");
        reviews.add(review);
      }
      LOGGER.info("Конвертация списка ревью для пулл реквеста прошла успешно");
    } catch (Exception e) {
      LOGGER.error("Ошибка конвертации ревью пулл реквеста ", e);
      throw new Exception("Ошибка конвертации ревью пулл реквеста ", e);
    }
  }

  Set<Account> getAccounts() {
    return accounts;
  }

  Set<Commit> getCommits() {
    return commits;
  }

  Set<PullRequest> getPullRequests() {
    return pullRequests;
  }

  Set<Review> getReviews() {
    return reviews;
  }

  Repository getRepository() {
    return repository;
  }
}
