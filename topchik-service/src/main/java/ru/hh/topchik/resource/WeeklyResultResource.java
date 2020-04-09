package ru.hh.topchik.resource;

import ru.hh.topchik.dto.FinalResponseDto;
import ru.hh.topchik.service.WeeklyResultMapper;

import javax.inject.Inject;
import javax.inject.Singleton;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

@Path("/")
@Produces(MediaType.APPLICATION_JSON)
@Singleton
public class WeeklyResultResource {
  private final WeeklyResultMapper weeklyResultMapper;

  @Inject
  public WeeklyResultResource(WeeklyResultMapper weeklyResultMapper) {
    this.weeklyResultMapper = weeklyResultMapper;
  }

  @GET
  public FinalResponseDto getTops() {
    return weeklyResultMapper.mapFinal(weeklyResultMapper.mapRepositoryDto(weeklyResultMapper.mapCategoryDto()));
  }
}
