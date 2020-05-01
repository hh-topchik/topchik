package ru.hh.topchik.dto;

import java.util.List;

/**
 * Data Transfer Object для списка всех репозиториев и всех категорий
 * */
public class ReposAndCatsDto {
  private List<RepositoryInfoDto> repositories;
  private List<CategoryInfoDto> categories;

  public ReposAndCatsDto() {
  }

  public ReposAndCatsDto(List<RepositoryInfoDto> repositories, List<CategoryInfoDto> categories) {
    this.repositories = repositories;
    this.categories = categories;
  }

  public List<RepositoryInfoDto> getRepositories() {
    return repositories;
  }

  public void setRepositories(List<RepositoryInfoDto> repositories) {
    this.repositories = repositories;
  }

  public List<CategoryInfoDto> getCategories() {
    return categories;
  }

  public void setCategories(List<CategoryInfoDto> categories) {
    this.categories = categories;
  }
}
