package lv.iljapavlovs.gatling.tutorials

import io.gatling.core.Predef._
import io.gatling.http.Predef._

class BasicSimulation101Test_1 extends Simulation {


    val httpProtocol = http
      .baseUrl("http://computer-database.gatling.io")
      .header("Accept", "application/json")

    val searchScenario = scenario("Search Scenario")
        .exec(
          http("Search Macbook")
            .get("/computers?f=macbook")
        )

    setUp(searchScenario.inject(atOnceUsers(1)).protocols(httpProtocol))

}
