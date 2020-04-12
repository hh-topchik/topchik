package ru.hh.topchik.dto;

import java.util.List;

/**
 * Data Transfer Object для списка категорий
 * */
public class CategoryDto {
  private String name;
  private String description;
  private List<PointsDto> topWeek;
  private List<PointsDto> topQuarter;
  private List<PointsDto> topYear;
  private List<PointsDto> topAllTime;

  public CategoryDto() {
  }

  public CategoryDto(String name, String description,
                     List<PointsDto> topWeek, List<PointsDto> topQuarter,
                     List<PointsDto> topYear, List<PointsDto> topAllTime) {
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

  public List<PointsDto> getTopWeek() {
    return topWeek;
  }

  public void setTopWeek(List<PointsDto> topWeek) {
    this.topWeek = topWeek;
  }

  public List<PointsDto> getTopQuarter() {
    return topQuarter;
  }

  public void setTopQuarter(List<PointsDto> topQuarter) {
    this.topQuarter = topQuarter;
  }

  public List<PointsDto> getTopYear() {
    return topYear;
  }

  public void setTopYear(List<PointsDto> topYear) {
    this.topYear = topYear;
  }

  public List<PointsDto> getTopAllTime() {
    return topAllTime;
  }

  public void setTopAllTime(List<PointsDto> topAllTime) {
    this.topAllTime = topAllTime;
  }
}
