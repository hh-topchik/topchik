import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.kohsuke.github.GitHub;
import org.kohsuke.github.GitHubBuilder;
import reposettings.RepoSetting;
import reposettings.RepoSettingsLoader;
import reposettings.ResourceHelper;

import java.io.File;
import java.util.List;
import java.util.Objects;

/**
 * Класс запуска Parser'a
 */
public class Start implements Runnable {
  private Logger logger;
  private RepoSetting repoSetting;

  public static void main(String[] args) {
    try {
      final String settingsFileName;
      if (args.length == 1) {
        settingsFileName = args[0];
      }
      else {
        final String resource = "repo_data.csv";
        final File settingsFile = ResourceHelper.getResourceFile(resource);
        settingsFileName = Objects.requireNonNull(settingsFile).getAbsolutePath();
      }
      final List<RepoSetting> repoSettings = new RepoSettingsLoader(settingsFileName).getRepoSettings();
      startMultiThreads(repoSettings);
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  private static void startMultiThreads(List<RepoSetting> repoSettings) {
    for (final RepoSetting repoSetting : repoSettings) {
      final Thread thread = new Thread(new Start(repoSetting, LogManager.getLogger(repoSetting.getPath())));
      thread.start();
    }
  }

  private static void startOneThread(List<RepoSetting> repoSettings) {
    for (final RepoSetting repoSetting : repoSettings) {
      final Start start = new Start(repoSetting, LogManager.getLogger(Start.class));
      start.init();
    }
  }

  /**
   * Метод инициализации процесса перевода сущностей из Github в БД
   */
  private void init() {
    try {
      final GitHub github = new GitHubBuilder().withOAuthToken(repoSetting.getToken()).build();
      System.out.println("Начало инициализации репозитория " + repoSetting.getPath());
      logger.info("Начало инициализации репозитория {}", repoSetting.getPath());
      final Fetcher fetcher = new Fetcher(github, repoSetting.getPath());
      System.out.println("Инициализация репозитория " + repoSetting.getPath() + " прошла успешно");
      logger.info("Инициализация репозитория {} прошла успешно", repoSetting.getPath());
      System.out.println("Добавление записи в таблицу БД");
      final DaoFactory daoFactory = new DaoFactory();
      daoFactory.getAccountDao().saveOrUpdateAll(fetcher.getAccounts());
      daoFactory.getRepositoryDao().saveOrUpdate(fetcher.getRepository());
      daoFactory.getPullRequestDao().saveOrUpdateAll(fetcher.getPullRequests());
      daoFactory.getReviewServices().saveOrUpdateAll(fetcher.getReviews());
      daoFactory.getCommitDao().saveOrUpdateAll(fetcher.getCommits());
      System.out.println("\tЗаписи добавлены");
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  private Start(RepoSetting repoSetting, Logger logger) {
    this.repoSetting = repoSetting;
    this.logger = logger;
  }

  @Override
  public void run() {
    init();
  }
}
