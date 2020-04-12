package ru.hh.topchik.dto;

/**
 * Data Transfer Object (DTO) для результатов в очках (за квартал/год/всё время)
 * */
public class PointsDto {
  private String account;
  private Integer points;

  public PointsDto() {
  }

  public PointsDto(String account, Integer points) {
    this.account = account;
    this.points = points;
  }

  public String getAccount() {
    return account;
  }

  public void setAccount(String account) {
    this.account = account;
  }

  public Integer getPoints() {
    return points;
  }

  public void setPoints(Integer points) {
    this.points = points;
  }
}
