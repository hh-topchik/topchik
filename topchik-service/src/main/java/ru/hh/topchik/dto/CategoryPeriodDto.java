package ru.hh.topchik.dto;

import java.util.List;

/**
 * Data Transfer Object для списка топов за одну категорию
 * */
public class CategoryPeriodDto {
  private String name;
  private String description;
  private List<CountPointsDto> top;

  public CategoryPeriodDto() {
  }

  public CategoryPeriodDto(String name, String description, List<CountPointsDto> top) {
    this.name = name;
    this.description = description;
    this.top = top;
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

  public List<CountPointsDto> getTop() {
    return top;
  }

  public void setTop(List<CountPointsDto> top) {
    this.top = top;
  }
}
