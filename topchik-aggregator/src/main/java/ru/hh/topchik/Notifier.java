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
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class Notifier {

  public Notifier() {
  }

  public void sendNotification(DaoFactory daoFactory, Account account, Repository repo,
                               int place, int categoryId, long count) {
    Properties properties = new Properties();
    properties.put("mail.smtp.auth", "true");
    properties.put("mail.smtp.starttls.enable", "true");
    properties.put("mail.smtp.host", "smtp.gmail.com");
    properties.put("mail.smtp.port", "587");

    String senderEmail = "";
    String senderPassword = "";

    try (InputStream input = getClass().getClassLoader().getResourceAsStream("sender.properties")) {
      Properties prop = new Properties();
      prop.load(input);
      senderEmail = prop.getProperty("sender.email");
      senderPassword = prop.getProperty("sender.password");
    } catch (IOException e) {
      e.printStackTrace();
    }

    String finalSenderEmail = senderEmail;
    String finalSenderPassword = senderPassword;

    Session session = Session.getInstance(properties, new Authenticator() {
      @Override
      protected PasswordAuthentication getPasswordAuthentication() {
        return new PasswordAuthentication(finalSenderEmail, finalSenderPassword);
      }
    });

    if (isRecipientEmailAvailable(daoFactory, account)) {
      try {
        Message message = prepareMessage(daoFactory, session, senderEmail, account, repo, place, categoryId, count);
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
                                 int place, int categoryId, long count) throws MessagingException {
    Message message = new MimeMessage(session);
    message.setFrom(new InternetAddress(senderEmail));
    message.setRecipient(Message.RecipientType.TO,
        new InternetAddress(daoFactory.getAccountDao().getAccountEmailByAccount(account)));
    message.setSubject("hh-topchik: Успехи прошедшей недели");
    message.setText(String.format("Поздравляем, %s! \n\n" +
            "На прошедшей неделе Вы заняли %d место в категории \"%s\" (%s) с результатом %d %s в репозитории %s\n\n" +
            "С уважением,\n" +
            "Команда hh-topchik\n",
        daoFactory.getAccountDao().getAccountLoginByAccount(account), place, Category.getById(categoryId).getTitle(),
        Category.getById(categoryId).getDescription(), count, Category.getById(categoryId).getUnitOfMeasure(),
        daoFactory.getRepositoryDao().getRepoPathByRepo(repo)));
    return message;
  }
}
