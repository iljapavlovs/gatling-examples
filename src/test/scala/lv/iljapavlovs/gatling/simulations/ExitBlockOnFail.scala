package lv.iljapavlovs.gatling.simulations

import io.gatling.core.Predef.{Simulation, css, csv, exec, rampUsers, repeat, scenario, tryMax}
import io.gatling.core.feeder.BatchableFeederBuilder
import io.gatling.core.structure.{ChainBuilder, ScenarioBuilder}
import io.gatling.http.Predef.{http, status}
import io.gatling.http.protocol.HttpProtocolBuilder

import java.util.concurrent.ThreadLocalRandom
import scala.concurrent.duration._
import io.gatling.core.Predef._

import io.gatling.http.Predef._

//https://stackoverflow.com/questions/58361315/exitblockonfail-causes-my-failing-my-script-with-failed-to-execute-no-attribut
class ExitBlockOnFail extends Simulation {

  object Search {

    val feeder: BatchableFeederBuilder[String]#F = csv("search.csv").random


    def search: ChainBuilder = {
      exec(
        http("Home")
          .get("/")
      ).pause(1)
        .feed(feeder)
        .exec(
          http("Search")
            .get("/computers?f=${searchCriterion}")
            .check(css("a:contains('FAIL')", "href").saveAs("computerURL"))
        )
        .pause(1)
        .exec(
          http("Select")
            .get("${computerURL}")
            .check(status.is(200))
        )
        .pause(1)
    }


    val searchWithExitBlockOnFail: ChainBuilder =
      exitBlockOnFail {
        search
      }

    val searchWithoutExitBlockOnFail: ChainBuilder = search

  }

  val httpProtocol: HttpProtocolBuilder = http
    .baseUrl("http://computer-database.gatling.io")
    .acceptHeader("text/html,application/xhtml+xml,application/xml;q=0.9,*/*;q=0.8")
    .doNotTrackHeader("1")
    .acceptLanguageHeader("en-US,en;q=0.5")
    .acceptEncodingHeader("gzip, deflate")
    .userAgentHeader("Mozilla/5.0 (Macintosh; Intel Mac OS X 10.8; rv:16.0) Gecko/20100101 Firefox/16.0")

//  val users: ScenarioBuilder = scenario("Users").exec(Search.searchWithoutExitBlockOnFail)
//  CONTINUEs EXECUTION
//  > Select: Failed to build request: No attribute named 'computerU      3 (50.00%)
//RL' is defined
//> css((a:contains('FAIL'),Some(href))).find.exists, found nothin      3 (50.00%)
//g


  // DOES NOT CONTINUE EXECUTION
  val users: ScenarioBuilder = scenario("Users").exec(Search.searchWithExitBlockOnFail)

  setUp(
    users.inject(rampUsers(3).during(10.seconds)),
  ).protocols(httpProtocol)
}
