package lv.iljapavlovs.gatling.tutorials

import io.gatling.core.Predef._
import io.gatling.http.Predef._

class BasicSimulationImprovedTest_2 extends Simulation {


    val httpProtocol = http
      .baseUrl("http://computer-database.gatling.io")
      .header("Accept", "application/json")

    val searchScenario = scenario("Search Scenario")
      .exec(
        http("Home") // Name of the HTTP requests, which will be displayed in the report
          .get("/") // 1st request - GET
      ).pause(7) // Some pause/think time to replicate end user behavior
      .exec(
        http("Search")
          .get("/computers?f=macbook") // 2nd request
      )
      .pause(2)
      .exec(
        http("Select")
          .get("/computers/6") // 3rd request
      )
      .pause(3)

    setUp(searchScenario.inject(atOnceUsers(1)).protocols(httpProtocol))

}
