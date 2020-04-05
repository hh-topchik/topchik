package ru.hh.topchik.service;

import dao.AchievementDao;
import entity.Achievement;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.inject.Inject;
import javax.inject.Singleton;
import javax.transaction.Transactional;
import java.util.List;

/**
 * Сервис-слой для сущности Achievement
 * */
@Singleton
public class AchievementService {
  private static final Logger LOGGER = LogManager.getLogger(AchievementService.class);
  private final AchievementDao achievementDao;

  @Inject
  public AchievementService(AchievementDao achievementDao) {
    this.achievementDao = achievementDao;
  }

  /**
   * Метод получения списка достижений за неделю
   * */
  @Transactional
  public List<Achievement> getWeekResults() {
    LOGGER.info("Получение списка результатов за неделю");
    return achievementDao.getWeekResults();
  }

  /**
   * Метод получения списка достижений за квартал
   * */
  @Transactional
  public List<Achievement> getQuarterResults() {
    LOGGER.info("Получение списка результатов за квартал");
    return achievementDao.getQuarterResults();
  }

  /**
   * Метод получения списка достижений за год
   * */
  @Transactional
  public List<Achievement> getYearResults() {
    LOGGER.info("Получение списка результатов за год");
    return achievementDao.getYearResults();
  }

  /**
   * Метод получения списка достижений за всё время
   * */
  @Transactional
  public List<Achievement> getAllTimeResults() {
    LOGGER.info("Получение списка результатов за всё время");
    return achievementDao.getAllTimeResults();
  }
}
