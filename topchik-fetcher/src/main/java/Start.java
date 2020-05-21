import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.kohsuke.github.GitHub;
import org.kohsuke.github.GitHubBuilder;
import reposettings.RepoSetting;
import reposettings.RepoSettingsLoader;
import reposettings.ResourceHelper;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Objects;

/**
 * Класс запуска Parser'a
 */
public class Start implements Runnable {
  private Logger logger;
  private RepoSetting repoSetting;

  private static Date since;
  private static Date until;

  private final static String FILE_PARAM = "-f";
  private final static String SINCE_PARAM = "-s";
  private final static String UNTIL_PARAM = "-u";
  private final static String ERROR_PARAM = "Неверный формат:\n\t" +
      "Usage: topchik.jar [-f SettingFileName] [-s Since (dd.MM.yyyy)] [-u Until (dd.MM.yyyy)]";
  private final static SimpleDateFormat FORMATTER = new SimpleDateFormat("dd.MM.yyyy", Locale.ENGLISH);

  public static void main(String[] args) {
    try {
      final List<String> params = Arrays.asList(args);
      final String settingsFileName;

      if (params.contains(FILE_PARAM)) {
        final int index = params.indexOf(FILE_PARAM) + 1;
        if (index >= params.size()) {
          throw new Exception(ERROR_PARAM);
        }
        settingsFileName = params.get(index);
      }
      else {
        final String resource = "repo_data.csv";
        final File settingsFile = ResourceHelper.getResourceFile(resource);
        settingsFileName = Objects.requireNonNull(settingsFile).getAbsolutePath();
      }
      since = getDate(params, SINCE_PARAM);
      until = getDate(params, UNTIL_PARAM);

      final List<RepoSetting> repoSettings = new RepoSettingsLoader(settingsFileName).getRepoSettings();
      startMultiThreads(repoSettings);
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  private static Date getDate(final List<String> params, final String param) throws Exception {
    Date date = null;
    if (params.contains(param)) {
      final int index = params.indexOf(param) + 1;
      if (index >= params.size()) {
        throw new Exception(ERROR_PARAM);
      }
      date = FORMATTER.parse(params.get(index));
    }
    return date;
  }

  private static void startMultiThreads(final List<RepoSetting> repoSettings) {
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
      final Fetcher fetcher = new Fetcher(github, repoSetting.getPath(), since, until);
      System.out.println("Инициализация репозитория " + repoSetting.getPath() + " прошла успешно");
      logger.info("Инициализация репозитория {} прошла успешно", repoSetting.getPath());
      System.out.println("Добавление записи в таблицу БД");
      final DaoFactory daoFactory = new DaoFactory();
      daoFactory.getAccountDao().saveOrUpdateAll(fetcher.getAccounts());
      daoFactory.getRepositoryDao().saveOrUpdate(fetcher.getRepository());
      daoFactory.getPullRequestDao().saveOrUpdateAll(fetcher.getPullRequests());
      daoFactory.getReviewServices().saveOrUpdateAll(fetcher.getReviews());
      daoFactory.getCommentDao().saveOrUpdateAll(fetcher.getComments());
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
