package ru.hh.topchik.dto;

public class DateStatisticsDto {
  private String week;
  private Long value;
  private int medal;

  public DateStatisticsDto() {
  }

  public DateStatisticsDto(String week, Long value, int medal) {
    this.week = week;
    this.value = value;
    this.medal = medal;
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

  public int getMedal() {
    return medal;
  }

  public void setMedal(int medal) {
    this.medal = medal;
  }
}
