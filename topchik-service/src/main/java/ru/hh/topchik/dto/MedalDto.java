package ru.hh.topchik.dto;

public class MedalDto {
  private Long gold;
  private Long silver;
  private Long bronze;

  public MedalDto() {
  }

  public MedalDto(Long gold, Long silver, Long bronze) {
    this.gold = gold;
    this.silver = silver;
    this.bronze = bronze;
  }

  public Long getGold() {
    return gold;
  }

  public void setGold(Long gold) {
    this.gold = gold;
  }

  public Long getSilver() {
    return silver;
  }

  public void setSilver(Long silver) {
    this.silver = silver;
  }

  public Long getBronze() {
    return bronze;
  }

  public void setBronze(Long bronze) {
    this.bronze = bronze;
  }
}
