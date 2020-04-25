package ru.hh.topchik.mapper;

import dao.CountPointsDao;
import dao.RepositoryDaoImpl;
import entity.Repository;
import enums.Category;
import pojo.CountPointsPojo;
import ru.hh.topchik.dto.CategoryInfoDto;
import ru.hh.topchik.dto.CategoryPeriodDto;
import ru.hh.topchik.dto.CountPointsDto;
import ru.hh.topchik.dto.ReposAndCatsDto;
import ru.hh.topchik.dto.RepositoryInfoDto;

import javax.inject.Inject;
import javax.inject.Singleton;
import java.util.ArrayList;
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
    List<Repository> repositories = repositoryDao.findAll();
    List<RepositoryInfoDto> repositoryInfoDtos = new ArrayList<>();
    for (Repository repository : repositories) {
      repositoryInfoDtos.add(new RepositoryInfoDto(repository.getRepoId(),
          repository.getPath().substring(REPO_PATH_PATTERN.length())));
    }

    List<Integer> categoriesId = countPointsDao.getCategoriesIdList();
    List<CategoryInfoDto> categoryInfoDtos = new ArrayList<>();
    for (Integer categoryId : categoriesId) {
      categoryInfoDtos.add(new CategoryInfoDto(categoryId,
          Category.getById(categoryId).getTitle()));
    }

    return new ReposAndCatsDto(repositoryInfoDtos, categoryInfoDtos);
  }

  /**
   * Маппинг отдельных топов на глобальный топ по всем категориям
   * */
  public List<CategoryPeriodDto> mapGlobalTops(List<String> categories, String period) {
    period = period.toLowerCase();
    List<CategoryPeriodDto> globalTopsList = new ArrayList<>();
    for (String categoryIdStr : categories) {
      int categoryId = Integer.parseInt(categoryIdStr);
      globalTopsList.add(new CategoryPeriodDto(Category.getById(categoryId).getTitle(),
          Category.getById(categoryId).getDescription(),
          getGlobalTop(categoryId, period)));
    }
    return globalTopsList;
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
    period = period.toLowerCase();
    List<CategoryPeriodDto> concreteTopsList = new ArrayList<>();
    for (String categoryIdStr : categories) {
      int categoryId = Integer.parseInt(categoryIdStr);
      concreteTopsList.add(new CategoryPeriodDto(Category.getById(categoryId).getTitle(),
          Category.getById(categoryId).getDescription(),
          getConcreteTop(repoId, categoryId, period)));
    }
    return concreteTopsList;
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
