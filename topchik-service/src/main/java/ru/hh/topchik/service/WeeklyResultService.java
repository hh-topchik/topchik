package ru.hh.topchik.service;

import dao.WeeklyResultDao;
import entity.WeeklyResult;
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
public class WeeklyResultService {
  private static final Logger LOGGER = LogManager.getLogger(WeeklyResultService.class);
  private final WeeklyResultDao weeklyResultDao;

  @Inject
  public WeeklyResultService(WeeklyResultDao weeklyResultDao) {
    this.weeklyResultDao = weeklyResultDao;
  }

  /**
   * Метод получения списка достижений за неделю
   * */
  @Transactional
  public List<WeeklyResult> getWeekResults() {
    LOGGER.info("Получение списка результатов за неделю");
    return weeklyResultDao.getWeekResults();
  }

  /**
   * Метод получения списка достижений за квартал
   * */
  @Transactional
  public List<WeeklyResult> getQuarterResults() {
    LOGGER.info("Получение списка результатов за квартал");
    return weeklyResultDao.getQuarterResults();
  }

  /**
   * Метод получения списка достижений за год
   * */
  @Transactional
  public List<WeeklyResult> getYearResults() {
    LOGGER.info("Получение списка результатов за год");
    return weeklyResultDao.getYearResults();
  }

  /**
   * Метод получения списка достижений за всё время
   * */
  @Transactional
  public List<WeeklyResult> getAllTimeResults() {
    LOGGER.info("Получение списка результатов за всё время");
    return weeklyResultDao.getAllTimeResults();
  }
}
