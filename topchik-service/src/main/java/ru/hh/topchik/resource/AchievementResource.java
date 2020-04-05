package ru.hh.topchik.resource;

import ru.hh.topchik.dto.FinalResponseDto;
import ru.hh.topchik.service.AchievementMapper;

import javax.inject.Inject;
import javax.inject.Singleton;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.util.List;

@Path("/")
@Produces(MediaType.APPLICATION_JSON)
@Singleton
public class AchievementResource {
  private final AchievementMapper achievementMapper;

  @Inject
  public AchievementResource(AchievementMapper achievementMapper) {
    this.achievementMapper = achievementMapper;
  }

  @GET
  public List<FinalResponseDto> getTops() {
    return List.of(achievementMapper.mapFinal(
        achievementMapper.mapRepositoryDto(achievementMapper.mapCategoryDto())
    ));
  }
}
