import dao.AccountDaoImpl;
import dao.CommentDaoImpl;
import dao.CommitDaoImpl;
import dao.PullRequestDaoImpl;
import dao.RepositoryDaoImpl;
import dao.ReviewDaoImpl;

class DaoFactory {
  private AccountDaoImpl accountDao;
  private CommitDaoImpl commitDao;
  private PullRequestDaoImpl pullRequestDao;
  private RepositoryDaoImpl repositoryDao;
  private ReviewDaoImpl reviewDao;
  private CommentDaoImpl commentDao;

  AccountDaoImpl getAccountDao() {
    if (accountDao == null) {
      accountDao = new AccountDaoImpl();
    }
    return accountDao;
  }

  CommitDaoImpl getCommitDao() {
    if (commitDao == null) {
      commitDao = new CommitDaoImpl();
    }
    return commitDao;
  }

  PullRequestDaoImpl getPullRequestDao() {
    if (pullRequestDao == null) {
      pullRequestDao = new PullRequestDaoImpl();
    }
    return pullRequestDao;
  }

  RepositoryDaoImpl getRepositoryDao() {
    if (repositoryDao == null) {
      repositoryDao = new RepositoryDaoImpl();
    }
    return repositoryDao;
  }

  ReviewDaoImpl getReviewServices() {
    if (reviewDao == null) {
      reviewDao = new ReviewDaoImpl();
    }
    return reviewDao;
  }

  CommentDaoImpl getCommentDao() {
    if (commentDao == null) {
      commentDao = new CommentDaoImpl();
    }
    return commentDao;
  }

}
