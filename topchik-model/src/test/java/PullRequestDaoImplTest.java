import com.opentable.db.postgres.embedded.EmbeddedPostgres;
import dao.PullRequestDaoImpl;
import entity.Account;
import entity.Comment;
import entity.Commit;
import entity.DailyCount;
import entity.PullRequest;
import entity.Repository;
import entity.Review;
import entity.WeeklyResult;
import org.assertj.core.api.Assertions;
import org.hibernate.SessionFactory;
import org.hibernate.boot.Metadata;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.service.ServiceRegistry;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PowerMockIgnore;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;
import pojo.CommonCountPojo;
import util.HibernateUtil;

import java.io.IOException;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.List;

@RunWith(PowerMockRunner.class)
@PrepareForTest({HibernateUtil.class})
@PowerMockIgnore({"com.sun.org.apache.xerces.*", "javax.xml.*", "org.xml.*", "javax.management.*"})
public class PullRequestDaoImplTest {
  private static EmbeddedPostgres embeddedPostgres = null;

  @InjectMocks
  private PullRequestDaoImpl pullRequestDao = new PullRequestDaoImpl();

  @BeforeClass
  public static void setUp() {
    try {
      embeddedPostgres = EmbeddedPostgres.builder()
          .setPort(5431)
          .start();
    } catch (IOException e) {
      e.printStackTrace();
    }

    if (embeddedPostgres != null) {
      TestHelper.executeScript(embeddedPostgres.getPostgresDatabase(), "structure.sql");
    }

    MockitoAnnotations.initMocks(HibernateUtil.class);
  }

  @AfterClass
  public static void shutdown() {
    try {
      embeddedPostgres.close();
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  private static SessionFactory createSessionFactory() {
    ServiceRegistry serviceRegistry = new StandardServiceRegistryBuilder()
        .loadProperties("hibernate.properties")
        .build();

    Metadata metadata = new MetadataSources(serviceRegistry)
        .addAnnotatedClass(Account.class)
        .addAnnotatedClass(Comment.class)
        .addAnnotatedClass(Commit.class)
        .addAnnotatedClass(Review.class)
        .addAnnotatedClass(PullRequest.class)
        .addAnnotatedClass(Repository.class)
        .addAnnotatedClass(DailyCount.class)
        .addAnnotatedClass(WeeklyResult.class)
        .buildMetadata();

    return metadata.buildSessionFactory();
  }

  @Before
  public void cleanUpDb() {
    TestHelper.executeScript(embeddedPostgres.getPostgresDatabase(), "testdata-rollback.sql");
  }

  public void insertData() {
    TestHelper.executeScript(embeddedPostgres.getPostgresDatabase(), "testdata.sql");
  }

  @Test
  public void getAggregatedDailyMergedPullRequestsShouldReturnCorrectAmountOfMergedPR() {
    insertData();
    PowerMockito.mockStatic(HibernateUtil.class);
    PowerMockito.when(HibernateUtil.getSessionFactory()).thenReturn(createSessionFactory());
    List<CommonCountPojo> pojos = pullRequestDao.getAggregatedDailyMergedPullRequests();
    Assertions.assertThat(pojos.size()).isEqualTo(5);
  }

  @Test
  public void getAggregatedDailyMergedPullRequestsShouldFilterBot() {
    insertData();
    PowerMockito.mockStatic(HibernateUtil.class);
    PowerMockito.when(HibernateUtil.getSessionFactory()).thenReturn(createSessionFactory());
    List<CommonCountPojo> pojos = pullRequestDao.getAggregatedDailyMergedPullRequests();
    Assertions.assertThat(
        (int) pojos.stream().filter(p -> p.getAccountByAuthorId().getLogin().contains("[bot]")).count()
    ).isEqualTo(0);
  }

  @Test
  public void getAggregatedDailyMergedPullRequestsShouldNotContainsReposWithoutMergedPR() {
    insertData();
    PowerMockito.mockStatic(HibernateUtil.class);
    PowerMockito.when(HibernateUtil.getSessionFactory()).thenReturn(createSessionFactory());
    List<CommonCountPojo> pojos = pullRequestDao.getAggregatedDailyMergedPullRequests();
    Assertions.assertThat(pojos.stream().anyMatch(p -> p.getRepositoryByRepoId().getRepoId() == 3)).isFalse();
  }

  @Test
  public void getAggregatedDailyMergedPullRequestsShouldContainsAccountsWithoutMergedPR() {
    insertData();
    PowerMockito.mockStatic(HibernateUtil.class);
    PowerMockito.when(HibernateUtil.getSessionFactory()).thenReturn(createSessionFactory());
    List<CommonCountPojo> pojos = pullRequestDao.getAggregatedDailyMergedPullRequests();
    Assertions.assertThat(pojos.stream().anyMatch(p -> p.getAccountByAuthorId().getAccountId() == 4)).isFalse();
  }

  @Test
  public void getAggregatedWeeklyMergedPullRequestsShouldReturnCorrectAmountOfMergedPR() {
    insertData();
    PowerMockito.mockStatic(HibernateUtil.class);
    PowerMockito.when(HibernateUtil.getSessionFactory()).thenReturn(createSessionFactory());
    List<CommonCountPojo> pojos = pullRequestDao.getAggregatedWeeklyMergedPullRequests();
    Assertions.assertThat(pojos.size()).isEqualTo(4);
  }

  @Test
  public void getAggregatedWeeklyMergedPullRequestsShouldFilterBot() {
    insertData();
    PowerMockito.mockStatic(HibernateUtil.class);
    PowerMockito.when(HibernateUtil.getSessionFactory()).thenReturn(createSessionFactory());
    List<CommonCountPojo> pojos = pullRequestDao.getAggregatedWeeklyMergedPullRequests();
    Assertions.assertThat(
        (int) pojos.stream().filter(p -> p.getAccountByAuthorId().getLogin().contains("[bot]")).count()
    ).isEqualTo(0);
  }

  @Test
  public void getAggregatedWeeklyMergedPullRequestsShouldNotContainsReposWithoutMergedPR() {
    insertData();
    PowerMockito.mockStatic(HibernateUtil.class);
    PowerMockito.when(HibernateUtil.getSessionFactory()).thenReturn(createSessionFactory());
    List<CommonCountPojo> pojos = pullRequestDao.getAggregatedWeeklyMergedPullRequests();
    Assertions.assertThat(pojos.stream().anyMatch(p -> p.getRepositoryByRepoId().getRepoId() == 3)).isFalse();
  }

  @Test
  public void getAggregatedWeeklyMergedPullRequestsShouldContainsAccountsWithoutMergedPR() {
    insertData();
    PowerMockito.mockStatic(HibernateUtil.class);
    PowerMockito.when(HibernateUtil.getSessionFactory()).thenReturn(createSessionFactory());
    List<CommonCountPojo> pojos = pullRequestDao.getAggregatedWeeklyMergedPullRequests();
    Assertions.assertThat(pojos.stream().anyMatch(p -> p.getAccountByAuthorId().getAccountId() == 4)).isFalse();
  }

  @Test
  public void getAggregatedWeeklyMergedPullRequestsShouldNotContainsNotSundayWeekDate() {
    insertData();
    PowerMockito.mockStatic(HibernateUtil.class);
    PowerMockito.when(HibernateUtil.getSessionFactory()).thenReturn(createSessionFactory());
    List<CommonCountPojo> pojos = pullRequestDao.getAggregatedWeeklyMergedPullRequests();
    Assertions.assertThat(
        pojos.stream().anyMatch(p -> p.getDate().toInstant()
            .atZone(ZoneId.systemDefault())
            .toLocalDate().equals(LocalDate.parse("2020-04-08")))
    ).isFalse();
  }

}
