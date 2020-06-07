package ru.hh.topchik.dto;

import java.util.List;

public class ContributorDto {
  private List<ContributorInfoDto> contributors;

  public ContributorDto() {
  }

  public ContributorDto(List<ContributorInfoDto> contributors) {
    this.contributors = contributors;
  }

  public List<ContributorInfoDto> getContributors() {
    return contributors;
  }

  public void setContributors(List<ContributorInfoDto> contributors) {
    this.contributors = contributors;
  }
}
