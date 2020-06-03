package simulations

import io.gatling.core.Predef._
import io.gatling.core.structure.{ChainBuilder, ScenarioBuilder}
import io.gatling.http.Predef._
import io.gatling.http.protocol.HttpProtocolBuilder

import scala.concurrent.duration._
import scala.language.postfixOps

class GatlingSpec extends Simulation {

  val httpConf: HttpProtocolBuilder = http.baseUrl("http://localhost:9000")

  val indexReq = repeat(500) {
    exec(
      http("Index").get("/users").check(status.is(200))
    )
  }

  val readClientsScenario = scenario("Clients").exec(indexReq).pause(1)

  setUp(
    // For reference, this hits 25% CPU on a 5820K with 32 GB, running both server and load test.
    // In general, you want to ramp up load slowly, and measure with a JVM that has been "warmed up":
    // https://groups.google.com/forum/#!topic/gatling/mD15aj-fyo4
    readClientsScenario.inject(rampUsers(2000).during(100 seconds)).protocols(httpConf)
  )
}
