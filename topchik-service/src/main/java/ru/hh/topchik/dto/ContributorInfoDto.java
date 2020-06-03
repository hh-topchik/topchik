package ru.hh.topchik.dto;

public class ContributorInfoDto {
  private String avatar;
  private String account;

  public ContributorInfoDto() {
  }

  public ContributorInfoDto(String avatar, String account) {
    this.avatar = avatar;
    this.account = account;
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
}
