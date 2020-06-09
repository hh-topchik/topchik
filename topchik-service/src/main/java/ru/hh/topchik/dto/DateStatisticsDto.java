package ru.hh.topchik.dto;

import java.util.List;

public class DateStatisticsDto {
  private String week;
  private Long value;
  private List<Integer> medals;

  public DateStatisticsDto() {
  }

  public DateStatisticsDto(String week, Long value, List<Integer> medal) {
    this.week = week;
    this.value = value;
    this.medals = medal;
  }

  public String getWeek() {
    return week;
  }

  public void setWeek(String week) {
    this.week = week;
  }

  public Long getValue() {
    return value;
  }

  public void setValue(Long value) {
    this.value = value;
  }

  public List<Integer> getMedal() {
    return medals;
  }

  public void setMedal(List<Integer> medals) {
    this.medals = medals;
  }
}
