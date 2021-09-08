package lv.iljapavlovs.gatling.tutorials

import io.gatling.core.Predef._
import io.gatling.http.Predef._

// 1. SIMULATION EXTENDS FROM GATLING SIMULATION
// must always extend from the Simulation class of the Gatling package, to make a Gatling script
class BasicSimulationTest_Comments extends Simulation {

// 2. SIMULATION IS DIVIDED INTO 3 AREAS
//  1. HTTP Configuration - setup the HTTP configuration
//  2. Scenario Definition - where we define our user journey
//  3. Set Up the Simulation - describes how user populations will run


// 3. HTTP CONFIGURATION
    val httpProtocol = http
      .baseUrl("http://computer-database.gatling.io") // baseUrl - the root for all relative URLs and will be used for all requests of this configuration
      .header("Accept", "application/json") // header - setting header for all requests all of this configuration

// 4. SCENARIO DEFINITION
    val searchScenario = scenario("Search Scenario") // A scenario is a chain of requests and pauses
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


// 5. Set Up the Simulation -
//  * inject users
//  * attach http configuration declared above
    setUp(searchScenario.inject(atOnceUsers(1)).protocols(httpProtocol))

//  RAMP UP - mimic PROD like behavoir where users connect to application gradually
}
