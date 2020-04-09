package ru.hh.topchik.service;

import entity.WeeklyResult;
import ru.hh.topchik.dto.PointsDto;
import ru.hh.topchik.dto.CategoryDto;
import ru.hh.topchik.dto.FinalResponseDto;
import ru.hh.topchik.dto.RepositoryDto;

import javax.inject.Inject;
import javax.inject.Singleton;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Класс для маппинга сущности на DTO*/
@Singleton
public class WeeklyResultMapper {
  private final WeeklyResultService weeklyResultService;
  private static final String CATEGORY_TITLE = "Спринтеры";
  private static final String CATEGORY_DESCRIPTION = "Количество замёрдженных PR";
  private static final String REPO_TITLE = "hh-topchik/topchik";

  @Inject
  public WeeklyResultMapper(WeeklyResultService weeklyResultService) {
    this.weeklyResultService = weeklyResultService;
  }

  /**
   * Маппинг сущности WeeklyResult на DTO
   * */
  public PointsDto mapPointsDto(WeeklyResult weeklyResult) {
    return new PointsDto(weeklyResult.getAccountByAuthorId().getLogin(), weeklyResult.getPoints());
  }

  /**
   * Маппинг AchievementDTO на CategoryDTO
   * */
  public CategoryDto mapCategoryDto() {
    return new CategoryDto(CATEGORY_TITLE, CATEGORY_DESCRIPTION,
        filterWeeklyResult(weeklyResultService.getWeekResults().stream().map(this::mapPointsDto).collect(Collectors.toList())),
        filterWeeklyResult(weeklyResultService.getQuarterResults().stream().map(this::mapPointsDto).collect(Collectors.toList())),
        filterWeeklyResult(weeklyResultService.getYearResults().stream().map(this::mapPointsDto).collect(Collectors.toList())),
        filterWeeklyResult(weeklyResultService.getAllTimeResults().stream().map(this::mapPointsDto).collect(Collectors.toList())));
  }

  /**
   * Маппинг CategoryDTO на RepositoryDTO
   * */
  public RepositoryDto mapRepositoryDto(CategoryDto categoryDto) {
    return new RepositoryDto(REPO_TITLE, List.of(categoryDto));
  }

  /**
   * Маппинг RepositoryDTO на окончательную DTO, которая отправляется на фронт
   * */
  public FinalResponseDto mapFinal(RepositoryDto repositoryDto) {
    return new FinalResponseDto(List.of(repositoryDto));
  }

  /**
   * Метод для склеивания очков пользователя и ранжирования пользователей
   * */
  public List<PointsDto> filterWeeklyResult(List<PointsDto> achievementFromDb) {
    List<PointsDto> filteredResults = new ArrayList<>();
    for (PointsDto pointsDto : achievementFromDb) {
      Optional<PointsDto> duplicate = filteredResults.stream()
          .filter(achieve -> Objects.equals(achieve.getAccount(), pointsDto.getAccount())).findFirst();
      if (duplicate.isPresent()) {
        duplicate.get().setPoints(duplicate.get().getPoints() + pointsDto.getPoints());
      } else {
        filteredResults.add(pointsDto);
      }
    }
    filteredResults.sort(Comparator.comparing(PointsDto::getPoints).reversed());
    return filteredResults;
  }

}
