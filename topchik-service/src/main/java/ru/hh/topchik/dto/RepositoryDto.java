package ru.hh.topchik.dto;

import java.util.List;

/**
 * Data Transfer Object для списка репозиториев
 * */
public class RepositoryDto {
  private String title;
  private List<CategoryDto> sections;

  public RepositoryDto() {
  }

  public RepositoryDto(String title, List<CategoryDto> sections) {
    this.title = title;
    this.sections = sections;
  }

  public String getTitle() {
    return title;
  }

  public void setTitle(String title) {
    this.title = title;
  }

  public List<CategoryDto> getSections() {
    return sections;
  }

  public void setSections(List<CategoryDto> sections) {
    this.sections = sections;
  }
}
