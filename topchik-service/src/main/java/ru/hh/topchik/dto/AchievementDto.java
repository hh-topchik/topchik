package ru.hh.topchik.dto;

/**
 * Data Transfer Object (DTO) для конкретного достижений (achievement)
 * */
public class AchievementDto {
  private String worker;
  private Integer points;

  public AchievementDto() {
  }

  public AchievementDto(String worker, Integer points) {
    this.worker = worker;
    this.points = points;
  }

  public String getWorker() {
    return worker;
  }

  public void setWorker(String worker) {
    this.worker = worker;
  }

  public Integer getPoints() {
    return points;
  }

  public void setPoints(Integer points) {
    this.points = points;
  }
}
