package ru.hh.topchik.dto;

/**
 * Data Transfer Object (DTO) для результатов в релевантных действиях (count) и очках (points)
 * */
public class CountPointsDto {
  private String avatar;
  private String account;
  private Long count;
  private Long points;

  public CountPointsDto() {
  }

  public CountPointsDto(String avatar, String account, Long count, Long points) {
    this.avatar = avatar;
    this.account = account;
    this.count = count;
    this.points = points;
  }

  public String getAvatar() {
    return avatar;
  }

  public void setAvatar(String avatar) {
    this.avatar = avatar;
  }

  public String getAccount() {
    return account;
  }

  public void setAccount(String account) {
    this.account = account;
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
