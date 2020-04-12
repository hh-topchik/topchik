package init;


import entity.Account;
import entity.Review;
import entity.Comment;
import entity.Commit;
import entity.PullRequest;
import entity.Repository;
import enums.ApproveStatus;
import enums.PullRequestStatus;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.kohsuke.github.GHCommit;
import org.kohsuke.github.GHCommitComment;
import org.kohsuke.github.GHPullRequest;
import org.kohsuke.github.GHPullRequestCommitDetail;
import org.kohsuke.github.GHPullRequestReview;
import org.kohsuke.github.GHRepository;
import org.kohsuke.github.GHUser;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Класс создания сущностей по данным из Github'a
 */
class Converter {
  private static final Logger LOGGER = LogManager.getLogger(Converter.class);

  Converter() throws Exception {
    throw new Exception("Этот класс является статическим");
  }

  /**
   * Создание сущности "Аккаунт"
   *
   * @param ghUser
   *            - сущность "Аккаунт" из Github
   * @return
   *            - сущность "Аккаунт" для внесения в БД
   * @throws Exception
   */
  static Account convertUser(final GHUser ghUser) throws Exception {
    if (ghUser == null) {
      return null;
    }
    try {
      LOGGER.info("Начало конвертации пользователя " + ghUser);
      final long id = ghUser.getId();
      final String name = ghUser.getName();
      final String email = ghUser.getEmail();
      final String login = ghUser.getLogin();
      final Account account = new Account(id, login, email, name);
      LOGGER.info("Конвертация пользователя прошла успешно");
      return account;
    } catch (Exception e) {
      throw new Exception("Ошибка конвертации пользователя ", e);
    }
  }

  /**
   * Создание сущности "Коммит"
   *
   * @param ghCommit
   *            - сущность "Коммит" из Github
   * @return
   *            - сущность "Коммит" для внесения в БД
   * @throws Exception
   */
  static Commit convertCommit(final GHCommit ghCommit) throws Exception {
    if (ghCommit == null) {
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
   * Создание сущности "Комментарий"
   *
   * @param ghComment
   *            - сущность "Комментарий" из Github
   * @return
   *            - сущность "Комментарий" для внесения в БД
   * @throws Exception
   */
  static Comment convertComment(final GHCommitComment ghComment) throws Exception {
    if (ghComment == null) {
      return null;
    }
    try {
      LOGGER.info("Начало конвертации комментария " + ghComment);
      final long id = ghComment.getId();
      final Account author = Converter.convertUser(ghComment.getUser());
      if (author == null) {
        LOGGER.error("Автор комментария не найден");
        return null;
      }
      final Commit commit = Converter.convertCommit(ghComment.getCommit());
      final Timestamp creationDate = Timestamp.from(ghComment.getCreatedAt().toInstant());
      final Comment comment = new Comment(id, creationDate, author, commit);
      LOGGER.info("Конвертация комментария прошла успешно");
      return comment;
    } catch (Exception e) {
      LOGGER.error("Ошибка конвертации комментария ", e);
      throw new Exception("Ошибка конвертации комментария ", e);
    }
  }

  /**
   * Создание списка сущностей "Комментарий"
   *
   * @param ghCommit
   *            - сущность "Коммит" из Github
   * @return
   *            - список сущностей "Комментарий" для данного коммита для внесения в БД
   * @throws Exception
   */
  static List<Comment> listConvertedComment(final GHCommit ghCommit) throws Exception {
    if (ghCommit == null) {
      return Collections.emptyList();
    }
    try {
      LOGGER.info("Начало конвертации списка комментариев");
      final List<Comment> comments = new ArrayList<>();
      for (final GHCommitComment ghComment : ghCommit.listComments()) {
        final Comment comment = convertComment(ghComment);
        if (comment != null) {
          comments.add(comment);
        }
      }
      LOGGER.info("Конвертация списка комментариев прошла успешно");
      return comments;
    } catch (Exception e) {
      LOGGER.error("Ошибка конвертации списка комментариев ", e);
      throw new Exception("Ошибка конвертации списка комментариев ", e);
    }
  }

  /**
   * Создание сущности "Репозиторий"
   *
   * @param ghRepository
   *            - сущность "Репозиторий" из Github
   * @return
   *            - сущность "Репозиторий" для внесения в БД
   * @throws Exception
   */
  static Repository convertRepository(final GHRepository ghRepository) throws Exception {
    if (ghRepository == null) {
      return null;
    }
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
   * Создание сущности "Пулл Реквест"
   *
   * @param ghPullRequest
   *            - сущность "Пулл Реквест" из Github
   * @param commits
   *            - список сущностей "Коммит" для внесения в БД
   * @return
   *            - сущность "Пулл Реквест" для внесения в БД
   * @throws Exception
   */
  static PullRequest convertPullRequest(final GHPullRequest ghPullRequest, final List<Commit> commits) throws Exception {
    if (ghPullRequest == null) {
      return null;
    }
    try {
      LOGGER.info("Начало конвертации пулл реквеста " + ghPullRequest);
      final long id = ghPullRequest.getId();
      final Account author = Converter.convertUser(ghPullRequest.getUser());
      if (author == null) {
        LOGGER.error("Автор пулл реквеста не найден");
        return null;
      }
      final Timestamp creationDate = Timestamp.from(ghPullRequest.getCreatedAt().toInstant());
      final Repository repository = Converter.convertRepository(ghPullRequest.getRepository());

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

      //Так как первоначально в коммите нет информации о пулл реквесте, на этом шаге происходит ее добавление
      if (commits != null) {
        final List<String> shaList = ghPullRequest.listCommits().toList().stream()
            .map(GHPullRequestCommitDetail::getSha)
            .collect(Collectors.toList());
        LOGGER.info("Начало добваление информации о пулл реквесте в коммиты");
        for (final Commit commit : commits) {
          if (shaList.contains(commit.getSha())) {
            commit.setPullRequestByPullRequestId(pullRequest);
          }
        }
        LOGGER.debug("Добавление в коммиты информации о пулл реквесте прошла успешно");
      }
      return pullRequest;
    } catch (Exception e) {
      LOGGER.error("Ошибка конвертации пулл реквеста ", e);
      throw new Exception("Ошибка конвертации пулл реквеста ", e);
    }
  }

  /**
   * Создание списка сущностей "Пулл Реквест"
   *
   * @param ghPullRequests
   *            - список сущностей "Пулл Реквест" из Github
   * @param commits
   *            - список сущностей "Коммит" для внесения в БД
   * @return
   *            - список сущностей "Пулл Реквест" для внесения в БД
   * @throws Exception
   */
  static List<PullRequest> listConvertedPullRequest(final List<GHPullRequest> ghPullRequests, final List<Commit> commits) throws Exception {
    if (ghPullRequests == null) {
      return Collections.emptyList();
    }
    LOGGER.info("Начало конвертации списка пулл реквестов");
    final List<PullRequest> pullRequests = new ArrayList<>(ghPullRequests.size());
    for (final GHPullRequest ghPullRequest : ghPullRequests) {
      final PullRequest pullRequest = convertPullRequest(ghPullRequest, commits);
      if (pullRequest != null) {
        pullRequests.add(convertPullRequest(ghPullRequest, commits));
      }
    }
    LOGGER.info("Конвертация списка пулл реквестов прошла успешно");
    return pullRequests;
  }

  /**
   * Создание списка сущностей "Ревью" для данного ПР
   *
   * @param ghPullRequest
   *            - сущность "Пулл Реквест" из Github
   * @return
   *            - список сущностей "Ревью" для данного ПР для внесения в БД
   * @throws Exception
   */
  static List<Review> convertApproveByGHPullRequest(final GHPullRequest ghPullRequest) throws Exception {
    if (ghPullRequest == null) {
      return Collections.emptyList();
    }
    try {
      LOGGER.info("Начало конвертации списка ревью для пулл реквеста " + ghPullRequest);
      final List<Review> reviewList = new ArrayList<>();
      for (final GHPullRequestReview ghReview : ghPullRequest.listReviews()) {
        LOGGER.info("Начало конвертации ревью для пулл реквеста " + ghReview);
        final long reviewId = ghReview.getId();
        final Account author = Converter.convertUser(ghReview.getUser());
        if (author == null || author.getLogin() == null) {
          LOGGER.error("Автор ревью для пулл реквеста не найден");
          continue;
        }
        final PullRequest pullRequest = Converter.convertPullRequest(ghPullRequest, null);
        if (pullRequest == null) {
          LOGGER.error("Пулл реквест не найден");
          continue;
        }
        final Timestamp creationDate = Timestamp.from(ghReview.getCreatedAt().toInstant());
        final ApproveStatus status = ApproveStatus.valueOf(ghReview.getState().name());
        final Review review = new Review(reviewId, author, pullRequest, status.getId(), creationDate);
        LOGGER.info("Конвертация ревью для пулл реквеста прошла успешно");
        reviewList.add(review);
      }
      LOGGER.info("Конвертация списка ревью для пулл реквеста прошла успешно");
      return reviewList;
    } catch (Exception e) {
      LOGGER.error("Ошибка конвертации ревью пулл реквеста ", e);
      throw new Exception("Ошибка конвертации ревью пулл реквеста ", e);
    }
  }

  /**
   * Создание списка сущносте "Проверка"
   *
   * @param ghPullRequestList
   *            - список сущностей "Пулл Реквест" из Github
   * @return
   *            - список сущностей "Проверка" для внесения в БД
   * @throws Exception
   */
  static List<Review> listConvertedApprove(final List<GHPullRequest> ghPullRequestList) throws Exception {
    if (ghPullRequestList == null) {
      return Collections.emptyList();
    }
    LOGGER.info("Начало конвертации списка ревью для списка пулл реквестов " + ghPullRequestList);
    final List<Review> reviewList = new ArrayList<>();
    for (final GHPullRequest request : ghPullRequestList) {
      reviewList.addAll(convertApproveByGHPullRequest(request));
    }
    LOGGER.info("Конвертация списка ревью для списка пулл реквестов прошла успешно");
    return reviewList;
  }
}
