package ru.hh.topchik.dto;

import java.util.List;

/**
 * Data Transfer Object (DTO) для отправки на фронт
 * Содержит полную информацию: название репозитория, название и описание категории, список топов по временным интервалам
 * */
public class FinalResponseDto {
  private List<RepositoryDto> repositories;

  public FinalResponseDto() {
  }

  public FinalResponseDto(List<RepositoryDto> repositories) {
    this.repositories = repositories;
  }

  public List<RepositoryDto> getRepositories() {
    return repositories;
  }

  public void setRepositories(List<RepositoryDto> repositories) {
    this.repositories = repositories;
  }
}
