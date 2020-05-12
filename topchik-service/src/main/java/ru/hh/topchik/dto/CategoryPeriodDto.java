package ru.hh.topchik.dto;

import java.util.List;

/**
 * Data Transfer Object для списка топов за одну категорию
 * */
public class CategoryPeriodDto {
  private int categoryId;
  private List<CountPointsDto> top;

  public CategoryPeriodDto() {
  }

  public CategoryPeriodDto(int categoryId, List<CountPointsDto> top) {
    this.categoryId = categoryId;
    this.top = top;
  }

  public int getCategoryId() {
    return categoryId;
  }

  public void setCategoryId(int categoryId) {
    this.categoryId = categoryId;
  }

  public List<CountPointsDto> getTop() {
    return top;
  }

  public void setTop(List<CountPointsDto> top) {
    this.top = top;
  }
}
