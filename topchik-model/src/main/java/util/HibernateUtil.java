package util;

import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

/**
 * Класс для создания и хранения SessionFactory Hibernate
 */
public class HibernateUtil {

  private static SessionFactory sessionFactory;

  public static SessionFactory getSessionFactory() {
    if (sessionFactory == null) {
      try {
        try {
          Configuration cfg = new Configuration().addResource("hibernate.cfg.xml").configure();
          sessionFactory = cfg.buildSessionFactory();
        } catch (Throwable e) {
          System.err.println("Failed to create sessionFactory object." + e);
          throw new ExceptionInInitializerError(e);
        }
        System.out.println("Создание сессии");
      } catch (Exception e) {
        System.out.println(e.getMessage());
      }
    }
    return sessionFactory;
  }
}
