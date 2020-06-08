import com.opentable.db.postgres.embedded.EmbeddedPostgres;
import dao.AccountDaoImpl;
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
import util.HibernateUtil;

import java.io.IOException;

@RunWith(PowerMockRunner.class)
@PrepareForTest({HibernateUtil.class})
@PowerMockIgnore({"com.sun.org.apache.xerces.*", "javax.xml.*", "org.xml.*", "javax.management.*"})
public class AccountDaoImplTest {
  private static EmbeddedPostgres embeddedPostgres = null;

  @InjectMocks
  private AccountDaoImpl accountDao = new AccountDaoImpl();

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
  @Test
  public void getAccountEmailByAccountShouldReturnCorrectEmail() {
    insertData();
    PowerMockito.mockStatic(HibernateUtil.class);
    PowerMockito.when(HibernateUtil.getSessionFactory()).thenReturn(createSessionFactory());
    String email = accountDao.getAccountEmailByAccount(new Account(1, "BigBoss", "a.korolev@roga-i-kopyta.ru", null, null));
    Assertions.assertThat(email).isEqualTo("a.korolev@roga-i-kopyta.ru");
  }

  @Before
  public void cleanUpDb() {
    TestHelper.executeScript(embeddedPostgres.getPostgresDatabase(), "testdata-rollback.sql");
  }

  public void insertData() {
    TestHelper.executeScript(embeddedPostgres.getPostgresDatabase(), "testdata.sql");
  }


  @Test
  public void getAccountEmailByAccountShouldReturnNull() {
    insertData();
    PowerMockito.mockStatic(HibernateUtil.class);
    PowerMockito.when(HibernateUtil.getSessionFactory()).thenReturn(createSessionFactory());
    String email = accountDao.getAccountEmailByAccount(new Account(1, "BigBoss", null, null, null));
    Assertions.assertThat(email).isNull();
  }

  @Test
  public void getAccountLoginByAccountShouldReturnCorrectAccount() {
    insertData();
    PowerMockito.mockStatic(HibernateUtil.class);
    PowerMockito.when(HibernateUtil.getSessionFactory()).thenReturn(createSessionFactory());
    String login = accountDao.getAccountLoginByAccount(new Account(1, "BigBoss", "a.korolev@roga-i-kopyta.ru", null, null));
    Assertions.assertThat(login).isEqualTo("BigBoss");
  }
}
