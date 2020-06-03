import com.opentable.db.postgres.embedded.EmbeddedPostgres;
import dao.CommentDaoImpl;
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
public class CommentDaoImplTest {
  private static EmbeddedPostgres embeddedPostgres = null;

  @InjectMocks
  private CommentDaoImpl commentDao = new CommentDaoImpl();

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
  public void getAggregatedDailyCommentsShouldReturnCorrectAmountOfDailyComments() {
    insertData();
    PowerMockito.mockStatic(HibernateUtil.class);
    PowerMockito.when(HibernateUtil.getSessionFactory()).thenReturn(createSessionFactory());
    List<CommonCountPojo> pojos = commentDao.getAggregatedDailyComments();
    Assertions.assertThat(pojos.size()).isEqualTo(8);
  }

  @Test
  public void getAggregatedDailyCommentsShouldFilterBot() {
    insertData();
    PowerMockito.mockStatic(HibernateUtil.class);
    PowerMockito.when(HibernateUtil.getSessionFactory()).thenReturn(createSessionFactory());
    List<CommonCountPojo> pojos = commentDao.getAggregatedDailyComments();
    Assertions.assertThat(
        (int) pojos.stream().filter(p -> p.getAccountByAuthorId().getLogin().contains("[bot]")).count()
    ).isEqualTo(0);
  }

  @Test
  public void getAggregatedDailyCommentsShouldNotContainsAuthorOfPR() {
    insertData();
    PowerMockito.mockStatic(HibernateUtil.class);
    PowerMockito.when(HibernateUtil.getSessionFactory()).thenReturn(createSessionFactory());
    List<CommonCountPojo> pojos = commentDao.getAggregatedDailyComments();
    Assertions.assertThat(pojos.stream().anyMatch(p -> p.getAccountByAuthorId().getAccountId() == 3)).isFalse();
  }

  @Test
  public void getAggregatedWeeklyCommentsShouldReturnCorrectAmountOfDailyComments() {
    insertData();
    PowerMockito.mockStatic(HibernateUtil.class);
    PowerMockito.when(HibernateUtil.getSessionFactory()).thenReturn(createSessionFactory());
    List<CommonCountPojo> pojos = commentDao.getAggregatedWeeklyComments();
    Assertions.assertThat(pojos.size()).isEqualTo(6);
  }

  @Test
  public void getAggregatedWeeklyCommentsShouldFilterBot() {
    insertData();
    PowerMockito.mockStatic(HibernateUtil.class);
    PowerMockito.when(HibernateUtil.getSessionFactory()).thenReturn(createSessionFactory());
    List<CommonCountPojo> pojos = commentDao.getAggregatedWeeklyComments();
    Assertions.assertThat(
        (int) pojos.stream().filter(p -> p.getAccountByAuthorId().getLogin().contains("[bot]")).count()
    ).isEqualTo(0);
  }

  @Test
  public void getAggregatedWeeklyCommentsShouldNotContainsAuthorOfPR() {
    insertData();
    PowerMockito.mockStatic(HibernateUtil.class);
    PowerMockito.when(HibernateUtil.getSessionFactory()).thenReturn(createSessionFactory());
    List<CommonCountPojo> pojos = commentDao.getAggregatedWeeklyComments();
    Assertions.assertThat(pojos.stream().anyMatch(p -> p.getAccountByAuthorId().getAccountId() == 3)).isFalse();
  }

  @Test
  public void getAggregatedWeeklyCommentsShouldNotContainsNotSundayWeekDate() {
    insertData();
    PowerMockito.mockStatic(HibernateUtil.class);
    PowerMockito.when(HibernateUtil.getSessionFactory()).thenReturn(createSessionFactory());
    List<CommonCountPojo> pojos = commentDao.getAggregatedWeeklyComments();
    Assertions.assertThat(
        pojos.stream().anyMatch(p -> p.getDate().toInstant()
            .atZone(ZoneId.systemDefault())
            .toLocalDate().equals(LocalDate.parse("2020-04-08")))
    ).isFalse();
  }

  @Test
  public void getAggregatedDailyCommentedPullRequestsShouldReturnCorrectAmountOfDailyComments() {
    insertData();
    PowerMockito.mockStatic(HibernateUtil.class);
    PowerMockito.when(HibernateUtil.getSessionFactory()).thenReturn(createSessionFactory());
    List<CommonCountPojo> pojos = commentDao.getAggregatedDailyCommentedPullRequests();
    Assertions.assertThat(pojos.size()).isEqualTo(8);
  }

  @Test
  public void getAggregatedDailyCommentedPullRequestsShouldFilterBot() {
    insertData();
    PowerMockito.mockStatic(HibernateUtil.class);
    PowerMockito.when(HibernateUtil.getSessionFactory()).thenReturn(createSessionFactory());
    List<CommonCountPojo> pojos = commentDao.getAggregatedDailyCommentedPullRequests();
    Assertions.assertThat(
        (int) pojos.stream().filter(p -> p.getAccountByAuthorId().getLogin().contains("[bot]")).count()
    ).isEqualTo(0);
  }

  @Test
  public void getAggregatedDailyCommentedPullRequestsShouldNotContainsAuthorOfPR() {
    insertData();
    PowerMockito.mockStatic(HibernateUtil.class);
    PowerMockito.when(HibernateUtil.getSessionFactory()).thenReturn(createSessionFactory());
    List<CommonCountPojo> pojos = commentDao.getAggregatedDailyCommentedPullRequests();
    Assertions.assertThat(pojos.stream().anyMatch(p -> p.getAccountByAuthorId().getAccountId() == 3)).isFalse();
  }

  @Test
  public void getAggregatedWeeklyCommentedPullRequestsShouldReturnCorrectAmountOfDailyComments() {
    insertData();
    PowerMockito.mockStatic(HibernateUtil.class);
    PowerMockito.when(HibernateUtil.getSessionFactory()).thenReturn(createSessionFactory());
    List<CommonCountPojo> pojos = commentDao.getAggregatedWeeklyCommentedPullRequests();
    Assertions.assertThat(pojos.size()).isEqualTo(6);
  }

  @Test
  public void getAggregatedWeeklyCommentedPullRequestsShouldFilterBot() {
    insertData();
    PowerMockito.mockStatic(HibernateUtil.class);
    PowerMockito.when(HibernateUtil.getSessionFactory()).thenReturn(createSessionFactory());
    List<CommonCountPojo> pojos = commentDao.getAggregatedWeeklyCommentedPullRequests();
    Assertions.assertThat(
        (int) pojos.stream().filter(p -> p.getAccountByAuthorId().getLogin().contains("[bot]")).count()
    ).isEqualTo(0);
  }

  @Test
  public void getAggregatedWeeklyCommentedPullRequestsShouldNotContainsAuthorOfPR() {
    insertData();
    PowerMockito.mockStatic(HibernateUtil.class);
    PowerMockito.when(HibernateUtil.getSessionFactory()).thenReturn(createSessionFactory());
    List<CommonCountPojo> pojos = commentDao.getAggregatedWeeklyCommentedPullRequests();
    Assertions.assertThat(pojos.stream().anyMatch(p -> p.getAccountByAuthorId().getAccountId() == 3)).isFalse();
  }

  @Test
  public void getAggregatedWeeklyCommentedPullRequestsShouldNotContainsNotSundayWeekDate() {
    insertData();
    PowerMockito.mockStatic(HibernateUtil.class);
    PowerMockito.when(HibernateUtil.getSessionFactory()).thenReturn(createSessionFactory());
    List<CommonCountPojo> pojos = commentDao.getAggregatedWeeklyCommentedPullRequests();
    Assertions.assertThat(
        pojos.stream().anyMatch(p -> p.getDate().toInstant()
            .atZone(ZoneId.systemDefault())
            .toLocalDate().equals(LocalDate.parse("2020-04-08")))
    ).isFalse();
  }
}
