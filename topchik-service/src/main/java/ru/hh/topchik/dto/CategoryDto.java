package ru.hh.topchik.dto;

import java.util.List;

/**
 * Data Transfer Object для списка категорий
 * */
public class CategoryDto {
  private String name;
  private String description;
  private List<AchievementDto> topWeek;
  private List<AchievementDto> topQuarter;
  private List<AchievementDto> topYear;
  private List<AchievementDto> topAllTime;

  public CategoryDto() {
  }

  public CategoryDto(String name, String description,
                     List<AchievementDto> topWeek, List<AchievementDto> topQuarter,
                     List<AchievementDto> topYear, List<AchievementDto> topAllTime) {
    this.name = name;
    this.description = description;
    this.topWeek = topWeek;
    this.topQuarter = topQuarter;
    this.topYear = topYear;
    this.topAllTime = topAllTime;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  public List<AchievementDto> getTopWeek() {
    return topWeek;
  }

  public void setTopWeek(List<AchievementDto> topWeek) {
    this.topWeek = topWeek;
  }

  public List<AchievementDto> getTopQuarter() {
    return topQuarter;
  }

  public void setTopQuarter(List<AchievementDto> topQuarter) {
    this.topQuarter = topQuarter;
  }

  public List<AchievementDto> getTopYear() {
    return topYear;
  }

  public void setTopYear(List<AchievementDto> topYear) {
    this.topYear = topYear;
  }

  public List<AchievementDto> getTopAllTime() {
    return topAllTime;
  }

  public void setTopAllTime(List<AchievementDto> topAllTime) {
    this.topAllTime = topAllTime;
  }
}
