package ru.hh.topchik.mapper;

import dao.CountPointsDao;
import dao.RepositoryDaoImpl;
import enums.Category;
import pojo.CountPointsPojo;
import ru.hh.topchik.dto.CategoryInfoDto;
import ru.hh.topchik.dto.CategoryPeriodDto;
import ru.hh.topchik.dto.CountPointsDto;
import ru.hh.topchik.dto.ReposAndCatsDto;
import ru.hh.topchik.dto.RepositoryInfoDto;

import javax.inject.Inject;
import javax.inject.Singleton;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Класс для маппинга сущности на DTO
 * */
@Singleton
public class AppMapper {
  private static final String REPO_PATH_PATTERN = "https://github.com/";

  private final RepositoryDaoImpl repositoryDao = new RepositoryDaoImpl();
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
        .getCategoriesIdList()
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
  private List<CountPointsDto> getGlobalTop(int categoryId, String period) {
    switch (period) {
      case "week":
        return countPointsDao.getWeekResults(categoryId).stream().map(this::mapCountPointsDto).collect(Collectors.toList());
      case "quarter":
        return countPointsDao.getQuarterResults(categoryId).stream().map(this::mapCountPointsDto).collect(Collectors.toList());
      case "year":
        return countPointsDao.getYearResults(categoryId).stream().map(this::mapCountPointsDto).collect(Collectors.toList());
      case "alltime":
        return countPointsDao.getAllTimeResults(categoryId).stream().map(this::mapCountPointsDto).collect(Collectors.toList());
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

}
