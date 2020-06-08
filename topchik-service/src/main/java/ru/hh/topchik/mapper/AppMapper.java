package ru.hh.topchik.mapper;

import dao.AccountDaoImpl;
import dao.CountPointsDao;
import dao.DailyCountDaoImpl;
import dao.RepositoryDaoImpl;
import dao.WeeklyResultDaoImpl;
import enums.Category;
import enums.Medal;
import pojo.CountPointsPojo;
import ru.hh.topchik.dto.CategoryInfoDto;
import ru.hh.topchik.dto.CategoryPeriodDto;
import ru.hh.topchik.dto.CategoryStatisticsDto;
import ru.hh.topchik.dto.ContributorDto;
import ru.hh.topchik.dto.ContributorInfoDto;
import ru.hh.topchik.dto.ContributorStatisticsDto;
import ru.hh.topchik.dto.CountPointsDto;
import ru.hh.topchik.dto.DateStatisticsDto;
import ru.hh.topchik.dto.MedalDto;
import ru.hh.topchik.dto.ReposAndCatsDto;
import ru.hh.topchik.dto.RepositoryInfoDto;

import javax.inject.Inject;
import javax.inject.Singleton;
import java.math.BigInteger;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Класс для маппинга сущности на DTO
 * */
@Singleton
public class AppMapper {
  private static final String REPO_PATH_PATTERN = "https://github.com/";

  private final AccountDaoImpl accountDao = new AccountDaoImpl();
  private final RepositoryDaoImpl repositoryDao = new RepositoryDaoImpl();
  private final DailyCountDaoImpl dailyCountDao = new DailyCountDaoImpl();
  private final WeeklyResultDaoImpl weeklyResultDao = new WeeklyResultDaoImpl();
  private final CountPointsDao countPointsDao;

  @Inject
  public AppMapper(CountPointsDao countPointsDao) {
    this.countPointsDao = countPointsDao;
  }

  /**
   * Маппинг информации (id и название) о всех репозиториях и всех категориях на DTO,
   * которая отправит информацию на фронт
   * */
  public ReposAndCatsDto mapReposAndCats() {
    List<RepositoryInfoDto> repositoryInfoDtos = repositoryDao
        .findAll()
        .stream()
        .map(repo -> new RepositoryInfoDto(repo.getRepoId(),
            repo.getPath().substring(REPO_PATH_PATTERN.length())))
        .collect(Collectors.toList());

    List<CategoryInfoDto> categoryInfoDtos = countPointsDao
        .getCategoriesIdList(null)
        .stream()
        .map(catId -> new CategoryInfoDto(catId,
            Category.getById(catId).getTitle(),
            Category.getById(catId).getDescription(),
            Category.getById(catId).getUnitOfMeasure()))
        .collect(Collectors.toList());

    return new ReposAndCatsDto(repositoryInfoDtos, categoryInfoDtos);
  }

  /**
   * Маппинг отдельных топов на глобальный топ по всем категориям
   * */
  public List<CategoryPeriodDto> mapGlobalTops(List<String> categories, String period) {
    return categories
        .stream()
        .map(Integer::parseInt)
        .map(catId -> new CategoryPeriodDto(catId,
            getGlobalTop(catId, period.toLowerCase())))
        .collect(Collectors.toList());
  }

  /**
   * Получение топа в зависимости от временного промежутка
   * */
  private List<CountPointsDto> getGlobalTop(Integer categoryId, String period) {
    switch (period) {
      case "week":
        return countPointsDao.getWeekResults(categoryId, null).stream().map(this::mapCountPointsDto).collect(Collectors.toList());
      case "quarter":
        return countPointsDao.getQuarterResults(categoryId, null).stream().map(this::mapCountPointsDto).collect(Collectors.toList());
      case "year":
        return countPointsDao.getYearResults(categoryId, null).stream().map(this::mapCountPointsDto).collect(Collectors.toList());
      case "alltime":
        return countPointsDao.getAllTimeResults(categoryId, null).stream().map(this::mapCountPointsDto).collect(Collectors.toList());
      default:
        System.out.println("Нет такого временного промежутка");
        return null;
    }
  }

  /**
   * Маппинг конкретного топа
   * */
  public List<CategoryPeriodDto> mapConcreteTops(long repoId, List<String> categories, String period) {
    return categories
        .stream()
        .map(Integer::parseInt)
        .map(catId -> new CategoryPeriodDto(catId,
            getConcreteTop(repoId, catId, period.toLowerCase())))
        .collect(Collectors.toList());
  }

  /**
   * Получение топа в зависимости от временного промежутка
   * */
  private List<CountPointsDto> getConcreteTop(long repoId, int categoryId, String period) {
    switch (period) {
      case "week":
        return countPointsDao.getWeekResults(categoryId, repoId).stream().map(this::mapCountPointsDto).collect(Collectors.toList());
      case "quarter":
        return countPointsDao.getQuarterResults(categoryId, repoId).stream().map(this::mapCountPointsDto).collect(Collectors.toList());
      case "year":
        return countPointsDao.getYearResults(categoryId, repoId).stream().map(this::mapCountPointsDto).collect(Collectors.toList());
      case "alltime":
        return countPointsDao.getAllTimeResults(categoryId, repoId).stream().map(this::mapCountPointsDto).collect(Collectors.toList());
      default:
        System.out.println("Нет такого временного промежутка");
        return null;
    }
  }

  /**
   * Маппинг агрегированной сущности CountPointsPojo на DTO
   * */
  private CountPointsDto mapCountPointsDto(CountPointsPojo countPointsPojo) {
    return new CountPointsDto(countPointsPojo.getAvatar(),
        countPointsPojo.getAccount(),
        countPointsPojo.getCount(),
        countPointsPojo.getPoints());
  }

  /**
   * Маппинг информации (аватар и логин) о всех пользователях в данном репозитории на DTO,
   * которая отправит информацию на фронт
   * */
  public ContributorDto mapContributors(Long repoId) {
    List<ContributorInfoDto> contributorInfoDtos = countPointsDao
        .getReposAccountIdList(repoId)
        .stream()
        .map(BigInteger::longValue)
        .map(accId -> new ContributorInfoDto(accountDao.findById(accId).getAccountId(),
            accountDao.findById(accId).getAvatar(),
            accountDao.findById(accId).getLogin()))
        .collect(Collectors.toList());
    return new ContributorDto(contributorInfoDtos);
  }

  /**
   * Маппинг личной статистики данного пользователя в данном репозитории на DTO,
   * которая отправит информацию на фронт
   * */
  public ContributorStatisticsDto mapContributorStatistics(Long repoId, Long accountId) {
    List<CategoryStatisticsDto> categoryStatisticsDtos = new ArrayList<>();
    for (Integer categoryId : countPointsDao.getCategoriesIdList(accountId)) {
      List<DateStatisticsDto> dateStatisticsDtos = makeDateStatisticsDto(categoryId, accountId, repoId);
      Long count = dailyCountDao.getCategoryCountSum(categoryId, accountId, repoId);
      Long points = weeklyResultDao.getCategoryPointsSum(categoryId, accountId, repoId);
      categoryStatisticsDtos.add(new CategoryStatisticsDto(categoryId, dateStatisticsDtos, count, points));
    }
    return new ContributorStatisticsDto(accountDao.findById(accountId).getLogin(),
        categoryStatisticsDtos,
        new MedalDto(
            weeklyResultDao.getMedalSum(Medal.GOLD.getId(), accountId, repoId),
            weeklyResultDao.getMedalSum(Medal.SILVER.getId(), accountId, repoId),
            weeklyResultDao.getMedalSum(Medal.BRONZE.getId(), accountId, repoId)
        ));
  }

  /**
   * Метод формирования DateStatisticsDto
   * */
  private List<DateStatisticsDto> makeDateStatisticsDto(Integer categoryId, Long accountId, Long repoId) {
    List<DateStatisticsDto> dateStatisticsDtos = new ArrayList<>();
    List<LocalDate> weekDates = weeklyResultDao.getDistinctWeekDates(categoryId, accountId, repoId);
    for (LocalDate weekDate : weekDates) {
      Long count = dailyCountDao.getWeekDateCountSum(weekDate, categoryId, accountId, repoId);
      List<Integer> medals = weeklyResultDao.getAccountMedalByWeekDate(weekDate, categoryId, accountId, repoId);
      dateStatisticsDtos.add(new DateStatisticsDto(weekDate.format(DateTimeFormatter.ofPattern("dd.MM.yyyy")),
          count, medals));
    }
    return dateStatisticsDtos;
  }
}
