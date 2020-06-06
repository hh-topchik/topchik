package ru.hh.topchik.dto;

public class ContributorInfoDto {
  private Long id;
  private String avatar;
  private String account;

  public ContributorInfoDto() {
  }

  public ContributorInfoDto(Long id, String avatar, String account) {
    this.id = id;
    this.avatar = avatar;
    this.account = account;
  }

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
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
