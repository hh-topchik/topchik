const Router = require("koa-router");
const fs = require("fs");

const data = fs.readFileSync(
  `${__dirname}/testData2.json`,
  "utf-8"
);

const contributorsData = fs.readFileSync(
  `${__dirname}/testDevelopersList.json`,
  "utf-8"
);

const contributorsStatisticsData = fs.readFileSync(
  `${__dirname}/testStatisticsData.json`,
  "utf-8"
);

module.exports = function() {
  const router = new Router({
    prefix: "/api"
  });

  router
    .get("/getRanking", ctx => {
      ctx.body = JSON.parse(data);
    })
    .get("/contributors", ctx => {
      ctx.body = JSON.parse(contributorsData);
    })
    .get("/contributorStatistics", ctx => {
      ctx.body = JSON.parse(contributorsStatisticsData);
    });

  return router;
};
