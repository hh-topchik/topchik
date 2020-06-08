package ru.hh.topchik.resource;

import ru.hh.topchik.mapper.AppMapper;

import javax.inject.Inject;
import javax.inject.Singleton;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

@Path("/api")
@Produces(MediaType.APPLICATION_JSON)
@Singleton
public class AppResource {
  private final AppMapper appMapper;

  @Inject
  public AppResource(AppMapper appMapper) {
    this.appMapper = appMapper;
  }

  @GET
  @Path("/reposAndCategories")
  public Response getRepositoriesAndCategoriesLists() {
    return Response
        .ok(appMapper.mapReposAndCats())
        .header("Access-Control-Allow-Origin", "*")
        .build();
  }

  @GET
  @Path("/globalTops")
  public Response getGlobalTops(@QueryParam("categoryId") List<String> categoryIds,
                                @QueryParam("period") String period) {
    return Response
        .ok(appMapper.mapGlobalTops(categoryIds, period))
        .header("Access-Control-Allow-Origin", "*")
        .build();
  }

  @GET
  @Path("/concreteTops")
  public Response getConcreteTops(@QueryParam("repoId") Long repoId,
                                 @QueryParam("categoryId") List<String> categoryIds,
                                 @QueryParam("period") String period) {
    return Response
        .ok(appMapper.mapConcreteTops(repoId, categoryIds, period))
        .header("Access-Control-Allow-Origin", "*")
        .build();
  }

  @GET
  @Path("/contributors")
  public Response getContributors(@QueryParam("repoId") Long repoId) {
    return Response
        .ok(appMapper.mapContributors(repoId))
        .header("Access-Control-Allow-Origin", "*")
        .build();
  }

  @GET
  @Path("/contributorStatistics")
  public Response getContributorStatistics(@QueryParam("repoId") Long repoId,
                                           @QueryParam("accountId") Long accountId) {
    return Response
        .ok(appMapper.mapContributorStatistics(repoId, accountId))
        .header("Access-Control-Allow-Origin", "*")
        .build();
  }
}
