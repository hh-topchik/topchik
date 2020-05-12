package ru.hh.topchik.dto;

public class CategoryInfoDto {
  private int id;
  private String title;
  private String description;
  private String unitOfMeasure;

  public CategoryInfoDto() {
  }

  public CategoryInfoDto(int id, String title, String description, String unitOfMeasure) {
    this.id = id;
    this.title = title;
    this.description = description;
    this.unitOfMeasure = unitOfMeasure;
  }

  public int getId() {
    return id;
  }

  public void setId(int id) {
    this.id = id;
  }

  public String getTitle() {
    return title;
  }

  public void setTitle(String title) {
    this.title = title;
  }

  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  public String getUnitOfMeasure() {
    return unitOfMeasure;
  }

  public void setUnitOfMeasure(String unitOfMeasure) {
    this.unitOfMeasure = unitOfMeasure;
  }
}
