package ru.hh.topchik.service;

import entity.Achievement;
import ru.hh.topchik.dto.AchievementDto;
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
public class AchievementMapper {
  private final AchievementService achievementService;
  private static final String categoryTitle = "Спринтеры";
  private static final String categoryDescription = "Количество замёрдженных PR";
  private static final String repoTitle = "hh-topchik/topchik";

  @Inject
  public AchievementMapper(AchievementService achievementService) {
    this.achievementService = achievementService;
  }

  /**
   * Маппинг сущности Achievement на AchievementDTO
   * */
  public AchievementDto mapAchievementDto(Achievement achievement) {
    return new AchievementDto(achievement.getAccountByDeveloperId().getLogin(), achievement.getPoints());
  }

  /**
   * Маппинг AchievementDTO на CategoryDTO
   * */
  public CategoryDto mapCategoryDto() {
    return new CategoryDto(categoryTitle, categoryDescription,
        filterAchievements(achievementService.getWeekResults().stream().map(this::mapAchievementDto).collect(Collectors.toList())),
        filterAchievements(achievementService.getQuarterResults().stream().map(this::mapAchievementDto).collect(Collectors.toList())),
        filterAchievements(achievementService.getYearResults().stream().map(this::mapAchievementDto).collect(Collectors.toList())),
        filterAchievements(achievementService.getAllTimeResults().stream().map(this::mapAchievementDto).collect(Collectors.toList())));
  }

  /**
   * Маппинг CategoryDTO на RepositoryDTO
   * */
  public RepositoryDto mapRepositoryDto(CategoryDto categoryDto) {
    return new RepositoryDto(repoTitle, List.of(categoryDto));
  }

  /**
   * Маппинг RepositoryDTO на окончательную DTO, которая отправляется на фронт
   * */
  public FinalResponseDto mapFinal(RepositoryDto repositoryDto) {
    return new FinalResponseDto(List.of(repositoryDto));
  }

  public List<AchievementDto> filterAchievements(List<AchievementDto> achievementFromDb) {
    List<AchievementDto> filteredResults = new ArrayList<>();
    for (AchievementDto achievementDto : achievementFromDb) {
      Optional<AchievementDto> duplicate = filteredResults.stream()
          .filter(achieve -> Objects.equals(achieve.getWorker(), achievementDto.getWorker())).findFirst();
      if (duplicate.isPresent()) {
        duplicate.get().setPoints(duplicate.get().getPoints() + achievementDto.getPoints());
      } else {
        filteredResults.add(achievementDto);
      }
    }
    filteredResults.sort(Comparator.comparing(AchievementDto::getPoints).reversed());
    return filteredResults;
  }

}
