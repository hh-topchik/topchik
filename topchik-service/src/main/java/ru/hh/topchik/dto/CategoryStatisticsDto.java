package ru.hh.topchik.dto;

import java.util.List;

public class CategoryStatisticsDto {
  private int categoryId;
  private List<DateStatisticsDto> date;
  private Long count;
  private Long points;

  public CategoryStatisticsDto() {
  }

  public CategoryStatisticsDto(int categoryId, List<DateStatisticsDto> date, Long count, Long points) {
    this.categoryId = categoryId;
    this.date = date;
    this.count = count;
    this.points = points;
  }

  public int getCategoryId() {
    return categoryId;
  }

  public void setCategoryId(int categoryId) {
    this.categoryId = categoryId;
  }

  public List<DateStatisticsDto> getDate() {
    return date;
  }

  public void setDate(List<DateStatisticsDto> date) {
    this.date = date;
  }

  public Long getCount() {
    return count;
  }

  public void setCount(Long count) {
    this.count = count;
  }

  public Long getPoints() {
    return points;
  }

  public void setPoints(Long points) {
    this.points = points;
  }
}
