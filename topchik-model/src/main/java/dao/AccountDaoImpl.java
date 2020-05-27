package dao;


import entity.Account;
import org.hibernate.Session;
import org.hibernate.Transaction;
import util.HibernateUtil;

import javax.inject.Singleton;

/**
 * Имплементация DAO для сущности Account
 * */
@Singleton
public class AccountDaoImpl extends DaoImpl<Account> {

  public String getAccountEmailByAccount(Account account) {
    Transaction transaction;
    String email = "";
    try (Session session = HibernateUtil.getSessionFactory().openSession()) {
      transaction = session.beginTransaction();
      session.update(account); // required for reattaching detached Account entity to an active Hibernate Session to access Account fields
      email = account.getEmail();
      transaction.commit();
    } catch (Exception e) {
      e.printStackTrace();
    }
    return email;
  }

  public String getAccountLoginByAccount(Account account) {
    Transaction transaction;
    String login = "";
    try (Session session = HibernateUtil.getSessionFactory().openSession()) {
      transaction = session.beginTransaction();
      session.update(account); // required for reattaching detached Account entity to an active Hibernate Session to access Account fields
      login = account.getLogin();
      transaction.commit();
    } catch (Exception e) {
      e.printStackTrace();
    }
    return login;
  }
}
