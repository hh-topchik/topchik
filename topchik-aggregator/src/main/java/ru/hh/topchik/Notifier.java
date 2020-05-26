package ru.hh.topchik;

import entity.Account;
import entity.Repository;
import enums.Category;

import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Properties;

public class Notifier {
  private static final String SENDER_EMAIL = "hh.topchik@gmail.com";
  private static final String SENDER_PASSWORD = "XXX"; // <- CHANGE THIS VALUE TO OUR PASSWORD
  private static final String REPO_PATH_PATTERN = "https://github.com/";

  public Notifier() {
  }

  public void sendNotification(DaoFactory daoFactory, Account account, Repository repo,
                               int place, int categoryId, long count) {
    Properties properties = new Properties();
    properties.put("mail.smtp.auth", "true");
    properties.put("mail.smtp.starttls.enable", "true");
    properties.put("mail.smtp.host", "smtp.gmail.com");
    properties.put("mail.smtp.port", "587");

    Session session = Session.getInstance(properties, new Authenticator() {
      @Override
      protected PasswordAuthentication getPasswordAuthentication() {
        return new PasswordAuthentication(SENDER_EMAIL, SENDER_PASSWORD);
      }
    });

    if (isRecipientEmailAvailable(daoFactory, account)) {
      Message message = prepareMessage(daoFactory, session, SENDER_EMAIL, account, repo, place, categoryId, count);
      try {
        Transport.send(message);
        System.out.println("Message sent successfully");
      } catch (MessagingException e) {
        e.printStackTrace();
      }
    }
  }

  private boolean isRecipientEmailAvailable(DaoFactory daoFactory, Account account) {
    String recipientEmail = daoFactory.getAccountDao().getAccountEmailByAccount(account);
    return recipientEmail != null;
  }

  private Message prepareMessage(DaoFactory daoFactory, Session session, String senderEmail, Account account, Repository repo,
                                 int place, int categoryId, long count) {
    Message message = new MimeMessage(session);
    try {
      message.setFrom(new InternetAddress(senderEmail));
      message.setRecipient(Message.RecipientType.TO,
          new InternetAddress(daoFactory.getAccountDao().getAccountEmailByAccount(account)));
      message.setSubject("hh-topchik: Успехи прошедшей недели");
      message.setText("Поздравляем, " + daoFactory.getAccountDao().getAccountLoginByAccount(account) + "! \n\n" +
          "На прошедшей неделе Вы заняли " + place + " место в категории " + "\"" + Category.getById(categoryId).getTitle() + "\"" +
          " (" + Category.getById(categoryId).getDescription() + ") с результатом " +
          count + " " + Category.getById(categoryId).getUnitOfMeasure() +
          " в репозитории " + daoFactory.getRepositoryDao().getRepoPathByRepo(repo).substring(REPO_PATH_PATTERN.length()) + "\n\n" +
          "С уважением,\n" +
          "Команда hh-topchik\n");
      return message;
    } catch (MessagingException e) {
      e.printStackTrace();
    }
    return null;
  }
}
