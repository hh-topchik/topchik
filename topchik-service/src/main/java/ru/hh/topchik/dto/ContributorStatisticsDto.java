package ru.hh.topchik.dto;

import java.util.List;

public class ContributorStatisticsDto {
  private String contributor;
  private List<CategoryStatisticsDto> categoriesStatistics;
  private MedalDto medals;

  public ContributorStatisticsDto() {
  }

  public ContributorStatisticsDto(String contributor, List<CategoryStatisticsDto> categoriesStatistics, MedalDto medals) {
    this.contributor = contributor;
    this.categoriesStatistics = categoriesStatistics;
    this.medals = medals;
  }

  public String getContributor() {
    return contributor;
  }

  public void setContributor(String contributor) {
    this.contributor = contributor;
  }

  public List<CategoryStatisticsDto> getCategoriesStatistics() {
    return categoriesStatistics;
  }

  public void setCategoriesStatistics(List<CategoryStatisticsDto> categoriesStatistics) {
    this.categoriesStatistics = categoriesStatistics;
  }

  public MedalDto getMedals() {
    return medals;
  }

  public void setMedals(MedalDto medals) {
    this.medals = medals;
  }
}
